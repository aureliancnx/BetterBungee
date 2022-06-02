package fr.aureliancnx.betterbungee.commands;

import fr.aureliancnx.betterbungee.BetterBungeePlugin;
import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.manager.player.IPlayerManager;
import fr.aureliancnx.betterbungee.packet.player.PacketPlayerSendServer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CommandSSend extends Command {

    private final BetterBungeePlugin plugin;

    public CommandSSend(final BetterBungeePlugin plugin) {
        super("ssend", "betterbungee.ssend");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final IPlayerManager manager = plugin.getPlayerManager();

        if (args.length != 2) {
            sender.sendMessage("§cUsage: /ssend <pseudo> <serveur>");
            return;
        }
        final IBetterPlayer player = manager.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage("§cLe joueur " + args[0] + " ne semble pas être connecté sur le réseau.");
            return;
        }
        final String serverName = args[1];
        final ProxyServer proxyServer = ProxyServer.getInstance();
        final ServerInfo server = proxyServer.getServerInfo(serverName);
        if (server == null) {
            sender.sendMessage("§cLe serveur Minecraft '" + serverName + "' n'est pas trouvé.");
            return;
        }
        final IBungeeServer bungee = plugin.getBungeeManager().getBungee(player.getBungeeName());
        if (bungee == null) {
            sender.sendMessage("§cServeur BungeeCord '" + player.getBungeeName() + "' non trouvé.");
            return;
        }
        boolean success = false;
        if (!bungee.isMy()) {
            new PacketPlayerSendServer(player, server).send();
            success = true;
        } else {
            final ProxiedPlayer proxiedPlayer = proxyServer.getPlayer(player.getUsername());
            if (proxiedPlayer == null) {
                sender.sendMessage("§cLe joueur " + player.getUsername() + " semble déconnecté.");
            } else {
                proxiedPlayer.connect(server);
                success = true;
            }
        }
        if (success) {
            sender.sendMessage("§b" + player.getUsername() + " §ea été envoyé sur le serveur Minecraft §b" + server.getName());
        }
    }

}
