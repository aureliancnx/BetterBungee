package fr.aureliancnx.betterbungee.api.event;

import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.api.proxy.IBungeeServer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Event;

@AllArgsConstructor
@Getter
public abstract class BetterPlayerEvent extends Event {

    private final IBungeeServer bungee;
    private final IBetterPlayer player;

}
