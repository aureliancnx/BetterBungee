package fr.aureliancnx.betterbungee.schedulers;

import fr.aureliancnx.betterbungee.BetterBungeePlugin;
import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SchedulerBungeeRemoveOffline extends ABungeeScheduler {

    public SchedulerBungeeRemoveOffline(BetterBungeePlugin plugin) {
        super(plugin, 0L, 10L, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        final Iterator<Map.Entry<String, IBungeeServer>> bungeeIterator = plugin.getBungeeManager().iterator();

        while (bungeeIterator.hasNext()) {
            final Map.Entry<String, IBungeeServer> bungeeData = bungeeIterator.next();

            if (!bungeeData.getValue().isAlive()) {
                final String name = bungeeData.getKey();
                bungeeIterator.remove();
                plugin.getLogger().info("[BetterBungee] Unregistered bungee: " + name);
            }
        }
    }

}
