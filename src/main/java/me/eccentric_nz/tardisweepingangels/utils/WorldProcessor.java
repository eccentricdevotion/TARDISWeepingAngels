/*
 * Copyright (C) 2021 eccentric_nz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package me.eccentric_nz.tardisweepingangels.utils;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.FileUtil;

import java.io.File;
import java.util.Objects;

public class WorldProcessor implements Runnable {

    private final TardisWeepingAngelsPlugin plugin;
    private final FileConfiguration config;

    public WorldProcessor(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
        config = this.plugin.getConfig();
    }

    public static String sanitiseName(String name) {
        return name.replaceAll("\\.", "_");
    }

    @Override
    public void run() {
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
        plugin.getServer().getWorlds().forEach((w) -> {
            String name = sanitiseName(w.getName());
            // set TARDIS worlds, nether and end worlds to zero by default
            int maxSpawnRate = (config.contains("spawn_rate.default_max")) ? config.getInt("spawn_rate.default_max") : 0;
            if (!config.contains("angels.worlds." + name)) {
                plugin.getConfig().set("angels.worlds." + name, maxSpawnRate);
            }
            if (!config.contains("cybermen.worlds." + name)) {
                plugin.getConfig().set("cybermen.worlds." + name, maxSpawnRate);
            }
            if (!config.contains("daleks.worlds." + name)) {
                plugin.getConfig().set("daleks.worlds." + name, maxSpawnRate);
            }
            if (!config.contains("empty_child.worlds." + name)) {
                plugin.getConfig().set("empty_child.worlds." + name, maxSpawnRate);
            }
            if (!config.contains("hath.worlds." + name)) {
                plugin.getConfig().set("hath.worlds." + name, maxSpawnRate);
            }
            if (!config.contains("ice_warriors.worlds." + name)) {
                plugin.getConfig().set("ice_warriors.worlds." + name, maxSpawnRate);
            }
            if (!config.contains("silent.worlds." + name)) {
                plugin.getConfig().set("silent.worlds." + name, maxSpawnRate);
            }
            if (!config.contains("ood.worlds." + name) || (config.contains("ood.worlds." + name) && config.getInt("ood.worlds." + name) == 20)) {
                plugin.getConfig().set("ood.worlds." + name, true);
            }
            if (!config.contains("judoon.worlds." + name) || (config.contains("judoon.worlds." + name) && Objects.equals(config.getString("judoon.worlds." + name), "true"))) {
                plugin.getConfig().set("judoon.worlds." + name, maxSpawnRate);
            }
            if (!config.contains("k9.worlds." + name)) {
                plugin.getConfig().set("k9.worlds." + name, true);
            }
            if (!config.contains("toclafane.worlds." + name)) {
                plugin.getConfig().set("toclafane.worlds." + name, maxSpawnRate);
            }
            if (!config.contains("silurians.worlds." + name)) {
                plugin.getConfig().set("silurians.worlds." + name, maxSpawnRate);
            }
            if (!config.contains("sontarans.worlds." + name)) {
                plugin.getConfig().set("sontarans.worlds." + name, maxSpawnRate);
            }
            if (!config.contains("vashta_nerada.worlds." + name)) {
                plugin.getConfig().set("vashta_nerada.worlds." + name, maxSpawnRate);
            }
            if (!config.contains("zygons.worlds." + name)) {
                plugin.getConfig().set("zygons.worlds." + name, maxSpawnRate);
            }
        });
        plugin.saveConfig();
    }
}
