package fr.aureliancnx.betterbungee.impl.player;

import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.impl.proxy.MyBungee;
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
    private final String  bungeeName;
    @Setter
    private String        serverName;

    public BetterPlayer(final MyBungee proxy, final ProxiedPlayer player) {
        this(player.getUniqueId(), player.getName(), player.getAddress().getAddress().getHostAddress(),
                proxy.getName(), player.getServer() != null ? player.getServer().getInfo().getName() : "");
    }

    public BetterPlayer(final UUID uniqueId, final String username, final String ip,
                      final String bungeeName, final String serverName) {
        this.uniqueId = uniqueId;
        this.username = username;
        this.ip = ip;
        this.bungeeName = bungeeName;
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