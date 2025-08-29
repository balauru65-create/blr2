package me.pvpisfun.core;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.*;

public class RankManager {

    private static FileConfiguration config;
    private static Map<UUID, PermissionAttachment> attachments = new HashMap<>();

    public static void loadConfig(FileConfiguration cfg) {
        config = cfg;
    }

    public static void applyPermissions(Player player) {
        String rank = config.getString("users." + player.getUniqueId() + ".rank", "jucator");
        setRank(player, rank);
    }

    public static void setRank(Player player, String rank) {
        removeAttachment(player);
        PermissionAttachment attach = player.addAttachment(PvPisFUNCore.getInstance());
        attachments.put(player.getUniqueId(), attach);

        List<String> perms = config.getStringList("ranks." + rank.toLowerCase() + ".permissions");
        if (perms != null) {
            for (String perm : perms) {
                attach.setPermission(perm, true);
            }
        }

        config.set("users." + player.getUniqueId() + ".rank", rank.toLowerCase());
        PvPisFUNCore.getInstance().saveConfig();

        player.recalculatePermissions();
    }

    public static void removeAttachment(Player player) {
        UUID uuid = player.getUniqueId();
        if (attachments.containsKey(uuid)) {
            player.removeAttachment(attachments.get(uuid));
            attachments.remove(uuid);
        }
    }

    public static String getRank(Player player) {
        return config.getString("users." + player.getUniqueId() + ".rank", "jucator");
    }

    public static String getRankPrefix(Player player) {
        switch (getRank(player).toLowerCase()) {
            case "owner": return ChatColor.DARK_RED + "[Owner]";
            case "admin": return ChatColor.RED + "[Admin]";
            case "moderator": return ChatColor.BLUE + "[Mod]";
            case "vip": return ChatColor.GREEN + "[VIP]";
            default: return ChatColor.GRAY + "[Jucător]";
        }
    }

    public static ChatColor getNameColor(Player player) {
        switch (getRank(player).toLowerCase()) {
            case "owner": return ChatColor.DARK_RED;
            case "admin": return ChatColor.RED;
            case "moderator": return ChatColor.BLUE;
            case "vip": return ChatColor.GREEN;
            default: return ChatColor.WHITE;
        }
    }

    public static String formatChat(Player player, String msg) {
        return getRankPrefix(player) + " " + getNameColor(player) + player.getName() + ChatColor.GRAY + ": " + ChatColor.WHITE + msg;
    }
}
