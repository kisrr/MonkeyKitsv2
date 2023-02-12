package me.kisr.monkeykits.events;

import me.kisr.monkeykits.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class JoinEvent implements Listener {
    private static final FileConfiguration config = Main.instance.getConfig();

    @EventHandler
    public void on(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();

        Main.kitMap.putIfAbsent(uuid, new HashMap<>());
        Main.echestMap.putIfAbsent(uuid, new HashMap<>());

        if (config.getBoolean("motd-enabled")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (String s : config.getStringList("motd")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
                    }
                }
            }.runTaskLater(Main.instance, config.getInt("motd-delay"));
        }
    }
}
