package fr.aureliancnx.betterbungee.packet.player;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.packet.ListenPacket;
import fr.aureliancnx.betterbungee.packet.Packet;
import fr.aureliancnx.betterbungee.packet.util.PacketReaderUtils;
import fr.aureliancnx.betterbungee.packet.util.PacketWriterUtils;
import fr.aureliancnx.betterbungee.rabbit.packet.RabbitPacketType;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

@Getter
public class PacketPlayerSendMessage extends ListenPacket {

    private static final String QUEUE_NAME = "betterbungee.player.message";

    private UUID       uuid;
    private String     message;

    public PacketPlayerSendMessage() {
        super();
    }

    public PacketPlayerSendMessage(final IBetterPlayer player, final String message) {
        this.uuid = player.getUniqueId();
        this.message = message;
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
        this.uuid = PacketReaderUtils.readUUID(input);
        this.message = input.readUTF();
    }

    @Override
    public void onReceive() {
        final ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
        if (player == null) {
            return;
        }
        player.sendMessage(message);
    }

    @Override
    public byte[] toBytes() {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();

        PacketWriterUtils.writeUUID(out, this.uuid);
        out.writeUTF(this.message);
        return out.toByteArray();
    }

}
