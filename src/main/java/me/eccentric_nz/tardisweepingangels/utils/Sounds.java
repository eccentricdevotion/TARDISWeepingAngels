package me.eccentric_nz.tardisweepingangels.utils;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Sounds implements Listener {

    private final TARDISWeepingAngels plugin;
    private final List<UUID> tracker = new ArrayList<>();

    public Sounds(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTargetPlayer(EntityTargetLivingEntityEvent event) {
        Entity entity = event.getEntity();
        if (MonsterEquipment.isMonster(entity) && event.getTarget() instanceof Player player) {
            UUID uuid = entity.getUniqueId();
            if (tracker.contains(uuid)) {
                return;
            }
            tracker.add(uuid);
            PersistentDataContainer pdc = entity.getPersistentDataContainer();
            String which = "";
            long delay = 50L;
            if (entity instanceof Guardian && entity.getVehicle() != null && entity.getVehicle().getType().equals(EntityType.SKELETON)) {
                delay = 90L;
                which = "silence";
            }
            if (pdc.has(TARDISWeepingAngels.HATH, PersistentDataType.INTEGER)) {
                delay = 100L;
                which = "hath";
            }
            if (pdc.has(TARDISWeepingAngels.WARRIOR, PersistentDataType.INTEGER)) {
                which = "warrior";
            }
            if (pdc.has(TARDISWeepingAngels.EMPTY, PersistentDataType.INTEGER)) {
                which = "empty_child";
            }
            if (pdc.has(TARDISWeepingAngels.CYBERMAN, PersistentDataType.INTEGER)) {
                which = "cyberman";
                delay = 80L;
            }
            if (pdc.has(TARDISWeepingAngels.SONTARAN, PersistentDataType.INTEGER)) {
                which = "sontaran";
                delay = 55L;
            }
            if (pdc.has(TARDISWeepingAngels.VASHTA, PersistentDataType.INTEGER)) {
                which = "vashta";
                delay = 30L;
            }
            if (pdc.has(TARDISWeepingAngels.ZYGON, PersistentDataType.INTEGER)) {
                which = "zygon";
                delay = 100L;
            }
            if (pdc.has(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER)) {
                which = "dalek";
            }
            if (pdc.has(TARDISWeepingAngels.MONK, PersistentDataType.INTEGER)) {
                delay = 180L;
                which = "headless_monk";
            }
            if (pdc.has(TARDISWeepingAngels.SILURIAN, PersistentDataType.INTEGER)) {
                which = "silurian";
            }
            if (!entity.getPassengers().isEmpty() && entity.getPassengers().get(0).getType().equals(EntityType.GUARDIAN)) {
                delay = 90L;
                which = "silence";
            }
            if (!which.isEmpty()) {
                String sound = which;
                // schedule delayed task
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    player.playSound(entity.getLocation(), sound, 1.0f, 1.0f);
                    tracker.remove(uuid);
                }, delay);
            }
        }
    }
}
