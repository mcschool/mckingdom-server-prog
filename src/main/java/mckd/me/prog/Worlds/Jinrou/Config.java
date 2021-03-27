package mckd.me.prog.Worlds.Jinrou;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class Config {
    FileConfiguration customConfig = null;
    File customConfigFile = null;
    //Jinro main = Jinro.getMain();
    String FileName;

    Config(String FileName) {
        this.customConfig = null;
        this.customConfigFile = null;
        this.FileName = FileName;
    }


    FileConfiguration getConfig() {
        if (customConfig == null) {
            //reloadConfig();
        }
        return customConfig;
    }



    void set(String path, Object value) {
        getConfig().set(path, value);
        //saveConfig();
    }
}