package me.pvpisfun.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class CommandReloadCore implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("hubcore.reload")) {
            sender.sendMessage(ChatColor.RED + "Nu ai permisiunea să folosești această comandă.");
            return true;
        }

        PvPisFUNCore.getInstance().reloadConfig();
        RankManager.loadConfig(PvPisFUNCore.getInstance().getConfig());

        for (Player player : Bukkit.getOnlinePlayers()) {
            RankManager.applyPermissions(player);
        }

        sender.sendMessage(ChatColor.GREEN + "Config și permisiunile au fost reîncărcate.");
        return true;
    }
}
