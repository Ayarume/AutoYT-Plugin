package pl.ayarume.youtubebot.mysql;

import pl.ayarume.youtubebot.config.ConfigManager;

public class MySQLManager {
   private static MySQL mySQL;

   public static void register() {
      mySQL = new MySQL(ConfigManager.getHost(), ConfigManager.getPort(), ConfigManager.getUser(), ConfigManager.getPass(), ConfigManager.getDatabase());
      mySQL.send("CREATE TABLE IF NOT EXISTS `videos` (`uuid` varchar(36) NOT NULL, `id` text NOT NULL)");
   }

   public static MySQL getMySQL() {
      return mySQL;
   }
}
