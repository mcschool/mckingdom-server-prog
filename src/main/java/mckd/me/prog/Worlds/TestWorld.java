package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;
import org.bukkit.event.Listener;

public class TestWorld implements Listener {
    public TestWorld(Prog plugin){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }
}
