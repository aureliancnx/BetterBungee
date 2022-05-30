package fr.aureliancnx.betterbungee.packet.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.packet.Packet;
import fr.aureliancnx.betterbungee.rabbit.packet.RabbitPacketType;
import lombok.Getter;

@Getter
public class PacketBungeeStop extends Packet {

    private static final String QUEUE_NAME = "betterbungee.proxy.stop";

    private String                          bungeeName;

    public PacketBungeeStop() {
        super();
    }

    public PacketBungeeStop(final IBungeeServer proxyServer) {
        this.bungeeName = proxyServer.getName();
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
        this.bungeeName = input.readUTF();
    }

    @Override
    public byte[] toBytes() {
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF(this.bungeeName);
        return output.toByteArray();
    }

}
