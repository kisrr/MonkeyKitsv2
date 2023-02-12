package me.kisr.monkeykits.utils;

import me.kisr.monkeykits.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class EnderChestUtils {

    public static void claim(Player player, int echest) {
        String uuid = player.getUniqueId().toString();
        String echestkey = "echest" + echest;

        try {
            player.getEnderChest().setContents(Main.echestMap.get(uuid).get(echestkey));
        } catch (Exception ignored) {

        }
    }

    public static void save(Player player, int echest) {
        String uuid = player.getUniqueId().toString();
        String echestkey = "echest" + echest;

        ItemStack[] echestItems = Arrays.copyOfRange(player.getOpenInventory().getTopInventory().getContents(), 0, 26);

        boolean echestItemsEmpty = true;

        for (int i = 0; i <= 25; i++) {
            if (echestItems[i] != null) {
                echestItemsEmpty = false;
                break;
            }
        }

        if (echestItemsEmpty) {
            Main.echestMap.get(uuid).remove(echestkey);
            return;
        }

        Main.echestMap.get(uuid).put(echestkey, echestItems);

        player.sendMessage("§dSaved §6Ender Chest " + echest + "§d! Type §b/k" + echest + "§d or §b/kit" + echest + " §dto load!");
    }
}
