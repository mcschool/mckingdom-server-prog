package mckd.me.prog;

import mckd.me.prog.Worlds.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class Prog extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("hello");
        new MainWorld(this);
        new TestWorld(this);
        new TntWorld(this);
        new SnowWorld(this);
        new AnvilWorld(this);
        new FallColorWorld(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
