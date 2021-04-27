package mckd.me.prog.Worlds.OneNightJinro.player;

import mckd.me.prog.Prog;
import mckd.me.prog.Worlds.OneNightJinro.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.JSONObject;
import org.json.JSONArray;


import java.util.UUID;
import java.util.logging.Logger;


/**
 * ツールで生成したやつを反映させるやつ
 */
public class ToolImport {

    private static Logger logger = Prog.getMain().getLogger();

    public static void Import(String Json){
        Import( new JSONObject(Json) );
    }

    public static void Import(JSONObject jsonObject){
        for( Job j : Job.values() ){
            logger.info( "===================["+j.getJobID()+"]" );
            for( Object o : jsonObject.getJSONArray(j.getJobID()) ){
                try {
                    UUID uuid = UUIDFetcher.getUUIDOf(o.toString());
                    JinroPlayers.addPlayer(uuid, j);
                    Player p = Bukkit.getPlayer(uuid);
                    if( p != null ){
                        p.sendMessage(ChatColor.RED + "=== " + ChatColor.WHITE + "あなたは " + j.getColor() + "[" + j.getJobName() + "]" + ChatColor.WHITE + " です。" + ChatColor.RED + " ===");
                        logger.info( p.getName() + "("+uuid+")" );
                    } else {
                        logger.info( "[Not Found] ("+uuid+")" );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
