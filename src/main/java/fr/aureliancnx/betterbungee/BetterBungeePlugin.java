package fr.aureliancnx.betterbungee;

import fr.aureliancnx.betterbungee.config.BetterBungeeConfig;
import fr.aureliancnx.betterbungee.manager.proxy.IBungeeManager;
import fr.aureliancnx.betterbungee.rabbit.service.RabbitWorker;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

@Getter
public class BetterBungeePlugin extends Plugin {

    @Getter
    private static BetterBungeePlugin instance;

    private BetterBungeeConfig  config;
    private boolean             debugMode;
    private RabbitWorker        rabbit;

    private IBungeeManager      bungeeManager;

    @Override
    public void onLoad() {
        instance = this; // Set current instance
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}
