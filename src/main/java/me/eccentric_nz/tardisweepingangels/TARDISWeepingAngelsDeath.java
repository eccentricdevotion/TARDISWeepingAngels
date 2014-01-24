/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels;

import java.util.ArrayList;
import java.util.List;
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
    private final List<Material> angel_drops = new ArrayList<Material>();
    private final List<Material> ice_drops = new ArrayList<Material>();

    public TARDISWeepingAngelsDeath(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        for (String s : plugin.getConfig().getStringList("angels.drops")) {
            this.angel_drops.add(Material.valueOf(s));
        }
        for (String i : plugin.getConfig().getStringList("ice_warriors.drops")) {
            this.ice_drops.add(Material.valueOf(i));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAngelDeath(EntityDeathEvent event) {
        if (event.getEntityType().equals(EntityType.SKELETON)) {
            EntityEquipment ee = event.getEntity().getEquipment();
            if (ee.getHelmet().getType().equals(Material.WATER_LILY)) {
                event.getDrops().clear();
                ItemStack stack;
                if (plugin.getRandom().nextInt(100) < 3) {
                    stack = new ItemStack(Material.SKULL_ITEM, 1);
                } else {
                    stack = new ItemStack(angel_drops.get(plugin.getRandom().nextInt(angel_drops.size())), plugin.getRandom().nextInt(3) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
            }
        }
        if (event.getEntityType().equals(EntityType.PIG_ZOMBIE)) {
            EntityEquipment ee = event.getEntity().getEquipment();
            if (ee.getHelmet().getType().equals(Material.CHAINMAIL_HELMET)) {
                event.getDrops().clear();
                ItemStack stack;
                stack = new ItemStack(ice_drops.get(plugin.getRandom().nextInt(ice_drops.size())), plugin.getRandom().nextInt(3) + 1);
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
            }
        }
    }
}
