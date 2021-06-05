package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class AnvilWorld implements Listener {
    private Prog plugin;
    public String worldName = "anvil";
    public Location StartPlace;
    public Location centerPlace;
    public boolean isPlaying = false;

    public AnvilWorld(Prog plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.centerPlace = new Location(Bukkit.getWorld(this.worldName), -520, 50, 1292);
        this.StartPlace = new Location(Bukkit.getWorld(this.worldName), -473, 53, -1289);

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
            ItemStack itemStack = new ItemStack(Material.ANVIL);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("ゲームスタート");
            itemStack.setItemMeta(itemMeta);
            player.getInventory().addItem(itemStack);

        }
    }

    @EventHandler
    public void breakBlock(BlockBreakEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            Block block = e.getBlock();
            if (block.getType() == Material.SANDSTONE) {
                if (block.getType() == Material.SANDSTONE) {
                    player.setGameMode(GameMode.CREATIVE);
                }
            }
        }
    }

    @EventHandler
    public void interactBlock(PlayerInteractEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            ItemStack item = e.getItem();
            if (item.getType() == Material.ANVIL) {
                int NowPlayerCount = player.getWorld().getPlayers().size();
                if (isPlaying == true) {
                    player.sendMessage("ゲームがおわるまでまってね");
                } else {
                    if (NowPlayerCount >= 1) {
                        isPlaying = true;
                        player.getInventory().clear();
                        this.startGame();
                        player.sendTitle("Gamestart", "ゲームスタート", 20, 20, 20);
                        player.sendMessage("移動するよ");
                        this.fallAnvil();
                        if(isPlaying == false) {
                            e.setCancelled(true);

                        }

                    }
                }
            }

        }
    }

    public void startGame() {
        World world = Bukkit.getWorld("Anvil");
        List<Player> players = world.getPlayers();
        for (Player player : players) {
            player.teleport(new Location(Bukkit.getWorld("Anvil"), -512, 5, -1284));
        }
    }

    public void fallAnvil() {
        World world = Bukkit.getWorld("anvil");
        Location location = new Location(Bukkit.getWorld(worldName), -521, 55, -1293);
        world.getBlockAt(location).setType(Material.ANVIL);
        int a = 20;
        for (int i = 0; i < 1000; i++) {
            if (i < 30) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (isPlaying == true) {
                            Location location = new Location(Bukkit.getWorld(worldName), -521, 55, -1293);
                            Random R = new Random();
                            int x = R.nextInt(14);
                            int z = R.nextInt(14);
                            location.add(x, 0, z);
                            world.getBlockAt(location).setType(Material.ANVIL);
                        }
                    }
                }.runTaskLater(this.plugin, a * i);
            } else if (i < 600) {
                a = 10;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (isPlaying == true) {
                            Location location = new Location(Bukkit.getWorld(worldName), -521, 55, -1293);
                            Random R = new Random();
                            int x = R.nextInt(14);
                            int z = R.nextInt(14);
                            location.add(x, 0, z);
                            world.getBlockAt(location).setType(Material.ANVIL);
                        }
                    }
                }.runTaskLater(this.plugin, a * i);

            } else {
                    a = 50;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (isPlaying == true) {
                            Location location = new Location(Bukkit.getWorld(worldName), -521, 55, -1293);
                            Random R = new Random();
                            int x = R.nextInt(14);
                            int z = R.nextInt(14);
                            location.add(x, 0, z);
                            world.getBlockAt(location).setType(Material.ANVIL);
                        }
                    }
                }.runTaskLater(this.plugin, a * i);
            }

        }
    }

    @EventHandler
    public void PlayerDeath(PlayerDeathEvent e) {
        if (e.getEntity().getWorld().getName().equals(this.worldName)) {
            Player player = e.getEntity();
            if (e.getEntity() instanceof Player) {
                player.sendMessage("金床の下敷きになった！");
                player.setHealth(20.0);
                player.setFoodLevel(10);
                player.setGameMode(GameMode.SPECTATOR);
                this.checkGamemode();

            }
        }
    }

    private void checkGamemode() {
        World world = Bukkit.getWorld("Anvil");
        int SurvivalCount = 0;
        for (Player p : world.getPlayers()) {
            for (Player pa : world.getPlayers()) {
                pa.sendMessage("うううううう");
            }
            if (p.getGameMode() == GameMode.ADVENTURE) {
                for (Player pb : world.getPlayers()) {
                    pb.sendMessage("えええええええ");
                }
                SurvivalCount++;
            }
        }

        for (Player p : world.getPlayers()) {
            p.sendMessage(String.valueOf(SurvivalCount) + "人残ってるよ");
        }

        if ((SurvivalCount <= 1)){
            for (Player p : world.getPlayers()) {
                p.sendMessage("ああああああああ");
            }
            isPlaying = false;
            for (Player p : world.getPlayers()){
                p.setGameMode(GameMode.ADVENTURE);
                p.teleport(StartPlace);
                p.sendTitle("GameSet!","ゲームが終了しました...",0,40,0);
            }
        }
    }
    @EventHandler
    public void PlayerDamageEvent(EntityDamageEvent e) {
        if (e.getEntity().getWorld().getName().equals(this.worldName)) {
            Player player = (Player) e.getEntity();
            if (e.getCause()!= null && e.getCause() == EntityDamageEvent.DamageCause.CONTACT) {
                e.setCancelled(true);
            }
            }

        }





}
//    if (SurvivalCount = 1) or (SurvivalCount = 0){
//        isPlaying = false;
//        for (Player p: World.getPlayers()){
//            p.setGameMode(GameMode.ADVENTURE);
//            p.teleport(StartPlace);
//            p.sendTitle("Gameset!","ゲームが終了しました",0,40 ,0);
//        }
//
//        }
//        }
//
//    }




//    @EventHandler
//    public void PlayerDeath(PlayerDeathEvent e) {
//        if (e.getEntity().getWorld().getName().equals(this.worldName)) {
//            Player player = e.getEntity();
//            if (e.getEntity() instanceof Player) {
//                player.sendMessage("金床の下敷きになった！");
//                player.setHealth(20.0);
//                player.setFoodLevel(10);
//                player.setGameMode(GameMode.SPECTATOR);
//                this.checkGamemode();
//                public void checkGamemode(){
//                                World world = Bukkit.getWorld("Anvil");
//                                int SurviralCount = 0;
//                                for (Player p : world.getPlayers()){
//                                    if(p.getGameMode() == GameMode.SURVIVAL) {
//                                        SurviralCount++;
//                                    }
//                                }
//                                if (SurviralCount == 1) or (SurviralCount == 0) {
//                                    isPlaying = false;
//                                    for (Player p: world.getPlayers()) {
//                                        p.setGameMode(GameMode.ADVENTURE);
//                                        p.teleport(StartPlace);
//                                        p.sendTitle("Gameset!","ゲームが終了しました",0,40 ,0);
//                        }
//        }
//
//
//
//
//
//
//    }
//            }
//
//
//
//        }
//            }
//
//    }
//
//
//
