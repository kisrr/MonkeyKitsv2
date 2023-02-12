package me.kisr.monkeykits.utils;

import me.kisr.monkeykits.Main;
import me.kisr.monkeykits.files.KitRoomFile;
import me.kisr.monkeykits.files.KitsFile;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class FileUtils {

    public static void restore() {
        try {
            if (KitsFile.get().contains("kits")) {
                for (String uuid : KitsFile.get().getConfigurationSection("kits").getKeys(false)) {
                    Main.kitMap.putIfAbsent(uuid, new HashMap<>());

                    for (String kitkey : KitsFile.get().getConfigurationSection("kits." + uuid).getKeys(false)) {
                        ItemStack[] items = (ItemStack[]) ((List) KitsFile.get().get("kits." + uuid + "." + kitkey)).toArray(new ItemStack[0]);

                        Main.kitMap.get(uuid).put(kitkey, items);
                    }
                }

                KitsFile.get().set("kits", Main.kitMap);
                KitsFile.save();
            }
        } catch (Exception e) {
            Bukkit.getLogger().warning("Couldn't restore the KitMap");
            Bukkit.getLogger().warning(e.toString());
        }

        try {
            if (KitsFile.get().contains("echest")) {
                for (String uuid : KitsFile.get().getConfigurationSection("echest").getKeys(false)) {
                    Main.echestMap.putIfAbsent(uuid, new HashMap<>());

                    for (String echestkey : KitsFile.get().getConfigurationSection("echest." + uuid).getKeys(false)) {
                        ItemStack[] items = (ItemStack[]) ((List) KitsFile.get().get("echest." + uuid + "." + echestkey)).toArray(new ItemStack[0]);

                        Main.echestMap.get(uuid).put(echestkey, items);
                    }
                }

                KitsFile.get().set("echest", Main.echestMap);
                KitsFile.save();
            }
        } catch (Exception e) {
            Bukkit.getLogger().warning("Couldn't restore the EnderChestMap");
            Bukkit.getLogger().warning(e.toString());
        }

        for (int i = 1; i <= 5; i++) {
            try {
                ItemStack[] items = (ItemStack[]) ((List) KitRoomFile.get().get("items." + i)).toArray(new ItemStack[0]);
                Main.kitRoomMap.put(i, items);
            } catch (Exception e) {
                Bukkit.getLogger().info("Setting up Kit Room page " + i);
                Main.kitRoomMap.put(i, new ItemStack[45]);
            }
        }
    }
}
