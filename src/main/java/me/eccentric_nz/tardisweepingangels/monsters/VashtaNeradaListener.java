/*
 *  Copyright Error: on line 4, column 30 in Templates/Licenses/license-default.txt
 The string doesn't match the expected date/time format. The string to parse was: "15/07/2014". The expected format was: "MMM d, yyyy". eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Config;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
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

/**
 *
 * @author eccentric_nz
 */
public class VashtaNeradaListener implements Listener {

    private final TARDISWeepingAngels plugin;
    private final Random r = new Random();
    private final List<BlockFace> faces = new ArrayList<BlockFace>();
    private final MonsterEquipment equipper;

    public VashtaNeradaListener(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
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
            String name = Config.sanitiseName(b.getWorld().getName());
            if (plugin.getConfig().getInt("vashta_nerada.worlds." + name) > 0 && r.nextInt(100) < plugin.getConfig().getInt("vashta_nerada.worlds." + name)) {
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

    private void spawnVashtaNerada(final Location l) {
        final LivingEntity e = (LivingEntity) l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
        e.setSilent(true);
        Zombie vashta = (Zombie) e;
        //vashta.setVillager(false);
        vashta.setVillagerProfession(null);
        vashta.setBaby(false);
        PotionEffect p = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3, true, false);
        e.addPotionEffect(p);
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                equipper.setVashtaNeradaEquipment(e, false);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.ZOMBIE, Monster.VASHTA_NERADA, l));
            }
        }, 5L);
    }
}
