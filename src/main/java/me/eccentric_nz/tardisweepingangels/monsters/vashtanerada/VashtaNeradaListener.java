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
package me.eccentric_nz.tardisweepingangels.monsters.vashtanerada;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import me.eccentric_nz.tardisweepingangels.utils.WorldProcessor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author eccentric_nz
 */
public class VashtaNeradaListener implements Listener {

    private final TardisWeepingAngelsPlugin plugin;
    private final List<BlockFace> faces = new ArrayList<>();

    public VashtaNeradaListener(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
        faces.add(BlockFace.EAST);
        faces.add(BlockFace.NORTH);
        faces.add(BlockFace.SOUTH);
        faces.add(BlockFace.WEST);
    }

    @EventHandler
    public void onBookshelfBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType().equals(Material.BOOKSHELF)) {
            String name = WorldProcessor.sanitiseName(block.getWorld().getName());
            if (plugin.getConfig().getInt("vashta_nerada.worlds." + name) > 0 && TardisWeepingAngelsPlugin.random.nextInt(100) < plugin.getConfig().getInt("vashta_nerada.worlds." + name)) {
                Location location = getClearLocation(event.getPlayer());
                if (location != null) {
                    // spawn Vashta Nerada at location
                    spawnVashtaNerada(location);
                }
            }
        }
    }

    private Location getClearLocation(Player player) {
        Location location = null;
        Block locationBlock = player.getLocation().getBlock();
        World world = locationBlock.getWorld();
        Collections.shuffle(faces, TardisWeepingAngelsPlugin.random);
        for (BlockFace blockFace : faces) {
            Block block = locationBlock.getRelative(blockFace, 3);
            if (block.getType().equals(Material.AIR) && block.getRelative(BlockFace.UP).getType().equals(Material.AIR)) {
                location = new Location(world, block.getX() + 0.5d, block.getY(), block.getZ() + 0.5d);
                break;
            }
        }
        return location;
    }

    private void spawnVashtaNerada(Location location) {
        LivingEntity vashtaNerada = (LivingEntity) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        vashtaNerada.setSilent(true);
        Zombie zombie = (Zombie) vashtaNerada;
        zombie.setAdult();
        PotionEffect potionEffect = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3, true, false);
        vashtaNerada.addPotionEffect(potionEffect);
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            VashtaNeradaEquipment.set(vashtaNerada, false);
            plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(vashtaNerada, EntityType.ZOMBIE, Monster.VASHTA_NERADA, location));
        }, 5L);
    }
}
