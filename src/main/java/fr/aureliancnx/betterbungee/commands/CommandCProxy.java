package fr.aureliancnx.betterbungee.commands;

import fr.aureliancnx.betterbungee.BetterBungeePlugin;
import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import fr.aureliancnx.betterbungee.manager.player.IPlayerManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CommandCProxy extends Command {

    private final BetterBungeePlugin plugin;

    public CommandCProxy(final BetterBungeePlugin plugin) {
        super("cproxy", "betterbungee.cproxy");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final IPlayerManager manager = plugin.getPlayerManager();

        if (args.length != 1) {
            sender.sendMessage("§cUsage: /cproxy <pseudo>");
            return;
        }
        final IBetterPlayer player = manager.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage("§cLe joueur " + args[0] + " ne semble pas être connecté sur le réseau.");
            return;
        }
        sender.sendMessage("§b" + player.getUsername() + " §eest connecté sur le serveur BungeeCord §b" +
                player.getBungeeName() + "§e, sur le serveur Minecraft §b" + player.getServerName());
    }

}
