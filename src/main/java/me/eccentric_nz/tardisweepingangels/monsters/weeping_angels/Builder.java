/*
 *  Copyright 2015 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.weeping_angels;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 *
 * @author eccentric_nz
 */
public class Builder implements Listener {

    private final TARDISWeepingAngels plugin;
    private final MonsterEquipment equipper;

    public Builder(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.equipper = new MonsterEquipment();
    }

    @EventHandler(ignoreCancelled = true)
    public void onSkullPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType().equals(Material.SKULL)) {
            Skull skull = (Skull) event.getBlock().getState();
            if (skull.getSkullType().equals(SkullType.SKELETON)) {
                final Block placed = event.getBlockPlaced();
                // check the blocks below
                final Block below = placed.getRelative(BlockFace.DOWN);
                if (!below.getType().equals(Material.COBBLE_WALL)) {
                    return;
                }
                final Block bottom = below.getRelative(BlockFace.DOWN);
                if (!bottom.getType().equals(Material.COBBLE_WALL)) {
                    return;
                }
                final Block east = below.getRelative(BlockFace.EAST);
                final Block west = below.getRelative(BlockFace.WEST);
                final Block north = below.getRelative(BlockFace.NORTH);
                final Block south = below.getRelative(BlockFace.SOUTH);
                if ((east.getType().equals(Material.COBBLE_WALL) && west.getType().equals(Material.COBBLE_WALL)) || (north.getType().equals(Material.COBBLE_WALL) && south.getType().equals(Material.COBBLE_WALL))) {
                    if (!event.getPlayer().hasPermission("tardisweepingangels.build")) {
                        event.getPlayer().sendMessage(plugin.pluginName + "You don't have permission to build a Weeping Angel!");
                        return;
                    }
                    // we're building an angel
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            placed.setType(Material.AIR);
                            below.setType(Material.AIR);
                            bottom.setType(Material.AIR);
                            if (east.getType().equals(Material.COBBLE_WALL)) {
                                east.setType(Material.AIR);
                                west.setType(Material.AIR);
                            } else {
                                north.setType(Material.AIR);
                                south.setType(Material.AIR);
                            }
                            Location l = bottom.getLocation();
                            final LivingEntity e = (LivingEntity) l.getWorld().spawnEntity(l, EntityType.SKELETON);
                            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    equipper.setAngelEquipment(e, false);
                                }
                            }, 5L);
                        }
                    }, 20L);
                }
            }
        }
    }
}
