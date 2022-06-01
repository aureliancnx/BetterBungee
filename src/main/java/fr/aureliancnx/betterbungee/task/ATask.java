package fr.aureliancnx.betterbungee.task;

import fr.aureliancnx.betterbungee.BetterBungeePlugin;

import java.util.concurrent.TimeUnit;

public abstract class ATask implements Runnable {

    protected final BetterBungeePlugin plugin;

    public ATask(final BetterBungeePlugin plugin, final long delay, final long repeat, final TimeUnit timeUnit) {
        this.plugin = plugin;
        plugin.getProxy().getScheduler().schedule(plugin, this, delay, repeat, timeUnit);
    }

}
