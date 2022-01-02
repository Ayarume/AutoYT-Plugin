package pl.ayarume.youtubebot.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {
   public static String fixColor(String s) {
      return ChatColor.translateAlternateColorCodes('&', s);
   }

   public static List<String> fixColor(List<String> s) {
      ArrayList<String> list = new ArrayList();
      Iterator var3 = s.iterator();

      while(var3.hasNext()) {
         String str = (String)var3.next();
         list.add(fixColor(str));
      }

      return list;
   }

   public static void sendMsg(CommandSender p, String msg) {
      p.sendMessage(fixColor(msg));
   }

   public static void sendMsg(Player p, String msg) {
      p.sendMessage(fixColor(msg));
   }

   public static void sendMsg(Collection<? extends Player> players, String msg) {
      Iterator var3 = players.iterator();

      while(var3.hasNext()) {
         Player p = (Player)var3.next();
         p.sendMessage(fixColor(msg));
      }

   }

   public static void sendMsg(Player[] players, String msg) {
      Player[] var5 = players;
      int var4 = players.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Player p = var5[var3];
         p.sendMessage(fixColor(msg));
      }

   }
}
