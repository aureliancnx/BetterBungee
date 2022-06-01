package fr.aureliancnx.betterbungee.api.event.bungee;

import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.api.event.BetterBungeeEvent;

/**
 * Bungeecord related event, broadcast all over the network.
 *
 * Fired when a bungeecord instance sent a stop signal
 */
public class BetterBungeeStoppedEvent extends BetterBungeeEvent {

    public BetterBungeeStoppedEvent(final IBungeeServer bungee) {
        super(bungee);
    }

}
