package fr.aureliancnx.betterbungee.manager.player;

import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Predicate;

public interface IPlayerManager {

    /**
     * Get all better players online over the network that matches
     * a specific predicate.
     * @param predicate predicate
     * @return collection of unique IBetterPlayer objects
     */
    Collection<IBetterPlayer> getPlayers(Predicate<IBetterPlayer> predicate);

    /**
     * Get all better players online over the network.
     * @return collection of unique IBetterPlayer objects
     */
    Collection<IBetterPlayer> getPlayers();

    /**
     * Get online player from his attached proxied player instance. Returns null if the
     * player is not online over the network.
     * @param player proxied player
     * @return IBetterPlayer or null
     */
    IBetterPlayer getPlayer(final ProxiedPlayer player);

    /**
     * Get online player from its unique id. Returns null if the player is not online
     * over the network.
     * @param uuid uuid player's unique id
     * @return IBetterPlayer or null
     */
    IBetterPlayer getPlayer(final UUID uuid);

    /**
     * Get online player from username. Returns null if the player is not online
     * over the network.
     * @param username player username
     * @return IBetterPlayer or null
     */
    IBetterPlayer getPlayer(final String username);

    /**
     * Update cached player count over the network
     *
     * It iterates over all working proxies in the same environment, so please
     * don't use it when it's not necessary to know the current exact count of players
     */
    void updatePlayerCount();

    /**
     * Update slot count over the network
     */
    void updateSlots();

    /**
     * Get cached player count. It doesn't refresh player count.
     *
     * @return new player count
     */
    long getPlayerCount();

    /**
     * Get player count with an offset period of data refresh.
     *
     * For example, if you set the value to 500 milliseconds and the method
     * is being called every 120ms, player count will be refreshed every ~4.16 call
     * to this method (500/120)
     * @param refreshMilliseconds refresh rate in milliseconds
     * @return current player count (cached or live, depending if it has been refreshed)
     */
    long getPlayerCount(long refreshMilliseconds);

    /**
     * Get cached slot count. It doesn't refresh slot count.
     *
     * @return new slot count
     */
    long getSlots();

    /**
     * Get slot count with an offset period of data refresh.
     *
     * For example, if you set the value to 500 milliseconds and the method
     * is being called every 120ms, slot count will be refreshed every ~4.16 call
     * to this method (500/120)
     * @param refreshMilliseconds refresh rate in milliseconds
     * @return current slot count (cached or live, depending if it has been refreshed)
     */
    long getSlots(long refreshMilliseconds);

}
