package fr.aureliancnx.betterbungee.listeners;

import fr.aureliancnx.betterbungee.manager.player.IPlayerManager;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingListener implements Listener {

    private final IPlayerManager playerManager;
    private final long           startedAt;

    public ProxyPingListener(final IPlayerManager playerManager) {
        this.playerManager = playerManager;
        this.startedAt = System.currentTimeMillis();
    }

    @EventHandler
    public void onProxyPing(final ProxyPingEvent event) {
        final ServerPing ping = event.getResponse();

        if (ping == null) {
            return;
        }
        final long now = System.currentTimeMillis();
        final ServerPing.Players players = ping.getPlayers();

        // We don't want to make proxy replies in the first 15 seconds
        // because we may not have all data when proxy is starting up
        if (now - startedAt < 15_000L) {
            players.setOnline(0);
            players.setMax(0);
            return;
        }

        final int playerCount = Math.toIntExact(playerManager.getPlayerCount(500L));
        final int slotCount = Math.toIntExact(playerManager.getSlots(10000L));
        players.setOnline(playerCount);
        players.setMax(slotCount);
    }
    
}
