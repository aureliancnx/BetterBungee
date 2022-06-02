package fr.aureliancnx.betterbungee.schedulers;

import fr.aureliancnx.betterbungee.BetterBungeePlugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.concurrent.TimeUnit;

public abstract class ABungeeScheduler implements Runnable {

    protected final BetterBungeePlugin plugin;

    private final ScheduledTask        task;

    public ABungeeScheduler(final BetterBungeePlugin plugin, final long delay, final long repeat, final TimeUnit timeUnit) {
        this.plugin = plugin;
        this.task = plugin.getProxy().getScheduler().schedule(plugin, this, delay, repeat, timeUnit);
    }

    public void stop() {
        plugin.getProxy().getScheduler().cancel(task);
    }

}
