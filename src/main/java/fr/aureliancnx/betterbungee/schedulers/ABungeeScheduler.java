package fr.aureliancnx.betterbungee.schedulers;

import fr.aureliancnx.betterbungee.BetterBungeePlugin;

import java.util.concurrent.TimeUnit;

public abstract class ABungeeScheduler implements Runnable {

    protected final BetterBungeePlugin plugin;

    public ABungeeScheduler(final BetterBungeePlugin plugin, final long delay, final long repeat, final TimeUnit timeUnit) {
        this.plugin = plugin;
        plugin.getProxy().getScheduler().schedule(plugin, this, delay, repeat, timeUnit);
    }

}
