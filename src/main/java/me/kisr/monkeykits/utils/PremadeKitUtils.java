package me.kisr.monkeykits.utils;

import me.kisr.monkeykits.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class PremadeKitUtils {

    private static final FileConfiguration config = Main.instance.getConfig();

    public static void claim(Player player) {
        try {
            player.getInventory().setContents(Main.kitMap.get("system").get("premadekit"));
            player.sendMessage("§dLoaded §bPremade Kit §d!");

            if (config.getBoolean("premadekit-enabled")) {
                Bukkit.getServer().broadcastMessage(ConfigUtils.getColoredString("premadekit-prefix") + player.getName() + ConfigUtils.getColoredString("premadekit-suffix"));
            }
        } catch (Exception ignored) {
            player.sendMessage("§6Premade Kit §chas not been created! ");

        }
    }

    public static void save(Player player) {

        ItemStack[] kitItems = Arrays.copyOfRange(player.getOpenInventory().getTopInventory().getContents(), 0, 41);
        ItemStack[] equipmentItems = new ItemStack[5];

        boolean kitItemsEmpty = true;

        for (int i = 0; i <= 40; i++) {
            if (kitItems[i] != null) {
                kitItemsEmpty = false;
                break;
            }
        }

        if (kitItemsEmpty) {
            Main.kitMap.get("system").remove("premadekit");
            return;
        }

        for (int i = 36; i <= 40; i++) {
            equipmentItems[i - 36] = kitItems[i];
            kitItems[i] = null;
        }

        for (int i = 0; i <= 4; i++) {
            if (equipmentItems[i] != null) {
                String item = equipmentItems[i].getType().toString();
                if (item.contains("BOOTS")) {
                    kitItems[36] = equipmentItems[i];
                } else if (item.contains("LEGGINGS")) {
                    kitItems[37] = equipmentItems[i];
                } else if (item.contains("CHESTPLATE") || item.contains("ELYTRA")) {
                    kitItems[38] = equipmentItems[i];
                } else if (item.contains("HELMET")) {
                    kitItems[39] = equipmentItems[i];
                } else {
                    kitItems[40] = equipmentItems[i];
                }
            }
        }

        Main.kitMap.get("system").put("premadekit", kitItems);

        player.sendMessage("§bPremade Kit §dsaved!");
    }
}
