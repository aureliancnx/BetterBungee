package fr.aureliancnx.betterbungee.api;

import fr.aureliancnx.betterbungee.BetterBungeePlugin;
import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.manager.player.IPlayerManager;
import fr.aureliancnx.betterbungee.manager.proxy.IBungeeManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.Collection;
import java.util.UUID;

public class BetterBungeeAPI implements IBetterBungeeAPI {

    private final IBungeeManager bungeeManager;
    private final IPlayerManager playerManager;

    public BetterBungeeAPI(final IBungeeManager bungeeManager, final IPlayerManager playerManager) {
        this.bungeeManager = bungeeManager;
        this.playerManager = playerManager;
    }

    @Override
    public Collection<IBungeeServer> getAvailableBungees() {
        return bungeeManager.getBungees();
    }

    @Override
    public long getPlayerCountOnBungee(String bungeeName) {
        final IBungeeServer bungeeServer = bungeeManager.getBungee(bungeeName);

        return bungeeServer != null ? bungeeServer.getPlayerCount() : -1L;
    }

    @Override
    public Collection<IBetterPlayer> getPlayers() {
        return playerManager.getPlayers();
    }

    @Override
    public long getPlayerCount() {
        return bungeeManager.getBungees().stream()
                .mapToLong(IBungeeServer::getPlayerCount)
                .sum();
    }

    @Override
    public IBetterPlayer getPlayer(ProxiedPlayer proxiedPlayer) {
        return playerManager.getPlayer(proxiedPlayer);
    }

    @Override
    public IBetterPlayer getPlayer(UUID uuid) {
        return playerManager.getPlayer(uuid);
    }

    @Override
    public IBetterPlayer getPlayer(String username) {
        return playerManager.getPlayer(username);
    }

    @Override
    public boolean isPlayerOnline(UUID uuid) {
        return getPlayer(uuid) != null;
    }

    @Override
    public boolean isPlayerOnline(String username) {
        if (username == null) {
            return false;
        }
        return getPlayer(username) != null;
    }

    @Override
    public String getPlayerUsername(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        final IBetterPlayer player = getPlayer(uuid);
        return player != null ? player.getUsername() : null;
    }

    @Override
    public UUID getPlayerUniqueId(String username) {
        if (username == null) {
            return null;
        }
        final IBetterPlayer player = getPlayer(username);
        return player != null ? player.getUniqueId() : null;
    }

    @Override
    public ServerInfo getServerFor(ProxiedPlayer proxiedPlayer) {
        if (proxiedPlayer == null || proxiedPlayer.getServer() == null) {
            return null;
        }
        return proxiedPlayer.getServer().getInfo(); // Wtf is this?
    }

    @Override
    public ServerInfo getServerFor(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        final IBetterPlayer player = getPlayer(uuid);
        if (player == null) {
            return null;
        }
        return ProxyServer.getInstance().getServerInfo(player.getServerName());
    }

    @Override
    public ServerInfo getServerFor(String username) {
        if (username == null) {
            return null;
        }
        final IBetterPlayer player = getPlayer(username);
        if (player == null) {
            return null;
        }
        return ProxyServer.getInstance().getServerInfo(player.getServerName());
    }

    @Override
    public long getPlayerCountOnServer(ServerInfo server) {
        if (server == null) {
            return -1L;
        }
        return getPlayerCountOnServer(server.getName());
    }

    @Override
    public long getPlayerCountOnServer(String serverName) {
        if (serverName == null) {
            return -1L;
        }
        return playerManager.getPlayers(player -> player.getServerName().equals(serverName)).size();
    }

    public static IBetterBungeeAPI getApi() {
        final ProxyServer proxyServer = ProxyServer.getInstance();
        if (proxyServer == null || proxyServer.getPluginManager() == null) {
            return null;
        }

        final PluginManager pluginManager = proxyServer.getPluginManager();
        final Plugin plugin = pluginManager.getPlugin("BetterBungee");

        if (plugin == null) {
            throw new IllegalStateException("Better Bungee not found!");
        }
        if (!(plugin instanceof BetterBungeePlugin)) {
            throw new IllegalStateException("Better Bungee is not an instance of the right class");
        }
        final BetterBungeePlugin betterBungee = (BetterBungeePlugin) plugin;
        if (betterBungee.getApi() == null) {
            throw new IllegalStateException("API not initialized");
        }
        return betterBungee.getApi();
    }

}
