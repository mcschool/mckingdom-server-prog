package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;

public class AnvilWorld implements Listener {
    private Prog plugin;
    public String worldName = "anvil";
    public Location StartPlace;
    public Location centerPlace;

    public AnvilWorld(Prog plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.centerPlace = new Location(Bukkit.getWorld(this.worldName), -520, 50, 1292);
        this.StartPlace = new Location(Bukkit.getWorld(this.worldName), -473, 53, -1289);
    }


    @EventHandler
    public void chengeWorld(PlayerChangedWorldEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            player.teleport(this.StartPlace);
            player.getInventory().clear();
            player.setFoodLevel(20);
            player.setHealth(20.0);
            player.setPlayerWeather(WeatherType.CLEAR);
            World world = player.getWorld();
            world.setTime(30000);
        }
    }

    @EventHandler
    public void breakBlock(BlockBreakEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            Block block = e.getBlock();
            if (block.getType() == Material.STONE) {
                player.sendMessage("anvil1");
                player.setGameMode(GameMode.CREATIVE);
            }


        }
    }
}


