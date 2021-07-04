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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class MonsterTargetListener implements Listener {

    private static boolean isWearingMonsterHead(Player player, Material material) {
        ItemStack itemStack = player.getInventory().getHelmet();
        if (itemStack != null && itemStack.getType().equals(material) && itemStack.hasItemMeta()) {
            return itemStack.getItemMeta().getPersistentDataContainer().has(TardisWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER);
        }
        return false;
    }

    public static boolean monsterShouldIgnorePlayer(Entity entity, Player player) {
        PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
        boolean ignore = false;
        switch (entity.getType()) {
            case ZOMBIE:
                // cyberman
                if (persistentDataContainer.has(TardisWeepingAngelsPlugin.cyberman, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.IRON_INGOT);
                }
                // empty child
                if (persistentDataContainer.has(TardisWeepingAngelsPlugin.emptyChild, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.SUGAR);
                }
                // sontaran
                if (persistentDataContainer.has(TardisWeepingAngelsPlugin.sontaran, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.POTATO);
                }
                // vashta nerada
                if (persistentDataContainer.has(TardisWeepingAngelsPlugin.vashtaNerada, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.BOOK);
                }
                // zygon
                if (persistentDataContainer.has(TardisWeepingAngelsPlugin.zygon, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.PAINTING);
                }
                break;
            case SKELETON:
                // dalek
                if (persistentDataContainer.has(TardisWeepingAngelsPlugin.dalek, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.SLIME_BALL);
                }
                // silurian
                if (persistentDataContainer.has(TardisWeepingAngelsPlugin.silurian, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.FEATHER);
                }
                // weeping angel
                if (persistentDataContainer.has(TardisWeepingAngelsPlugin.weepingAngel, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.BRICK);
                }
                break;
            case ENDERMAN:
            case GUARDIAN:
                // silent
                if (persistentDataContainer.has(TardisWeepingAngelsPlugin.silent, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.END_STONE);
                }
                break;
            case ZOMBIFIED_PIGLIN:
                // hath
                if (persistentDataContainer.has(TardisWeepingAngelsPlugin.hath, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.PUFFERFISH);
                }
                // ice warrior
                if (persistentDataContainer.has(TardisWeepingAngelsPlugin.iceWarrior, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.SNOWBALL);
                }
                // strax
                if (persistentDataContainer.has(TardisWeepingAngelsPlugin.strax, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.BAKED_POTATO);
                }
                break;
            default:
                break;
        }
        return ignore;
    }

    @EventHandler
    public void onMonsterTargetEvent(EntityTargetLivingEntityEvent event) {
        Entity eventEntity = event.getEntity();
        if (eventEntity instanceof Monster monster) {
            double range = (eventEntity instanceof Zombie) ? 40.0d : 16.0d;
            Player closest = null;
            double distance = 1000.0f;
            Location entityLocation = eventEntity.getLocation();
            for (Entity entity : eventEntity.getNearbyEntities(range, range, range)) {
                if (entity instanceof Player player) {
                    if (closest == null) {
                        closest = player;
                        distance = entity.getLocation().distanceSquared(entityLocation);
                    } else if (entity.getLocation().distanceSquared(entityLocation) < distance) {
                        closest = player;
                        distance = entity.getLocation().distanceSquared(entityLocation);
                    }
                }
            }
            if (closest != null && monsterShouldIgnorePlayer(eventEntity, closest)) {
                event.setCancelled(true);
                monster.setTarget(null);
            }
        }
    }
}
