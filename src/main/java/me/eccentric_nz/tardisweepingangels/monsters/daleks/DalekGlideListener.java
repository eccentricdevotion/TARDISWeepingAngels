package me.eccentric_nz.tardisweepingangels.monsters.daleks;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class DalekGlideListener implements Listener {

    private final TARDISWeepingAngels plugin;

    public DalekGlideListener(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGlideChange(EntityToggleGlideEvent event) {
        if (!event.isGliding()) {
            Entity entity = event.getEntity();
            if (entity instanceof Skeleton skeleton) {
                PersistentDataContainer pdc = skeleton.getPersistentDataContainer();
                if (pdc.has(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER)) {
                    EntityEquipment ee = skeleton.getEquipment();
                    if (ee.getChestplate().getType().equals(Material.ELYTRA)) {
                        ee.setChestplate(null);
                    }
                }
            }
        }
    }
}
