package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;


import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.material.Wool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FallColorWorld implements Listener {
    private Prog plugin;
    public String worldName = "fallColor";
    public Location startPlace;
    private Object Arrow;


    public FallColorWorld(Prog plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.startPlace = new Location(Bukkit.getWorld(this.worldName), 87, 70, 111);
    }

    //待合所にテレポート
    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            Random rand = new Random();
            int colors = rand.nextInt(5) + 1;
            this.removeWool(player);
            player.sendTitle("FallColor", "", 20, 20, 20);
            player.sendMessage("ここから落ちたら地面に色がつくよ！");
            player.sendMessage("次落ちるときは色が付いたところに着地しないでね！");
            player.sendMessage(String.valueOf(colors));
            String colorName;
            player.teleport(this.startPlace);
            player.getInventory().clear();
            player.setFoodLevel(20);
            player.setHealth(20.0);
            player.setFlying(false);
            player.setPlayerWeather(WeatherType.CLEAR);
            player.getWorld().setPVP(false);
            player.setGameMode(GameMode.SURVIVAL);
            player.setPlayerWeather(WeatherType.CLEAR);
            FileConfiguration config = this.plugin.getConfig();
            player.sendMessage(player.getUniqueId().toString());
            if (colors == 1) {
                colorName = "red";
                player.sendMessage("赤チーム");
            } else if (colors == 2) {
                colorName = "blue";
                player.sendMessage("青チーム");
            } else if (colors == 3) {
                colorName = "green";
                player.sendMessage("緑チーム");
            } else {
                colorName = "black";
                player.sendMessage("黒チーム");
            }
            config.set(player.getUniqueId() + "test", colorName);
        }

    }

    //着地したとき
    @EventHandler
    public void FallPlayer(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (e.getPlayer().getWorld().getName().equals("fallColor")) {
            Location location = e.getPlayer().getLocation().clone().subtract(0, 0, 0);
            if (location.getY() <= 4) {
                this.SetColorBlock(player);
                player.teleport(this.startPlace);
                FileConfiguration config = this.plugin.getConfig();
                String color = config.getString(player.getUniqueId() + "test");
                player.sendMessage(color);
            }
        }
    }

    //色分け
    public void SetColorBlock(Player player) {
        World world = player.getWorld();
        Location location = player.getLocation().clone().subtract(0, 0, 0);
        Block block = world.getBlockAt(location);
        block.setType(Material.WOOL);
        BlockState blockState = block.getState();
        FileConfiguration config = this.plugin.getConfig();
        String color = config.getString(player.getUniqueId() + "test");
        player.sendMessage(color);
        if (color.equals("red")) {
            player.sendMessage("a");
            blockState.setData(new Wool(DyeColor.RED));
        } else if (color.equals("blue")) {
            player.sendMessage("b");
            blockState.setData(new Wool(DyeColor.BLUE));
        } else if (color.equals("green")) {
            player.sendMessage("c");
            blockState.setData(new Wool(DyeColor.GREEN));
        } else if (color.equals("black")) {
            player.sendMessage("d");
            blockState.setData(new Wool(DyeColor.BLACK));
        }
        blockState.update();
    }

    //ダメージ無効化
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity().getWorld().getName().equals(this.worldName)) {
            if (!(e.getEntity() instanceof Player)) {
                return;
            }
            if (e.getCause() != null && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                e.setCancelled(true);
            }
        }
    }

    //羊毛を踏んだ時
    @EventHandler
    public void onStepColor(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (e.getPlayer().getWorld().getName().equals("fallColor")) {
            Location location = e.getPlayer().getLocation().clone().subtract(0, +1, 0);
            Block block = location.getBlock();
            if (block.getType() == Material.WOOL) {
                player.teleport(location);
            }
        }
    }

    //プレイヤー数確認
    public int playerCheck() {
        World world = Bukkit.getWorld("fallColor");
        List<Player> players = world.getPlayers();
        int safePlayerCount = 0;
        int safePlayerIndex = 0;
        int i = 0;
              for (Player player : players) {
            double y = player.getLocation().getY();
            if (y <= 70) {
                safePlayerCount = safePlayerCount + 1;
                safePlayerIndex = i;
            }
            i = i + 1;
        }
        if (safePlayerCount == 10){
            Player player = players.get(safePlayerIndex);
            player.setGameMode(GameMode.SPECTATOR);
        }
        return safePlayerCount;
    }
    //時間測る

    //羊毛の色ごと計算
    public int woolCount(){
    World world = Bukkit.getWorld("fallColor");
    List<Player> players = world.getPlayers();
    int WoolCount = 0;
    int WoolIndex = 0;
    int i= 0;
        ArrayList<Block> blocks = new ArrayList<Block>();
        for (Block block : blocks){
                block.getType().getData();
                double y = block.getLocation().getY();
                if (y<=4){
                    WoolCount = WoolCount +1;
                    WoolIndex = i;
                }
                i = i+1;
        }
        if (WoolCount==1){
            Block block = blocks.get(WoolIndex);
        }
        return WoolCount;
    }
    //羊毛消す
    public void removeWool(Player player){
        World world = Bukkit.getWorld("fallColor");
        for (Entity entity : world.getEntities()){
            player.sendMessage(entity.toString());
            if (entity instanceof Wool){
                entity.remove();
            }
        }
    }

    public  void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        Entity entity = (Entity) world.getEntities();
        if (e.getPlayer().getWorld().getName().equals("fallColor")) {
            Location location = e.getPlayer().getLocation().clone().subtract(0, 0, 0);
            Block block = location.getBlock();
            if (block.getType() == Material.STONE) {
                this.woolCount();
                this.removeWool(player);

                if (world.getEntities() == Arrow){
                    
                }
            }
        }
    }
}



