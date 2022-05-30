package fr.aureliancnx.betterbungee.packet;

import com.google.common.io.ByteArrayDataInput;
import fr.aureliancnx.betterbungee.BetterBungeePlugin;
import fr.aureliancnx.betterbungee.rabbit.packet.RabbitPacketType;
import fr.aureliancnx.betterbungee.rabbit.service.RabbitService;

public abstract class Packet implements IPacket {

    public abstract String getQueueName();

    public abstract RabbitPacketType getType();

    public abstract void fromBytes(final ByteArrayDataInput input);

    public abstract byte[] toBytes();

    public void send() {
        send(false);
    }

    public void send(final boolean sync) {
        final RabbitService rabbitService = BetterBungeePlugin.getInstance().getRabbit().getService();

        if (BetterBungeePlugin.getInstance().isDebugMode()) {
            BetterBungeePlugin.getInstance().getLogger().info("[DEBUG] Sent packet: " + getQueueName()
                    + " (" + getType() + ") -> size " + toBytes().length);
        }
        if (sync) {
            rabbitService.sendBlockingPacket(rabbitService.convertPacket(this));
            return;
        }
        rabbitService.sendPacket(this);
    }

}
