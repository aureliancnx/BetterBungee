package fr.aureliancnx.betterbungee.api.event.bungee;

import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.api.event.BetterBungeeEvent;

public class BetterBungeeStoppedEvent extends BetterBungeeEvent {

    public BetterBungeeStoppedEvent(final IBungeeServer bungee) {
        super(bungee);
    }

}
