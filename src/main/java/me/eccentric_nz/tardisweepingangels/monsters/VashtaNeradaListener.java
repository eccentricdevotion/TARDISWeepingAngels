/*
 *  Copyright Error: on line 4, column 30 in Templates/Licenses/license-default.txt
 The string doesn't match the expected date/time format. The string to parse was: "15/07/2014". The expected format was: "MMM d, yyyy". eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author eccentric_nz
 */
public class VashtaNeradaListener implements Listener {

    private final TARDISWeepingAngels plugin;
    private final List<String> worlds;
    private final Random r = new Random();
    private final int chance;
    private final List<BlockFace> faces = new ArrayList<BlockFace>();
    private final MonsterEquipment equipper;

    public VashtaNeradaListener(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.worlds = this.plugin.getConfig().getStringList("vashta_nerada.worlds");
        this.chance = this.plugin.getConfig().getInt("vashta_nerada.spawn_chance");
        faces.add(BlockFace.EAST);
        faces.add(BlockFace.NORTH);
        faces.add(BlockFace.SOUTH);
        faces.add(BlockFace.WEST);
        this.equipper = new MonsterEquipment();
    }

    @EventHandler
    public void onBookshelfBreak(BlockBreakEvent event) {
        Block b = event.getBlock();
        if (b != null && b.getType().equals(Material.BOOKSHELF)) {
            if (worlds.contains(b.getWorld().getName()) && r.nextInt(100) < chance) {
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
        Collections.shuffle(faces, r);
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
        final LivingEntity e = (LivingEntity) l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
        PotionEffect p = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3);
        e.addPotionEffect(p);
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                equipper.setVashtaNeradaEquipment(e, false);
            }
        }, 5L);
    }
}
