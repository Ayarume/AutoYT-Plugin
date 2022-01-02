package pl.ayarume.youtubebot.config;

import java.util.List;
import pl.ayarume.youtubebot.Main;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
   private static String api_key;
   private static String correct_command;
   private static String needed_title;
   private static int needed_views;
   private static String needed_desc;
   private static int min_duration;
   private static int max_duration;
   private static int min_sub;
   private static List<String> succ_cmds;
   private static List<String> err_cmds;
   private static int check_delay;
   private static String invalid_url;
   private static String invalid_title;
   private static String invalid_desc;
   private static String invalid_views;
   private static String invalid_state;
   private static String invalid_duration;
   private static String invalid_sub;
   private static String invalid_privsub;
   private static String succ_added;
   private static String player_already_registered;
   private static String video_already_registered;
   private static String host;
   private static int port;
   private static String user;
   private static String pass;
   private static String database;

   public static void load() {
      FileConfiguration c = Main.getInst().getConfig();
      api_key = c.getString("api_key");
      correct_command = c.getString("correct_command");
      needed_title = c.getString("needed_title");
      needed_views = c.getInt("needed_views");
      needed_desc = c.getString("needed_desc");
      min_duration = c.getInt("min_duration");
      max_duration = c.getInt("max_duration");
      min_sub = c.getInt("min_sub");
      succ_cmds = c.getStringList("succ_cmds");
      err_cmds = c.getStringList("err_cmds");
      check_delay = c.getInt("check_delay");
      invalid_url = c.getString("invalid_url");
      invalid_title = c.getString("invalid_title");
      invalid_desc = c.getString("invalid_desc");
      invalid_views = c.getString("invalid_views");
      invalid_state = c.getString("invalid_state");
      invalid_duration = c.getString("invalid_duration");
      invalid_sub = c.getString("invalid_sub");
      invalid_privsub = c.getString("invalid_privsub");
      succ_added = c.getString("succ_added");
      player_already_registered = c.getString("player_already_registered");
      video_already_registered = c.getString("video_already_registered");
      host = c.getString("mysql.host");
      port = c.getInt("mysql.port");
      user = c.getString("mysql.user");
      pass = c.getString("mysql.pass");
      database = c.getString("mysql.database");
   }

   public static String getApi_key() {
      return api_key;
   }

   public static String getNeeded_title() {
      return needed_title;
   }

   public static int getNeeded_views() {
      return needed_views;
   }

   public static String getNeeded_desc() {
      return needed_desc;
   }

   public static List<String> getSucc_cmds() {
      return succ_cmds;
   }

   public static List<String> getErr_cmds() {
      return err_cmds;
   }

   public static int getCheck_delay() {
      return check_delay;
   }

   public static String getHost() {
      return host;
   }

   public static int getPort() {
      return port;
   }

   public static String getUser() {
      return user;
   }

   public static String getPass() {
      return pass;
   }

   public static String getDatabase() {
      return database;
   }

   public static String getInvalid_url() {
      return invalid_url;
   }

   public static String getInvalid_title() {
      return invalid_title;
   }

   public static String getInvalid_desc() {
      return invalid_desc;
   }

   public static String getInvalid_views() {
      return invalid_views;
   }

   public static String getInvalid_state() {
      return invalid_state;
   }

   public static String getInvalid_sub() {
      return invalid_sub;
   }

   public static String getInvalid_privsub() {
      return invalid_privsub;
   }

   public static String getSucc_added() {
      return succ_added;
   }

   public static String getPlayer_already_registered() {
      return player_already_registered;
   }

   public static String getVideo_already_registered() {
      return video_already_registered;
   }

   public static String getInvalid_duration() {
      return invalid_duration;
   }

   public static int getMin_duration() {
      return min_duration;
   }

   public static int getMax_duration() {
      return max_duration;
   }

   public static int getMin_Sub() {
      return min_sub;
   }

   public static String getCorrect_command() {
      return correct_command;
   }
}
