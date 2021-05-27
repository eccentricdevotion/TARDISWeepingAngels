/*
 *  Copyright 2015 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.weeping_angels;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author eccentric_nz
 */
public class ImageHolder implements Listener {

	private final TARDISWeepingAngels plugin;
	private final List<BlockFace> faces = new ArrayList<>();

	public ImageHolder(TARDISWeepingAngels plugin) {
		this.plugin = plugin;
		faces.add(BlockFace.EAST);
		faces.add(BlockFace.NORTH);
		faces.add(BlockFace.WEST);
		faces.add(BlockFace.SOUTH);
	}

	@EventHandler(ignoreCancelled = true)
	public void onChatAboutWeepingAngel(AsyncPlayerChatEvent event) {
		String message = event.getMessage();
		if (message.toLowerCase().contains("angel") && TARDISWeepingAngels.random.nextInt(100) < plugin.getConfig().getInt("angels.spawn_from_chat.chance")) {
			int dist = plugin.getConfig().getInt("angels.spawn_from_chat.distance_from_player");
			Block b = event.getPlayer().getLocation().getBlock().getRelative(faces.get(TARDISWeepingAngels.random.nextInt(4)), dist);
			// get highest block in a random direction
			Location highest = b.getWorld().getHighestBlockAt(b.getLocation()).getLocation();
			Location l = highest.add(0, 1, 0);
			// spawn an angel
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
				LivingEntity e = (LivingEntity) Objects.requireNonNull(l.getWorld()).spawnEntity(l, EntityType.SKELETON);
				e.setSilent(true);
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
					AngelEquipment.set(e, false);
					plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.SKELETON, Monster.WEEPING_ANGEL, l));
				}, 5L);
			}, 20L);
		}
	}
}
