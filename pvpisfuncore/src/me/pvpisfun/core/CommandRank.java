package me.pvpisfun.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRank implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("rank.admin")) {
            sender.sendMessage(ChatColor.RED + "Nu ai permisiunea de a folosi aceasta comanda!");
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "Utilizare: /rank <player> <rank>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Jucatorul nu este online!");
            return true;
        }

        String rank = args[1].toLowerCase();
        if (!isValidRank(rank)) {
            sender.sendMessage(ChatColor.RED + "Rank invalid! Rank-uri disponibile: owner, admin, moderator, vip, jucator");
            return true;
        }

        RankManager.setRank(target, rank);
        sender.sendMessage(ChatColor.GREEN + "Rank-ul jucatorului " + target.getName() + " a fost setat la " + capitalize(rank));
        target.sendMessage(ChatColor.GREEN + "Rank-ul tau a fost setat la " + capitalize(rank));

        return true;
    }

    private boolean isValidRank(String rank) {
        return rank.equals("owner") || rank.equals("admin") || rank.equals("moderator") || rank.equals("vip") || rank.equals("jucator");
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
