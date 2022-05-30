package fr.aureliancnx.betterbungee.packet.util;

import com.google.common.io.ByteArrayDataOutput;
import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;

import java.util.UUID;

public class PacketWriterUtils {

    public static void writeUUID(final ByteArrayDataOutput output, final UUID uuid) {
        if (output == null || uuid == null) {
            return;
        }
        final long leastSignificantBits = uuid.getLeastSignificantBits();
        final long mostSignificantBits = uuid.getMostSignificantBits();
        output.writeLong(leastSignificantBits);
        output.writeLong(mostSignificantBits);
    }

    public static void writePlayer(final ByteArrayDataOutput output, final IBetterPlayer player) {
        if (output == null || player == null) {
            return;
        }
        writeUUID(output, player.getUniqueId());
        output.writeUTF(player.getUsername());
        output.writeUTF(player.getIp());
        output.writeUTF(player.getBungeeName());
        output.writeUTF(player.getServerName());
    }

}
