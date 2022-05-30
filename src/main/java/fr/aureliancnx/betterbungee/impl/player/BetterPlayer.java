package fr.aureliancnx.betterbungee.impl.player;

import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.impl.proxy.MyProxy;
import fr.aureliancnx.betterbungee.packet.player.PacketPlayerSendMessage;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

@Getter
public class BetterPlayer implements IBetterPlayer {

    private final UUID    uniqueId;
    private final String  username;
    private final String  ip;
    private final String  proxyName;
    @Setter
    private String        serverName;

    public BetterPlayer(final MyProxy proxy, final ProxiedPlayer player) {
        this(player.getUniqueId(), player.getName(), player.getAddress().getAddress().getHostAddress(),
                proxy.getProxyName(), player.getServer() != null ? player.getServer().getInfo().getName() : "");
    }

    public BetterPlayer(final UUID uniqueId, final String username, final String ip,
                      final String proxyName, final String serverName) {
        this.uniqueId = uniqueId;
        this.username = username;
        this.ip = ip;
        this.proxyName = proxyName;
        this.serverName = serverName;
    }

    public void sendMessage(final BaseComponent message) {
        assert message != null;
        final ProxiedPlayer proxiedPlayer = ProxyServer.getInstance().getPlayer(uniqueId);

        if (proxiedPlayer != null) {
            proxiedPlayer.sendMessage(message);
            return;
        }
        final PacketPlayerSendMessage packet = new PacketPlayerSendMessage(this, message.toPlainText());
        packet.send();
    }

    public void sendMessage(final String message) {
        assert message != null;
        final ProxiedPlayer proxiedPlayer = ProxyServer.getInstance().getPlayer(uniqueId);

        if (proxiedPlayer != null) {
            proxiedPlayer.sendMessage(message);
            return;
        }
        final PacketPlayerSendMessage packet = new PacketPlayerSendMessage(this, message);
        packet.send();
    }

}