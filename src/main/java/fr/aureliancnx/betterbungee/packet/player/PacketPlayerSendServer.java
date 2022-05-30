package fr.aureliancnx.betterbungee.packet.player;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.packet.Packet;
import fr.aureliancnx.betterbungee.packet.util.PacketReaderUtils;
import fr.aureliancnx.betterbungee.packet.util.PacketWriterUtils;
import fr.aureliancnx.betterbungee.rabbit.packet.RabbitPacketType;
import lombok.Getter;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.UUID;

@Getter
public class PacketPlayerSendServer extends Packet {

    private static final String QUEUE_NAME = "betterbungee.player.send";

    private UUID    playerUuid;
    private String  serverName;

    public PacketPlayerSendServer() {
        super();
    }

    public PacketPlayerSendServer(final IBetterPlayer player, final ServerInfo serverInfo) {
        this(player.getUniqueId(), serverInfo.getName());
    }

    public PacketPlayerSendServer(final IBetterPlayer player, final String serverName) {
        this(player.getUniqueId(), serverName);
    }

    public PacketPlayerSendServer(final UUID uuid, final String serverName) {
        this.playerUuid = uuid;
        this.serverName = serverName;
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
    public void fromBytes(ByteArrayDataInput input) {
        this.playerUuid = PacketReaderUtils.readUUID(input);
        this.serverName = input.readUTF();
    }

    @Override
    public byte[] toBytes() {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        PacketWriterUtils.writeUUID(output, this.playerUuid);
        output.writeUTF(this.serverName);
        return output.toByteArray();
    }

}
