package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class StayCommand {

	private final TARDISWeepingAngels plugin;

	public StayCommand(TARDISWeepingAngels plugin) {
		this.plugin = plugin;
	}

	public boolean stay(CommandSender sender) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (player == null) {
			sender.sendMessage(plugin.pluginName + "Command can only be used by a player!");
			return true;
		}
		UUID uuid = player.getUniqueId();
		if (plugin.getFollowTasks().containsKey(uuid)) {
			plugin.getServer().getScheduler().cancelTask(plugin.getFollowTasks().get(uuid));
			plugin.getFollowTasks().remove(uuid);
		} else {
			player.sendMessage(plugin.pluginName + "A entity is not following you!");
		}
		return true;
	}
}
