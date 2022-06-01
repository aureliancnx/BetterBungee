package fr.aureliancnx.betterbungee.api.event.player;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class BetterPlayerReceiveMessageEvent extends Event {

    private final ProxiedPlayer player;

    @Getter @Setter
    private String              message;

    @Getter @Setter
    private boolean             canceled;

    public BetterPlayerReceiveMessageEvent(final ProxiedPlayer player, final String message) {
        this.player = player;
        this.message = message;
    }

}
