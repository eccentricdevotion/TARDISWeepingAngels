/*
 *  Copyright 2017 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.equip;

import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityTameEvent;

/**
 * @author eccentric_nz
 */
public class K9TameOrBreed implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onWolfTame(EntityTameEvent event) {
        LivingEntity ent = event.getEntity();
        if (ent.getType().equals(EntityType.WOLF)) {
            Wolf k9 = (Wolf) ent;
            k9.setCollarColor(DyeColor.WHITE);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onWolfBreed(EntityBreedEvent event) {
        LivingEntity ent = event.getEntity();
        if (ent.getType().equals(EntityType.WOLF)) {
            Wolf k9 = (Wolf) ent;
            if (k9.isTamed()) {
                k9.setCollarColor(DyeColor.WHITE);
            }
        }
    }
}
