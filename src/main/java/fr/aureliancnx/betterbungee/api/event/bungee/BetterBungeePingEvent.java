package fr.aureliancnx.betterbungee.api.event.bungee;

import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.api.event.BetterBungeeEvent;

public class BetterBungeePingEvent extends BetterBungeeEvent {

    public BetterBungeePingEvent(final IBungeeServer bungee) {
        super(bungee);
    }

}
