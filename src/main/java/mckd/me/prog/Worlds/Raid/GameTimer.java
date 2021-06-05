package mckd.me.prog.Worlds.Raid;

import com.sun.glass.ui.Timer;
import mckd.me.prog.Prog;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class GameTimer extends BukkitRunnable {
    BukkitTask task = null;
    int time;
    Prog plugin;
    public GameTimer(Prog plugin ,int i) {
        this.time = i;
        this.plugin = plugin;
    }
    public void start(){
        //task = this.plugin.getServer().getScheduler().runTaskTimer(this, new Timer(this ,5), 0L, 20L);
    }
    @Override
    public void run() {
        if (time <= 0){
            //plugin.getServer().broadcastMessage("Start!");
            //plugin.getServer().getScheduler().cancelTask(task.getTaskId());
            this.cancel();
        }else {
            plugin.getServer().broadcastMessage("" + time);
        }
        time--;
    }
}
