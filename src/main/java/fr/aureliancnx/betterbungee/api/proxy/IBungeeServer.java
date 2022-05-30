package fr.aureliancnx.betterbungee.api.proxy;

import fr.aureliancnx.betterbungee.packet.proxy.PacketProxyKeepAlive;

public interface IProxyServer extends IPlayerProxy {

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
     * Get last time we received a KeepAlive packet
     * from this proxy server.
     * @return long
     */
    long getLastKeepAlive();

    /**
     * Updates current IProxyServer instance with data
     * sent by using the keepalive packet
     * @param keepAlive PacketProxyKeepAlive
     */
    void update(final PacketProxyKeepAlive keepAlive);

    /**
     * Checks if the proxy server is the local one
     * @return true if local, false otherwise
     */
    boolean isLocal();

}
