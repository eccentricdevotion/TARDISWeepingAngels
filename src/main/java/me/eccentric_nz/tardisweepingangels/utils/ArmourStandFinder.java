package me.eccentric_nz.tardisweepingangels.utils;

import me.eccentric_nz.tardisweepingangels.monsters.weeping_angels.Blink;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class ArmourStandFinder {

	public static ArmorStand getStand(Player player) {
		// get the armour stand player is looking at
		Location observerPos = player.getEyeLocation();
		Vector3D observerDir = new Vector3D(observerPos.getDirection());
		Vector3D observerStart = new Vector3D(observerPos);
		Vector3D observerEnd = observerStart.add(observerDir.multiply(16));
		// Get nearby entities
		for (Entity target : player.getNearbyEntities(8.0d, 8.0d, 8.0d)) {
			// Bounding box of the given player
			Vector3D targetPos = new Vector3D(target.getLocation());
			Vector3D minimum = targetPos.add(-0.5, 0, -0.5);
			Vector3D maximum = targetPos.add(0.5, 1.67, 0.5);
			if (target.getType().equals(EntityType.ARMOR_STAND) && Blink.hasIntersection(observerStart, observerEnd, minimum, maximum)) {
				return (ArmorStand) target;
			}
		}
		return null;
	}
}
