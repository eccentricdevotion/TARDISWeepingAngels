/*
 *  Copyright 2015 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.silent;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

/**
 * @author eccentric_nz
 */
public class SilentTarget implements Listener {

    private final TARDISWeepingAngels plugin;

    public SilentTarget(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGuardianTarget(EntityTargetLivingEntityEvent event) {
        Entity attacker = event.getEntity();
        LivingEntity target = event.getTarget();
        if (!(attacker instanceof Guardian guardian)) {
            return;
        }
        if (!(target instanceof Player)) {
            return;
        }
        if (guardian.getVehicle() == null) {
            return;
        }
        Entity vehicle = guardian.getVehicle();
        if (!(vehicle instanceof Skeleton silent)) {
            return;
        }
        if (!silent.getPersistentDataContainer().has(TARDISWeepingAngels.SILENT, PersistentDataType.INTEGER)) {
            return;
        }
        EntityEquipment ee = silent.getEquipment();
        if (ee == null) {
            return;
        }
        ItemStack head = ee.getHelmet();
        if (head == null) {
            return;
        }
        ItemMeta im = head.getItemMeta();
        if (im == null) {
            return;
        }
        EntityTargetEvent.TargetReason reason = event.getReason();
        int cmd = (reason == EntityTargetEvent.TargetReason.CLOSEST_PLAYER) ? 6 : 5;
        im.setCustomModelData(cmd);
        head.setItemMeta(im);
        ee.setHelmet(head);
    }
}
