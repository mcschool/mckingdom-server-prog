package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;

public class Raid implements Listener {
    private Prog plugin;
    public String worldName = "raid";
    public Location startPlace;

    public Raid(Prog plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        this.startPlace = new Location(Bukkit.getWorld(this.worldName),296,68,469);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask((Plugin) this, new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("test");
            }
        }, 100);
    }

    @EventHandler
    public void ChangeWorld(PlayerChangedWorldEvent e) {
        if (e.getPlayer().getWorld().equals(this.worldName)) {
            Player player = e.getPlayer();
            player.teleport(this.startPlace);
        }
    }
}
