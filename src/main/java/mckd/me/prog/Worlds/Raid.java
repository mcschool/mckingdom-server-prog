package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

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
        player.sendMessage("test");
    }
}
