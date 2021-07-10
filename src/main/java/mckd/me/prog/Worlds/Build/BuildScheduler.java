package mckd.me.prog.Worlds.Build;

import mckd.me.prog.Prog;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class BuildScheduler implements CommandExecutor {

    private BukkitTask task;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd,String label,String[] args){
        /*Player player = null;
        if(sender instanceof Player){
            player = (Player) sender;
        } else {
            return true;
        }*/
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
                //Player player = (Player) Bukkit.getOnlinePlayers();
                int i = count;
                @Override
                public void run() {
                    if(i == 0){
                        sender.sendMessage("§aカウントが終了しました");
                        cancel();
                        return;
                    }
                    BossBar bossBar = Bukkit.createBossBar("残り " + i + "秒",BarColor.YELLOW,BarStyle.SEGMENTED_10);
                    bossBar.setVisible(true);
                    sender.sendMessage("test");
                    //bossBar.addPlayer(player);
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

}
