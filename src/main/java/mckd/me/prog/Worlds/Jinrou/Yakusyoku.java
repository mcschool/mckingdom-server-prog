package mckd.me.prog.Worlds.Jinrou;

import org.bukkit.entity.Player;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public enum Yakusyoku {
    村人, 人狼, 占い師, 霊能者, 狂人;


    private static String[] YakuList = new String[]{"murabito", "uranai", "reinou", "jinro", "kyoujin"};


    public static List<String> getPlayingPlayersName() {
        ArrayList<String> out = new ArrayList<>();
        out.add("null回避だよ・。・");
        if (Data.getConfig().get("Players") != null) {
            Data.getConfig().getConfigurationSection("Players").getKeys(false).forEach((String key) -> {
                out.add(key);
            });
        }
        return out;
    }

    public static String[] getYakuList() {
        return YakuList;
    }

    public static void addYaku(Player p, Yakusyoku y) {
        Data.set("Players." + p.getUniqueId() + ".yaku", getYakuToName(y).toString());
        return;
    }



    public static String getYakuToName(Yakusyoku name) {
        String out = null;
        switch (name) {
            case 村人:
                out = "murabito";
                break;
            case 人狼:
                out = "jinro";
                break;
            case 占い師:
                out = "uranai";
                break;
            case 霊能者:
                out = "reinou";
                break;
            case 狂人:
                out = "kyoujin";
                break;
        }
        return out;
    }



}



