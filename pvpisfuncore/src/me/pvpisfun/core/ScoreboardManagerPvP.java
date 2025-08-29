package me.pvpisfun.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardManagerPvP {

    private static final String OBJECTIVE_NAME = "hubcore";

    public static void createScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        if (manager == null) return;

        Scoreboard board = manager.getNewScoreboard();

        Objective objective = board.registerNewObjective(OBJECTIVE_NAME, "dummy");
        objective.setDisplayName(ChatColor.GOLD + "PvPisFUN");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        setupLines(player, board, objective);

        player.setScoreboard(board);
    }

    public static void updateAllScoreboards() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updateScoreboard(player);
        }
    }

    public static void updateScoreboard(Player player) {
        Scoreboard board = player.getScoreboard();
        Objective objective = board.getObjective(OBJECTIVE_NAME);

        if (objective == null) {
            createScoreboard(player);
            return;
        }

        setupLines(player, board, objective);
    }

    private static void setupLines(Player player, Scoreboard board, Objective objective) {
        // Ștergem toate echipele și scorurile vechi
        for (Team team : board.getTeams()) {
            team.unregister();
        }
        for (String entry : board.getEntries()) {
            board.resetScores(entry);
        }

        // Spacer linie 4
        String line4 = ChatColor.BLACK.toString() + ChatColor.RESET;
        objective.getScore(line4).setScore(4);

        // Linie 3: Rank
        Team rankTeam = board.registerNewTeam("rankTeam");

        // Folosim un entry unic invizibil (caracter color invizibil)
        String rankEntry = ChatColor.RED.toString() + ChatColor.BOLD.toString() + ChatColor.RESET.toString();

        rankTeam.addEntry(rankEntry);

        // Prefix: "Rank: "
        rankTeam.setPrefix(ChatColor.YELLOW.toString() + "Rank: ");

        // Suffix: numele rank-ului (ex: Owner)
        String rankName = RankManager.getRank(player); // Ex: "Owner", "Admin"
        rankTeam.setSuffix(ChatColor.WHITE + rankName);

        objective.getScore(rankEntry).setScore(3);

        // Linie 2: Nume
        Team nameTeam = board.registerNewTeam("nameTeam");
        String nameEntry = ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + ChatColor.RESET.toString();

        nameTeam.addEntry(nameEntry);

        // Prefix: "Nume: "
        nameTeam.setPrefix(ChatColor.GREEN.toString() + "Nume: ");

        // Suffix: numele jucătorului
        nameTeam.setSuffix(ChatColor.WHITE + player.getName());

        objective.getScore(nameEntry).setScore(2);

        // Spacer linie 1
        String line1 = ChatColor.DARK_GRAY.toString() + ChatColor.RESET;
        objective.getScore(line1).setScore(1);

        // Linie 0: Jucători online
        String onlineEntry = ChatColor.YELLOW + "Jucători Online: " + ChatColor.WHITE + Bukkit.getOnlinePlayers().size();
        objective.getScore(onlineEntry).setScore(0);

        player.setScoreboard(board);
    }





    public static void removeScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        if (manager == null) return;

        player.setScoreboard(manager.getNewScoreboard());
    }
}
