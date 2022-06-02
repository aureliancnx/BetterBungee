package fr.aureliancnx.betterbungee.commands;

import fr.aureliancnx.betterbungee.BetterBungeePlugin;
import fr.aureliancnx.betterbungee.api.bungee.IBungeeServer;
import fr.aureliancnx.betterbungee.manager.proxy.IBungeeManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CommandProxy extends Command {

    private final BetterBungeePlugin plugin;

    public CommandProxy(final BetterBungeePlugin plugin) {
        super("proxy", "betterbungee.proxy");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final IBungeeManager manager = plugin.getBungeeManager();
        int playerCount = 0;
        int slotCount = 0;

        sender.sendMessage("§eListe des proxies (§b" + manager.getBungees().size() + "§e) :");
        for (final IBungeeServer server : manager.getBungees()) {
            sender.sendMessage("§e" + server.getName() + " : §d" + server.getPlayerCount() + "/" + server.getSlots() + " §ejoueurs");
            playerCount += server.getPlayerCount();
            slotCount += server.getSlots();
        }
        sender.sendMessage("§e---- Total : §d" + playerCount + "/" + slotCount + " §ejoueurs ----");
    }

}
