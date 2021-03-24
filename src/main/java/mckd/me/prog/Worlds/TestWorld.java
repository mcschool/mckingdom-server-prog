package mckd.me.prog.Worlds;
import mckd.me.prog.Prog;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
public class TestWorld implements Listener {
    private Prog plugin;
    public String worldName = "Test";

    public TestWorld(Prog plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getPlayer().getWorld().getName().equals("Test")) {
            Player player = e.getPlayer();
            Block block = e.getBlock();
            if (block.getType() == Material.SAND) {
                player.sendMessage("Hello World!");
            }
        }
    }
}
