package mckd.me.prog.Worlds.Raid;

import mckd.me.prog.Prog;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class Raid implements Listener {
    private Prog plugin;
    public String worldName = "raid";
    public Location startPlace;//スタート地点
    public int LimitTime = 600;//ゲームの制限時間

    public Raid(Prog plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        this.startPlace = new Location(Bukkit.getWorld(this.worldName),297,68,470);
    }

/*    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        player.sendMessage("test");
        BukkitTask task = new GameTimer(this.plugin, LimitTime).runTaskTimer(this.plugin,0,20);
    }*/

    @EventHandler
    public void ChangeWorld(PlayerChangedWorldEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            player.teleport(this.startPlace);
            this.SetChest();
        }
    }
    public void SetChest() {
        World world = Bukkit.getWorld("raid");
        Location location = new Location(Bukkit.getWorld(this.worldName),297,68,471);
        world.getBlockAt(location).setType(Material.CHEST);
    }
}
