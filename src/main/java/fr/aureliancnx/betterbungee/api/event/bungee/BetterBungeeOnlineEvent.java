package fr.aureliancnx.betterbungee.api.event.bungee;

import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.api.event.BetterBungeeEvent;
import fr.aureliancnx.betterbungee.api.event.BetterPlayerEvent;
import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;

public class BetterBungeeOnlineEvent extends BetterBungeeEvent {

    public BetterBungeeOnlineEvent(final IBungeeServer bungee) {
        super(bungee);
    }

}
