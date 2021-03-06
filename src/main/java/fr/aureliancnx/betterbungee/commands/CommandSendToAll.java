package fr.aureliancnx.betterbungee.commands;

import fr.aureliancnx.betterbungee.packet.bungee.PacketBungeeSendToAll;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CommandSendToAll extends Command {

    public CommandSendToAll() {
        super("sendtoall", "betterbungee.sendtoall");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cUsage: /sendtoall <commande>");
            return;
        }
        final String fullCommandLine = String.join(" ", args);
        sender.sendMessage("§eEnvoi de la commande à tous les serveurs BungeeCord : §b" + fullCommandLine);
        new PacketBungeeSendToAll(fullCommandLine).send();
    }

}
