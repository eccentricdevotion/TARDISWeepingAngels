/*
 *  Copyright 2015 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.headless_monks;

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
public class HeadlessTarget implements Listener {

    private final TARDISWeepingAngels plugin;

    public HeadlessTarget(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHeadlessMonkTarget(EntityTargetLivingEntityEvent event) {
        Entity attacker = event.getEntity();
        LivingEntity target = event.getTarget();
        if (!(attacker instanceof Skeleton skeleton)) {
            return;
        }
        if (!(target instanceof Player)) {
            return;
        }
        if (!skeleton.getPersistentDataContainer().has(TARDISWeepingAngels.HEADLESS_TASK, PersistentDataType.INTEGER)) {
            return;
        }
        int taskID = skeleton.getPersistentDataContainer().get(TARDISWeepingAngels.HEADLESS_TASK, PersistentDataType.INTEGER);
        int flameID = skeleton.getPersistentDataContainer().get(TARDISWeepingAngels.FLAME_TASK, PersistentDataType.INTEGER);
        EntityTargetEvent.TargetReason reason = event.getReason();
        if (reason == EntityTargetEvent.TargetReason.CLOSEST_PLAYER) {
            // start projectile runnable
            taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new HeadlessShootRunnable(attacker, target, plugin.getConfig().getString("headless_monks.projectile").equals("SMALL_FIREBALL")), 1, 40);
            skeleton.getPersistentDataContainer().set(TARDISWeepingAngels.HEADLESS_TASK, PersistentDataType.INTEGER, taskID);
            if (flameID != -1) {
                // stop flame runnable
                plugin.getServer().getScheduler().cancelTask(flameID);
            }
        } else if (reason == EntityTargetEvent.TargetReason.FORGOT_TARGET || reason == EntityTargetEvent.TargetReason.TARGET_DIED) {
            // stop projectile runnable
            plugin.getServer().getScheduler().cancelTask(taskID);
            if (plugin.getConfig().getBoolean("headless_monks.particles")) {
                // start flame runnable
                flameID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new HeadlessFlameRunnable((LivingEntity) attacker), 1, 10);
                skeleton.getPersistentDataContainer().set(TARDISWeepingAngels.FLAME_TASK, PersistentDataType.INTEGER, flameID);
            }
        }
    }
}
