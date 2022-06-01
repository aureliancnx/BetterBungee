package fr.aureliancnx.betterbungee.api.event.player;

import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.api.event.BetterPlayerEvent;
import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;

public class BetterPlayerConnectedEvent extends BetterPlayerEvent {

    public BetterPlayerConnectedEvent(final IBungeeServer bungee, final IBetterPlayer player) {
        super(bungee, player);
    }

}
