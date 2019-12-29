/*
 *  Copyright 2015 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.daleks;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
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
            if (d instanceof Skeleton) {
                Skeleton skeleton = (Skeleton) d;
                if (pdc.has(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER) && skeleton.getEquipment().getHelmet() == null) {
                    TARDISWeepingAngels.getEqipper().setDalekEquipment(skeleton, false);
                } else if (pdc.has(TARDISWeepingAngels.ANGEL, PersistentDataType.INTEGER) && skeleton.getEquipment().getHelmet() != null && skeleton.getEquipment().getHelmet().getType() == Material.STONE_BUTTON) {
                    TARDISWeepingAngels.getEqipper().setAngelEquipment(skeleton, false);
                } else if (pdc.has(TARDISWeepingAngels.SILURIAN, PersistentDataType.INTEGER) && skeleton.getEquipment().getHelmet() != null && skeleton.getEquipment().getHelmet().getType() == Material.GOLDEN_HELMET) {
                    TARDISWeepingAngels.getEqipper().setSilurianEquipment(skeleton, false);
                }
            }
            if (d instanceof PigZombie) {
                PigZombie pigZombie = (PigZombie) d;
                if (pdc.has(TARDISWeepingAngels.WARRIOR, PersistentDataType.INTEGER) && pigZombie.getEquipment().getHelmet() != null && pigZombie.getEquipment().getHelmet().getType() == Material.IRON_HELMET) {
                    TARDISWeepingAngels.getEqipper().setWarriorEquipment(pigZombie, false);
                } else if (pdc.has(TARDISWeepingAngels.STRAX, PersistentDataType.INTEGER) && pigZombie.getEquipment().getHelmet() != null && pigZombie.getEquipment().getHelmet().getType() == Material.CHAINMAIL_HELMET) {
                    TARDISWeepingAngels.getEqipper().setButlerEquipment(pigZombie, false);
                }
            }
            if (d instanceof Zombie) {
                Zombie zombie = (Zombie) d;
                if (pdc.has(TARDISWeepingAngels.CYBERMAN, PersistentDataType.INTEGER) && zombie.getEquipment().getHelmet() != null && zombie.getEquipment().getHelmet().getType() == Material.IRON_HELMET) {
                    TARDISWeepingAngels.getEqipper().setCyberEquipment(zombie, false);
                } else if (zombie.getEquipment().getHelmet() != null && zombie.getEquipment().getHelmet().getType() == Material.GOLDEN_HELMET) {
                    if (pdc.has(TARDISWeepingAngels.EMPTY, PersistentDataType.INTEGER)) {
                        TARDISWeepingAngels.getEqipper().setEmptyChildEquipment(zombie, false);
                    } else if (pdc.has(TARDISWeepingAngels.ZYGON, PersistentDataType.INTEGER)) {
                        TARDISWeepingAngels.getEqipper().setZygonEquipment(zombie, false);
                    } else if (pdc.has(TARDISWeepingAngels.SONTARAN, PersistentDataType.INTEGER)) {
                        TARDISWeepingAngels.getEqipper().setSontaranEquipment(zombie, false);
                    } else if (pdc.has(TARDISWeepingAngels.VASHTA, PersistentDataType.INTEGER)) {
                        TARDISWeepingAngels.getEqipper().setVashtaNeradaEquipment(zombie, false);
                    }
                }
            }
        }
    }
}
