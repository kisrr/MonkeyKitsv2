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

public class PremadeKit {

    public PremadeKit(Player player, boolean copykit, String code) {
        UUID unique = player.getUniqueId();

        Inventory premadeKit = Bukkit.createInventory(player, 54, "§5§lPremade Kit");

        try {
            if (copykit) premadeKit.setContents(Main.codeMap.get(code));
            else premadeKit.setContents(Main.kitMap.get("system").get("premadekit"));
        } catch (Exception ignored) {

        }

        List<String> lore = new ArrayList<>();

        if (player.hasPermission("monkeykits.edit")) {
            lore.add("§7Order doesn't matter!");
        }

        ItemStack item = ItemUtils.getItem("§7§l← ARMOR + OFFHAND", Material.BLUE_STAINED_GLASS_PANE, lore);

        for (int i = 41; i <= 44; i++) {
            premadeKit.setItem(i, item);
        }

        lore.clear();

        item = ItemUtils.getItem("§a§lSAVE CHANGES", Material.GREEN_TERRACOTTA, lore);

        if (player.hasPermission("monkeykits.edit")) {
            premadeKit.setItem(45, item);

            item = ItemUtils.getItem("§a§lIMPORT CURRENT INVENTORY", Material.CHEST, null);

            premadeKit.setItem(47, item);

            item = ItemUtils.getItem("§a§lCOPY", Material.BOOK, null);

            premadeKit.setItem(48, item);

            item = ItemUtils.getItem("§a§lCLEAR", Material.STRUCTURE_VOID, null);

            premadeKit.setItem(49, item);

            item = ItemUtils.getItem("§a§lREPAIR", Material.EXPERIENCE_BOTTLE, null);

            premadeKit.setItem(50, item);
        } else {
            item = ItemUtils.getItem("§cEXIT", Material.OAK_DOOR, lore);
            premadeKit.setItem(45, item);
        }

        lore.clear();

        item = ItemUtils.getItem(" ", Material.BLACK_STAINED_GLASS_PANE, null);

        for (int i = 45; i <= 53; i++) {
            if (premadeKit.getItem(i) == null)
                premadeKit.setItem(i, item);
        }

        player.openInventory(premadeKit);

        Main.premadeKitChecker.add(unique);
    }
}
