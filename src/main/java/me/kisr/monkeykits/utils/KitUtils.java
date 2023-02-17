package me.kisr.monkeykits.utils;

import me.kisr.monkeykits.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class KitUtils {

    private static final FileConfiguration config = Main.instance.getConfig();

    public static void claim(Player player, int kit, boolean command) {
        String uuid = player.getUniqueId().toString();
        String kitkey = "kit" + kit;
        String kitname = "Kit " + kit;

        try {
            player.getInventory().setContents(Main.kitMap.get(uuid).get(kitkey));
            EnderChestUtils.claim(player, kit);
            player.sendMessage("§dLoaded §b" + kitname + "§d!");

            if (config.getBoolean("loadkit-enabled")) {
                Bukkit.getServer().broadcastMessage(ConfigUtils.getColoredString("loadkit-prefix") + player.getName() + ConfigUtils.getColoredString("loadkit-suffix"));
            }
        } catch (Exception e) {
            String message = "§6" + kitname + "§c has not been created! ";

            if (command) {
                message += "Type §6/k §cor §6/kit §cto get started!";
            } else {
                message += "Right click the chest to customize!";
            }

            player.sendMessage(message);
        }
    }

    public static void save(Player player, int kit, boolean message) {
        String uuid = player.getUniqueId().toString();
        String kitkey = "kit" + kit;

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
            Main.kitMap.get(uuid).remove(kitkey);
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

        Main.kitMap.get(uuid).put(kitkey, kitItems);

        if (message) {
            player.sendMessage("§dSaved §bKit " + kit + "§d! Type §b/k" + kit + "§d or §b/kit" + kit + " §dto load!");
        }
    }

    public static boolean isKitEmpty(Player player) {
        ItemStack[] kitItems = Arrays.copyOfRange(player.getOpenInventory().getTopInventory().getContents(), 0, 41);

        boolean kitItemsEmpty = true;

        for (int i = 0; i <= 40; i++) {
            if (kitItems[i] != null) {
                kitItemsEmpty = false;
                break;
            }
        }

        return kitItemsEmpty;
    }
}
