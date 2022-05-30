package fr.aureliancnx.betterbungee.packet;

import com.google.common.io.ByteArrayDataInput;
import fr.aureliancnx.betterbungee.rabbit.RabbitPacketType;

public interface IPacket {

    String getQueueName();

    RabbitPacketType getType();

    void fromBytes(final ByteArrayDataInput input);

    byte[] toBytes();

    void send();

    void send(boolean sync);

}
