package me.eccentric_nz.tardisweepingangels.monsters.toclafane;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class BeeSpawnListener implements Listener {

    private final TARDISWeepingAngels plugin;

    public BeeSpawnListener(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBeeSpawnEvent(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        if (!entity.getType().equals(EntityType.BEE)) {
            return;
        }
        World world = entity.getWorld();
        if (plugin.getConfig().getInt("tocalane.worlds." + world.getName()) < 1) {
            return;
        }
        if (TARDISWeepingAngels.random.nextInt(100) < plugin.getConfig().getInt("toclafane.spawn_from_bee")) {
            Entity toclafane = world.spawnEntity(entity.getLocation(), EntityType.ARMOR_STAND);
            ToclafaneEquipment.set(toclafane, false);
            plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(toclafane, EntityType.ARMOR_STAND, Monster.TOCLAFANE, entity.getLocation()));
            entity.remove();
        }
    }
}
