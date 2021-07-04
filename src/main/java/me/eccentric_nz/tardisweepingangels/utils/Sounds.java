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
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Sounds implements Listener {

    private final TardisWeepingAngelsPlugin plugin;
    private final List<UUID> tracker = new ArrayList<>();

    public Sounds(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTargetPlayer(EntityTargetLivingEntityEvent event) {
        Entity entity = event.getEntity();
        UUID uuid = entity.getUniqueId();
        if (tracker.contains(uuid)) {
            return;
        }
        if (entity instanceof Enderman) {
            if (entity.getPassengers().size() > 0 && entity.getPassengers().get(0).getType().equals(EntityType.GUARDIAN)) {
                tracker.add(uuid);
                LivingEntity livingEntity = event.getTarget();
                if (livingEntity instanceof Player player) {
                    long delay = 90L;
                    // schedule delayed task
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        player.playSound(entity.getLocation(), "silence", 1.0f, 1.0f);
                        tracker.remove(uuid);
                    }, delay);
                }
            }
        }
        if (entity instanceof Guardian) {
            if (entity.getVehicle() != null && entity.getVehicle().getType().equals(EntityType.ENDERMAN)) {
                tracker.add(uuid);
                LivingEntity livingEntity = event.getTarget();
                if (livingEntity instanceof Player player) {
                    long delay = 20L;
                    // schedule delayed task
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        player.playSound(entity.getLocation(), "silence", 1.0f, 1.0f);
                        tracker.remove(uuid);
                    }, delay);
                }
            }
        }
        if (entity instanceof PigZombie) {
            if (entity.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.hath, PersistentDataType.INTEGER)) {
                tracker.add(uuid);
                LivingEntity livingEntity = event.getTarget();
                if (livingEntity instanceof Player player) {
                    long delay = 100L;
                    // schedule delayed task
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        player.playSound(entity.getLocation(), "hath", 1.0f, 1.0f);
                        tracker.remove(uuid);
                    }, delay);
                }
            }
            if (entity.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.iceWarrior, PersistentDataType.INTEGER)) {
                tracker.add(uuid);
                LivingEntity livingEntity = event.getTarget();
                if (livingEntity instanceof Player player) {
                    long delay = 50L;
                    // schedule delayed task
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        player.playSound(entity.getLocation(), "warrior", 1.0f, 1.0f);
                        tracker.remove(uuid);
                    }, delay);
                }
            }
        }
        if (entity instanceof Zombie zombie) {
            EntityEquipment entityEquipment = zombie.getEquipment();
            ItemStack head = entityEquipment.getHelmet();
            if (head != null && head.hasItemMeta() && head.getItemMeta().hasDisplayName()) {
                tracker.add(uuid);
                LivingEntity livingEntity = event.getTarget();
                String displayName = head.getItemMeta().getDisplayName();
                if (livingEntity instanceof Player player) {
                    String temp = "";
                    long delay = 50L;
                    if (!zombie.isAdult() && displayName.equals("Empty Child Head") && head.getType().equals(Material.SUGAR)) {
                        temp = "empty_child";
                    }
                    if (displayName.equals("Cyberman Head") && head.getType().equals(Material.IRON_INGOT)) {
                        temp = "cyberman";
                        delay = 80L;
                    }
                    if (displayName.equals("Sontaran Head") && head.getType().equals(Material.POTATO)) {
                        temp = "sontaran";
                        delay = 55L;
                    }
                    if (displayName.equals("Vashta Nerada Head") && head.getType().equals(Material.BOOK)) {
                        temp = "vashta";
                        delay = 30L;
                    }
                    if (displayName.equals("Zygon Head") && head.getType().equals(Material.PAINTING)) {
                        temp = "zygon";
                        delay = 100L;
                    }
                    if (!temp.isEmpty()) {
                        String sound = temp;
                        // schedule delayed task
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            player.playSound(entity.getLocation(), sound, 1.0f, 1.0f);
                            tracker.remove(uuid);
                        }, delay);
                    }
                }
            }
            return;
        }
        if (entity instanceof Skeleton) {
            PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.dalek, PersistentDataType.INTEGER)) {
                tracker.add(uuid);
                LivingEntity livingEntity = event.getTarget();
                if (livingEntity instanceof Player player) {
                    long delay = 50L;
                    // schedule delayed task
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        player.playSound(entity.getLocation(), "dalek", 1.0f, 1.0f);
                        tracker.remove(uuid);
                    }, delay);
                }
            } else if (persistentDataContainer.has(TardisWeepingAngelsPlugin.silurian, PersistentDataType.INTEGER)) {
                tracker.add(uuid);
                LivingEntity livingEntity = event.getTarget();
                if (livingEntity instanceof Player player) {
                    long delay = 50L;
                    // schedule delayed task
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        player.playSound(entity.getLocation(), "silurian", 1.0f, 1.0f);
                        tracker.remove(uuid);
                    }, delay);
                }
            }
        }
    }
}
