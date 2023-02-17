package me.kisr.monkeykits.gui;

import me.kisr.monkeykits.Main;
import me.kisr.monkeykits.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KitMenu {
    private static final FileConfiguration config = Main.instance.getConfig();

    public KitMenu(Player player) {
        Inventory kitMenu = Bukkit.createInventory(player, 54, "§9§l" + player.getName() + "'s Kits");

        List<String> lore = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            lore.add(ChatColor.LIGHT_PURPLE + "to edit:");
            lore.add(ChatColor.LIGHT_PURPLE + "• right click this chest");
            lore.add(ChatColor.GRAY + "to load:");
            lore.add(ChatColor.GRAY + "• /k" + i);
            lore.add(ChatColor.GRAY + "• /kit" + i);

            ItemStack item = ItemUtils.getItem("§b§lKit " + i, Material.CHEST, lore);

            kitMenu.setItem(i + 9, item);
            lore.clear();
        }

        for (int i = 1; i <= 7; i++) {
            if (config.getBoolean("disable-echest")) {
                lore.add(ChatColor.GRAY + "Disabled");
            } else {
                lore.add(ChatColor.LIGHT_PURPLE + "to edit:");
                lore.add(ChatColor.LIGHT_PURPLE + "• click this ender chest");
                lore.add(ChatColor.GRAY + "to load:");
                lore.add(ChatColor.GRAY + "• /k" + i);
                lore.add(ChatColor.GRAY + "• /kit" + i);
            }

            ItemStack item = ItemUtils.getItem("§6§lEnder Chest " + i, Material.ENDER_CHEST, lore);
            kitMenu.setItem(i + 18, item);
            lore.clear();
        }

        ItemStack item = ItemUtils.getItem(" ", Material.LIGHT_BLUE_STAINED_GLASS_PANE, null);

        for (int i = 28; i <= 34; i++) {
            kitMenu.setItem(i, item);
        }

        item = ItemUtils.getItem(" ", Material.BLACK_STAINED_GLASS_PANE, null);

        kitMenu.setItem(37, item);
        kitMenu.setItem(38, item);

        kitMenu.setItem(42, item);
        kitMenu.setItem(43, item);

        item = ItemUtils.getItem("§5§lKit Room", Material.NETHER_STAR, null);

        kitMenu.setItem(39, item);

        item = ItemUtils.getItem("§7§lINFO", Material.OAK_SIGN, null);

        kitMenu.setItem(40, item);

        lore.add(ChatColor.LIGHT_PURPLE + "to view:");
        lore.add(ChatColor.LIGHT_PURPLE + "• right click this emerald");
        lore.add(ChatColor.GRAY + "to load:");
        lore.add(ChatColor.GRAY + "• left click this emerald");

        item = ItemUtils.getItem("§a§lPremade Kit", Material.EMERALD, lore);

        kitMenu.setItem(41, item);
        lore.clear();

        item = ItemUtils.getItem(" ", Material.BLUE_STAINED_GLASS_PANE, null);

        for (int i = 0; i < 54; ++i) {
            if (kitMenu.getItem(i) == null) {
                kitMenu.setItem(i, item);
            }
        }

        player.openInventory(kitMenu);

        Main.kitMenuChecker.add(player.getUniqueId());
    }
}
