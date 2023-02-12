package me.kisr.monkeykits.events;

import me.kisr.monkeykits.Main;
import me.kisr.monkeykits.gui.KitMenu;
import me.kisr.monkeykits.utils.EnderChestUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public class EnderChestEditorEvent implements Listener {

    @EventHandler
    public void on(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID unique = player.getUniqueId();

        if (event.getClickedInventory() instanceof PlayerInventory)
            return;

        if (!Main.echestEditorChecker.containsKey(unique))
            return;

        if (event.getSlot() >= 27) {
            event.setCancelled(true);
        }

        if (event.getSlot() == 27) {
            new KitMenu(player);
        }

        if (event.getSlot() == 30) {
            for (int i = 0; i <= 26; i++) {
                event.getInventory().setItem(i, null);
            }
        }
    }

    @EventHandler
    public void on(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        UUID unique = player.getUniqueId();

        if (Main.echestEditorChecker.containsKey(unique)) {
            int echest = Main.echestEditorChecker.get(unique);
            EnderChestUtils.save(player, echest);
            Main.echestEditorChecker.remove(unique);
        }
    }
}
