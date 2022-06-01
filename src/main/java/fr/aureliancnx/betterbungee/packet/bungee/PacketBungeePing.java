package fr.aureliancnx.betterbungee.packet.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.packet.Packet;
import fr.aureliancnx.betterbungee.packet.util.PacketReaderUtils;
import fr.aureliancnx.betterbungee.packet.util.PacketWriterUtils;
import fr.aureliancnx.betterbungee.rabbit.packet.RabbitPacketType;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Getter
public class PacketBungeePing extends Packet {

    private static final String QUEUE_NAME = "betterbungee.proxy.keepalive";

    private String                              name;
    private int                                 slots;
    private ConcurrentMap<UUID, IBetterPlayer>  players;

    public PacketBungeePing() {
        super();
    }

    public PacketBungeePing(final IBungeeServer proxyServer) {
        this.name = proxyServer.getName();
        this.slots = proxyServer.getSlots();
        this.players = proxyServer.getPlayers();
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
    public byte[] toBytes() {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF(this.name);
        output.writeInt(this.slots);
        output.writeInt(this.players.size());
        players.values().forEach(player -> PacketWriterUtils.writePlayer(output, player));
        return output.toByteArray();
    }

}
