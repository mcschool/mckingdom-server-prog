package mckd.me.prog.Worlds.Raid;

import mckd.me.prog.Prog;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class Raid implements Listener {
    private Prog plugin;
    public String worldName = "raid";
    public Location RespawnPlace;//スタート地点
    public int LimitTime = 600;//ゲームの制限時間
    public Location Camp;//ミッション開始地点
    public Boolean frag;
    public int deadEntityCount;
    public int deadPlayerCount;
    public String MissionType="";

    public Raid(Prog plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        this.RespawnPlace = new Location(Bukkit.getWorld(this.worldName),297,68,470);
        this.Camp = new Location(Bukkit.getWorld(this.worldName),262,64,644);
    }

    //テスト用
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        if (e.getPlayer().getWorld().getName().equals("raid")) {
            if (block.getType() == Material.STONE) {
                player.sendMessage(String.valueOf(MissionType));
            }
        /*player.sendMessage("test");
        BukkitTask task = new GameTimer(this.plugin, LimitTime).runTaskTimer(this.plugin,0,20);*/
            //this.Mission();
            //this.countDown();
        }
    }
    //ワールドに入ったとき
    @EventHandler
    public void ChangeWorld(PlayerChangedWorldEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            World world = player.getWorld();
            player.teleport(this.RespawnPlace);
            this.SetChest(new Location(world,297,68,471),0);
        }
    }
    //チェスト設置
    public void SetChest(Location location, int type) {
        World world = Bukkit.getWorld(this.worldName);
        world.getBlockAt(Camp).setType(Material.CHEST);
        Chest chest = (Chest)world.getBlockAt(location).getState();
        Inventory inv = chest.getInventory();
        inv.clear();
        if (type == 0) {
            inv.setItem(1, new ItemStack(Material.SAND, 20));
        }
        if (type == 1) {
            inv.setItem(0,new ItemStack(Material.AIR,0));
        }
    }

    //チェストに納品
    @EventHandler
    public void ChestGameClear(InventoryCloseEvent e) {
        HumanEntity player = e.getPlayer();
        Inventory inventory = e.getInventory();
        ItemStack itemStack = e.getInventory().getItem(1);
        int type = 1;
        if (e.getPlayer().getWorld().getName().equals("raid")) {
            if (inventory.getType() == InventoryType.CHEST && itemStack.getType() == Material.DIAMOND && type == 1) {
                this.GameFinishCheck();
            }
        }
    }
/*    //ゾンビ討伐クリア
    public void GameClearZ() {
        World world = Bukkit.getWorld("raid");
        List<Player> players = world.getPlayers();
        for (Player player : players) {
            if (deadEntityCount == 5) {
                player.sendMessage("クリア！！");
            }
        }
    }*/

    //ミッション開始
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        Sign sign = (Sign) block.getState();
        if (e.getPlayer().getWorld().getName().equals("raid")) {
            if (!player.getWorld().getName().equals(this.worldName)) {
                return;
            }
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    //player.sendMessage(String.valueOf(block.getType()));
                if (block.getType() == Material.WALL_SIGN && sign.getLine(0).equalsIgnoreCase("Mission")) {
                    this.MissionStart();
                        }
                    }
                }
            }
    //ミッション一覧
    public  void MissionStart() {
        World world = Bukkit.getWorld("raid");
        List<Player> players = world.getPlayers();
        String[] str = {"ダイヤモンドを５個納品せよ", "ゾンビを五体討伐せよ"};
        Random r = new Random();
        Location location = this.Camp;
        String ms = str[r.nextInt(str.length)];
        if (ms.equals("ゾンビを五体討伐せよ")) {
            MissionType = "ゾンビ";
        }
        if (ms.equals("ダイヤモンドを５個納品せよ")) {
            MissionType = "ダイヤ";
        }
        this.countDown();
        for (Player player : players) {
            player.sendMessage(ms);
        }
    }
    //ゲームクリアチェック
    public void GameFinishCheck() {
        World world = Bukkit.getWorld("raid");
        List<Player> players = world.getPlayers();
        if (MissionType.equals("ゾンビ")) {
            if (deadEntityCount >= 5) {
                for (Player player:players) {
                    player.sendMessage("MissionClear!");
                    player.teleport(RespawnPlace);
                    player.getWorld().setTime(0);
                }
                MissionType = "";
            }
        }
        if (MissionType.equals("ダイヤ")) {
            for (Player player:players) {
                player.sendMessage("MissionClear!");
                player.teleport(RespawnPlace);
            }
            MissionType = "";
        }
    }
    //敵をkillした回数　
    @EventHandler
    public void CountKill(EntityDeathEvent e) {
        World world = Bukkit.getWorld("raid");
        List<Entity> entities = world.getEntities();
        List<Player> players = world.getPlayers();
        Entity entity = e.getEntity();
                if (entity.getType() == EntityType.ZOMBIE && entity.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                    deadEntityCount = deadEntityCount + 1;
                    this.GameFinishCheck();
                    for (Player player : players) {
                        player.sendMessage("kill数" + deadEntityCount);
                    }
                }
    }

    //カウントダウン
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
            if (MissionType.equals("ゾンビ")) {
                player.getWorld().setTime(15000);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.teleport(Camp);
                    }
                }.runTaskLater(this.plugin, 80);
            }
            if (MissionType.equals("ダイヤ")) {
                player.getWorld().setTime(0);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.teleport(Camp);
                    }
                }.runTaskLater(this.plugin, 80);
            }
        }
    }
    //リスポーン地点周辺のブロック破壊防止
    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            if (e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                for (int i = 0; i < 10; i++) {
                    RespawnPlace.add(0, 0, 1);
                    for (int j = 0; j < 10; j++) {
                        RespawnPlace.add(1, 0, 0);
                    }
                    RespawnPlace.add(-10, 0, 0);
                }
                e.getPlayer().sendMessage("やめて！！");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        World world = Bukkit.getWorld(this.worldName);
        List<Player> players = world.getPlayers();
        if (e.getEntity().getWorld().getName().equals(this.worldName)) {
            if (MissionType.equals("ゾンビ") || MissionType.equals("ダイヤ")) {
                for (Player player:players) {
                    player.sendMessage(String.valueOf(deadPlayerCount));
                }
                deadPlayerCount = deadPlayerCount + 1;
            }
        }
    }
    public void GameOver() {
        World world = Bukkit.getWorld(this.worldName);
        List<Player> players = world.getPlayers();
        if (deadPlayerCount == 1) {
            for (Player player:players) {

            }
        }
    }
}
