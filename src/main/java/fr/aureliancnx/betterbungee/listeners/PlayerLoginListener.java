package fr.aureliancnx.betterbungee.listeners;

import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.config.BetterBungeeConfig;
import fr.aureliancnx.betterbungee.impl.player.BetterPlayer;
import fr.aureliancnx.betterbungee.impl.proxy.MyBungee;
import fr.aureliancnx.betterbungee.manager.player.IPlayerManager;
import fr.aureliancnx.betterbungee.manager.proxy.IBungeeManager;
import fr.aureliancnx.betterbungee.packet.player.PacketPlayerConnected;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class PlayerLoginListener implements Listener {

    private final IBungeeManager        bungeeManager;
    private final IPlayerManager        playerManager;
    private final BetterBungeeConfig    config;

    public PlayerLoginListener(final IBungeeManager bungeeManager, final IPlayerManager playerManager,
                               final BetterBungeeConfig config) {
        this.bungeeManager = bungeeManager;
        this.playerManager = playerManager;
        this.config = config;
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerLogin(final LoginEvent event) {
        final PendingConnection pendingConnection = event.getConnection();

        final IBetterPlayer player = playerManager.getPlayer(pendingConnection.getUniqueId());

        if (player != null) {
            event.setCancelled(true);
            event.setCancelReason("§cVous êtes déjà connecté au serveur.");
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerPostLogin(final PostLoginEvent event) {
        final ProxiedPlayer player = event.getPlayer();

        if (player == null)
            return;

        final MyBungee localBungee = bungeeManager.getMy();
        final IBetterPlayer betterPlayer = new BetterPlayer(localBungee, player);

        localBungee.addPlayer(betterPlayer);
        new PacketPlayerConnected(betterPlayer).send();
    }

}
