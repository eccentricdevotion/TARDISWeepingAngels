/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.utils;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.FileUtil;

/**
 *
 * @author eccentric_nz
 */
public class Config {

    private final TARDISWeepingAngels plugin;
    private FileConfiguration config = null;
    private File configFile = null;
    HashMap<String, List<String>> listOptions = new HashMap<String, List<String>>();
    HashMap<String, String> strOptions = new HashMap<String, String>();
    HashMap<String, Integer> intOptions = new HashMap<String, Integer>();
    HashMap<String, Double> doubleOptions = new HashMap<String, Double>();
    HashMap<String, Boolean> boolOptions = new HashMap<String, Boolean>();
    final double min_version = 2.0d;

    public Config(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "config.yml");
        this.config = YamlConfiguration.loadConfiguration(configFile);
        // integer
        intOptions.put("angels.freeze_time", 100);
        intOptions.put("spawn_rate.how_many", 2);
        intOptions.put("spawn_rate.how_often", 400);
        intOptions.put("spawn_rate.default_max", 0);
        // string
        strOptions.put("angels.weapon", "DIAMOND_PICKAXE");
        // list
        listOptions.put("angels.drops", Arrays.asList(new String[]{"STONE", "COBBLESTONE"}));
        listOptions.put("cybermen.drops", Arrays.asList(new String[]{"REDSTONE", "STONE_BUTTON"}));
        listOptions.put("daleks.drops", Arrays.asList(new String[]{"SLIME_BALL", "ROTTEN_FLESH"}));
        listOptions.put("empty_child.drops", Arrays.asList(new String[]{"COOKED_BEEF", "SUGAR"}));
        listOptions.put("ice_warriors.drops", Arrays.asList(new String[]{"ICE", "PACKED_ICE", "SNOW_BLOCK"}));
        listOptions.put("silurians.drops", Arrays.asList(new String[]{"GOLD_NUGGET", "FEATHER"}));
        listOptions.put("sontarans.drops", Arrays.asList(new String[]{"POTATO_ITEM", "MILK_BUCKET"}));
        listOptions.put("vashta_nerada.drops", Arrays.asList(new String[]{"BONE", "LEATHER"}));
        listOptions.put("zygons.drops", Arrays.asList(new String[]{"PAINTING", "SAND"}));
        // boolean
        boolOptions.put("angels.angels_can_steal", true);
        boolOptions.put("cybermen.can_upgrade", true);
        boolOptions.put("sontarans.can_tame", true);
        // float
        doubleOptions.put("config_version", min_version);
    }

    public void updateConfig() {
        if (!config.contains("config_version")) {
            // back up the old config
            File oldFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
            File newFile = new File(plugin.getDataFolder() + File.separator + "config_" + System.currentTimeMillis() + ".yml");
            FileUtil.copy(oldFile, newFile);
            // clear old world settings
            plugin.getConfig().set("angels.worlds", null);
            plugin.getConfig().set("cybermen.worlds", null);
            plugin.getConfig().set("daleks.worlds", null);
            plugin.getConfig().set("empty_child.worlds", null);
            plugin.getConfig().set("ice_warriors.worlds", null);
            plugin.getConfig().set("silurians.worlds", null);
            plugin.getConfig().set("sontarans.worlds", null);
            plugin.getConfig().set("vashta_nerada.worlds", null);
            plugin.getConfig().set("zygons.worlds", null);
        }
        // add new world settings
        for (World w : this.plugin.getServer().getWorlds()) {
            String n = sanitiseName(w.getName());
            // set TARDIS worlds, nether and end worlds to zero by default
            int m = (config.contains("spawn_rate.default_max")) ? config.getInt("spawn_rate.default_max") : 0;
            if (!config.contains("angels.worlds." + n)) {
                plugin.getConfig().set("angels.worlds." + n, m);
            }
            if (!config.contains("cybermen.worlds." + n)) {
                plugin.getConfig().set("cybermen.worlds." + n, m);
            }
            if (!config.contains("daleks.worlds." + n)) {
                plugin.getConfig().set("daleks.worlds." + n, m);
            }
            if (!config.contains("empty_child.worlds." + n)) {
                plugin.getConfig().set("empty_child.worlds." + n, m);
            }
            if (!config.contains("ice_warriors.worlds." + n)) {
                plugin.getConfig().set("ice_warriors.worlds." + n, m);
            }
            if (!config.contains("silurians.worlds." + n)) {
                plugin.getConfig().set("silurians.worlds." + n, m);
            }
            if (!config.contains("sontarans.worlds." + n)) {
                plugin.getConfig().set("sontarans.worlds." + n, m);
            }
            if (!config.contains("vashta_nerada.worlds." + n)) {
                plugin.getConfig().set("vashta_nerada.worlds." + n, m);
            }
            if (!config.contains("zygons.worlds." + n)) {
                plugin.getConfig().set("zygons.worlds." + n, m);
            }
        }
        // clear the old spawn_rate settings
        if (config.contains("angels.spawn_rate")) {
            plugin.getConfig().set("angels.spawn_rate", null);
            plugin.getConfig().set("cybermen.spawn_rate", null);
            plugin.getConfig().set("daleks.spawn_rate", null);
            plugin.getConfig().set("empty_child.spawn_rate", null);
            plugin.getConfig().set("ice_warriors.spawn_rate", null);
            plugin.getConfig().set("silurians.spawn_rate", null);
            plugin.getConfig().set("sontarans.spawn_rate", null);
            plugin.getConfig().set("vashta_nerada.spawn_rate", null);
            plugin.getConfig().set("zygons.spawn_rate", null);
        }

        int i = 0;
        // int values
        for (Map.Entry<String, Integer> entry : intOptions.entrySet()) {
            if (!config.contains(entry.getKey())) {
                plugin.getConfig().set(entry.getKey(), entry.getValue());
                i++;
            }
        }
        // string values
        for (Map.Entry<String, String> entry : strOptions.entrySet()) {
            if (!config.contains(entry.getKey())) {
                plugin.getConfig().set(entry.getKey(), entry.getValue());
                i++;
            }
        }
        // list values
        for (Map.Entry<String, List<String>> entry : listOptions.entrySet()) {
            if (!config.contains(entry.getKey())) {
                plugin.getConfig().set(entry.getKey(), entry.getValue());
                i++;
            }
        }
        // boolean values
        for (Map.Entry<String, Boolean> entry : boolOptions.entrySet()) {
            if (!config.contains(entry.getKey())) {
                plugin.getConfig().set(entry.getKey(), entry.getValue());
                i++;
            }
        }
        // double values
        for (Map.Entry<String, Double> entry : doubleOptions.entrySet()) {
            if (!config.contains(entry.getKey())) {
                plugin.getConfig().set(entry.getKey(), entry.getValue());
                i++;
            }
        }
        plugin.saveConfig();
        if (i > 0) {
            plugin.getServer().getConsoleSender().sendMessage(plugin.pluginName + "Added " + ChatColor.AQUA + i + ChatColor.RESET + " new items to config");
        }
    }

    public static String sanitiseName(String name) {
        return name.replaceAll("\\.", "_");
    }
}
