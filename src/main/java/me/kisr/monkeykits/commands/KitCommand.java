package me.kisr.monkeykits.commands;

import me.kisr.monkeykits.Main;
import me.kisr.monkeykits.gui.KitMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {

    private static final FileConfiguration config = Main.instance.getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command");
            return true;
        }

        Player player = (Player) sender;

        if (config.getBoolean("disabled-worlds") && config.getStringList("worlds").contains(player.getWorld().getName()) && !player.hasPermission("monkeykits.bypass")) {
            sender.sendMessage("§cNo permission!");
            return true;
        }

        new KitMenu(player);
        return true;
    }
}
