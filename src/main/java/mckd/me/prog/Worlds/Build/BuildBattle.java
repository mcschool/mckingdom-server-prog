package mckd.me.prog.Worlds.Build;


import mckd.me.prog.Prog;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import org.bukkit.scheduler.BukkitTask;


import java.util.List;



public class BuildBattle implements Listener {
    private Prog plugin;
    public String worldName = "Build";
    public Location changePlace;
    public Location KIBLOCK;
    public boolean isPlaying = false;
    private BukkitTask task;

    public BuildBattle(Prog plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.changePlace = new Location(Bukkit.getWorld(this.worldName), -20, 10, 538);
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
                    Location loc = new Location(Bukkit.getWorld(this.worldName), x = x + 62, y, z);
                    p.teleport(loc);


                }


            }
            if(line.equals("true")){
                isPlaying = true;
            }
            if(line.equals("false")){
                isPlaying = false;
            }


            if (line.equals("a1")) {
                player.sendMessage("aaa");
                double x = 26;
                double y = 3;
                double z = 24;
                for (int i = 0; i <= 24; i++) {
                    world.getBlockAt(new Location(
                            Bukkit.getWorld(this.worldName), x + i, y, z)).setType(Material.WOOD);
                    for (int j = 0; j <= 24; j++) {
                        world.getBlockAt(new Location(Bukkit.getWorld(this.worldName), x + i, y, z - j)).setType(Material.WOOD);
                    }
                }


            }


        }
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        Player player = e.getPlayer();
        World world = player.getWorld();
        if(e.getPlayer().getWorld().getName().equals(this.worldName)){
            if(!isPlaying){
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer ();
        World world = player.getWorld();
        Block block = e.getBlock();
        Location location = block.getLocation();
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            if (!isPlaying) {
                e.setCancelled(true);
                return;
            }
            if(location.getX() % 20 == 0 || location.getZ() % 20 == 0){
                e.setCancelled(true);
            }
            if (location.getY() < 3 && location.getY() >= 2) {
                e.setCancelled(true);
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
                if (location.getY() > 18) {
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