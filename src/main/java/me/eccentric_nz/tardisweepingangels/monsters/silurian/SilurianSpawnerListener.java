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
package me.eccentric_nz.tardisweepingangels.monsters.silurian;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import me.eccentric_nz.tardisweepingangels.utils.WorldGuardChecker;
import me.eccentric_nz.tardisweepingangels.utils.WorldProcessor;
import org.bukkit.Location;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SilurianSpawnerListener implements Listener {

    private final TardisWeepingAngelsPlugin plugin;
    private final int spawnRate;

    public SilurianSpawnerListener(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
        spawnRate = plugin.getConfig().getInt("spawn_rate.how_many");
    }

    @EventHandler
    public void onSpawnEvent(SpawnerSpawnEvent event) {
        CreatureSpawner spawner = event.getSpawner();
        if (spawner.getSpawnedType().equals(EntityType.CAVE_SPIDER)) {
            Location cave = event.getLocation();
            String name = WorldProcessor.sanitiseName(cave.getWorld().getName());
            if (plugin.getConfig().getInt("silurians.worlds." + name) <= 0) {
                return;
            }
            if (plugin.getServer().getPluginManager().getPlugin("WorldGuard") != null && !WorldGuardChecker.canSpawn(cave)) {
                return;
            }
            // get the current silurian count
            List<Skeleton> silurians = new ArrayList<>();
            Collection<Skeleton> skeletons = cave.getWorld().getEntitiesByClass(Skeleton.class);
            skeletons.forEach((silurian) -> {
                PersistentDataContainer persistentDataContainer = silurian.getPersistentDataContainer();
                if (persistentDataContainer.has(TardisWeepingAngelsPlugin.silurian, PersistentDataType.INTEGER)) {
                    silurians.add(silurian);
                }
            });
            if (silurians.size() < plugin.getConfig().getInt("silurians.worlds." + name)) {
                // if less than maximum, spawn another
                for (int i = 0; i < spawnRate; i++) {
                    LivingEntity silurian = (LivingEntity) cave.getWorld().spawnEntity(cave, EntityType.SKELETON);
                    silurian.setSilent(true);
                    PotionEffect potionEffect = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3, true, false);
                    silurian.addPotionEffect(potionEffect);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        SilurianEquipment.set(silurian, false);
                        plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(silurian, EntityType.SKELETON, Monster.SILURIAN, cave));
                    }, 5L);
                }
            }
        }
    }
}
