package fr.aureliancnx.betterbungee.listeners;

import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.manager.player.IPlayerManager;
import fr.aureliancnx.betterbungee.manager.proxy.IBungeeManager;
import fr.aureliancnx.betterbungee.packet.player.PacketPlayerDisconnected;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class PlayerLogoutListener implements Listener {

    private final IBungeeManager    bungeeManager;
    private final IPlayerManager    playerManager;

    public PlayerLogoutListener(final IBungeeManager bungeeManager, final IPlayerManager playerManager) {
        this.bungeeManager = bungeeManager;
        this.playerManager = playerManager;
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerLogout(final PlayerDisconnectEvent event) {
        final ProxiedPlayer player = event.getPlayer();
        final IBetterPlayer betterPlayer = playerManager.getPlayer(player);

        if (betterPlayer == null) {
            return;
        }
        final IBungeeServer bungee = bungeeManager.getBungee(betterPlayer.getBungeeName());

        // Remove player in local memory
        if (bungee != null) {
            bungee.removePlayer(betterPlayer);
        }
        // Broadcast player quit packet to all proxies
        new PacketPlayerDisconnected(betterPlayer).send();
    }

}
