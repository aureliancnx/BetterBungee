package fr.aureliancnx.betterbungee.packet.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.aureliancnx.betterbungee.BetterBungeePlugin;
import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.api.event.bungee.BetterBungeeSendToAllEvent;
import fr.aureliancnx.betterbungee.packet.ListenPacket;
import fr.aureliancnx.betterbungee.packet.Packet;
import fr.aureliancnx.betterbungee.rabbit.packet.RabbitPacketType;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.PluginManager;

@Getter
public class PacketBungeeSendToAll extends ListenPacket {

    private static final String QUEUE_NAME = "betterbungee.proxy.sendtoall";

    private String  command;

    public PacketBungeeSendToAll() {
        super();
    }

    public PacketBungeeSendToAll(final String command) {
        this.command = command;
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
        this.command = input.readUTF();
    }

    @Override
    public void onReceive() {
        final ProxyServer proxyServer = ProxyServer.getInstance();
        final CommandSender commandSender = proxyServer.getConsole();
        final PluginManager pluginManager = proxyServer.getPluginManager();
        final BetterBungeePlugin plugin = BetterBungeePlugin.getInstance();
        final IBungeeServer bungeeServer = plugin.getBungeeManager().getMy();

        final BetterBungeeSendToAllEvent event = new BetterBungeeSendToAllEvent(bungeeServer, command);
        ProxyServer.getInstance().getPluginManager().callEvent(event);
        if (!event.isCanceled()) {
            pluginManager.dispatchCommand(commandSender, event.getCommand());
            plugin.getLogger().info("Send to all: '" + event.getCommand() + "'");
        }
    }

    @Override
    public byte[] toBytes() {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF(command);
        return output.toByteArray();
    }

}
