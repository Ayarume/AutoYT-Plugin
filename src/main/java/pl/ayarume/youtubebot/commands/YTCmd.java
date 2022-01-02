package pl.ayarume.youtubebot.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.JSONException;
import pl.ayarume.youtubebot.Main;
import pl.ayarume.youtubebot.config.ConfigManager;
import pl.ayarume.youtubebot.mysql.MySQLManager;
import pl.ayarume.youtubebot.objects.Video;
import pl.ayarume.youtubebot.objects.VideoManager;
import pl.ayarume.youtubebot.utils.StatusEnum;
import pl.ayarume.youtubebot.utils.Utils;
import pl.ayarume.youtubebot.utils.YouTubeUtils;

import java.io.IOException;
import java.util.Iterator;

public class YTCmd implements CommandExecutor {

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      Player p = (Player)sender;
      Utils.sendMsg(p, "&7Wymagania na range &cY&fT&8: \n&f- 1000 subskrypcji\n&f- minimum 300 wyswietlen pod filmem z serwera\n\n&aAby odebrac wpisz /ytlink <link do filmu>");
      return false;
   }
}
