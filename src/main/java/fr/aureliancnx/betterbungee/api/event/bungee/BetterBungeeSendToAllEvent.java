package fr.aureliancnx.betterbungee.api.event.bungee;

import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.api.event.BetterBungeeEvent;

/**
 * Bungeecord related event, broadcast all over the network.
 */
public class BetterBungeeSendToAllEvent extends BetterBungeeEvent {

    public BetterBungeeSendToAllEvent(final IBungeeServer bungee) {
        super(bungee);
    }

}
