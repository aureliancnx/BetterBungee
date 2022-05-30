package fr.aureliancnx.betterbungee.packet.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.aureliancnx.betterbungee.api.proxy.IBungeeServer;
import fr.aureliancnx.betterbungee.packet.Packet;
import fr.aureliancnx.betterbungee.rabbit.packet.RabbitPacketType;
import lombok.Getter;

@Getter
public class PacketBungeeStop extends Packet {

    private static final String QUEUE_NAME = "betterbungee.proxy.stop";

    private String                          proxyName;

    public PacketBungeeStop() {
        super();
    }

    public PacketBungeeStop(final IBungeeServer proxyServer) {
        this.proxyName = proxyServer.getProxyName();
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
        this.proxyName = input.readUTF();
    }

    @Override
    public byte[] toBytes() {
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF(proxyName);
        return output.toByteArray();
    }

}
