package me.kisr.monkeykits.events;

import me.kisr.monkeykits.Main;
import me.kisr.monkeykits.gui.CopyKit;
import me.kisr.monkeykits.gui.KitMenu;
import me.kisr.monkeykits.utils.KitShareUtils;
import me.kisr.monkeykits.utils.KitUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.UUID;

public class KitEditorEvent implements Listener {
    private static final FileConfiguration config = Main.instance.getConfig();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID unique = player.getUniqueId();

        if (event.getClickedInventory() instanceof PlayerInventory)
            return;

        if (!Main.kitEditorChecker.containsKey(unique))
            return;

        int kit = Main.kitEditorChecker.get(unique);

        if (event.getSlot() >= 41) {
            event.setCancelled(true);
        }

        if (event.getSlot() == 45) {
            new KitMenu(player);
        }

        if (event.getSlot() == 47) {
            for (int i = 0; i <= 40; i++) {
                event.getInventory().setItem(i, player.getInventory().getItem(i));
            }
        }

        if (event.getSlot() == 48) {
            if (!KitUtils.isKitEmpty(player)) {
                int delay = config.getInt("expire-time");
                String code = KitShareUtils.generateCode();
                ItemStack[] items = Arrays.copyOfRange(player.getOpenInventory().getTopInventory().getContents(), 0, 41);
                Main.codeMap.put(code, items);
                player.sendMessage("§dUse code §b" + code + " §dto share your kit, expires in " + delay + " minutes.");
                player.closeInventory();

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Main.codeMap.remove(code);
                    }
                }.runTaskLater(Main.instance, 20L * 60 * delay);
            } else {
                player.sendMessage("§cCannot share an empty kit.");
            }
        }

        if (event.getSlot() == 49) {
            player.closeInventory();
            new CopyKit(player, kit, false);
        }

        if (event.getSlot() == 50) {
            for (int i = 0; i <= 40; i++) {
                event.getInventory().setItem(i, null);
            }
        }

        if (event.getSlot() == 51) {
            for (int i = 0; i <= 40; i++) {
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

        if (Main.kitEditorChecker.containsKey(unique)) {
            int kit = Main.kitEditorChecker.get(unique);
            KitUtils.save(player, kit, true);
            Main.kitEditorChecker.remove(unique);
        }
    }
}
