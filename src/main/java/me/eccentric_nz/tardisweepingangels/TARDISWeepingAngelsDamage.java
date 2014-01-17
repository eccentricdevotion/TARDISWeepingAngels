/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;

/**
 *
 * @author eccentric_nz
 */
public class TARDISWeepingAngelsDamage implements Listener {

    private final TARDISWeepingAngels plugin;
    private final Material mat;

    public TARDISWeepingAngelsDamage(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.mat = Material.valueOf(plugin.getConfig().getString("weapon"));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBeatUpAngel(EntityDamageByEntityEvent event) {
        if (event.getEntityType().equals(EntityType.SKELETON)) {
            EntityEquipment ee = ((LivingEntity) event.getEntity()).getEquipment();
            if (ee.getHelmet().getType().equals(Material.WATER_LILY)) {
                Entity e = event.getDamager();
                if (e instanceof Player) {
                    Player p = (Player) e;
                    if (!p.getItemInHand().getType().equals(mat)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
