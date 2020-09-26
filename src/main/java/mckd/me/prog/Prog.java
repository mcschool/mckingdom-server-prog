package mckd.me.prog;

import org.bukkit.plugin.java.JavaPlugin;

public final class Prog extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("hello");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
