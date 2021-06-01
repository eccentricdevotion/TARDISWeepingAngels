/*
 *  Copyright 2015 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.daleks;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.monsters.cybermen.CybermanEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.empty_child.EmptyChildEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.ice_warriors.IceWarriorEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.silurians.SilurianEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.sontarans.SontaranEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.sontarans.StraxEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.vashta_nerada.VashtaNeradaEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.weeping_angels.AngelEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.zygons.ZygonEquipment;
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
		for (Entity d : event.getChunk().getEntities()) {
			PersistentDataContainer pdc = d.getPersistentDataContainer();
			if (d instanceof Skeleton skeleton) {
				if (pdc.has(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER) &&
					(Objects.requireNonNull(skeleton.getEquipment()).getHelmet() == null ||
					 Objects.requireNonNull(skeleton.getEquipment().getHelmet()).getType() == Material.MUSHROOM_STEM)) {
					DalekEquipment.set(skeleton, false);
				} else if (pdc.has(TARDISWeepingAngels.ANGEL, PersistentDataType.INTEGER) &&
						   (Objects.requireNonNull(skeleton.getEquipment()).getHelmet() != null &&
							Objects.requireNonNull(skeleton.getEquipment().getHelmet()).getType() ==
							Material.STONE_BUTTON)) {
					AngelEquipment.set(skeleton, false);
				} else if (pdc.has(TARDISWeepingAngels.SILURIAN, PersistentDataType.INTEGER) &&
						   Objects.requireNonNull(skeleton.getEquipment()).getHelmet() != null &&
						   Objects.requireNonNull(skeleton.getEquipment().getHelmet()).getType() ==
						   Material.GOLDEN_HELMET) {
					SilurianEquipment.set(skeleton, false);
				}
			} else if (d instanceof PigZombie pigZombie) {
				if (pdc.has(TARDISWeepingAngels.WARRIOR, PersistentDataType.INTEGER) &&
					Objects.requireNonNull(pigZombie.getEquipment()).getHelmet() != null &&
					Objects.requireNonNull(pigZombie.getEquipment().getHelmet()).getType() == Material.IRON_HELMET) {
					IceWarriorEquipment.set(pigZombie, false);
				} else if (pdc.has(TARDISWeepingAngels.STRAX, PersistentDataType.INTEGER) &&
						   Objects.requireNonNull(pigZombie.getEquipment()).getHelmet() != null &&
						   Objects.requireNonNull(pigZombie.getEquipment().getHelmet()).getType() ==
						   Material.CHAINMAIL_HELMET) {
					StraxEquipment.set(pigZombie, false);
				}
			} else if (d instanceof Drowned drowned) {
				if (Objects.requireNonNull(drowned.getEquipment()).getHelmet() != null) {
					ItemMeta im = Objects.requireNonNull(drowned.getEquipment().getHelmet()).getItemMeta();
					if (im != null && im.hasDisplayName() && im.getDisplayName().endsWith(" Head")) {
						Bukkit.getScheduler().scheduleSyncDelayedTask(TARDISWeepingAngels.plugin, drowned::remove, 2L);
					}
				}
			} else if (d instanceof Zombie zombie) {
				if (pdc.has(TARDISWeepingAngels.CYBERMAN, PersistentDataType.INTEGER) &&
					Objects.requireNonNull(zombie.getEquipment()).getHelmet() != null &&
					Objects.requireNonNull(zombie.getEquipment().getHelmet()).getType() == Material.IRON_HELMET) {
					CybermanEquipment.set(zombie, false);
				} else if (Objects.requireNonNull(zombie.getEquipment()).getHelmet() != null &&
						   Objects.requireNonNull(zombie.getEquipment().getHelmet()).getType() ==
						   Material.GOLDEN_HELMET) {
					if (pdc.has(TARDISWeepingAngels.EMPTY, PersistentDataType.INTEGER)) {
						EmptyChildEquipment.set(zombie, false);
					} else if (pdc.has(TARDISWeepingAngels.ZYGON, PersistentDataType.INTEGER)) {
						ZygonEquipment.set(zombie, false);
					} else if (pdc.has(TARDISWeepingAngels.SONTARAN, PersistentDataType.INTEGER)) {
						SontaranEquipment.set(zombie, false);
					} else if (pdc.has(TARDISWeepingAngels.VASHTA, PersistentDataType.INTEGER)) {
						VashtaNeradaEquipment.set(zombie, false);
					}
				}
			}
		}
	}
}
