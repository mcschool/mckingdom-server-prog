package mckd.me.prog.Worlds.Build;

import junit.framework.Test;
import mckd.me.prog.Prog;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.List;

public class BuildBattle implements Listener {
    private Prog plugin;
    public String worldName = "Build";
    public Location changePlace;
    public Location KIBLOCK;
    private BukkitTask task;
    /*public int LimitTime = 600;
    int count = 60;*/


    public BuildBattle(Prog plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.changePlace = new Location(Bukkit.getWorld(this.worldName), -20, 10, 538);
        this.KIBLOCK = new Location(Bukkit.getWorld(this.worldName),26,4,24);
    }
    @EventHandler
    public void changeWorld(PlayerChangedWorldEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            player.teleport(this.changePlace);
            player.getInventory().clear();
            player.setFoodLevel(20);
            player.setHealth(20.0);
            player.setGameMode(GameMode.ADVENTURE);

        }

    }


    @EventHandler
    public void BlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();

    }



    @EventHandler
    public void signClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        Location location = player.getLocation();
        if (!player.getWorld().getName().equals(this.worldName)) {
            return;
        }
        Block b = e.getClickedBlock();
        Sign sign;
        sign = (Sign) b.getState();
        String line = sign.getLine(1);
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && b.getType() == Material.SIGN_POST) {
            if (line.equals("Start")) {
                player.sendMessage("test");
        //BukkitTask task = new BuildScheduler(this.plugin, LimitTime).runTaskTimer(this.plugin,0,20);

                double x = 0;
                double y = 5;
                double z = -12;
                List<Player> players = world.getPlayers();
                for (Player p : players) {
                    p.setGameMode(GameMode.CREATIVE);
                    Location loc = new Location(Bukkit.getWorld(this.worldName), x = x + 62, y,z);
                    p.teleport(loc);


                }


            }
            if(line.equals("a1")){
                player.sendMessage("aaa");
                double x = 26;
                double y = 3;
                double z = 24;
                for(int i = 0; i <= 24; i++){
                    world.getBlockAt(new Location(
                            Bukkit.getWorld(this.worldName), x + i, y, z)).setType(Material.WOOD);
                    for(int j = 0; j <= 24; j++) {
                        world.getBlockAt(new Location(Bukkit.getWorld(this.worldName), x + i, y, z - j)).setType(Material.WOOD);
                    }
                }


            }
            if(line.equals("b1")){
                player.sendMessage("aaa");
                double x = 26;
                double y = 3;
                double z = 24;
                for(int i = 0; i <= 24; i++){
                    world.getBlockAt(new Location(
                            Bukkit.getWorld(this.worldName), x + i, y, z)).setType(Material.GRASS);
                    for(int j = 0; j <= 24; j++) {
                        world.getBlockAt(new Location(Bukkit.getWorld(this.worldName), x + i, y, z - j)).setType(Material.GRASS);
                    }
                }


            }

        }
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        Block block = e.getBlock();
        Location location = block.getLocation();
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            if (player.getGameMode() == GameMode.CREATIVE) {
                if (location.getX() >= 75 && location.getX() < 76 || location.getX() >= 125 && location.getX() < 126 || location.getX() < 26 && location.getX() >= 25 || location.getX() < 51 && location.getX() >= 50 || location.getX() < 101 && location.getX() >= 100) {
                    player.sendMessage(ChatColor.RED + "おい、減点するぞ！！");
                    e.setCancelled(true);
                }
                if (location.getZ() >= 0 && location.getZ() < 1 || location.getZ() >= -25 && location.getZ() < -24 || location.getZ() >= 25 && location.getZ() < 26) {
                    player.sendMessage(ChatColor.RED + "おい、減点するぞ！！");
                    e.setCancelled(true);
                }
                if(location.getY() < 3 && location.getY() >= 2){
                    player.sendMessage(ChatColor.RED + "おい、減点するぞ！！");
                    e.setCancelled(true);
                }
            }

        }
    }


    @EventHandler
    public void PlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        Location location = player.getLocation();
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            if (player.getGameMode() == GameMode.CREATIVE) {
                if (location.getY() > 70) {
                    double x = location.getX();
                    double y = 5;
                    double z = location.getZ();
                    player.sendMessage(ChatColor.RED + "これより上は行けないよ！(パクリ対策)");
                    Location loc = new Location(Bukkit.getWorld(this.worldName), x, y, z);
                    player.teleport(loc);
                }
            }
        }
    }
}

