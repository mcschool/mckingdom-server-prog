package mckd.me.prog;

import mckd.me.prog.Worlds.MainWorld;
import org.bukkit.plugin.java.JavaPlugin;

public final class Prog extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("hello");
        new MainWorld(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
