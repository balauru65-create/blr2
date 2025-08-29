package me.pvpisfun.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PvPisFUNCore extends JavaPlugin implements Listener {

    private static PvPisFUNCore instance;
    public static PvPisFUNCore getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        RankManager.loadConfig(getConfig());

        getServer().getPluginManager().registerEvents(this, this);
        getCommand("rank").setExecutor(new CommandRank());
        getCommand("gm").setExecutor(new CommandGamemode());
        getCommand("fly").setExecutor(new CommandFly());
        getCommand("reloadcore").setExecutor(new CommandReloadCore());

        new BukkitRunnable() {
            @Override
            public void run() {
                ScoreboardManagerPvP.updateAllScoreboards();
            }
        }.runTaskTimer(this, 0L, 100L);
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            RankManager.removeAttachment(player);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        RankManager.applyPermissions(player);
        ScoreboardManagerPvP.createScoreboard(player);
        player.sendMessage("§aBine ai venit pe serverul PvPisFUN!");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        RankManager.removeAttachment(event.getPlayer());
        ScoreboardManagerPvP.removeScoreboard(event.getPlayer());
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setFormat(RankManager.formatChat(player, event.getMessage()));
    }
}
