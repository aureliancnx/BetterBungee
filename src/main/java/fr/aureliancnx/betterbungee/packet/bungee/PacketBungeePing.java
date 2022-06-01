package fr.aureliancnx.betterbungee.packet.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.aureliancnx.betterbungee.BetterBungeePlugin;
import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.api.event.bungee.BetterBungeePingEvent;
import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.manager.proxy.IBungeeManager;
import fr.aureliancnx.betterbungee.packet.IListenPacket;
import fr.aureliancnx.betterbungee.packet.ListenPacket;
import fr.aureliancnx.betterbungee.packet.Packet;
import fr.aureliancnx.betterbungee.packet.util.PacketReaderUtils;
import fr.aureliancnx.betterbungee.packet.util.PacketWriterUtils;
import fr.aureliancnx.betterbungee.rabbit.packet.RabbitPacketType;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Getter
public class PacketBungeePing extends ListenPacket {

    private static final String QUEUE_NAME = "betterbungee.proxy.keepalive";

    private String                              name;
    private int                                 slots;
    private ConcurrentMap<UUID, IBetterPlayer>  players;

    public PacketBungeePing() {
        super();
    }

    public PacketBungeePing(final IBungeeServer bungeeServer) {
        this.name = bungeeServer.getName();
        this.slots = bungeeServer.getSlots();
        this.players = bungeeServer.getPlayers();
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
        this.name = input.readUTF();
        this.players = new ConcurrentHashMap<>();
        this.slots = input.readInt();
        final int playerCount = input.readInt();

        if (playerCount < 0) {
            return;
        }
        for (int i = 0; i < playerCount; ++i) {
            final IBetterPlayer player = PacketReaderUtils.readPlayer(input);
            players.put(player.getUniqueId(), player);
        }
    }

    @Override
    public void onReceive() {
        final IBungeeManager bungeeManager = BetterBungeePlugin.getInstance().getBungeeManager();
        final IBungeeServer bungeeServer = bungeeManager.updateBungee(this);

        if (bungeeServer == null) {
            return;
        }
        final PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        pluginManager.callEvent(new BetterBungeePingEvent(bungeeServer));
    }

    @Override
    public byte[] toBytes() {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF(this.name);
        output.writeInt(this.slots);
        output.writeInt(this.players.size());
        players.values().forEach(player -> PacketWriterUtils.writePlayer(output, player));
        return output.toByteArray();
    }

}
