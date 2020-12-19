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
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;


public class SnowWorld implements Listener {
    private Prog plugin;
    public String worldName = "snow";
    public Location StartPlace;
    public Location centerPlace;


    public SnowWorld(Prog plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        this.centerPlace = new Location(Bukkit.getWorld(this.worldName),507,6,630);
        this.StartPlace = new Location(Bukkit.getWorld(this.worldName),568,6,662);
    }
    /*@EventHandler
    public void ProjectileHit(ProjectileHitEvent e){
        if (!e.getEntity().getWorld().equals(this.worldName)) {
            return;
        }
        Player player = (Player) e.getEntity();
        Projectile projectile = e.getEntity();
        if(projectile instanceof Arrow){
            player.sendMessage("test4");
        }
        if(projectile instanceof Player){
            player.sendMessage("test6");
        }

    }*/





    @EventHandler
    public void changeWorld(PlayerChangedWorldEvent e){
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            player.teleport(this.StartPlace);
            player.getInventory().clear();
            player.setFoodLevel(20);
            player.setHealth(20.0);
        }
    }

    //矢を降らす
    public static void hitPlayer(Player player){
        for (int i=0;i<100;i++) {
            Random r = new Random();
            int x = r.nextInt(15);
            int z = r.nextInt(15);
            Location location = new Location(Bukkit.getWorld("snow"),507, 20, 630);
            location.add(x,0,z);
            location.getWorld().spawnArrow(location,new Vector(0,-1,0),0.2f,16);

            //spawnLocation.getWorld().spawnArrow(spawnLocation, new Vector(x, -1, z), 0.2f,8);

        }
    }




    public void random(Player player) {
        World world = Bukkit.getWorld("snow");
        Location location = new Location(Bukkit.getWorld(this.worldName), 507, 6, 630);
        Random r = new Random();
        int n = r.nextInt(15);
        int m = r.nextInt(15);
        location.add(n, 0, 0);
        location.add(0, 0, m);
        world.getBlockAt(location).setType(Material.WOOD);
        new BukkitRunnable() {
            @Override
            public void run() {
                player.sendMessage("test");
                world.getBlockAt(location).setType(Material.AIR);
            }

        }.runTaskLater(this.plugin, 100);


    }


    @EventHandler
    public void breakBlock(BlockBreakEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            Block block = e.getBlock();
            if (block.getType() == Material.STONE) {
                this.random(player);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.sendMessage("start");
                        hitPlayer(player);
                    }

                }.runTaskLater(this.plugin, 40);
            }
        }
    }
}
