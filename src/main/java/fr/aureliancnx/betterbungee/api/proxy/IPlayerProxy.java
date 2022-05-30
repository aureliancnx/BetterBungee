package fr.aureliancnx.betterbungee.api.proxy;

import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public interface IPlayerProxy {

    /**
     * Get map of players connected to
     * the proxy server
     *
     * @return map of UUID and IBetterPlayer
     */
    ConcurrentMap<UUID, IBetterPlayer> getPlayers();

    /**
     * Add a player in the proxy server
     *
     * @param betterPlayer player object
     */
    void addPlayer(final IBetterPlayer betterPlayer);

    /**
     * Removes a player from the proxy server
     *
     * @param betterPlayer player object
     */
    void removePlayer(final IBetterPlayer betterPlayer);

    /**
     * Removes a player from the proxy server
     *
     * @param uuid player's unique id
     */
    void removePlayer(final UUID uuid);

    /**
     * Get player instance from another IBetterPlayer instance
     *
     * @param otherPlayer other player instance
     * @return a IBetterPlayer object
     */
    IBetterPlayer getPlayer(final IBetterPlayer otherPlayer);

    /**
     * Get player instance by its unique id
     *
     * @param uuid player's unique id
     * @return IBetterPlayer object
     */
    IBetterPlayer getPlayer(final UUID uuid);

    /**
     * Get player instance by its username
     *
     * @param username player's username
     * @return IBetterPlayer object
     */
    IBetterPlayer getPlayer(final String username);

    /**
     * Update current player instance by using
     * data from another player object
     *
     * @param player another IBetterPlayer object
     */
    void updatePlayer(IBetterPlayer player);

    /**
     * Retrieves number of players currently
     * connected to the proxy server
     *
     * @return long
     */
    long getPlayerCount();

    /**
     * Get slot count
     *
     * @return int
     */
    int getSlotCount();

}