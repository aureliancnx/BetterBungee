package fr.aureliancnx.betterbungee.packet.player;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.aureliancnx.betterbungee.BetterBungeePlugin;
import fr.aureliancnx.betterbungee.api.event.player.BetterPlayerConnectedEvent;
import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.api.proxy.IBungeeServer;
import fr.aureliancnx.betterbungee.impl.player.BetterPlayer;
import fr.aureliancnx.betterbungee.packet.ListenPacket;
import fr.aureliancnx.betterbungee.packet.util.PacketReaderUtils;
import fr.aureliancnx.betterbungee.packet.util.PacketWriterUtils;
import fr.aureliancnx.betterbungee.rabbit.packet.RabbitPacketType;
import lombok.Getter;
import net.md_5.bungee.api.plugin.PluginManager;

@Getter
public class PacketPlayerConnected extends ListenPacket {

    private static final String QUEUE_NAME = "betterbungee.player.join";

    private IBetterPlayer player;

    public PacketPlayerConnected() {
        super();
    }

    public PacketPlayerConnected(final BetterPlayer player) {
        this.player = player;
    }

    @Override
    public String getQueueName() {
        return QUEUE_NAME;
    }

    @Override
    public RabbitPacketType getType() {
        return RabbitPacketType.PUBLISH;
    }

    @Override
    public void fromBytes(final ByteArrayDataInput input) {
        this.player = PacketReaderUtils.readPlayer(input);
    }

    @Override
    public void onReceive() {
        if (player == null) {
            return;
        }
        // TODO: create bungee manager
        final BungeeManager bungeeManager = BetterBungeePlugin.getInstance().getBungeeManager();
        // Try to retrieve bungeecord server with name
        IBungeeServer bungeeServer = bungeeManager.getBungee(player.getBungeeName());
        if (bungeeServer == null) {
            BetterBungeePlugin.getInstance().getLogger().severe(player.getUsername() + " tried to join an unknown proxy: " + player.getBungeeName());
            return;
        }
        // Add player to current bungeecord server
        bungeeServer.addPlayer(player);
        // Call custom event
        final PluginManager pluginManager = BetterBungeePlugin.getInstance().getProxy().getPluginManager();
        pluginManager.callEvent(new BetterPlayerConnectedEvent(bungeeServer, player));
    }

    @Override
    public byte[] toBytes() {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();

        PacketWriterUtils.writePlayer(out, player);
        return out.toByteArray();
    }

}
