package me.pvpisfun.core;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.entity.Player;

public class ChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        String prefix = RankManager.getRankPrefix(player);
        ChatColor nameColor = RankManager.getNameColor(player);

        event.setFormat(prefix + " " + nameColor + player.getName() + ChatColor.RESET + ": " + event.getMessage());
    }
}
