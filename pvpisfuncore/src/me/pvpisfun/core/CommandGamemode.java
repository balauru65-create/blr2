package me.pvpisfun.core;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGamemode implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Această comandă poate fi executată doar de un jucător.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("hubcore.gamemode")) {
            player.sendMessage(ChatColor.RED + "Nu ai permisiunea să folosești această comandă.");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.YELLOW + "Folosire: /gm <0|1|2|3>");
            return true;
        }

        switch (args[0]) {
            case "0":
                player.setGameMode(GameMode.SURVIVAL);
                break;
            case "1":
                player.setGameMode(GameMode.CREATIVE);
                break;
            case "2":
                player.setGameMode(GameMode.ADVENTURE);
                break;
            case "3":
                player.setGameMode(GameMode.SPECTATOR);
                break;
            default:
                player.sendMessage(ChatColor.RED + "Mod necunoscut! Folosește: 0, 1, 2, sau 3.");
                return true;
        }

        player.sendMessage(ChatColor.GREEN + "GameMode schimbat în: " + ChatColor.WHITE + player.getGameMode().name());
        return true;
    }
}
