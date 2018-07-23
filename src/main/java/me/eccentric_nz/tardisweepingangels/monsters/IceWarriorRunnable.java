/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Config;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author eccentric_nz
 */
public class IceWarriorRunnable implements Runnable {

    private final TARDISWeepingAngels plugin;
    private final int spawn_rate;
    private final MonsterEquipment equipper;
    private final List<Biome> biomes = new ArrayList<>();

    public IceWarriorRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.spawn_rate = plugin.getConfig().getInt("spawn_rate.how_many");
        this.equipper = new MonsterEquipment();
        biomes.add(Biome.DEEP_FROZEN_OCEAN);
        biomes.add(Biome.FROZEN_OCEAN);
        biomes.add(Biome.FROZEN_RIVER);
        biomes.add(Biome.ICE_SPIKES);
        biomes.add(Biome.SNOWY_BEACH);
        biomes.add(Biome.SNOWY_MOUNTAINS);
        biomes.add(Biome.SNOWY_TAIGA);
        biomes.add(Biome.SNOWY_TAIGA_HILLS);
        biomes.add(Biome.SNOWY_TAIGA_MOUNTAINS);
        biomes.add(Biome.SNOWY_TUNDRA);
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((w) -> {
            // only configured worlds
            String name = Config.sanitiseName(w.getName());
            if (plugin.getConfig().getInt("ice_warriors.worlds." + name) > 0) {
                long time = w.getTime();
                // only spawn in day - times according to http://minecraft.gamepedia.com/Day-night_cycle
                if ((time > 0 && time < 13187) || time > 22812) {
                    // get the current warriors
                    List<PigZombie> warriors = new ArrayList<>();
                    Collection<PigZombie> piggies = w.getEntitiesByClass(PigZombie.class);
                    piggies.forEach((pz) -> {
                        EntityEquipment ee = pz.getEquipment();
                        if (ee.getHelmet().getType().equals(Material.IRON_HELMET)) {
                            ItemStack is = ee.getHelmet();
                            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Ice Warrior")) {
                                warriors.add(pz);
                            }
                        }
                    });
                    // count the current warriors
                    if (warriors.size() < plugin.getConfig().getInt("ice_warriors.worlds." + name)) {
                        // if less than maximum, spawn some more
                        for (int i = 0; i < spawn_rate; i++) {
                            spawnIceWarrior(w);
                        }
                    }
                }
            }
        });
    }

    private void spawnIceWarrior(World w) {
        Chunk[] chunks = w.getLoadedChunks();
        if (chunks.length > 0) {
            Chunk c = chunks[plugin.getRandom().nextInt(chunks.length)];
            int x = c.getX() * 16 + plugin.getRandom().nextInt(16);
            int z = c.getZ() * 16 + plugin.getRandom().nextInt(16);
            int y = w.getHighestBlockYAt(x, z);
            final Location l = new Location(w, x, y + 1, z);
            if (biomes.contains(l.getBlock().getBiome())) {
                final LivingEntity e = (LivingEntity) w.spawnEntity(l, EntityType.PIG_ZOMBIE);
                e.setSilent(true);
                PigZombie warrior = (PigZombie) e;
                warrior.setAngry(true);
                warrior.setAnger(Integer.MAX_VALUE);
                PotionEffect p = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3, true, false);
                warrior.addPotionEffect(p);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    equipper.setWarriorEquipment(e, false);
                    plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.PIG_ZOMBIE, Monster.ICE_WARRIOR, l));
                }, 5L);
            }
        }
    }
}
