package mckd.me.prog.Worlds.Build;

import mckd.me.prog.Prog;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

public class BuildScheduler extends JavaPlugin {

    private Objective obj;
    private Scoreboard board;


    @Override
    public boolean onCommand(CommandSender sender, Command cmd,String label,String[] args){
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

            if(obj == null){
                board = getServer().getScoreboardManager().getMainScoreboard();
                if(board.getObjective("objective") != null){
                    obj = board.getObjective("objective");
                } else {
                    obj = board.registerNewObjective("objective","dummy");
                    obj.setDisplaySlot(DisplaySlot.SIDEBAR);
                    obj.setDisplayName("時間");
                    Score space = obj.getScore("");
                    space.setScore(2);
                }
            }
            sender.sendMessage("§b" + count + "秒数えます...");
            BukkitRunnable task = new BukkitRunnable() {
                int i = count;
                @Override
                public void run() {
                    if(i == 0){
                        sender.sendMessage("§aカウントが終了しました");
                        cancel();
                        removeAllScores();
                        return;
                    }
                    Score timer = obj.getScore("残り" + i + "秒");
                    timer.setScore(1);
                    sender.sendMessage("カウント: " + i);
                    i--;
                }
            };
            task.runTaskTimer(Prog.getPlugin(),0L,20L);
            return true;
        }
        sender.sendMessage("§c使い方: /count <count>");
        return true;
    }
    public void removeAllScores(){
        for(OfflinePlayer item : board.getPlayers()){
            obj.getScore(item).setScore(0);
            board.resetScores(item);
        }
    }
}
