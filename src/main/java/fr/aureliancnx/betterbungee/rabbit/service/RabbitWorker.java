package fr.aureliancnx.betterbungee.rabbit.service;

import fr.aureliancnx.betterbungee.BetterBungeePlugin;
import fr.aureliancnx.betterbungee.rabbit.RabbitCredentials;
import fr.aureliancnx.betterbungee.util.ConfigUtils;
import lombok.Getter;

@Getter
public class RabbitWorker {

    private final BetterBungeePlugin    plugin;

    private RabbitService               service;

    public RabbitWorker(final BetterBungeePlugin plugin) {
        this.plugin = plugin;
    }

    public void registerListeners() {

    }

    public boolean loadRabbitMQ(final RabbitCredentials credentials) {
        this.service = new RabbitService(this.plugin, credentials);
        return this.service.isConnected();
    }

}
