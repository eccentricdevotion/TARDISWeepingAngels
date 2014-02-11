/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author eccentric_nz
 */
public class TARDISWeepingAngelsConfig {

    private final TARDISWeepingAngels plugin;
    private FileConfiguration config = null;
    private File configFile = null;
    HashMap<String, List<String>> listOptions = new HashMap<String, List<String>>();
    HashMap<String, String> strOptions = new HashMap<String, String>();
    HashMap<String, Integer> intOptions = new HashMap<String, Integer>();
    HashMap<String, Boolean> boolOptions = new HashMap<String, Boolean>();

    public TARDISWeepingAngelsConfig(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "config.yml");
        this.config = YamlConfiguration.loadConfiguration(configFile);
        // integer
        intOptions.put("ice_warriors.spawn_rate.how_many", 3);
        intOptions.put("ice_warriors.spawn_rate.how_often", 400);
        intOptions.put("ice_warriors.spawn_rate.max_per_world", 25);
        intOptions.put("cybermen.spawn_rate.how_many", 3);
        intOptions.put("cybermen.spawn_rate.how_often", 400);
        intOptions.put("cybermen.spawn_rate.max_per_world", 30);
        // string
        //strOptions.put("angels.weapon", "DIAMOND_PICKAXE");
        // list
        listOptions.put("ice_warriors.worlds", Arrays.asList(new String[]{"world"}));
        listOptions.put("ice_warriors.drops", Arrays.asList(new String[]{"ICE", "PACKED_ICE", "SNOW_BLOCK"}));
        listOptions.put("cybermen.worlds", Arrays.asList(new String[]{"world"}));
        listOptions.put("cybermen.drops", Arrays.asList(new String[]{"REDSTONE", "STONE_BUTTON"}));
        // boolean
        boolOptions.put("cybermen.can_upgrade", true);
        boolOptions.put("always_use_leather", false);
    }

    public void updateConfig() {
        if (!config.contains("angels.freeze_time")) {
            // set new values from old
            plugin.getConfig().set("angels.spawn_rate.how_many", plugin.getConfig().getInt("spawn_rate.how_many"));
            plugin.getConfig().set("angels.spawn_rate.how_often", plugin.getConfig().getInt("spawn_rate.how_often"));
            plugin.getConfig().set("angels.spawn_rate.max_per_world", plugin.getConfig().getInt("spawn_rate.max_per_world"));
            plugin.getConfig().set("angels.worlds", plugin.getConfig().getStringList("worlds"));
            plugin.getConfig().set("angels.drops", plugin.getConfig().getStringList("drops"));
            plugin.getConfig().set("angels.weapon", plugin.getConfig().getString("weapon"));
            plugin.getConfig().set("angels.freeze_time", plugin.getConfig().getInt("freeze_time"));
            plugin.getConfig().set("angels.can_steal", plugin.getConfig().getBoolean("angels_can_steal"));
            // remove old values
            plugin.getConfig().set("spawn_rate.how_many", null);
            plugin.getConfig().set("spawn_rate.how_often", null);
            plugin.getConfig().set("spawn_rate.max_per_world", null);
            plugin.getConfig().set("spawn_rate", null);
            plugin.getConfig().set("worlds", null);
            plugin.getConfig().set("drops", null);
            plugin.getConfig().set("weapon", null);
            plugin.getConfig().set("freeze_time", null);
            plugin.getConfig().set("angels_can_steal", null);
            try {
                config.save(new File(plugin.getDataFolder(), "config.yml"));
            } catch (IOException io) {
                plugin.debug("Could not save config.yml, " + io);
            }
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
        plugin.saveConfig();
        if (i > 0) {
            plugin.getServer().getConsoleSender().sendMessage(plugin.pluginName + "Added " + ChatColor.AQUA + i + ChatColor.RESET + " new items to config");
        }
    }
}
