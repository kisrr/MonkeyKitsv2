package me.kisr.monkeykits.gui;

import me.kisr.monkeykits.Main;
import me.kisr.monkeykits.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class EnderChestEditor {

    public EnderChestEditor(Player player, int echest) {
        UUID unique = player.getUniqueId();
        String uuid = unique.toString();
        String echestkey = "echest" + echest;
        String echestname = "Ender Chest " + echest;

        Inventory echestEditor = Bukkit.createInventory(player, 36, "§5§l" + echestname);

        try {
            echestEditor.setContents(Main.echestMap.get(uuid).get(echestkey));
        } catch (Exception ignored) {

        }

        ItemStack item = ItemUtils.getItem("§cEXIT", Material.OAK_DOOR, null);

        echestEditor.setItem(27, item);

        item = ItemUtils.getItem("§a§lCLEAR", Material.STRUCTURE_VOID, null);

        echestEditor.setItem(30, item);

        item = ItemUtils.getItem("§a§lREPAIR", Material.EXPERIENCE_BOTTLE, null);

        echestEditor.setItem(31, item);

        item = ItemUtils.getItem(" ", Material.BLACK_STAINED_GLASS_PANE, null);

        for (int i = 27; i <= 35; i++) {
            if (echestEditor.getItem(i) == null)
                echestEditor.setItem(i, item);
        }

        player.openInventory(echestEditor);

        Main.echestEditorChecker.put(unique, echest);
    }
}
