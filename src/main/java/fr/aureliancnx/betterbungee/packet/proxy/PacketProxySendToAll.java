package fr.aureliancnx.betterbungee.packet.proxy;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.aureliancnx.betterbungee.packet.Packet;
import fr.aureliancnx.betterbungee.rabbit.RabbitPacketType;
import lombok.Getter;

@Getter
public class PacketProxySendToAll extends Packet {

    private static final String QUEUE_NAME = "betterbungee.proxy.sendtoall";

    private String  command;

    public PacketProxySendToAll() {
        super();
    }

    public PacketProxySendToAll(final String command) {
        this.command = command;
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
        this.command = input.readUTF();
    }

    @Override
    public byte[] toBytes() {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF(command);
        return output.toByteArray();
    }

}
