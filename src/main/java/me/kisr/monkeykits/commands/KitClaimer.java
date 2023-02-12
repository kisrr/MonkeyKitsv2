package me.kisr.monkeykits.commands;

import me.kisr.monkeykits.Main;
import me.kisr.monkeykits.utils.KitUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class KitClaimer implements CommandExecutor {
    private static final FileConfiguration config = Main.instance.getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command");
            return true;
        }

        Player player = (Player) sender;

        try {
            int kit = Integer.valueOf(command.getName().replace("kit", "").replace("k", ""));
            KitUtils.claim(player, kit, true);
        } catch (Exception ignored) {

        }
        return true;
    }
}
