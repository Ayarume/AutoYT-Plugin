package pl.ayarume.youtubebot.commands;

import java.io.IOException;
import java.util.Iterator;
import pl.ayarume.youtubebot.Main;
import pl.ayarume.youtubebot.config.ConfigManager;
import pl.ayarume.youtubebot.mysql.MySQLManager;
import pl.ayarume.youtubebot.objects.Video;
import pl.ayarume.youtubebot.objects.VideoManager;
import pl.ayarume.youtubebot.utils.StatusEnum;
import pl.ayarume.youtubebot.utils.Utils;
import pl.ayarume.youtubebot.utils.YouTubeUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.JSONException;

public class YouTubeCmd implements CommandExecutor {
   private void main(String id, String title, String desc, int views, StatusEnum status, int duration, Player p) {
      if (duration <= ConfigManager.getMax_duration() && duration >= ConfigManager.getMin_duration()) {
         if (!title.toLowerCase().contains(ConfigManager.getNeeded_title().toLowerCase())) {
            Utils.sendMsg(p, ConfigManager.getInvalid_title());
         } else if (!desc.toLowerCase().contains(ConfigManager.getNeeded_desc().toLowerCase())) {
            Utils.sendMsg(p, ConfigManager.getInvalid_desc());
         } else if (views < ConfigManager.getNeeded_views()) {
            Utils.sendMsg(p, ConfigManager.getInvalid_views());
         } else if (status != StatusEnum.PUBLIC) {
            Utils.sendMsg(p, ConfigManager.getInvalid_state());
         } else {
            Video v = VideoManager.createVideo(p.getUniqueId(), id);
            MySQLManager.getMySQL().saveDefaultData(p.getUniqueId(), v);
            Utils.sendMsg(p, ConfigManager.getSucc_added());
            Iterator var10 = ConfigManager.getSucc_cmds().iterator();

            while(var10.hasNext()) {
               String s = (String)var10.next();
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("{nick}", p.getName()));
            }

         }
      } else {
         Utils.sendMsg(p, ConfigManager.getInvalid_duration());
      }
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      Player p = (Player)sender;
      if (args.length != 1) {
         Utils.sendMsg(p, ConfigManager.getCorrect_command());
         return false;
      } else {
         String id;
         try {
            id = args[0].split("v=")[1].split("&")[0];
         } catch (IndexOutOfBoundsException var16) {
            Utils.sendMsg(sender, ConfigManager.getInvalid_url());
            return false;
         }

         String[] title = new String[1];
         String[] desc = new String[1];
         int[] views = new int[1];
         StatusEnum[] status = new StatusEnum[1];
         int[] duration = new int[1];
         int[] sub = new int[1];
         boolean[] privsub = new boolean[1];
         if (VideoManager.isRegistered(id)) {
            Utils.sendMsg(sender, ConfigManager.getVideo_already_registered());
            return false;
         } else if (VideoManager.hasRegistered(p.getUniqueId())) {
            Utils.sendMsg(sender, ConfigManager.getPlayer_already_registered());
            return false;
         } else {
            try {
               Object[] idch = YouTubeUtils.getInfo(id);
               Object[] o = YouTubeUtils.getInfoSub((String)idch[5]);
               sub[0] = (Integer)o[0];
               privsub[0] = (Boolean)o[1];
               if (privsub[0]) {
                  Utils.sendMsg(p, ConfigManager.getInvalid_privsub());
                  return false;
               }

               if (sub[0] < ConfigManager.getMin_Sub()) {
                  Utils.sendMsg(p, ConfigManager.getInvalid_sub());
                  return false;
               }
            } catch (JSONException | IOException var17) {
               Utils.sendMsg(sender, ConfigManager.getInvalid_url());
               return false;
            }

            Bukkit.getScheduler().runTaskAsynchronously(Main.getInst(), () -> {
               try {
                  Object[] o = YouTubeUtils.getInfo(id);
                  title[0] = (String)o[0];
                  desc[0] = (String)o[1];
                  views[0] = (Integer)o[2];
                  status[0] = (StatusEnum)o[3];
                  duration[0] = (Integer)o[4];
                  Bukkit.getScheduler().runTask(Main.getInst(), () -> {
                     this.main(id, title[0], desc[0], views[0], status[0], duration[0], p);
                  });
               } catch (JSONException | IOException var10) {
                  Utils.sendMsg(sender, ConfigManager.getInvalid_url());
               }

            });
            return false;
         }
      }
   }
}
