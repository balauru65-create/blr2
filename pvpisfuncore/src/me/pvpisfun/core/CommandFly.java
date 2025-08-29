package me.pvpisfun.core;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFly implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Această comandă poate fi executată doar de un jucător.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("hubcore.fly")) {
            player.sendMessage(ChatColor.RED + "Nu ai permisiunea să folosești această comandă.");
            return true;
        }

        boolean fly = !player.getAllowFlight();
        player.setAllowFlight(fly);
        player.sendMessage(ChatColor.GREEN + "Zborul a fost " + (fly ? "activat." : "dezactivat."));
        return true;
    }
}
