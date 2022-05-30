package fr.aureliancnx.betterbungee.api;

import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.api.proxy.IProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;
import java.util.UUID;

public interface IBetterBungeeAPI {

    /* PROXY */

    /**
     * Get a collection of current available proxies.
     * Proxy servers objects are meant to be used
     * for read-only purposes.
     *
     * Proxies considered as available are proxies online
     * and plugged into the network.
     *
     * @return all available proxies
     */
    Collection<IProxyServer> getAvailableProxies();

    /**
     * Get current online player count on a proxy server
     * @param proxyName proxy name (internal name)
     * @return number of players.
     * If proxy is not existant, -1 is returned.
     */
    long getPlayerCountOnProxy(String proxyName);

    /* PLAYERS */

    /**
     * Get list of all online players, all over the network.
     * Be aware of not using this function when you don't need it,
     * as it concatenates all online player lists online on
     * available proxies.
     * @return a collection of IBetterPlayer
     */
    Collection<IBetterPlayer> getPlayers();

    /**
     * Get count of current online players logged accross
     * the network server.
     * @return current player count (long)
     */
    long getPlayerCount();

    /**
     * Get an instance of IBetterPlayer from a ProxiedPlayer
     * (bungeecord implementation of player object).
     *
     * This instance is read-only, and therefore, cannot be
     * used to change player data.
     *
     * Passing a null proxiedPlayer would return an assert
     * exception, please check your input.
     *
     * If player is not found and/or offline, null is returned.
     *
     * @param proxiedPlayer bungeecord player
     * @return IBetterPlayer instance
     */
    IBetterPlayer getPlayer(ProxiedPlayer proxiedPlayer);

    /**
     * Get an instance of IBetterPlayer from player's unique id
     *
     * This instance is read-only, and therefore, cannot be
     * used to change player data.
     *
     * Passing a null UUID would return an assert
     * exception, please check your input.
     *
     * If player is not found and/or offline, null is returned.
     *
     * @param uuid player's UUID in Mojang implementation
     * @return IBetterPlayer instance
     */
    IBetterPlayer getPlayer(UUID uuid);

    /**
     * Get an instance of IBetterPlayer from player's username
     *
     * This instance is read-only, and therefore, cannot be
     * used to change player data.
     *
     * Passing a null username would return an assert
     * exception, please check your input.
     *
     * If player is not found and/or offline, null is returned.
     *
     * @param username player name
     * @return IBetterPlayer instance
     */
    IBetterPlayer getPlayer(String username);

    /**
     * Retrieves if a player is online or not over the
     * network by using its unique id.
     * @param uuid player's unique id
     * @return true if online, false otherwise.
     */
    boolean isPlayerOnline(UUID uuid);

    /**
     * Retrieves if a player is online or not over the
     * network by using its username.
     * @param username player's username
     * @return true if online, false otherwise.
     */
    boolean isPlayerOnline(String username);

    /**
     * Lookup player username from its unique id.
     * Beware that lookup player username is requiring
     * player to be online over the network, not necessarily onto this proxy.
     *
     * If player is not online or player's username cannot be retrieved
     * from the unique id, null is returned.
     *
     * @param uuid player's unique id
     * @return username, or null if not found
     */
    String getPlayerUsername(UUID uuid);

    /**
     * Lookup player's unique id from its username.
     * Beware that lookup player UUID is requiring
     * player to be online over the network, not necessarily onto this proxy.
     *
     * If player is not online or player's unique id cannot be retrieved
     * from the username, null is returned.
     *
     * @param username player's username
     * @return username, or null if not found
     */
    UUID getPlayerUniqueId(String username);

    /* SERVER */

    /**
     * Lookup the minecraft server where player is
     * connected to by using its ProxiedPlayer reference.
     *
     * Beware that, if player is not online or not found,
     * null is returned.
     *
     * Even if the player server's name is retrieved, if this one
     * is not registered in the current proxy server, null is returned.
     * You might want to get player server's name directly from
     * @see #getPlayer(ProxiedPlayer)
     *
     * @param proxiedPlayer online player
     * @return ServerInfo where the player is online
     */
    ServerInfo getServerFor(ProxiedPlayer proxiedPlayer);

    /**
     * Lookup the minecraft server where player is
     * connected to by using its unique id.
     *
     * Beware that, if player is not online or not found,
     * null is returned.
     *
     * Even if the player server's name is retrieved, if this one
     * is not registered in the current proxy server, null is returned.
     * You might want to get player server's name directly from
     * @see #getPlayer(UUID)
     *
     * @param uuid unique id
     * @return ServerInfo where the player is online
     */
    ServerInfo getServerFor(UUID uuid);

    /**
     * Lookup the minecraft server where player is
     * connected to by using its username.
     *
     * Beware that, if player is not online or not found,
     * null is returned.
     *
     * Even if the player server's name is retrieved, if this one
     * is not registered in the current proxy server, null is returned.
     * You might want to get player server's name directly from
     * @see #getPlayer(String)
     *
     * @param username player's username
     * @return ServerInfo where the player is online
     */
    ServerInfo getServerFor(String username);

    /**
     * Retrieves the number of players currently connected to
     * a minecraft server instance over the network by the server info
     * even if they are all in separate proxies in the current cluster.
     *
     * If the ServerInfo object is null, -1 is returned.
     * If nobody is connected to the requested server name, 0 is returned.
     *
     * @param server ServerInfo, the object that references the so-called
     *               minecraft instance in BungeeCord.
     * @return count of online players
     */
    long getPlayerCountOnServer(ServerInfo server);

    /**
     * Retrieves the number of players currently connected to
     * a minecraft server instance over the network, by using its server name,
     * even if they are all in separate proxies in the current cluster.
     *
     * If the server name cannot be retrieved, -1 is returned.
     * If nobody is connected to the requested server name, 0 is returned.
     *
     * @param serverName server name as it's registered in
     *                   the proxy server.
     * @return count of online players
     */
    long getPlayerCountOnServer(String serverName);

}
