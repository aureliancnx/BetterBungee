package fr.aureliancnx.betterbungee.api.event;

import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Event;

@AllArgsConstructor
@Getter
public abstract class BetterBungeeEvent extends Event {

    private final IBungeeServer bungee;

}
