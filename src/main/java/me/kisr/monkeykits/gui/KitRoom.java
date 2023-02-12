package me.kisr.monkeykits.gui;

import me.kisr.monkeykits.Main;
import me.kisr.monkeykits.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.List;

public class KitRoom {

    public KitRoom(Player player) {
        Inventory kitRoom = Bukkit.createInventory(player, 54, "§5§lVirtual Kit Room");
        kitRoom.setContents(Main.kitRoomMap.get(1));

        List<String> lore = new ArrayList<>();
        ItemStack item = ItemUtils.getItem("§a§lSAVE CHANGES", Material.GREEN_TERRACOTTA, lore);

        if (player.hasPermission("monkeykits.edit")) {
            lore.add("§8Permission to see this button: monkeykits.edit");
            kitRoom.setItem(45, item);
        } else {
            item = ItemUtils.getItem("§cEXIT", Material.OAK_DOOR, null);
            kitRoom.setItem(45, item);
        }

        lore.clear();

        item = ItemUtils.getItem("§a§lREFILL", Material.STRUCTURE_VOID, null);
        kitRoom.setItem(53, item);

        ItemStack[] buttons = new ItemStack[]{ItemUtils.getItem("§aArmory", Material.NETHERITE_SWORD, null), ItemUtils.getItem("§aPotions", Material.SPLASH_POTION, null), ItemUtils.getItem("§aConsumables", Material.ENDER_PEARL, null), ItemUtils.getItem("§aArrows", Material.TIPPED_ARROW, null), ItemUtils.getItem("§aExplosives", Material.RESPAWN_ANCHOR, null)};

        int slot = 47;

        for (int i = 0; i < buttons.length; i++) {
            ItemStack button = buttons[i];
            ItemMeta meta = button.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            button.setItemMeta(meta);
            kitRoom.setItem(slot, button);
            slot++;
        }

        item = kitRoom.getItem(47);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        item.setItemMeta(meta);
        item = kitRoom.getItem(50);
        PotionMeta potMeta = (PotionMeta) item.getItemMeta();
        potMeta.setColor(Color.WHITE);
        item.setItemMeta(potMeta);

        item = ItemUtils.getItem(" ", Material.BLACK_STAINED_GLASS_PANE, null);

        for (int i = 45; i <= 53; i++) {
            if (kitRoom.getItem(i) == null) {
                kitRoom.setItem(i, item);
            }
        }

        player.openInventory(kitRoom);

        Main.kitRoomChecker.put(player.getUniqueId(), 1);
    }
}
