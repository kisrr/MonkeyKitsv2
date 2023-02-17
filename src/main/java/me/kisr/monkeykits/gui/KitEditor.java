package me.kisr.monkeykits.gui;

import me.kisr.monkeykits.Main;
import me.kisr.monkeykits.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class KitEditor {
    public KitEditor(Player player, int kit, boolean copykit, String code) {
        UUID unique = player.getUniqueId();
        String uuid = unique.toString();
        String kitkey = "kit" + kit;
        String kitname = "Kit " + kit;

        Inventory kitEditor = Bukkit.createInventory(player, 54, "§5§l" + kitname);

        try {
            if (copykit) kitEditor.setContents(Main.codeMap.get(code));
            else kitEditor.setContents(Main.kitMap.get(uuid).get(kitkey));
        } catch (Exception ignored) {

        }

        List<String> lore = new ArrayList<>();
        lore.add("§7Order doesn't matter!");

        ItemStack item = ItemUtils.getItem("§7§l← ARMOR + OFFHAND", Material.BLUE_STAINED_GLASS_PANE, lore);

        for (int i = 41; i <= 44; i++) {
            kitEditor.setItem(i, item);
        }

        lore.clear();

        item = ItemUtils.getItem("§cEXIT", Material.OAK_DOOR, null);

        kitEditor.setItem(45, item);

        item = ItemUtils.getItem("§a§lIMPORT CURRENT INVENTORY", Material.CHEST, null);

        kitEditor.setItem(47, item);

        item = ItemUtils.getItem("§a§lSHARE", Material.COMPASS, null);

        kitEditor.setItem(48, item);

        item = ItemUtils.getItem("§a§lCOPY", Material.BOOK, null);

        kitEditor.setItem(49, item);

        item = ItemUtils.getItem("§a§lCLEAR", Material.STRUCTURE_VOID, null);

        kitEditor.setItem(50, item);

        item = ItemUtils.getItem(" ", Material.BLACK_STAINED_GLASS_PANE, null);

        for (int i = 45; i <= 53; i++) {
            if (kitEditor.getItem(i) == null)
                kitEditor.setItem(i, item);
        }

        player.openInventory(kitEditor);

        Main.kitEditorChecker.put(unique, kit);
    }
}
