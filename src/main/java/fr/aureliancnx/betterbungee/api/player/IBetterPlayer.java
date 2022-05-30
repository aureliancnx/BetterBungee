package fr.aureliancnx.betterbungee.api.player;

import net.md_5.bungee.api.chat.BaseComponent;

import java.util.UUID;

public interface IBetterPlayer {

    /**
     * Get player's unique id
     * @return UUID object
     */
    UUID getUniqueId();

    /**
     * Get player's username
     * @return string
     */
    String getUsername();

    /**
     * Get player's IP address (IPv4)
     * @return string
     */
    String getIp();

    /**
     * Get current proxy where player is
     * logged on
     * @return string
     */
    String getProxyName();

    /**
     * Get current minecraft server instance
     * where player is logged on
     * @return string
     */
    String getServerName();

    /**
     * Update minecraft server name. It should only
     * be used internally.
     * @param serverName new server name
     */
    void setServerName(final String serverName);

    /**
     * Send message to player across the network
     * @param message a BaseComponent
     */
    void sendMessage(final BaseComponent message);

    /**
     * Send message to player across the network
     * @param message a string
     */
    void sendMessage(final String message);


}
