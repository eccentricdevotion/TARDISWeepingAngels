/*
 *  Copyright 2015 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.daleks;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

/**
 * @author eccentric_nz
 */
public class ChunkLoad implements Listener {

    private final TARDISWeepingAngels plugin;

    public ChunkLoad(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChunkLoad(ChunkLoadEvent event) {
        for (Entity d : event.getChunk().getEntities()) {
            if (d instanceof Skeleton) {
                if (d.getPersistentDataContainer().has(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER)) {
                    Skeleton skeleton = (Skeleton) d;
                    if (skeleton.getEquipment().getHelmet() == null) {
                        TARDISWeepingAngels.getEqipper().setDalekEquipment(skeleton);
                    }
                } else if (d.getPersistentDataContainer().has(TARDISWeepingAngels.ANGEL, PersistentDataType.INTEGER)) {
                    ItemStack head = new ItemStack(Material.STONE_BUTTON, 1);
                    ItemMeta hmeta = head.getItemMeta();
                    hmeta.setDisplayName("Weeping Angel Head");
                    hmeta.setCustomModelData(10000001);
                    head.setItemMeta(hmeta);
                    EntityEquipment ee = ((Skeleton) d).getEquipment();
                    ee.setHelmet(head);
                }
            }
        }
    }
}
