package fr.aureliancnx.betterbungee.manager.proxy;

import fr.aureliancnx.betterbungee.BetterBungeePlugin;
import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.api.event.bungee.BetterBungeePingEvent;
import fr.aureliancnx.betterbungee.config.BetterBungeeConfig;
import fr.aureliancnx.betterbungee.impl.proxy.BungeeServer;
import fr.aureliancnx.betterbungee.impl.proxy.MyBungee;
import fr.aureliancnx.betterbungee.packet.bungee.PacketBungeePing;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BungeeManager implements IBungeeManager {

    private final MyBungee my;
    private final ConcurrentMap<String, IBungeeServer>  bungeeByNames;

    public BungeeManager(final String localBungeeName, final int localSlotCount) {
        this.my = new MyBungee(localBungeeName, localSlotCount);
        this.bungeeByNames = new ConcurrentHashMap<>();
        this.bungeeByNames.put(localBungeeName, this.my);
    }

    public BungeeManager(final BetterBungeeConfig config) {
        this(config.getName(), config.getLocalSlots());
    }

    @Override
    public IBungeeServer getBungee(final String name) {
        if (name.equals(this.my.getName()))
            return this.my;

        final IBungeeServer bungeeServer = bungeeByNames.get(name);
        if (bungeeServer == null)
            return null;
        return bungeeServer.isAlive() ? bungeeServer : null;
    }

    @Override
    public Collection<IBungeeServer> getBungees() {
        return getBungees(bungeeServer -> true);
    }

    @Override
    public Iterator<Map.Entry<String, IBungeeServer>> iterator() {
        return bungeeByNames.entrySet().iterator();
    }

    @Override
    public Collection<IBungeeServer> getBungees(Predicate<IBungeeServer> predicate) {
        return bungeeByNames.values().stream()
                .filter(IBungeeServer::isAlive)
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public IBungeeServer addBungee(final PacketBungeePing ping) {
        final BetterBungeePlugin plugin = BetterBungeePlugin.getInstance();
        final IBungeeServer server = new BungeeServer(ping.getName(), ping.getSlots(), ping.getPlayers());

        bungeeByNames.put(server.getName(), server);
        plugin.getLogger().info("[BetterBungee] Registered running proxy: " + server.getName());

        final PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        pluginManager.callEvent(new BetterBungeePingEvent(server));
        return server;
    }

    @Override
    public IBungeeServer removeBungee(final String proxyName) {
        return bungeeByNames.remove(proxyName);
    }

    @Override
    public IBungeeServer updateBungee(final PacketBungeePing ping) {
        final IBungeeServer bungee = getBungee(ping.getName());

        if (bungee == null)
            return addBungee(ping);
        bungee.updateBungee(ping);
        return bungee;
    }

    @Override
    public MyBungee getMy() {
        return this.my;
    }

}
