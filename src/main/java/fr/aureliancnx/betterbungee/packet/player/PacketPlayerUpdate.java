package fr.aureliancnx.betterbungee.packet.player;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.packet.Packet;
import fr.aureliancnx.betterbungee.packet.util.PacketReaderUtils;
import fr.aureliancnx.betterbungee.packet.util.PacketWriterUtils;
import fr.aureliancnx.betterbungee.rabbit.RabbitPacketType;
import lombok.Getter;

@Getter
public class PacketPlayerUpdate extends Packet {

    private static final String QUEUE_NAME = "betterbungee.player.update";

    private IBetterPlayer player;

    public PacketPlayerUpdate() {
        super();
    }

    public PacketPlayerUpdate(final IBetterPlayer player) {
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
    public void fromBytes(ByteArrayDataInput input) {
        this.player = PacketReaderUtils.readPlayer(input);
    }

    @Override
    public byte[] toBytes() {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();

        PacketWriterUtils.writePlayer(out, player);
        return out.toByteArray();
    }

}
