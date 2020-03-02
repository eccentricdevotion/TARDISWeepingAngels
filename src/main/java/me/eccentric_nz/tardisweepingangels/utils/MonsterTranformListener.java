package me.eccentric_nz.tardisweepingangels.utils;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;

public class MonsterTranformListener implements Listener {

    private final TARDISWeepingAngels plugin;

    public MonsterTranformListener(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onMonsterDrowned(EntityTransformEvent event) {
        if (!event.getTransformReason().equals(EntityTransformEvent.TransformReason.DROWNED)) {
            return;
        }
        if (plugin.getWeepingAngelsAPI().isWeepingAngelMonster(event.getEntity())) {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> event.getTransformedEntity().remove(), 2L);
        }
    }
}
