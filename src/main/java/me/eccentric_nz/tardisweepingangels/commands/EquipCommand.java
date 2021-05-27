package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.ArmourStandEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.weeping_angels.Blink;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import me.eccentric_nz.tardisweepingangels.utils.Vector3D;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class EquipCommand {

	private final TARDISWeepingAngels plugin;

	public EquipCommand(TARDISWeepingAngels plugin) {
		this.plugin = plugin;
	}

	public boolean equip(CommandSender sender, String[] args) {
		if (args.length < 2) {
			return false;
		}
		// check monster type
		String upper = args[1].toUpperCase();
		Monster monster;
		try {
			monster = Monster.valueOf(upper);
		} catch (IllegalArgumentException e) {
			sender.sendMessage(plugin.pluginName + "Invalid monster type!");
			return true;
		}
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (player == null) {
			sender.sendMessage(plugin.pluginName + "Command can only be used by a player!");
			return true;
		}
		// get the armour stand player is looking at
		Location observerPos = player.getEyeLocation();
		Vector3D observerDir = new Vector3D(observerPos.getDirection());
		Vector3D observerStart = new Vector3D(observerPos);
		Vector3D observerEnd = observerStart.add(observerDir.multiply(16));
		ArmorStand as = null;
		// Get nearby entities
		for (Entity target : player.getNearbyEntities(8.0d, 8.0d, 8.0d)) {
			// Bounding box of the given player
			Vector3D targetPos = new Vector3D(target.getLocation());
			Vector3D minimum = targetPos.add(-0.5, 0, -0.5);
			Vector3D maximum = targetPos.add(0.5, 1.67, 0.5);
			if (target.getType().equals(EntityType.ARMOR_STAND) && Blink.hasIntersection(observerStart, observerEnd, minimum, maximum)) {
				if (as == null || as.getLocation().distanceSquared(observerPos) > target.getLocation().distanceSquared(observerPos)) {
					as = (ArmorStand) target;
				}
			}
		}
		if (as != null) {
			new ArmourStandEquipment().setStandEquipment(as, monster, (monster == Monster.EMPTY_CHILD));
		} else {
			sender.sendMessage(plugin.pluginName + "You are not looking at an armour stand within 8 blocks!");
			return true;
		}
		return true;
	}
}
