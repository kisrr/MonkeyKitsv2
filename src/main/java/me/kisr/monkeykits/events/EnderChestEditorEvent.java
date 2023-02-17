package me.kisr.monkeykits.events;

import me.kisr.monkeykits.Main;
import me.kisr.monkeykits.gui.KitMenu;
import me.kisr.monkeykits.utils.EnderChestUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;

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

        if (event.getSlot() == 31) {
            for (int i = 0; i <= 26; i++) {
                if (event.getInventory().getItem(i) != null) {
                    ItemStack item = event.getInventory().getItem(i);

                    if (item.getItemMeta() instanceof Damageable) {
                        Damageable meta = (Damageable) item.getItemMeta();

                        if (meta.hasDamage()) {
                            meta.setDamage(0);
                            item.setItemMeta(meta);
                            event.getInventory().setItem(i, item);
                        }
                    }
                }
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
