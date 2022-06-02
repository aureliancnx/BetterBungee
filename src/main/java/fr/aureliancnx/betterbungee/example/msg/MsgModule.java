package fr.aureliancnx.betterbungee.example.msg;

import fr.aureliancnx.betterbungee.BetterBungeePlugin;
import fr.aureliancnx.betterbungee.api.IBetterBungeeAPI;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.PluginManager;

public class MsgModule {

    private final IBetterBungeeAPI api;

    public MsgModule(final IBetterBungeeAPI api) {
        this.api = api;
    }

    public void register() {
        final PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();

        pluginManager.registerCommand(BetterBungeePlugin.getInstance(), new MsgCommand(api));
    }

}
