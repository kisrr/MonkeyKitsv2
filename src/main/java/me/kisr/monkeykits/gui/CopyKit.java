package me.kisr.monkeykits.gui;

import me.kisr.monkeykits.Main;
import me.kisr.monkeykits.utils.ItemUtils;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collections;

public class CopyKit {

    public CopyKit(Player player, int kit, boolean premade) {

        new AnvilGUI.Builder()
                .onComplete(((p, string) -> {
                    String code = string.toUpperCase();
                    if (premade) {
                        if (Main.codeMap.containsKey(code)) new PremadeKit(player, true, code);
                        else player.sendMessage("§cCode doesn't exist.");
                    } else {
                        if (Main.codeMap.containsKey(code)) new KitEditor(player, kit, true, code);
                        else player.sendMessage("§cCode doesn't exist.");
                    }
                    return Collections.singletonList(AnvilGUI.ResponseAction.close());
                })).itemLeft(ItemUtils.getItem("§6Enter code", Material.GOLD_NUGGET, null))
                .title("§5§lCopy Kit")
                .plugin(Main.instance)
                .open(player);
    }
}
