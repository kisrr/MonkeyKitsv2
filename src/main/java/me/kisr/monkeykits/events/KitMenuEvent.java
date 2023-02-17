package me.kisr.monkeykits.events;

import me.kisr.monkeykits.Main;
import me.kisr.monkeykits.gui.EnderChestEditor;
import me.kisr.monkeykits.gui.KitEditor;
import me.kisr.monkeykits.gui.KitRoom;
import me.kisr.monkeykits.gui.PremadeKit;
import me.kisr.monkeykits.utils.ConfigUtils;
import me.kisr.monkeykits.utils.KitUtils;
import me.kisr.monkeykits.utils.PremadeKitUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public class KitMenuEvent implements Listener {
    private static final FileConfiguration config = Main.instance.getConfig();

    @EventHandler
    public void on(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID unique = player.getUniqueId();

        if (!Main.kitMenuChecker.contains(unique))
            return;

        if (event.getClickedInventory() instanceof PlayerInventory)
            return;

        event.setCancelled(true);

        if (10 <= event.getSlot() && event.getSlot() <= 16) {
            int kit = event.getSlot() - 9;

            if (event.isLeftClick()) {
                KitUtils.claim(player, kit, false);
                player.closeInventory();
            } else if (event.isRightClick()) {
                new KitEditor(player, kit, false, null);
            }
        }

        if (19 <= event.getSlot() && event.getSlot() <= 25) {
            if (!config.getBoolean("disable-echest")) {
                int echest = event.getSlot() - 18;

                new EnderChestEditor(player, echest);
            }
        }

        if (event.getSlot() == 40 && config.getBoolean("info-enabled")) {
            player.closeInventory();

            for (String s : config.getStringList("info")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
            }
        }

        if (event.getSlot() == 39) {
            new KitRoom(player);

            if (config.getBoolean("open-kitroom-enabled")) {
                Bukkit.getServer().broadcastMessage(ConfigUtils.getColoredString("open-kitroom-prefix") + player.getName() + ConfigUtils.getColoredString("open-kitroom-suffix"));
            }
        }

        if (event.getSlot() == 41) {
            if (event.isRightClick()) {
                new PremadeKit(player, false, null);
            } else if (event.isLeftClick()) {
                PremadeKitUtils.claim(player);

                player.closeInventory();
            }
        }
    }

    @EventHandler
    public void on(InventoryCloseEvent event) {
        Main.kitMenuChecker.remove(event.getPlayer().getUniqueId());
    }
}
