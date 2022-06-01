package fr.aureliancnx.betterbungee.api.event.bungee;

import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.api.event.BetterBungeeEvent;

/**
 * Bungeecord related event, broadcast all over the network.
 *
 * Fired when the local bungeecord instance receives a ping from another bungeecord instance.
 */
public class BetterBungeePingEvent extends BetterBungeeEvent {

    public BetterBungeePingEvent(final IBungeeServer bungee) {
        super(bungee);
    }

}
