package me.kisr.monkeykits.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class KitRoomFile {

    private static File file;
    private static FileConfiguration config;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("MonkeyKits").getDataFolder(), "kitroom.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                Bukkit.getLogger().warning("Couldn't create kitroom.yml");
                Bukkit.getLogger().warning(e.toString());
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return config;
    }

    public static void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Couldn't save kitroom.yml");
            Bukkit.getLogger().warning(e.toString());
        }
    }

    public static void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }
}