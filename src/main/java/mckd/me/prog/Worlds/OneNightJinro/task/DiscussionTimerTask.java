package mckd.me.prog.Worlds.OneNightJinro.task;


import mckd.me.prog.Worlds.OneNightJinro.GameStatus;
import mckd.me.prog.Worlds.OneNightJinro.MConJinro;
import mckd.me.prog.Worlds.OneNightJinro.Utility;
import mckd.me.prog.Worlds.OneNightJinro.player.JinroPlayers;
import mckd.me.prog.Worlds.OneNightJinro.player.Job;
import mckd.me.prog.Worlds.OneNightJinro.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.text.Normalizer;

public class DiscussionTimerTask extends BaseTimerTask {

    // 議論時間のタイマー

    public DiscussionTimerTask(MConJinro pl, int sec) {
        super(pl, sec);
    }

    @Override
    public void start() {
        super.start();
        GameStatus.Cycle.setCycle(GameStatus.Cycle.Discussion);
        MConJinro.getRespawnLoc().getWorld().setTime(6000);
        Bukkit.broadcastMessage(ChatColor.RED + "===============[朝になりました]===============");
        Bukkit.broadcastMessage(ChatColor.AQUA + "中央の広場にお集まりください。");
        Bukkit.broadcastMessage(ChatColor.GREEN + "/jinro でコマンドの詳細を確認できます。");
        Bukkit.broadcastMessage(ChatColor.GREEN + "チャットに「@」(全角半角問わない)をつけると文章を強調できます。");

    }

    @Override
    public void exec() {

    }

    public void EndExec() {
        BaseTask task = new VoteTask(getPlugin());
        task.start();
        MConJinro.setTask(task);
    }

    @Override
    public void onChat(AsyncPlayerChatEvent e) {
        if ((getSecondsMax() - getSeconds()) < 5) {
            e.getPlayer().sendMessage(MConJinro.getPrefix() + ChatColor.RED + "最初の5秒間は発言できません。");
            return;
        }

        if (Normalizer.normalize(e.getMessage(), Normalizer.Form.NFKC).toLowerCase().contains("co")) {
            // ORB_PICKUP
            if (MConJinro.getMain().getConfig().getBoolean("NoticeComingOut")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, (float) 0.1, 1);
                }
            }
        }
        if (Normalizer.normalize(e.getMessage(), Normalizer.Form.NFKC).toLowerCase().contains("@")) {
            String f = Utility.myReplaceAll("@", "", e.getMessage());
            f = Utility.myReplaceAll("＠", "", f);
            e.setMessage(ChatColor.BOLD + f);
        }

        PlayerData pd = JinroPlayers.getData(e.getPlayer());
        Job coming = pd.getComingOut();
        Job.Marker ma = pd.getMarker();
        if ( !MConJinro.getMain().getConfig().getBoolean("ShowComingOut")) {
            Bukkit.broadcastMessage(ChatColor.WHITE + "<" + e.getPlayer().getName() + "> " + e.getMessage());
        } else {
            if( coming != null ){
                Bukkit.broadcastMessage(coming.getColor() + "[" +coming.getJobName2Moji() + "]" + ChatColor.WHITE + " <" + e.getPlayer().getName() + "> " + e.getMessage());
            } else if( ma != null ){
                Bukkit.broadcastMessage("[" + ma.getString() + "]" + ChatColor.WHITE + " <" + e.getPlayer().getName() + "> " + e.getMessage());
            } else {
                Bukkit.broadcastMessage(ChatColor.WHITE + "<" + e.getPlayer().getName() + "> " + e.getMessage());
            }
        }

    }
}