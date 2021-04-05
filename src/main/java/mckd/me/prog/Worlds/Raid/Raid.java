package mckd.me.prog.Worlds.Raid;

import mckd.me.prog.Prog;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Raid implements Listener {
    private Prog plugin;
    public String worldName = "raid";
    public Location startPlace;//スタート地点
    public int LimitTime = 600;//ゲームの制限時間
    public Location Arena;
    public Boolean frag;

    public Raid(Prog plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        this.startPlace = new Location(Bukkit.getWorld(this.worldName),297,68,470);
        this.Arena = new Location(Bukkit.getWorld(this.worldName),262,64,644);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (e.getPlayer().getWorld().getName().equals("raid")) {
        /*player.sendMessage("test");
        BukkitTask task = new GameTimer(this.plugin, LimitTime).runTaskTimer(this.plugin,0,20);*/
            //this.Mission();
            //this.countDown();
        }
    }
    @EventHandler
    public void ChangeWorld(PlayerChangedWorldEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            World world = player.getWorld();
            player.teleport(this.startPlace);
            this.SetChest(new Location(world,297,68,471),0);
        }
    }

    public void SetChest(Location location, int type) {
        World world = Bukkit.getWorld(this.worldName);
        world.getBlockAt(location).setType(Material.CHEST);
        Chest chest = (Chest)world.getBlockAt(location).getState();
        Inventory inv = chest.getInventory();
        inv.clear();
        if (type == 0) {
            inv.setItem(1, new ItemStack(Material.SAND, 20));
        }
    }

    @EventHandler
    public void GameClear(BlockPlaceEvent e) {
        final Block block = e.getBlock();
        if (e.getPlayer().getWorld().getName().equals("raid")) {
        if (block.getType() == Material.CHEST) {
            Chest chest = (Chest) e.getBlock().getState();
            Inventory chestInv = chest.getBlockInventory();
            chestInv.addItem(new ItemStack(Material.APPLE,1));
        }
    }
}
    @EventHandler
    public void clickSign(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getPlayer().getWorld().getName().equals("raid")) {
            if (!player.getWorld().getName().equals(this.worldName)) {
                return;
            }
            Block block = e.getClickedBlock();
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (e.getMaterial() == Material.SIGN) {
                    this.Mission();
                }
            }
        }
    }
    public  void Mission(){
        World world = Bukkit.getWorld("raid");
        List<Player> players = world.getPlayers();
        String[] str = {"ダイヤモンドを５個納品せよ","ゾンビを五体討伐せよ"};
        Random r = new Random();
        Location location = this.Arena;
        for (Player player : players) {
            String ms  = str[r.nextInt(str.length)];
            player.sendMessage(ms);
            if (ms == "ゾンビを五体討伐せよ") {
                player.getWorld().setTime(15000);
                this.countDown();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.teleport(location);
                    }
                }.runTaskLater(this.plugin,80);
            }
        }
    }
    @EventHandler
    public void CountKill(EntityDeathEvent e) {
        Entity entity = e.getEntity();
        int deadEntityCount = 0;
        int deadEntityIndex = 0;
        int i = 0;
                if (entity.getType() == EntityType.ZOMBIE && entity.getLastDamageCause() instanceof Player) {
                deadEntityCount = deadEntityCount + 1;
                plugin.getServer().broadcastMessage("Kill数" + i);
        }
    }
    public void countDown() {
        World world = Bukkit.getWorld("raid");
        List<Player> players = world.getPlayers();
        for (Player player : players) {
            player.sendTitle("3", "", 20, 20,20);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendTitle("2", "", 20, 20, 20);
                }
            }.runTaskLater(this.plugin,20);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendTitle("1", "", 20, 20, 20);
                }
            }.runTaskLater(this.plugin,40);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendTitle("ミッションが始まります", "", 20, 20, 20);
                }
            }.runTaskLater(this.plugin,60);
        }
    }
    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            if (e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                for (int i = 0; i < 10; i++) {
                    startPlace.add(0, 0, 1);
                    for (int j = 0; j < 10; j++) {
                        startPlace.add(1, 0, 0);
                    }
                    startPlace.add(-10, 0, 0);
                }
                e.getPlayer().sendMessage("やめて！！");
                e.setCancelled(true);
            }
        }
    }
}
