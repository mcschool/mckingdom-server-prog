package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Button;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class TntWorld implements Listener {
    private Prog plugin;
    public String worldName = "tnt";
    public Location startPlace;
    public Player winner;

    public TntWorld(Prog plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.startPlace = new Location(Bukkit.getWorld(this.worldName), -263, 52, 1088);
    }
    //ダメージ受けない
    @EventHandler
 public  void onEntityDamage(EntityDamageEvent e){
        if(e.getEntity().getWorld().getName().equals(this.worldName)){
            if (!(e.getEntity() instanceof  Player)){
                return;
            }
            String a = e.getCause().toString();
            Player player = (Player) e.getEntity();
            player.sendMessage(a);
            if (e.getCause() != null && e.getCause() == EntityDamageEvent.DamageCause.FALL){
                e.setCancelled(true);
            }
            if (e.getCause() != null && e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK){
                e.setCancelled(true);
            }
            if (e.getCause() != null && e.getCause() == EntityDamageEvent.DamageCause.LAVA){
                e.setCancelled(true);
            }
            return;
        }
 }
    //待合所にテレポート
    @EventHandler
    public void changeWorld(PlayerChangedWorldEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            player.teleport(this.startPlace);
            player.getInventory().clear();
            player.setFoodLevel(20);
            player.setHealth(20.0);
            player.setFlying(false);
            player.getWorld().setPVP(false);
            player.setGameMode(GameMode.SURVIVAL);
            ItemStack itemStack = new ItemStack(Material.WOOD_BUTTON);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("ゲームスタート");
            itemStack.setItemMeta(itemMeta);
            player.getInventory().addItem(itemStack);
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
            this.allFloors(50);
            this.allFloors(40);
            this.allFloors(30);
            this.allFloors(20);
            this.allFloors(10);
            this.damageFloors();
        }
    }
    public void startGame() {
        World world = Bukkit.getWorld("tnt");
        List<Player> players = world.getPlayers();

        for (Player player : players) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(new Location(Bukkit.getWorld("tnt"), -263, 52, 1052));
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
                if (this.playerCheck()==1){
                    this.gameClear();
                }
                this.playerCheck();
                player.sendTitle("GameOver", "ゲームオーバー", 20, 20, 20);
                player.teleport(new Location(Bukkit.getWorld("tnt"), -265, 52, 1088));
            }
        }
    }

    //床作る
    public void allFloors(int y) {
        World world = Bukkit.getWorld("tnt");
            Location location = new Location(Bukkit.getWorld(this.worldName), -266,  y, 1049);
            for (int i = 0; i < 5; i++) {
                location.add(0, 0, 1);
                for (int j = 0; j < 5; j++) {
                    location.add(1, 0, 0);
                    world.getBlockAt(location).setType(Material.TNT);
                }
                location.add(-5, 0, 0);
            }
        }

    //マグマ作る
    public void damageFloors() {
        World world = Bukkit.getWorld("tnt");
        Location location = new Location(Bukkit.getWorld(this.worldName), -266,  3, 1049);
        for (int i = 0; i < 20; i++) {
            location.add(0, 0, 1);
            for (int j = 0; j < 20; j++) {
                location.add(1, 0, 0);
                world.getBlockAt(location).setType(Material.GRASS);
            }
            location.add(-20, 0, 0);
        }
            location.add(0, 0, -20);
    }

        //ゲームクリア
    public void gameClear(){
        World world = Bukkit.getWorld("tnt");
        List<Player> players = world.getPlayers();
        for (Player player : players){
            String winnerName = this.winner.getDisplayName();
            player.sendTitle("GameWin",winnerName + "勝利しました",20,20,20);
        }
    }
    //プレイヤーチェック
    public int playerCheck(){
        World world = Bukkit.getWorld("tnt");
        List<Player> players = world.getPlayers();
        int safePlayerCount = 0;
        int safePlayerIndex= 0;
        int i = 0;
        for (Player player : players){
            double y= player.getLocation().getY();
            if (y<=50){
                safePlayerCount=safePlayerCount+1;
                safePlayerIndex = i;
            }
            i = i + 1;
        }
        if (safePlayerCount==1){
            Player player = players.get(safePlayerIndex);
            this.winner = player;
        }
        return safePlayerCount;
    }


    @EventHandler
    public void onBrakeBlock(BlockBreakEvent e){
        if (e.getPlayer().getWorld().getName().equals("tnt")){
            Player player = e.getPlayer();
            Block block =e.getBlock();
            if (block.getType() == Material.STONE){
                int safePlayerCount = this.playerCheck();
                player.sendMessage(String.valueOf(safePlayerCount) );
                this.gameClear();
            }
        }
    }
    } // end





