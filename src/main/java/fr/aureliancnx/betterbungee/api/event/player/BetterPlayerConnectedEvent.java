package fr.aureliancnx.betterbungee.api.event.player;

import fr.aureliancnx.betterbungee.api.event.BetterPlayerEvent;
import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.api.proxy.IBungeeServer;

public class BetterPlayerConnectedEvent extends BetterPlayerEvent {

    public BetterPlayerConnectedEvent(final IBungeeServer bungee, final IBetterPlayer player) {
        super(bungee, player);
    }

}
