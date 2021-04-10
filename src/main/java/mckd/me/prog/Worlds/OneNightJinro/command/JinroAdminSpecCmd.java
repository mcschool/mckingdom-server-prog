package mckd.me.prog.Worlds.OneNightJinro.command;

import mckd.me.prog.Prog;
import mckd.me.prog.Worlds.OneNightJinro.MConJinro;
import mckd.me.prog.Worlds.OneNightJinro.Utility;
import mckd.me.prog.Worlds.OneNightJinro.player.JinroPlayers;
import mckd.me.prog.Worlds.OneNightJinro.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class JinroAdminSpecCmd implements TabExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if( args.length <= 1 ){
            sendCmdHelp((Player)sender);
            return true;
        }
        Player p = Bukkit.getPlayerExact( args[1] );
        if( p == null ){
            sender.sendMessage( Prog.getPrefix() + ChatColor.RED + "指定プレイヤーはオフラインです。" );
            return true;
        }
        PlayerData pd = JinroPlayers.getData(p.getUniqueId());
        if( !pd.getPlayingType().equals(PlayerData.PlayingType.Spectator) ){
            pd.setPlayingType(PlayerData.PlayingType.Spectator);
            sender.sendMessage( Prog.getPrefix() + ChatColor.GREEN + p.getName() + "を観戦者に設定しました。" );
            p.sendMessage(Prog.getPrefix() + ChatColor.YELLOW + "あなたは観戦者に設定されました。" );
        } else {
            pd.setPlayingType(PlayerData.PlayingType.Player);
            sender.sendMessage( Prog.getPrefix() + ChatColor.GREEN + p.getName() + "を参加者に設定しました。" );
            p.sendMessage(Prog.getPrefix() + ChatColor.YELLOW + "あなたは参加者に設定されました。" );
        }
        JinroPlayers.setData(p.getUniqueId(), pd);
        return true;
    }

    private void sendCmdHelp(Player sender) {
        Utility.sendCmdHelp(sender, "/jinro_ad spec <Player>", "指定プレイヤーを観戦モードへ変更します。(自動役配布の対象外)");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> out = new ArrayList<String>();
        if( args.length == 2 ){
            for( Player p : Bukkit.getOnlinePlayers() ){
                if ( p.getName().toLowerCase().startsWith(args[1].toLowerCase()) ) {
                    out.add(p.getName());
                }
            }
        }
        return out;
    }
}
