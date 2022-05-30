package fr.aureliancnx.betterbungee.rabbit;

import fr.aureliancnx.betterbungee.BetterBungeePlugin;
import fr.aureliancnx.betterbungee.util.ConfigUtils;
import lombok.Getter;

@Getter
public class RabbitWorker {

    private final BetterBungeePlugin    plugin;

    private RabbitCredentials           credentials;
    private RabbitService               service;

    public RabbitWorker(final BetterBungeePlugin plugin) {
        this.plugin = plugin;
    }

    public void registerListeners() {

    }

    private boolean loadConfigurationFile() {
        try {
            this.credentials = (RabbitCredentials) ConfigUtils.load(this.plugin, "rabbit.json",
                    RabbitCredentials.class);
        }catch(Exception error) {
            error.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean loadRabbitMQ() {
        if (!this.loadConfigurationFile()) {
            return false;
        }
        this.service = new RabbitService(this.plugin, this.credentials);
        return this.service.isConnected();
    }

}
