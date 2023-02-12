package me.kisr.monkeykits.events;

import me.kisr.monkeykits.Main;
import me.kisr.monkeykits.gui.KitMenu;
import me.kisr.monkeykits.utils.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.UUID;

public class KitRoomEvent implements Listener {
    @EventHandler
    public void on(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID unique = player.getUniqueId();

        if (!Main.kitRoomChecker.containsKey(unique))
            return;

        if (event.getClickedInventory() instanceof PlayerInventory)
            return;

        if (event.getSlot() >= 45) {
            event.setCancelled(true);
        }

        if (event.getCurrentItem() != null
                && event.getCurrentItem().getType().toString().contains("SIGN")
                && !player.hasPermission("monkeykits.edit")) {
            event.setCancelled(true);
        }

        if (47 <= event.getSlot() && event.getSlot() <= 51) {
            int roomkey = Main.kitRoomChecker.get(unique);

            ItemStack item = ItemUtils.disenchant(event.getInventory().getItem(roomkey + 46));
            event.getInventory().setItem(roomkey + 46, item);

            int newPage = event.getSlot() - 46;

            item = ItemUtils.enchant(event.getCurrentItem());
            event.getInventory().setItem(event.getSlot(), item);

            for (int i = 0; i <= 44; i++) {
                event.getInventory().setItem(i, Main.kitRoomMap.get(newPage)[i]);
            }

            Main.kitRoomChecker.put(unique, newPage);
        }

        if (event.getSlot() == 53) {
            for (int i = 0; i <= 44; i++) {
                event.getInventory().setItem(i, Main.kitRoomMap.get(Main.kitRoomChecker.get(unique))[i]);
            }
        }

        if (event.getSlot() == 45) {
            if (player.hasPermission("monkeykits.edit")) {
                ItemStack[] items = Arrays.copyOfRange(event.getInventory().getContents(), 0, 45);

                Main.kitRoomMap.put(Main.kitRoomChecker.get(unique), items);

                event.getWhoClicked().sendMessage("§bPage " + Main.kitRoomChecker.get(unique) + " §dsaved!");
            } else {
                new KitMenu(player);
            }
        }
    }

    @EventHandler
    public void on(InventoryCloseEvent event) {
        UUID unique = event.getPlayer().getUniqueId();

        Main.kitRoomChecker.remove(unique);
    }
}
