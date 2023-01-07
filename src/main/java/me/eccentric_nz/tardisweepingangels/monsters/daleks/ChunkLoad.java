/*
 *  Copyright 2015 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.daleks;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.Equipper;
import me.eccentric_nz.tardisweepingangels.monsters.empty_child.EmptyChildEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 * @author eccentric_nz
 */
public class ChunkLoad implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChunkLoad(ChunkLoadEvent event) {
        for (Entity d : event.getChunk().getEntities()) {
            PersistentDataContainer pdc = d.getPersistentDataContainer();
            if (d instanceof Skeleton skeleton) {
                if (pdc.has(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER) && (skeleton.getEquipment().getHelmet() == null || skeleton.getEquipment().getHelmet().getType() == Monster.DALEK.getMaterial())) {
                    DalekEquipment.set(skeleton, false);
                } else if (pdc.has(TARDISWeepingAngels.ANGEL, PersistentDataType.INTEGER) && (skeleton.getEquipment().getHelmet() != null && skeleton.getEquipment().getHelmet().getType() == Monster.WEEPING_ANGEL.getMaterial())) {
                    new Equipper(Monster.WEEPING_ANGEL, skeleton, false, false).setHelmetAndInvisibilty();
                } else if (pdc.has(TARDISWeepingAngels.SILURIAN, PersistentDataType.INTEGER) && skeleton.getEquipment().getHelmet() != null && skeleton.getEquipment().getHelmet().getType() == Monster.SILURIAN.getMaterial()) {
                    new Equipper(Monster.SILURIAN, skeleton, false, false).setHelmetAndInvisibilty();
                }
            } else if (d instanceof PigZombie pigZombie) {
                if (pdc.has(TARDISWeepingAngels.WARRIOR, PersistentDataType.INTEGER) && pigZombie.getEquipment().getHelmet() != null && pigZombie.getEquipment().getHelmet().getType() == Monster.ICE_WARRIOR.getMaterial()) {
                    new Equipper(Monster.ICE_WARRIOR, pigZombie, false, false).setHelmetAndInvisibilty();
                } else if (pdc.has(TARDISWeepingAngels.STRAX, PersistentDataType.INTEGER) && pigZombie.getEquipment().getHelmet() != null && pigZombie.getEquipment().getHelmet().getType() == Monster.STRAX.getMaterial()) {
                    new Equipper(Monster.STRAX, pigZombie, false, false).setHelmetAndInvisibilty();
                } else if (pdc.has(TARDISWeepingAngels.HATH, PersistentDataType.INTEGER) && pigZombie.getEquipment().getHelmet() != null && pigZombie.getEquipment().getHelmet().getType() == Monster.HATH.getMaterial()) {
                    new Equipper(Monster.STRAX, pigZombie, false, false).setHelmetAndInvisibilty();
                }
            } else if (d instanceof Drowned drowned) {
                if (drowned.getEquipment().getHelmet() != null) {
                    ItemMeta im = drowned.getEquipment().getHelmet().getItemMeta();
                    if (im != null && im.hasDisplayName() && im.getDisplayName().endsWith(" Head")) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(TARDISWeepingAngels.plugin, () -> drowned.remove(), 2L);
                    }
                }
            } else if (d instanceof Zombie zombie) {
                if (pdc.has(TARDISWeepingAngels.CYBERMAN, PersistentDataType.INTEGER) && zombie.getEquipment().getHelmet() != null && zombie.getEquipment().getHelmet().getType() == Monster.CYBERMAN.getMaterial()) {
                    new Equipper(Monster.CYBERMAN, zombie, false, false).setHelmetAndInvisibilty();
                } else if (zombie.getEquipment().getHelmet() != null && zombie.getEquipment().getHelmet().getType() == Monster.EMPTY_CHILD.getMaterial()) {
                    if (pdc.has(TARDISWeepingAngels.EMPTY, PersistentDataType.INTEGER)) {
                        new Equipper(Monster.EMPTY_CHILD, zombie, false, false).setHelmetAndInvisibilty();
                        EmptyChildEquipment.setSpeed(zombie);
                    } else if (pdc.has(TARDISWeepingAngels.ZYGON, PersistentDataType.INTEGER)) {
                        new Equipper(Monster.ZYGON, zombie, false, false).setHelmetAndInvisibilty();
                    } else if (pdc.has(TARDISWeepingAngels.SONTARAN, PersistentDataType.INTEGER)) {
                        new Equipper(Monster.SONTARAN, zombie, false, false).setHelmetAndInvisibilty();
                    } else if (pdc.has(TARDISWeepingAngels.VASHTA, PersistentDataType.INTEGER)) {
                        new Equipper(Monster.VASHTA_NERADA, zombie, false, false).setHelmetAndInvisibilty();
                    }
                }
            }
        }
    }
}
