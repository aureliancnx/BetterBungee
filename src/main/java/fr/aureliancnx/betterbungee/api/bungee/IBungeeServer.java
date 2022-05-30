package fr.aureliancnx.betterbungee.api.bungee;

import fr.aureliancnx.betterbungee.packet.bungee.PacketBungeePing;

public interface IBungeeServer extends IPlayerBungee {

    /**
     * Get bungee server name
     * @return string
     */
    String getName();

    /**
     * Checks if the proxy server is alive (currently running)
     * and if it's still considered as synchronized
     * @return true if alive, false otherwise
     */
    boolean isAlive();

    /**
     * Get last time we received a ping packet
     * from this proxy server.
     * @return long
     */
    long getLastPing();

    /**
     * Updates current IBungeeServer instance with data
     * sent by using the ping packet
     * @param ping PacketBungeePing
     */
    void updateBungee(final PacketBungeePing ping);

    /**
     * Checks if the proxy server is the current one
     * @return true if it's the current one, false otherwise
     */
    boolean isMy();

}
