package fr.aureliancnx.betterbungee.api.proxy;

import fr.aureliancnx.betterbungee.packet.proxy.PacketProxyPing;

public interface IBungeeServer extends IPlayerBungee {

    /**
     * Get proxy server name
     * @return string
     */
    String getProxyName();

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
     * Updates current IProxyServer instance with data
     * sent by using the keepalive packet
     * @param keepAlive PacketProxyKeepAlive
     */
    void update(final PacketProxyPing keepAlive);

    /**
     * Checks if the proxy server is the current one
     * @return true if it's the current one, false otherwise
     */
    boolean isMy();

}
