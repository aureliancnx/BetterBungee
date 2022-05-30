package fr.aureliancnx.betterbungee.impl.proxy;

import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.packet.bungee.PacketBungeePing;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Getter
public class BungeeServer implements IBungeeServer {

    private final String                            name;

    private int                                     slots;
    long                                            lastPing;
    private ConcurrentMap<String, IBetterPlayer>    playerNames;
    private ConcurrentMap<UUID, IBetterPlayer>      players;

    public BungeeServer(final String name, final int slots,
                        final ConcurrentMap<UUID, IBetterPlayer> players) {
        this.name = name;
        this.slots = slots;
        this.players = players;
        this.lastPing = System.currentTimeMillis();
        this.playerNames = new ConcurrentHashMap<>(players.entrySet().stream().collect(Collectors.toMap(
                e -> e.getValue().getUsername().toLowerCase(),
                Map.Entry::getValue
        )));
    }

    @Override
    public void addPlayer(final IBetterPlayer betterPlayer) {
        assert betterPlayer != null;
        if (players.containsKey(betterPlayer.getUniqueId())) {
            return;
        }
        players.put(betterPlayer.getUniqueId(), betterPlayer);
    }

    @Override
    public void removePlayer(final IBetterPlayer betterPlayer) {
        assert betterPlayer != null;
        removePlayer(betterPlayer.getUniqueId());
    }

    @Override
    public void removePlayer(final UUID uuid) {
        assert uuid != null;

        final IBetterPlayer player = players.remove(uuid);
        if (player != null) {
            playerNames.remove(player.getUsername());
        }
    }

    @Override
    public IBetterPlayer getPlayer(final IBetterPlayer otherPlayer) {
        assert otherPlayer != null;
        return getPlayer(otherPlayer.getUniqueId());
    }

    @Override
    public IBetterPlayer getPlayer(final UUID uuid) {
        assert uuid != null;
        return players.get(uuid);
    }

    @Override
    public IBetterPlayer getPlayer(final String username) {
        assert username != null;
        return playerNames.get(username.toLowerCase());
    }

    @Override
    public void updatePlayer(IBetterPlayer player) {
        assert player != null;
        final IBetterPlayer currentPlayer = getPlayer(player);

        if (currentPlayer == null) {
            return;
        }
        currentPlayer.setServerName(player.getServerName());
    }

    @Override
    public boolean isAlive() {
        final long now = System.currentTimeMillis();

        return now - getLastPing() < 60_000L;
    }

    @Override
    public int getSlotCount() {
        return this.slots;
    }

    @Override
    public void updateBungee(final PacketBungeePing proxy) {
        this.players = proxy.getPlayers();
        this.slots = proxy.getSlots();
        this.lastPing = System.currentTimeMillis();
        this.playerNames = new ConcurrentHashMap<>(players.entrySet().stream().collect(Collectors.toMap(
                e -> e.getValue().getUsername().toLowerCase(),
                Map.Entry::getValue
        )));
    }

    @Override
    public boolean isMy() {
        return false;
    }

    @Override
    public long getPlayerCount() {
        return players.size();
    }

}
