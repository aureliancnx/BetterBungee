package fr.aureliancnx.betterbungee;

import fr.aureliancnx.betterbungee.api.BetterBungeeAPI;
import fr.aureliancnx.betterbungee.api.IBetterBungeeAPI;
import fr.aureliancnx.betterbungee.commands.CommandCProxy;
import fr.aureliancnx.betterbungee.commands.CommandProxy;
import fr.aureliancnx.betterbungee.commands.CommandSSend;
import fr.aureliancnx.betterbungee.commands.CommandSendToAll;
import fr.aureliancnx.betterbungee.config.BetterBungeeConfig;
import fr.aureliancnx.betterbungee.example.msg.MsgModule;
import fr.aureliancnx.betterbungee.listeners.PlayerLoginListener;
import fr.aureliancnx.betterbungee.listeners.PlayerLogoutListener;
import fr.aureliancnx.betterbungee.listeners.ProxyPingListener;
import fr.aureliancnx.betterbungee.listeners.ServerSwitchListener;
import fr.aureliancnx.betterbungee.manager.player.IPlayerManager;
import fr.aureliancnx.betterbungee.manager.player.PlayerManager;
import fr.aureliancnx.betterbungee.manager.proxy.BungeeManager;
import fr.aureliancnx.betterbungee.manager.proxy.IBungeeManager;
import fr.aureliancnx.betterbungee.packet.bungee.PacketBungeePing;
import fr.aureliancnx.betterbungee.packet.bungee.PacketBungeeSendToAll;
import fr.aureliancnx.betterbungee.packet.bungee.PacketBungeeStop;
import fr.aureliancnx.betterbungee.packet.player.*;
import fr.aureliancnx.betterbungee.rabbit.service.RabbitService;
import fr.aureliancnx.betterbungee.rabbit.service.RabbitWorker;
import fr.aureliancnx.betterbungee.schedulers.ABungeeScheduler;
import fr.aureliancnx.betterbungee.schedulers.SchedulerBungeePing;
import fr.aureliancnx.betterbungee.schedulers.SchedulerBungeeRemoveOffline;
import fr.aureliancnx.betterbungee.util.ConfigUtils;
import fr.aureliancnx.betterbungee.util.ProxyUtils;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BetterBungeePlugin extends Plugin {

    @Getter
    private static BetterBungeePlugin instance;

    private IBetterBungeeAPI    api;

    private BetterBungeeConfig  config;
    private boolean             debugMode;
    private RabbitWorker        rabbit;

    private IPlayerManager      playerManager;
    private IBungeeManager      bungeeManager;

    private List<ABungeeScheduler> schedulers;

    @Override
    public void onLoad() {
        instance = this; // Set current instance
    }

    @Override
    public void onEnable() {
        ProxyUtils.disableSecurityManager();

        rabbit = new RabbitWorker(this);
        config = loadConfiguration();
        if (config == null) {
            getProxy().stop();
            return;
        }
        if (!rabbit.loadRabbitMQ(config.getRabbit())) {
            getLogger().severe("Unable to load RabbitMQ. Shutdown..");
            getProxy().stop();
            return;
        }
        // Load managers
        bungeeManager = new BungeeManager(config);
        playerManager = new PlayerManager(bungeeManager);
        api = new BetterBungeeAPI(bungeeManager, playerManager);
        // Load everything
        registerListeners();
        registerCommands();
        registerSchedulers();
        registerModules();
    }

    @Override
    public void onDisable() {
        if (bungeeManager != null) {
            new PacketBungeeStop(bungeeManager.getMy()).send();
        }
        if (schedulers != null) {
            schedulers.forEach(ABungeeScheduler::stop);
        }
    }

    private void registerListeners() {
        final PluginManager pluginManager = getProxy().getPluginManager();
        final RabbitService service = rabbit.getService();

        // Listen to our useful BungeeCord events
        pluginManager.registerListener(this, new PlayerLoginListener(bungeeManager, playerManager, config));
        pluginManager.registerListener(this, new PlayerLogoutListener(bungeeManager, playerManager));
        pluginManager.registerListener(this, new ProxyPingListener(playerManager));
        pluginManager.registerListener(this, new ServerSwitchListener(bungeeManager));

        // Listen to RMQ queues
        service.registerListener(new PacketBungeePing());
        service.registerListener(new PacketBungeeSendToAll());
        service.registerListener(new PacketBungeeStop());
        service.registerListener(new PacketPlayerConnected());
        service.registerListener(new PacketPlayerDisconnected());
        service.registerListener(new PacketPlayerSendMessage());
        service.registerListener(new PacketPlayerSendServer());
        service.registerListener(new PacketPlayerUpdate());
    }

    private void registerCommands() {
        final PluginManager pluginManager = getProxy().getPluginManager();

        pluginManager.registerCommand(this, new CommandCProxy(this));
        pluginManager.registerCommand(this, new CommandProxy(this));
        pluginManager.registerCommand(this, new CommandSendToAll());
        pluginManager.registerCommand(this, new CommandSSend(this));
    }

    private void registerModules() {
        final MsgModule module = new MsgModule(api);
        module.register();
    }

    private void registerSchedulers() {
        schedulers = new ArrayList<>();

        schedulers.add(new SchedulerBungeePing(this));
        schedulers.add(new SchedulerBungeeRemoveOffline(this));
    }

    private BetterBungeeConfig loadConfiguration() {
        try {
            return (BetterBungeeConfig) ConfigUtils.load(this, "config.json", BetterBungeeConfig.class);
        }catch (Exception error) {
            getLogger().severe("Unable to read configuration file.");
            error.printStackTrace();
            return null;
        }
    }

}
