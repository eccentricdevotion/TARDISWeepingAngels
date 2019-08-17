/*
 *  Copyright 2015 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.daleks;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

/**
 * @author eccentric_nz
 */
public class Portal implements Listener {

    private final TARDISWeepingAngels plugin;

    public Portal(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void dalekExitPortal(EntityPortalExitEvent event) {
        Entity e = event.getEntity();
        if (e.getType().equals(EntityType.SKELETON)) {
            Skeleton skeleton = (Skeleton) e;
            EntityEquipment ee = skeleton.getEquipment();
            ItemStack is = ee.getHelmet();
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                if (is.getType().equals(Material.VINE)) {
                    if (plugin.isLibsEnabled() && !DalekDisguiseLibs.isDisguised(skeleton)) {
                        DalekDisguiseLibs.disguise(skeleton);
                    } else if (!DalekDisguise.isDisguised(skeleton)) {
                        DalekDisguise.disguise(skeleton);
                    }
                }
            }, 5L);
        }
    }
}
