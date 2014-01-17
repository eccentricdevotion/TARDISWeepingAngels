/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author eccentric_nz
 */
public class TARDISWeepingAngelsDeath implements Listener {

    private final TARDISWeepingAngels plugin;
    private final Material mat;

    public TARDISWeepingAngelsDeath(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.mat = Material.valueOf(plugin.getConfig().getString("drop"));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAngelDeath(EntityDeathEvent event) {
        if (event.getEntityType().equals(EntityType.SKELETON)) {
            EntityEquipment ee = event.getEntity().getEquipment();
            if (ee.getHelmet().getType().equals(Material.WATER_LILY)) {
                event.getDrops().clear();
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(mat, plugin.getRandom().nextInt(3) + 1));
            }
        }
    }
}
