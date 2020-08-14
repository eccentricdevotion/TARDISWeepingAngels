/*
 *  Copyright Error: on line 4, column 30 in Templates/Licenses/license-default.txt
 The string doesn't match the expected date/time format. The string to parse was: "15/07/2014". The expected format was: "MMM d, yyyy". eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.vashta_nerada;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import me.eccentric_nz.tardisweepingangels.utils.WorldProcessor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author eccentric_nz
 */
public class VashtaNeradaListener implements Listener {

    private final TARDISWeepingAngels plugin;
    private final List<BlockFace> faces = new ArrayList<>();

    public VashtaNeradaListener(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        faces.add(BlockFace.EAST);
        faces.add(BlockFace.NORTH);
        faces.add(BlockFace.SOUTH);
        faces.add(BlockFace.WEST);
    }

    @EventHandler
    public void onBookshelfBreak(BlockBreakEvent event) {
        Block b = event.getBlock();
        if (b != null && b.getType().equals(Material.BOOKSHELF)) {
            String name = WorldProcessor.sanitiseName(b.getWorld().getName());
            if (plugin.getConfig().getInt("vashta_nerada.worlds." + name) > 0 && TARDISWeepingAngels.random.nextInt(100) < plugin.getConfig().getInt("vashta_nerada.worlds." + name)) {
                Location l = getClearLocation(event.getPlayer());
                if (l != null) {
                    // spawn Vashta Nerada at location
                    spawnVashtaNerada(l);
                }
            }
        }
    }

    private Location getClearLocation(Player p) {
        Location ret = null;
        Block l = p.getLocation().getBlock();
        World w = l.getWorld();
        Collections.shuffle(faces, TARDISWeepingAngels.random);
        for (BlockFace f : faces) {
            Block b = l.getRelative(f, 3);
            if (b.getType().equals(Material.AIR) && b.getRelative(BlockFace.UP).getType().equals(Material.AIR)) {
                ret = new Location(w, b.getX() + 0.5d, b.getY(), b.getZ() + 0.5d);
                break;
            }
        }
        return ret;
    }

    private void spawnVashtaNerada(Location l) {
        LivingEntity e = (LivingEntity) l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
        e.setSilent(true);
        Zombie vashta = (Zombie) e;
        vashta.setAdult();
        PotionEffect p = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3, true, false);
        e.addPotionEffect(p);
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            VashtaNeradaEquipment.set(e, false);
            plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.ZOMBIE, Monster.VASHTA_NERADA, l));
        }, 5L);
    }
}
