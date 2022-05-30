package fr.aureliancnx.betterbungee.packet;

import com.google.common.io.ByteArrayDataInput;
import fr.aureliancnx.betterbungee.rabbit.packet.RabbitPacketType;

public interface IListenPacket {

    String getQueueName();

    RabbitPacketType getType();

    void fromBytes(final ByteArrayDataInput input);

    void onReceive();

    byte[] toBytes();

    void send();

    void send(boolean sync);

}
