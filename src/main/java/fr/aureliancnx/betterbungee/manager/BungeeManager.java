package fr.aureliancnx.betterbungee.manager;

import fr.aureliancnx.betterbungee.BetterBungeePlugin;
import fr.aureliancnx.betterbungee.api.proxy.IBungeeServer;
import fr.aureliancnx.betterbungee.impl.proxy.MyProxy;
import fr.aureliancnx.betterbungee.packet.bungee.PacketBungeePing;
import net.md_5.bungee.api.ChatColor;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BungeeManager implements IBungeeManager {

    private final MyProxy                               my;
    private final ConcurrentMap<String, IBungeeServer>  bungeeByNames;

    public BungeeManager(final String localBungeeName, final int localSlotCount) {
        this.my = new MyProxy(localBungeeName, localSlotCount);
        this.bungeeByNames = new ConcurrentHashMap<>();
        this.bungeeByNames.put(proxyName, local);
    }

    public BungeeManager(final BetterBungeeConfig config) {
        this(ProxyUtils.lookupProxyName(config), config.getSlots());
    }

    @Override
    public IBungeeServer getProxy(final String name) {
        if (name.equals(this.my.getBungeeName()))
            return this.my;

        final IBungeeServer bungeeServer = bungeeByNames.get(name);
        if (bungeeServer == null)
            return null;
        return bungeeServer.isAlive() ? bungeeServer : null;
    }

    @Override
    public Collection<IBungeeServer> getProxies() {
        return getProxies(proxyServer -> true);
    }

    @Override
    public Iterator<Map.Entry<String, IBungeeServer>> iterator() {
        return bungeeByNames.entrySet().iterator();
    }

    @Override
    public Collection<IBungeeServer> getProxies(Predicate<IBungeeServer> predicate) {
        return bungeeByNames.values().stream()
                .filter(IBungeeServer::isAlive)
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public IBungeeServer add(final PacketProxyKeepAlive keepAlive) {
        final IBungeeServer server = new BungeeServer(keepAlive.getProxyName(), keepAlive.getSlots(),
                keepAlive.getPlayers());

        bungeeByNames.put(server.getProxyName(), server);
        BetterBungeePlugin.getInstance().getLogger().info("[BetterBungee] " + ChatColor.GREEN + "Registered running proxy: " + server.getProxyName());
        return server;
    }

    @Override
    public IBungeeServer remove(final String proxyName) {
        return bungeeByNames.remove(proxyName);
    }

    @Override
    public IBungeeServer update(final PacketBungeePing keepAlive) {
        final IBungeeServer bungee = getProxy(keepAlive.getProxyName());

        if (bungee == null) {
            return add(keepAlive);
        }
        bungee.update(keepAlive);
        return bungee;
    }

    @Override
    public MyProxy getMy() {
        return this.my;
    }

}
