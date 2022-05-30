package fr.aureliancnx.betterbungee.packet.player;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.impl.player.BetterPlayer;
import fr.aureliancnx.betterbungee.packet.Packet;
import fr.aureliancnx.betterbungee.packet.util.PacketReaderUtils;
import fr.aureliancnx.betterbungee.packet.util.PacketWriterUtils;
import fr.aureliancnx.betterbungee.rabbit.RabbitPacketType;
import lombok.Getter;

@Getter
public class PacketPlayerJoin extends Packet {

    private static final String QUEUE_NAME = "betterbungee.player.join";

    private IBetterPlayer player;

    public PacketPlayerJoin() {
        super();
    }

    public PacketPlayerJoin(final BetterPlayer player) {
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
