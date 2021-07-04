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
package me.eccentric_nz.tardisweepingangels.monsters.weepingangel;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * @author eccentric_nz
 */
public class AngelBuilder implements Listener {

    private final TardisWeepingAngelsPlugin plugin;

    public AngelBuilder(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onSkullPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType().equals(Material.SKELETON_SKULL)) {
            Block placed = event.getBlockPlaced();
            // check the blocks below
            Block below = placed.getRelative(BlockFace.DOWN);
            if (!below.getType().equals(Material.COBBLESTONE_WALL)) {
                return;
            }
            Block bottom = below.getRelative(BlockFace.DOWN);
            if (!bottom.getType().equals(Material.COBBLESTONE_WALL)) {
                return;
            }
            Block east = below.getRelative(BlockFace.EAST);
            Block west = below.getRelative(BlockFace.WEST);
            Block north = below.getRelative(BlockFace.NORTH);
            Block south = below.getRelative(BlockFace.SOUTH);
            if ((east.getType().equals(Material.COBBLESTONE_WALL) && west.getType().equals(Material.COBBLESTONE_WALL)) || (north.getType().equals(Material.COBBLESTONE_WALL) && south.getType().equals(Material.COBBLESTONE_WALL))) {
                if (!event.getPlayer().hasPermission("tardisweepingangels.build.angel")) {
                    event.getPlayer().sendMessage(plugin.pluginName + "You don't have permission to build a Weeping Angel!");
                    return;
                }
                // we're building an angel
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    placed.setType(Material.AIR);
                    below.setType(Material.AIR);
                    bottom.setType(Material.AIR);
                    if (east.getType().equals(Material.COBBLESTONE_WALL)) {
                        east.setType(Material.AIR);
                        west.setType(Material.AIR);
                    } else {
                        north.setType(Material.AIR);
                        south.setType(Material.AIR);
                    }
                    Location location = bottom.getLocation();
                    LivingEntity weepingAngel = (LivingEntity) location.getWorld().spawnEntity(location, EntityType.SKELETON);
                    weepingAngel.setSilent(true);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        AngelEquipment.set(weepingAngel, false);
                        plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(weepingAngel, EntityType.SKELETON, Monster.WEEPING_ANGEL, location));
                    }, 5L);
                }, 20L);
            }
        }
    }
}
