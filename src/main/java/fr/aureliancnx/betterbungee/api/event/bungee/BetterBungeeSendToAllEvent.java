package fr.aureliancnx.betterbungee.api.event.bungee;

import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.api.event.BetterBungeeEvent;
import lombok.Getter;
import lombok.Setter;

/**
 * Bungeecord related event, broadcast all over the network.
 */
@Getter
@Setter
public class BetterBungeeSendToAllEvent extends BetterBungeeEvent {

    private String  command;
    private boolean canceled;

    public BetterBungeeSendToAllEvent(final IBungeeServer bungee, String command) {
        super(bungee);
        this.command = command;
    }

}
