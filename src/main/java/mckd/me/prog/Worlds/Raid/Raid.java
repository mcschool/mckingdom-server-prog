package mckd.me.prog.Worlds.Raid;

import mckd.me.prog.Prog;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Raid implements Listener {
    private Prog plugin;
    public String worldName = "raid";
    public Location startPlace;//スタート地点
    public int LimitTime = 600;//ゲームの制限時間

    public Raid(Prog plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        this.startPlace = new Location(Bukkit.getWorld(this.worldName),297,68,470);
    }

/*    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        player.sendMessage("test");
        BukkitTask task = new GameTimer(this.plugin, LimitTime).runTaskTimer(this.plugin,0,20);
    }*/

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
        if (block.getType() == Material.CHEST) {
            Chest chest = (Chest) e.getBlock().getState();
            Inventory chestInv = chest.getBlockInventory();
            chestInv.addItem(new ItemStack(Material.APPLE,1));
        }
    }
    @EventHandler
    public void test(InventoryMoveItemEvent e) {
        Bukkit.broadcastMessage("test");
    }
}
