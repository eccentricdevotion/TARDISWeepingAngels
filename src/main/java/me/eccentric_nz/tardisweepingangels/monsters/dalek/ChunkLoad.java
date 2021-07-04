/*
 *  Copyright 2015 eccentric_nz.
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

/**
 * @author eccentric_nz
 */
public class ChunkLoad implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChunkLoad(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
            if (entity instanceof Skeleton skeleton) {
                if (persistentDataContainer.has(TardisWeepingAngelsPlugin.dalek, PersistentDataType.INTEGER) && (skeleton.getEquipment().getHelmet() == null || skeleton.getEquipment().getHelmet().getType() == Material.MUSHROOM_STEM)) {
                    DalekEquipment.set(skeleton, false);
                } else if (persistentDataContainer.has(TardisWeepingAngelsPlugin.weepingAngel, PersistentDataType.INTEGER) && (skeleton.getEquipment().getHelmet() != null && skeleton.getEquipment().getHelmet().getType() == Material.STONE_BUTTON)) {
                    AngelEquipment.set(skeleton, false);
                } else if (persistentDataContainer.has(TardisWeepingAngelsPlugin.silurian, PersistentDataType.INTEGER) && skeleton.getEquipment().getHelmet() != null && skeleton.getEquipment().getHelmet().getType() == Material.GOLDEN_HELMET) {
                    SilurianEquipment.set(skeleton, false);
                }
            } else if (entity instanceof PigZombie pigZombie) {
                if (persistentDataContainer.has(TardisWeepingAngelsPlugin.iceWarrior, PersistentDataType.INTEGER) && pigZombie.getEquipment().getHelmet() != null && pigZombie.getEquipment().getHelmet().getType() == Material.IRON_HELMET) {
                    IceWarriorEquipment.set(pigZombie, false);
                } else if (persistentDataContainer.has(TardisWeepingAngelsPlugin.strax, PersistentDataType.INTEGER) && pigZombie.getEquipment().getHelmet() != null && pigZombie.getEquipment().getHelmet().getType() == Material.CHAINMAIL_HELMET) {
                    StraxEquipment.set(pigZombie, false);
                }
            } else if (entity instanceof Drowned drowned) {
                if (drowned.getEquipment().getHelmet() != null) {
                    ItemMeta itemMeta = drowned.getEquipment().getHelmet().getItemMeta();
                    if (itemMeta != null && itemMeta.hasDisplayName() && itemMeta.getDisplayName().endsWith(" Head")) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(TardisWeepingAngelsPlugin.plugin, drowned::remove, 2L);
                    }
                }
            } else if (entity instanceof Zombie zombie) {
                if (persistentDataContainer.has(TardisWeepingAngelsPlugin.cyberman, PersistentDataType.INTEGER) && zombie.getEquipment().getHelmet() != null && zombie.getEquipment().getHelmet().getType() == Material.IRON_HELMET) {
                    CybermanEquipment.set(zombie, false);
                } else if (zombie.getEquipment().getHelmet() != null && zombie.getEquipment().getHelmet().getType() == Material.GOLDEN_HELMET) {
                    if (persistentDataContainer.has(TardisWeepingAngelsPlugin.emptyChild, PersistentDataType.INTEGER)) {
                        EmptyChildEquipment.set(zombie, false);
                    } else if (persistentDataContainer.has(TardisWeepingAngelsPlugin.zygon, PersistentDataType.INTEGER)) {
                        ZygonEquipment.set(zombie, false);
                    } else if (persistentDataContainer.has(TardisWeepingAngelsPlugin.sontaran, PersistentDataType.INTEGER)) {
                        SontaranEquipment.set(zombie, false);
                    } else if (persistentDataContainer.has(TardisWeepingAngelsPlugin.vashtaNerada, PersistentDataType.INTEGER)) {
                        VashtaNeradaEquipment.set(zombie, false);
                    }
                }
            }
        }
    }
}
