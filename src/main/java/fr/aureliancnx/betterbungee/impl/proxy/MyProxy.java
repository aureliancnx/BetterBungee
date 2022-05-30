package fr.aureliancnx.betterbungee.impl.proxy;

import fr.aureliancnx.betterbungee.packet.proxy.PacketProxyPing;
import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;

@Getter
public class MyProxy extends ProxyServer {

    public MyProxy(final String proxyName, final int slotCount) {
        super(proxyName, slotCount, new ConcurrentHashMap<>());
    }

    public void ping() {
        new PacketProxyPing(this).send();
        this.lastPing = System.currentTimeMillis();
    }

    @Override
    public boolean isMy() {
        return true;
    }

}
