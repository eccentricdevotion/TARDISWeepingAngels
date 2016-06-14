/*
 *  Copyright 2015 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.weeping_angels;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 *
 * @author eccentric_nz
 */
public class ImageHolder implements Listener {

    private final TARDISWeepingAngels plugin;
    private final MonsterEquipment equipper;
    private final List<BlockFace> faces = new ArrayList<BlockFace>();
    Random rand = new Random();

    public ImageHolder(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.equipper = new MonsterEquipment();
        this.faces.add(BlockFace.EAST);
        this.faces.add(BlockFace.NORTH);
        this.faces.add(BlockFace.WEST);
        this.faces.add(BlockFace.SOUTH);
    }

    @EventHandler(ignoreCancelled = true)
    public void onSkullPlace(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        if (message.toLowerCase().contains("angel") && rand.nextInt(100) < plugin.getConfig().getInt("angels.spawn_from_chat.chance")) {
            int dist = plugin.getConfig().getInt("angels.spawn_from_chat.distance_from_player");
            Block b = event.getPlayer().getLocation().getBlock().getRelative(faces.get(rand.nextInt(4)), dist);
            // get highest block in a random direction
            Location highest = b.getWorld().getHighestBlockAt(b.getLocation()).getLocation();
            final Location l = highest.add(0, 1, 0);
            // spawn an angel
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    final LivingEntity e = (LivingEntity) l.getWorld().spawnEntity(l, EntityType.SKELETON);
                    e.setSilent(true);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            equipper.setAngelEquipment(e, false);
                        }
                    }, 5L);
                }
            }, 20L);
        }
    }
}
