package mckd.me.prog.Worlds.Build;

import mckd.me.prog.Prog;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BuildScheduler extends BukkitRunnable {
    private final World world;
    private int count;
    private Prog plugin;


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
            player.sendTitle(msg, "", 0, 20, 0);
        }
    }
}
