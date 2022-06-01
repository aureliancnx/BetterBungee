package fr.aureliancnx.betterbungee.listeners;

import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.impl.proxy.MyBungee;
import fr.aureliancnx.betterbungee.manager.proxy.IBungeeManager;
import fr.aureliancnx.betterbungee.packet.player.PacketPlayerUpdate;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class ServerSwitchListener implements Listener {

    private final IBungeeManager    bungeeManager;

    public ServerSwitchListener(final IBungeeManager bungeeManager) {
        this.bungeeManager = bungeeManager;
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onServerConnect(final ServerConnectedEvent event) {
        final ProxiedPlayer proxiedPlayer = event.getPlayer();

        if (proxiedPlayer == null)
            return;

        final MyBungee my = bungeeManager.getMy();
        final IBetterPlayer betterPlayer = my.getPlayer(proxiedPlayer.getUniqueId());

        if (betterPlayer == null)
            return;

        final Server server = event.getServer();
        if (server == null || server.getInfo() == null) {
            betterPlayer.setServerName("");
        }else{
            betterPlayer.setServerName(server.getInfo().getName());
        }
        new PacketPlayerUpdate(betterPlayer).send();
    }

}
