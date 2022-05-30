package fr.aureliancnx.betterbungee.rabbit;

import fr.aureliancnx.betterbungee.packet.Packet;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author aureliancnx
 */
@AllArgsConstructor
@Getter
public class RabbitPacket {

    private final String           queueName;
    private final RabbitPacketType type;
    private final byte[]           message;

    public RabbitPacket(final Packet packet) {
        this.queueName = packet.getQueueName();
        this.type = packet.getType();
        this.message = packet.toBytes();
    }

}
