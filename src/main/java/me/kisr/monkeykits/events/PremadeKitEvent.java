package me.kisr.monkeykits.events;

import me.kisr.monkeykits.Main;
import me.kisr.monkeykits.gui.KitMenu;
import me.kisr.monkeykits.utils.PremadeKitUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public class PremadeKitEvent implements Listener {

    @EventHandler
    public void on(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID unique = player.getUniqueId();

        if (!Main.premadeKitChecker.contains(unique))
            return;

        if (player.hasPermission("monkeykits.edit")) {
            if (event.getClickedInventory() instanceof PlayerInventory)
                return;

            if (event.getSlot() >= 41) {
                event.setCancelled(true);
            }

            if (event.getSlot() == 45) {
                PremadeKitUtils.save(player);
            }

            if (event.getSlot() == 48) {
                for (int i = 0; i <= 40; i++) {
                    event.getInventory().setItem(i, null);
                }
            }

            if (event.getSlot() == 49) {
                for (int i = 0; i <= 40; i++) {
                    event.getInventory().setItem(i, player.getInventory().getItem(i));
                }
            }
        } else {
            event.setCancelled(true);

            if (event.getSlot() == 45) {
                new KitMenu(player);
            }
        }
    }

    @EventHandler
    public void on(InventoryCloseEvent event) {
        Main.premadeKitChecker.remove(event.getPlayer().getUniqueId());
    }
}
