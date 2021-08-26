package mckd.me.prog.Worlds.Build;

import mckd.me.prog.Prog;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.List;

public class BuildCommand{
    public static boolean command(CommandSender sender, Command command, String label, String args[],BuildBattle buildBattle,Prog plguin){

        if(args.length == 1){
            if(!StringUtils.isNumeric(args[0])){
                sender.sendMessage("§ccountは数字で指定してください");
                return true;
            }
            final int count = Integer.valueOf(args[0]);
            if(count <= 0){
                sender.sendMessage("§ccountは1以上を指定してください");
                return true;
            }

            sender.sendMessage("§b" + count + "秒数えます...");
            BukkitRunnable task = new BukkitRunnable() {
                int i = count;
                @Override
                public void run() {
                    if(i == 0){
                        World world = ((Player) sender).getWorld();
                        List<Player> players = world.getPlayers();
                        for (Player p: players) {
                            p.sendMessage("§aカウントが終了しました");
                            p.setGameMode(GameMode.SPECTATOR);
                        }
                        cancel();
                        return;
                    }

                    World world = ((Player) sender).getWorld();
                    List<Player> players = world.getPlayers();
                    for (Player p: players) {
                        ScoreboardManager manager = Bukkit.getScoreboardManager();
                        Scoreboard board = manager.getNewScoreboard();
                        Objective objective = board.registerNewObjective("Count", "dummy");
                        objective.setDisplayName("のこり時間");
                        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                        ((Player) p).setScoreboard(board);
                        Score timer = objective.getScore("");
                        timer.setScore(2);
                        Score count = objective.getScore("カウント: " + i);
                        count.setScore(1);
                        //sender.sendMessage("カウント: " + i);

                    }

                    i--;
                }

            };

            task.runTaskTimer(plguin,0L,20L);
            return true;
        }
        sender.sendMessage("§c使い方: /count <count>");
        return true;
    }



}
