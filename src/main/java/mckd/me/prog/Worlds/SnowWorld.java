package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;


public class SnowWorld implements Listener {
    private Prog plugin;
    public String worldName = "snow";
    public Location StartPlace;


    public SnowWorld(Prog plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        this.StartPlace = new Location(Bukkit.getWorld(this.worldName),507,6,630);
    }


    public static void hitPlayer(Player player){
        for (int i=0;i<10;i++) {
            Random r = new Random();
            int x = r.nextInt(15);
            int z = r.nextInt(15);
            Location location = new Location(Bukkit.getWorld("snow"),507, 50, 630);
            location.add(x,0,z);
            location.getWorld().spawnArrow(location,new Vector(0,-90,0),0.2f,0);
            //spawnLocation.getWorld().spawnArrow(spawnLocation, new Vector(x, -1, z), 0.2f,8);
        }
    }


    public void random() {
        World world = Bukkit.getWorld("snow");
        Location location = new Location(Bukkit.getWorld(this.worldName), 507, 6, 630);
        Random r = new Random();
        int n = r.nextInt(15);
        int m = r.nextInt(15);
        location.add(n, 0, 0);
        location.add(0, 0, m);
        world.getBlockAt(location).setType(Material.WOOD);

    }


    @EventHandler
    public void breakBlock(BlockBreakEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            Block block = e.getBlock();
            if (block.getType() == Material.STONE) {
                player.sendMessage("test1");

                this.random();

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.sendMessage("start");
                    }

                }.runTaskLater(this.plugin, 20);
                this.hitPlayer(player);
            }
        }
    }
}
