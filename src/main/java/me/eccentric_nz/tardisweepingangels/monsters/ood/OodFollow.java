package me.eccentric_nz.tardisweepingangels.monsters.ood;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.UUID;

public class OodFollow {

	public static boolean run(TARDISWeepingAngels plugin, Player player, ArmorStand stand, String[] args) {
		if (!player.hasPermission("tardisweepingangels.follow.ood")) {
			player.sendMessage(plugin.pluginName + "You don't have permission to make an Ood follow you!");
			return true;
		}
		if (stand.getPersistentDataContainer().has(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID)) {
			UUID uuid = player.getUniqueId();
			UUID oodId = stand.getPersistentDataContainer().get(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID);
			assert oodId != null;
			if (oodId.equals(uuid)) {
				double speed = (args.length == 2) ? Math.min(Double.parseDouble(args[1]) / 100.0d, 0.5d) : 0.15d;
				int taskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new OodWalkRunnable(stand, speed, player), 2L, 2L);
				plugin.getFollowTasks().put(uuid, taskId);
			} else {
				player.sendMessage(plugin.pluginName + "That is not your Ood!");
			}
		} else {
			player.sendMessage(plugin.pluginName + "That is a broken Ood :(");
		}
		return true;
	}
}
