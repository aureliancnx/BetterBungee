package fr.aureliancnx.betterbungee.api.event.bungee;

import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.api.event.BetterBungeeEvent;

public class BetterBungeeOnlineEvent extends BetterBungeeEvent {

    public BetterBungeeOnlineEvent(final IBungeeServer bungee) {
        super(bungee);
    }

}
