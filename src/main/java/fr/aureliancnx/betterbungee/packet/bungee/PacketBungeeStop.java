package fr.aureliancnx.betterbungee.packet.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.aureliancnx.betterbungee.BetterBungeePlugin;
import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.api.event.bungee.BetterBungeeStoppedEvent;
import fr.aureliancnx.betterbungee.manager.proxy.IBungeeManager;
import fr.aureliancnx.betterbungee.packet.ListenPacket;
import fr.aureliancnx.betterbungee.rabbit.packet.RabbitPacketType;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;

@Getter
public class PacketBungeeStop extends ListenPacket {

    private static final String QUEUE_NAME = "betterbungee.proxy.stop";

    private String                          bungeeName;

    public PacketBungeeStop() {
        super();
    }

    public PacketBungeeStop(final IBungeeServer bungeeServer) {
        this.bungeeName = bungeeServer.getName();
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
    public void onReceive() {
        final BetterBungeePlugin plugin = BetterBungeePlugin.getInstance();
        final IBungeeManager bungeeManager = plugin.getBungeeManager();
        final IBungeeServer bungeeServer = bungeeManager.removeBungee(bungeeName);

        if (bungeeServer == null) {
            // Already remove? if so, we don't do anything
            return;
        }
        plugin.getLogger().info("Proxy " + bungeeName + " stopped gracefully.");
        // Trigger event
        final BetterBungeeStoppedEvent event = new BetterBungeeStoppedEvent(bungeeServer);
        ProxyServer.getInstance().getPluginManager().callEvent(event);
    }

    @Override
    public byte[] toBytes() {
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF(this.bungeeName);
        return output.toByteArray();
    }

}
