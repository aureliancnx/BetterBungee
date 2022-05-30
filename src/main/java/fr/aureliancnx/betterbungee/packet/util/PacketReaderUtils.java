package fr.aureliancnx.betterbungee.packet.util;

import com.google.common.io.ByteArrayDataInput;
import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.impl.player.BetterPlayer;

import java.util.UUID;

public class PacketReaderUtils {

    public static UUID readUUID(final ByteArrayDataInput input) {
        if (input == null) {
            return null;
        }
        final long leastSignificantBits = input.readLong();
        final long mostSignificantBits = input.readLong();
        return new UUID(mostSignificantBits, leastSignificantBits);
    }

    public static IBetterPlayer readPlayer(final ByteArrayDataInput input) {
        if (input == null) {
            return null;
        }
        final UUID uuid = readUUID(input);
        final String username = input.readUTF();
        final String ip = input.readUTF();
        final String proxyName = input.readUTF();
        final String serverName = input.readUTF();
        return new BetterPlayer(uuid, username, ip, proxyName, serverName);
    }

}
