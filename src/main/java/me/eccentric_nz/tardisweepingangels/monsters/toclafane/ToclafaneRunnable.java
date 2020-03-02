package me.eccentric_nz.tardisweepingangels.monsters.toclafane;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import me.eccentric_nz.tardisweepingangels.utils.WaterChecker;
import me.eccentric_nz.tardisweepingangels.utils.WorldGuardChecker;
import me.eccentric_nz.tardisweepingangels.utils.WorldProcessor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.Collection;

public class ToclafaneRunnable implements Runnable {

    private final TARDISWeepingAngels plugin;
    private final int spawn_rate;

    public ToclafaneRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        spawn_rate = this.plugin.getConfig().getInt("spawn_rate.how_many");
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((w) -> {
            // only configured worlds
            String name = WorldProcessor.sanitiseName(w.getName());
            if (plugin.getConfig().getInt("toclafane.worlds." + name) > 0) {
                // get the current toclafane
                int n = 0;
                Collection<Bee> hive = w.getEntitiesByClass(Bee.class);
                for (Bee b : hive) {
                    if (b.getPassengers() != null && b.getPassengers().size() > 0 && b.getPassengers().get(0) instanceof ArmorStand) {
                        n++;
                    }
                }
                if (n < plugin.getConfig().getInt("toclafane.worlds." + name)) {
                    // if less than maximum, spawn some more
                    for (int i = 0; i < spawn_rate; i++) {
                        spawnToclafane(w);
                    }
                }
            }
        });
    }

    private void spawnToclafane(World w) {
        Chunk[] chunks = w.getLoadedChunks();
        if (chunks.length > 0) {
            Chunk c = chunks[TARDISWeepingAngels.random.nextInt(chunks.length)];
            int x = c.getX() * 16 + TARDISWeepingAngels.random.nextInt(16);
            int z = c.getZ() * 16 + TARDISWeepingAngels.random.nextInt(16);
            int y = w.getHighestBlockYAt(x, z);
            Location l = new Location(w, x, y + 1, z);
            if (WaterChecker.isNotWater(l)) {
                if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null && !WorldGuardChecker.canSpawn(l)) {
                    return;
                }
                Entity e = w.spawnEntity(l, EntityType.ARMOR_STAND);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    ToclafaneEquipment.set(e, false);
                    plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.ARMOR_STAND, Monster.TOCLAFANE, l));
                }, 5L);
            }
        }
    }
}
