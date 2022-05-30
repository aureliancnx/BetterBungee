package fr.aureliancnx.betterbungee.packet.player;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.aureliancnx.betterbungee.BetterBungeePlugin;
import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.api.event.player.BetterPlayerConnectedEvent;
import fr.aureliancnx.betterbungee.api.event.player.BetterPlayerDisconnectedEvent;
import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.manager.IBungeeManager;
import fr.aureliancnx.betterbungee.packet.ListenPacket;
import fr.aureliancnx.betterbungee.packet.Packet;
import fr.aureliancnx.betterbungee.packet.util.PacketReaderUtils;
import fr.aureliancnx.betterbungee.packet.util.PacketWriterUtils;
import fr.aureliancnx.betterbungee.rabbit.packet.RabbitPacketType;
import lombok.Getter;
import net.md_5.bungee.api.plugin.PluginManager;

@Getter
public class PacketPlayerDisconnected extends ListenPacket {

    private static final String QUEUE_NAME = "betterbungee.player.quit";

    private IBetterPlayer player;

    public PacketPlayerDisconnected() {
        super();
    }

    public PacketPlayerDisconnected(final IBetterPlayer player) {
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
        final IBungeeManager bungeeManager = BetterBungeePlugin.getInstance().getBungeeManager();
        IBungeeServer bungeeServer = bungeeManager.getBungee(player.getBungeeName());
        if (bungeeServer == null) {
            return;
        }
        bungeeServer.removePlayer(player);
        // Call custom event
        final PluginManager pluginManager = BetterBungeePlugin.getInstance().getProxy().getPluginManager();
        pluginManager.callEvent(new BetterPlayerDisconnectedEvent(bungeeServer, player));

    }

    @Override
    public byte[] toBytes() {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();

        PacketWriterUtils.writePlayer(out, player);
        return out.toByteArray();
    }

}
