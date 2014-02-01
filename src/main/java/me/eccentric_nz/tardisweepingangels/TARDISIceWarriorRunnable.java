/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author eccentric_nz
 */
public class TARDISIceWarriorRunnable implements Runnable {

    private final TARDISWeepingAngels plugin;
    private final int spawn_rate;
    private final int maximum;
    private final TARDISWeepingAngelEquipment equipper;
    private final List<Biome> biomes = new ArrayList<Biome>();

    public TARDISIceWarriorRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.spawn_rate = plugin.getConfig().getInt("ice_warriors.spawn_rate.how_many");
        this.maximum = plugin.getConfig().getInt("ice_warriors.spawn_rate.max_per_world");
        this.equipper = new TARDISWeepingAngelEquipment();
        biomes.add(Biome.COLD_BEACH);
        biomes.add(Biome.COLD_TAIGA);
        biomes.add(Biome.COLD_TAIGA_HILLS);
        biomes.add(Biome.COLD_TAIGA_MOUNTAINS);
        biomes.add(Biome.FROZEN_OCEAN);
        biomes.add(Biome.FROZEN_RIVER);
        biomes.add(Biome.ICE_MOUNTAINS);
        biomes.add(Biome.ICE_PLAINS);
        biomes.add(Biome.ICE_PLAINS_SPIKES);
    }

    @Override
    public void run() {
        for (World w : plugin.getServer().getWorlds()) {
            // only configured worlds
            if (plugin.getConfig().getStringList("ice_warriors.worlds").contains(w.getName())) {
                long time = w.getTime();
                // only spawn in day - times according to http://minecraft.gamepedia.com/Day-night_cycle
                if ((time > 0 && time < 13187) || time > 22812) {
                    // get the current warriors
                    List<PigZombie> warriors = new ArrayList<PigZombie>();
                    Collection<PigZombie> skellies = w.getEntitiesByClass(PigZombie.class);
                    for (PigZombie s : skellies) {
                        EntityEquipment ee = s.getEquipment();
                        if (ee.getHelmet().getType().equals(Material.CHAINMAIL_HELMET)) {
                            ItemStack is = ee.getHelmet();
                            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Ice Warrior")) {
                                warriors.add(s);
                            }
                        }
                    }
                    // count the current warriors
                    if (warriors.size() < maximum) {
                        // if less than maximum, spawn some more
                        for (int i = 0; i < spawn_rate; i++) {
                            spawnIceWarrior(w);
                        }
                    }
                }
            }
        }
    }

    private void spawnIceWarrior(World w) {
        Chunk[] chunks = w.getLoadedChunks();
        if (chunks.length > 0) {
            Chunk c = chunks[plugin.getRandom().nextInt(chunks.length)];
            int x = c.getX() * 16 + plugin.getRandom().nextInt(16);
            int z = c.getZ() * 16 + plugin.getRandom().nextInt(16);
            int y = w.getHighestBlockYAt(x, z);
            Location l = new Location(w, x, y + 1, z);
            if (biomes.contains(l.getBlock().getBiome())) {
                LivingEntity e = (LivingEntity) w.spawnEntity(l, EntityType.PIG_ZOMBIE);
                PigZombie pigman = (PigZombie) e;
                pigman.setAngry(true);
                pigman.setAnger(Integer.MAX_VALUE);
                equipper.setWarriorEquipment(e, false);
            }
        }
    }
}
