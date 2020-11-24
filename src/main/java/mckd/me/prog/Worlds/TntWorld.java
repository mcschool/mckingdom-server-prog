package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Button;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class TntWorld implements Listener {
    private Prog plugin;
    public String worldName = "tnt";
    public Location startPlace;

    public TntWorld(Prog plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.startPlace = new Location(Bukkit.getWorld(this.worldName), -263, 51, 1088);
    }

    //待合所にテレポート
    @EventHandler
    public void changeWorld(PlayerChangedWorldEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            player.teleport(this.startPlace);
        }
    }

    //ゲームスタート
    @EventHandler
    public void breakBlock(PlayerInteractEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            ItemStack item = e.getItem();
            if (item.getType() == Material.WOOD_BUTTON) {
                player.sendTitle("GameStart", "ゲームスタート", 20, 20, 20);
                player.sendMessage("移動するよ");
                this.startGame();
            }
            this.allFloors();
        }
    }
    public void startGame() {
        World world = Bukkit.getWorld("tnt");
        List<Player> players = world.getPlayers();

        for (Player player : players) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(new Location(Bukkit.getWorld("tnt"), -263, 41, 1052));
                }
            }.runTaskLater(this.plugin, 20);
        }
    }

    //爆発させない
    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {
        if (e.getBlock().getWorld().getName().equals("tnt")) {
            e.setCancelled(true);
        }
    }

    //TNT消える
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (e.getPlayer().getWorld().getName().equals("tnt")) {
            Location location = e.getPlayer().getLocation().clone().subtract(0, +1, 0);
            Block block = location.getBlock();
            if (block.getType() == Material.TNT) {
                world.getBlockAt(location).setType(Material.AIR);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (block.getType() == Material.TNT) {
                            player.sendMessage(block.getType().name());
                            world.getBlockAt(location).setType(Material.AIR);
                        }
                    }
                }.runTaskLater(this.plugin, 20);
            }
        }
    }

    //ゲームオーバー
    @EventHandler
    public void gameOver(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (e.getPlayer().getWorld().getName().equals("tnt")) {
            Location location = e.getPlayer().getLocation().clone().subtract(0, 0, 0);
            if (location.getY() <= 3) {
                player.sendTitle("GameOver", "ゲームオーバー", 20, 20, 20);
                player.teleport(new Location(Bukkit.getWorld("tnt"), -265, 51, 1088));
            }
        }
    }

    //床作る
    public void allFloors() {
        World world = Bukkit.getWorld("tnt");
            Location location = new Location(Bukkit.getWorld(this.worldName), -266, 50, 1049);
            Location location1 = new Location(Bukkit.getWorld(this.worldName), -266, 40, 1049);
            Location location2 = new Location(Bukkit.getWorld(this.worldName), -266, 30, 1049);
            Location location3 = new Location(Bukkit.getWorld(this.worldName), -266, 20, 1049);
            Location location4 = new Location(Bukkit.getWorld(this.worldName), -266, 10, 1049);
            for (int i = 0; i < 5; i++) {
                location.add(0, 0, 1);
                location1.add(0, 0, 1);
                location2.add(0, 0, 1);
                location3.add(0, 0, 1);
                location4.add(0, 0, 1);
                for (int j = 0; j < 5; j++) {
                    location.add(1, 0, 0);
                    location1.add(0, 0, 1);
                    location2.add(0, 0, 1);
                    location3.add(0, 0, 1);
                    location4.add(0, 0, 1);
                    world.getBlockAt(location).setType(Material.TNT);
                    world.getBlockAt(location1).setType(Material.TNT);
                    world.getBlockAt(location2).setType(Material.TNT);
                    world.getBlockAt(location3).setType(Material.TNT);
                    world.getBlockAt(location4).setType(Material.TNT);
                }
                location.add(-5, 0, 0);
                location1.add(-5, 0, 0);
                location2.add(-5, 0, 0);
                location3.add(-5, 0, 0);
                location4.add(-5, 0, 0);
            }
        }

        //ゲームクリア
    public void gameClear(){}
    } // end





