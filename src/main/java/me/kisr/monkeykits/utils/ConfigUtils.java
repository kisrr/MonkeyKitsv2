package me.kisr.monkeykits.utils;

import me.kisr.monkeykits.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtils {

    private static final FileConfiguration config = Main.instance.getConfig();

    public static String getColoredString(String path) {
        return ChatColor.translateAlternateColorCodes('&', config.getString(path));
    }
}
