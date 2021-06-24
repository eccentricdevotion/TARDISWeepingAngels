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
package me.eccentric_nz.tardisweepingangels.monsters.dalek;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.monsters.cyberman.CybermanEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.emptychild.EmptyChildEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.icewarrior.IceWarriorEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.silurian.SilurianEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.sontaran.SontaranEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.sontaran.StraxEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.vashtanerada.VashtaNeradaEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.weepingangel.AngelEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.zygon.ZygonEquipment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

/**
 * @author eccentric_nz
 */
public class ChunkLoad implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChunkLoad(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
            if (entity instanceof Skeleton skeleton) {
                if (persistentDataContainer.has(TardisWeepingAngelsPlugin.DALEK, PersistentDataType.INTEGER) && (Objects.requireNonNull(skeleton.getEquipment()).getHelmet() == null || Objects.requireNonNull(skeleton.getEquipment().getHelmet()).getType() == Material.MUSHROOM_STEM)) {
                    DalekEquipment.set(skeleton, false);
                } else if (persistentDataContainer.has(TardisWeepingAngelsPlugin.WEEPING_ANGEL, PersistentDataType.INTEGER) && (Objects.requireNonNull(skeleton.getEquipment()).getHelmet() != null && Objects.requireNonNull(skeleton.getEquipment().getHelmet()).getType() == Material.STONE_BUTTON)) {
                    AngelEquipment.set(skeleton, false);
                } else if (persistentDataContainer.has(TardisWeepingAngelsPlugin.SILURIAN, PersistentDataType.INTEGER) && Objects.requireNonNull(skeleton.getEquipment()).getHelmet() != null && Objects.requireNonNull(skeleton.getEquipment().getHelmet()).getType() == Material.GOLDEN_HELMET) {
                    SilurianEquipment.set(skeleton, false);
                }
            } else if (entity instanceof PigZombie pigZombie) {
                if (persistentDataContainer.has(TardisWeepingAngelsPlugin.ICE_WARRIOR, PersistentDataType.INTEGER) && Objects.requireNonNull(pigZombie.getEquipment()).getHelmet() != null && Objects.requireNonNull(pigZombie.getEquipment().getHelmet()).getType() == Material.IRON_HELMET) {
                    IceWarriorEquipment.set(pigZombie, false);
                } else if (persistentDataContainer.has(TardisWeepingAngelsPlugin.STRAX, PersistentDataType.INTEGER) && Objects.requireNonNull(pigZombie.getEquipment()).getHelmet() != null && Objects.requireNonNull(pigZombie.getEquipment().getHelmet()).getType() == Material.CHAINMAIL_HELMET) {
                    StraxEquipment.set(pigZombie, false);
                }
            } else if (entity instanceof Drowned drowned) {
                if (Objects.requireNonNull(drowned.getEquipment()).getHelmet() != null) {
                    ItemMeta itemMeta = Objects.requireNonNull(drowned.getEquipment().getHelmet()).getItemMeta();
                    if (itemMeta != null && itemMeta.hasDisplayName() && itemMeta.getDisplayName().endsWith(" Head")) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(TardisWeepingAngelsPlugin.plugin, drowned::remove, 2L);
                    }
                }
            } else if (entity instanceof Zombie zombie) {
                if (persistentDataContainer.has(TardisWeepingAngelsPlugin.CYBERMAN, PersistentDataType.INTEGER) && Objects.requireNonNull(zombie.getEquipment()).getHelmet() != null && Objects.requireNonNull(zombie.getEquipment().getHelmet()).getType() == Material.IRON_HELMET) {
                    CybermanEquipment.set(zombie, false);
                } else if (Objects.requireNonNull(zombie.getEquipment()).getHelmet() != null && Objects.requireNonNull(zombie.getEquipment().getHelmet()).getType() == Material.GOLDEN_HELMET) {
                    if (persistentDataContainer.has(TardisWeepingAngelsPlugin.EMPTY, PersistentDataType.INTEGER)) {
                        EmptyChildEquipment.set(zombie, false);
                    } else if (persistentDataContainer.has(TardisWeepingAngelsPlugin.ZYGON, PersistentDataType.INTEGER)) {
                        ZygonEquipment.set(zombie, false);
                    } else if (persistentDataContainer.has(TardisWeepingAngelsPlugin.SONTARAN, PersistentDataType.INTEGER)) {
                        SontaranEquipment.set(zombie, false);
                    } else if (persistentDataContainer.has(TardisWeepingAngelsPlugin.VASHTA_NERADA, PersistentDataType.INTEGER)) {
                        VashtaNeradaEquipment.set(zombie, false);
                    }
                }
            }
        }
    }
}
