package fr.aureliancnx.betterbungee.manager.player;

import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.manager.proxy.IBungeeManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PlayerManager implements IPlayerManager {

    private final IBungeeManager    bungeeManager;
    private final AtomicLong        players;
    private final AtomicLong        slots;
    private final AtomicLong        lastCountRefresh;
    private final AtomicLong        lastSlotRefresh;

    public PlayerManager(final IBungeeManager bungeeManager) {
        this.bungeeManager = bungeeManager;
        this.lastCountRefresh = new AtomicLong(0);
        this.lastSlotRefresh = new AtomicLong(0);
        this.players = new AtomicLong(0);
        this.slots = new AtomicLong(0);
    }

    @Override
    public Collection<IBetterPlayer> getPlayers(Predicate<IBetterPlayer> predicate) {
        return bungeeManager.getBungees().stream()
                .map(bungee -> bungee.getPlayers().values())
                .flatMap(Collection::stream)
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<IBetterPlayer> getPlayers() {
        return getPlayers(player -> true);
    }

    @Override
    public IBetterPlayer getPlayer(final ProxiedPlayer player) {
        assert player != null;
        return getPlayer(player.getUniqueId());
    }

    @Override
    public IBetterPlayer getPlayer(final UUID uuid) {
        assert uuid != null;
        return bungeeManager.getBungees().stream()
                .map(bungee -> bungee.getPlayer(uuid))
                .filter(Objects::nonNull).findFirst()
                .orElse(null);
    }

    @Override
    public IBetterPlayer getPlayer(final String username) {
        assert username != null;
        return bungeeManager.getBungees().stream()
                .map(bungee -> bungee.getPlayer(username))
                .filter(Objects::nonNull).findFirst()
                .orElse(null);
    }

    @Override
    public void updatePlayerCount() {
        assert bungeeManager != null;

        long newCount = bungeeManager.getBungees().stream()
                .mapToLong(IBungeeServer::getPlayerCount)
                .sum();
        players.set(newCount);
    }

    @Override
    public void updateSlots() {
        assert bungeeManager != null;

        long newCount = bungeeManager.getBungees().stream()
                .mapToLong(IBungeeServer::getSlots)
                .sum();
        slots.set(newCount);
    }

    @Override
    public long getPlayerCount() {
        return getPlayerCount(-1L);
    }

    @Override
    public long getPlayerCount(long refreshMilliseconds) {
        if (refreshMilliseconds > 0L) {
            final long now = System.currentTimeMillis();

            if (now - lastCountRefresh.get() > refreshMilliseconds) {
                updatePlayerCount();
            }
        }
        return players.get();
    }

    @Override
    public long getSlots() {
        return getSlots(-1L);
    }

    @Override
    public long getSlots(long refreshMilliseconds) {
        if (refreshMilliseconds > 0L) {
            final long now = System.currentTimeMillis();

            if (now - lastSlotRefresh.get() > refreshMilliseconds) {
                updateSlots();
            }
        }
        return slots.get();
    }

}
