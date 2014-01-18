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
    private final List<Material> mat = new ArrayList<Material>();

    public TARDISWeepingAngelsDeath(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        for (String s : plugin.getConfig().getStringList("drops")) {
            this.mat.add(Material.valueOf(s));
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
                    stack = new ItemStack(mat.get(plugin.getRandom().nextInt(mat.size())), plugin.getRandom().nextInt(3) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
            }
        }
    }
}
