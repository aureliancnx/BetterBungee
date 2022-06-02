package fr.aureliancnx.betterbungee.example.msg;

import fr.aureliancnx.betterbungee.api.IBetterBungeeAPI;
import fr.aureliancnx.betterbungee.api.player.IBetterPlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;

public class MsgCommand extends Command {

    private final IBetterBungeeAPI api;

    public MsgCommand(IBetterBungeeAPI api) {
        super("msg");
        this.api = api;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§cUsage: /msg <joueur> <message");
            return;
        }
        final String otherPlayerName = args[0];
        final IBetterPlayer otherPlayer = api.getPlayer(otherPlayerName);
        if (otherPlayer == null) {
            sender.sendMessage("§cLe joueur '" + otherPlayerName + "' n'est pas connecté.");
            return;
        }

        final String[] messageContent = Arrays.copyOfRange(args, 1, args.length);
        final String message = String.join(" ", messageContent);
        final String mpFormat = "§e[MP] §b" + sender.getName() + " §f-> §b" + otherPlayer.getUsername() + " §e: §f" + message;
        otherPlayer.sendMessage(mpFormat);
        sender.sendMessage(mpFormat);
    }

}
