package me.kisr.monkeykits;

import me.kisr.monkeykits.commands.KitClaimer;
import me.kisr.monkeykits.commands.KitCommand;
import me.kisr.monkeykits.events.*;
import me.kisr.monkeykits.files.KitRoomFile;
import me.kisr.monkeykits.files.KitsFile;
import me.kisr.monkeykits.utils.FileUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin {
    public static final List<UUID> kitMenuChecker = new ArrayList<>();
    public static final HashMap<UUID, Integer> kitEditorChecker = new HashMap<>();
    public static final HashMap<UUID, Integer> echestEditorChecker = new HashMap<>();
    public static final HashMap<UUID, Integer> kitRoomChecker = new HashMap<>();
    public static final List<UUID> premadeKitChecker = new ArrayList<>();
    public static final HashMap<String, HashMap<String, ItemStack[]>> echestMap = new HashMap<>();
    public static final HashMap<String, HashMap<String, ItemStack[]>> kitMap = new HashMap<>();
    public static final HashMap<Integer, ItemStack[]> kitRoomMap = new HashMap<>();
    public static final HashMap<String, ItemStack[]> codeMap = new HashMap<>();
    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        kitMap.putIfAbsent("system", new HashMap<>());

        getCommand("kit").setExecutor(new KitCommand());
        getCommand("kit1").setExecutor(new KitClaimer());
        getCommand("kit2").setExecutor(new KitClaimer());
        getCommand("kit3").setExecutor(new KitClaimer());
        getCommand("kit4").setExecutor(new KitClaimer());
        getCommand("kit5").setExecutor(new KitClaimer());
        getCommand("kit6").setExecutor(new KitClaimer());
        getCommand("kit7").setExecutor(new KitClaimer());


        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new KitEditorEvent(), this);
        getServer().getPluginManager().registerEvents(new KitMenuEvent(), this);
        getServer().getPluginManager().registerEvents(new EnderChestEditorEvent(), this);
        getServer().getPluginManager().registerEvents(new KitRoomEvent(), this);
        getServer().getPluginManager().registerEvents(new PremadeKitEvent(), this);

        KitsFile.setup();
        KitsFile.get().options().copyDefaults(true);
        KitsFile.save();

        KitRoomFile.setup();
        KitRoomFile.get().options().copyDefaults(true);
        KitRoomFile.save();

        saveDefaultConfig();

        FileUtils.restore();
    }

    @Override
    public void onDisable() {
        KitsFile.get().set("kits", kitMap);
        KitsFile.save();

        KitsFile.get().set("echest", echestMap);
        KitsFile.save();

        KitRoomFile.get().set("items", kitRoomMap);
        KitRoomFile.save();

    }
}
