package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TPvp implements Listener {
    private final Location StartPlace;
    private Prog plugin;
    public String worldName = "TPvp";
    public boolean isPlaying = false;


    public TPvp(Prog plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.StartPlace = new Location(Bukkit.getWorld(this.worldName), -1328, 14, -723);

    }


    @EventHandler
    public void changeWorld(PlayerChangedWorldEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            player.teleport(this.StartPlace);
            player.getInventory().clear();
            player.setFoodLevel(20);
            player.setHealth(20.0);
            player.setPlayerWeather(WeatherType.CLEAR);
            World world = player.getWorld();
            world.setTime(30000);
            player.setGameMode(GameMode.ADVENTURE);
        }
    }

    @EventHandler
    public void ChangeStage1(PlayerMoveEvent e) {
        Location location = new Location(Bukkit.getWorld(worldName), -1341, 11, -720);
        Location location2 = new Location(Bukkit.getWorld(worldName), -1361, 12, -839);
        World world = Bukkit.getWorld("Tpvp");
        Player player = e.getPlayer();
        int x = (int) player.getLocation().getX();
        int y = (int) player.getLocation().getY();
        int z = (int) player.getLocation().getZ();
        //player.sendMessage(String.valueOf(player.getLocation()));
        if (x == (int) location.getX() && y == (int) location.getY() && z == (int) location.getZ()) {
            player.teleport(location2);
        }
    }

    @EventHandler
    public void ChangeStage(PlayerMoveEvent e) {
        Location location3 = new Location(Bukkit.getWorld(worldName), -1341, 11, -727);
        Location location4 = new Location(Bukkit.getWorld(worldName), -1277, 13, -846);
        Player player = e.getPlayer();
        int x = (int) player.getLocation().getX();
        int y = (int) player.getLocation().getY();
        int z = (int) player.getLocation().getZ();
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            if (x == (int) location3.getX() && y == (int) location3.getY() && z == (int) location3.getZ()) {
                player.teleport(location4);
            }

        }
    }

}









