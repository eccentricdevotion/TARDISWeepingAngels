package me.eccentric_nz.tardisweepingangels.monsters.k9;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.UUID;

public class K9Follow {

	public static boolean run(TARDISWeepingAngels plugin, Player player, ArmorStand stand, String[] args) {
		if (!player.hasPermission("tardisweepingangels.follow.k9")) {
			player.sendMessage(plugin.pluginName + "You don't have permission to make K9 follow you!");
			return true;
		}
		if (stand.getPersistentDataContainer().has(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID)) {
			UUID uuid = player.getUniqueId();
			UUID k9Id = stand.getPersistentDataContainer().get(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID);
			assert k9Id != null;
			if (k9Id.equals(uuid)) {
				double speed = (args.length == 2) ? Math.min(Double.parseDouble(args[1]) / 100.0d, 0.5d) : 0.15d;
				int taskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new K9WalkRunnable(stand, speed, player), 2L, 2L);
				plugin.getFollowTasks().put(uuid, taskId);
			} else {
				player.sendMessage(plugin.pluginName + "That is not your K9!");
			}
		} else {
			player.sendMessage(plugin.pluginName + "That is a broken K9 :(");
		}
		return true;
	}
}
