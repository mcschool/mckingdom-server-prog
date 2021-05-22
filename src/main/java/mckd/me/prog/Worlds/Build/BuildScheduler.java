package mckd.me.prog.Worlds.Build;

import mckd.me.prog.Prog;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class BuildScheduler extends BukkitRunnable {
    private final World world;
    private int count;
    private Prog plugin;
    private BukkitTask task;


    public BuildScheduler(Prog plugin, World world) {
        this.plugin = plugin;
        this.world = world;
        this.count = 20;


    }


    @Override
    public void run() {
        this.count--;
        this.sendMessageToPlayers(this.world, String.valueOf(this.count));
        if (this.count < 1) {
            this.sendMessageToPlayers(this.world, "START!");
            this.cancel();
        }
    }

    private void sendMessageToPlayers(World world, String msg) {
        for (Player player : world.getPlayers()) {
            player.sendMessage(msg);
            BossBar bossBar = Bukkit.createBossBar("countdown", BarColor.YELLOW, BarStyle.SEGMENTED_10);
            if(task == null){
                this.task == new BukkitRunnable(){
                    int seconds = 10;
                    @Override
                    public void run() {

                        if((seconds -= 1) == 0) {
                            task.cancel();
                            bossBar.removeAll();
                        }else{
                            bossBar.setProgress(seconds / 10D);
                        }

                    }
                }.runTaskTimer(<plugin >, 0, 20);
            }
            bossBar.setVisible(true);
            bossBar.addPlayer(player);
            //player.sendTitle(msg, "", 0, 20, 0);
        }
    }



}
