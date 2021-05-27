package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.monsters.judoon.JudoonFollow;
import me.eccentric_nz.tardisweepingangels.monsters.k9.K9Follow;
import me.eccentric_nz.tardisweepingangels.monsters.ood.OodFollow;
import me.eccentric_nz.tardisweepingangels.utils.ArmourStandFinder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class FollowCommand {

	private final TARDISWeepingAngels plugin;

	public FollowCommand(TARDISWeepingAngels plugin) {
		this.plugin = plugin;
	}

	public boolean follow(CommandSender sender, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (player == null) {
			sender.sendMessage(plugin.pluginName + "Command can only be used by a player!");
			return true;
		}
		if (plugin.getFollowTasks().containsKey(player.getUniqueId())) {
			player.sendMessage(plugin.pluginName + "An entity is already following you!");
			return true;
		}
		// get the armour stand
		ArmorStand stand = ArmourStandFinder.getStand(player);
		if (stand == null) {
			player.sendMessage(plugin.pluginName + "You are not looking at an entity that can follow you!");
			return true;
		}
		PersistentDataContainer pdc = stand.getPersistentDataContainer();
		if (pdc.has(TARDISWeepingAngels.OOD, PersistentDataType.INTEGER)) {
			OodFollow.run(plugin, player, stand, args);
		} else if (pdc.has(TARDISWeepingAngels.JUDOON, PersistentDataType.INTEGER)) {
			JudoonFollow.run(plugin, player, stand, args);
		} else if (pdc.has(TARDISWeepingAngels.K9, PersistentDataType.INTEGER)) {
			K9Follow.run(plugin, player, stand, args);
		} else {
			player.sendMessage(plugin.pluginName + "You are not looking at an entity that can follow you!");
		}
		return true;
	}
}
