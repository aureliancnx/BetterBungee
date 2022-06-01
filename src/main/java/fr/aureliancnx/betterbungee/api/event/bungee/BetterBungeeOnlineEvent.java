package fr.aureliancnx.betterbungee.api.event.bungee;

import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.api.event.BetterBungeeEvent;

/**
 * Bungeecord related event, broadcast all over the network.
 *
 * Fired when the local bungeecord instance discovers another bungeecord instance.
 */
public class BetterBungeeOnlineEvent extends BetterBungeeEvent {

    public BetterBungeeOnlineEvent(final IBungeeServer bungee) {
        super(bungee);
    }

}
