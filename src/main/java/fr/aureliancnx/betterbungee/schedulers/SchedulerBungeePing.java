package fr.aureliancnx.betterbungee.schedulers;

import fr.aureliancnx.betterbungee.BetterBungeePlugin;

import java.util.concurrent.TimeUnit;

public class SchedulerBungeePing extends ABungeeScheduler {

    public SchedulerBungeePing(BetterBungeePlugin plugin) {
        super(plugin, 0L, 1L, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        plugin.getBungeeManager().getMy().ping();
    }

}
