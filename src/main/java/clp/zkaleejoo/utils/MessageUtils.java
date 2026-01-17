package clp.zkaleejoo.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtils {

    public static String getColoredMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void broadcastToPlayersOnly(String message) {
        String coloredMessage = getColoredMessage(message);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(coloredMessage);
        }
    }
}
