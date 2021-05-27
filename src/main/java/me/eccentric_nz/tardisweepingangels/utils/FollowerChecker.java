package me.eccentric_nz.tardisweepingangels.utils;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class FollowerChecker {

	private Monster monster;
	private int persist = -1;
	private boolean following = false;

	public FollowerChecker(Entity entity, UUID playerUUID) {
		checkEntity(entity, playerUUID);
	}

	void checkEntity(Entity entity, UUID playerUUID) {
		if (!entity.getType().equals(EntityType.ARMOR_STAND)) {
			monster = Monster.WEEPING_ANGEL;
			return;
		}
		PersistentDataContainer pdc = entity.getPersistentDataContainer();
		if (pdc.has(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID)) {
			UUID uuid = pdc.get(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID);
			if (playerUUID.equals(uuid)) {
				if (TARDISWeepingAngels.plugin.getFollowTasks().containsKey(playerUUID)) {
					following = true;
					// remove following task
					TARDISWeepingAngels.plugin.getFollowTasks().remove(playerUUID);
				}
				if (pdc.has(TARDISWeepingAngels.JUDOON, PersistentDataType.INTEGER)) {
					monster = Monster.JUDOON;
					persist = pdc.get(TARDISWeepingAngels.JUDOON, PersistentDataType.INTEGER);
					return;
				} else if (pdc.has(TARDISWeepingAngels.K9, PersistentDataType.INTEGER)) {
					monster = Monster.K9;
					return;
				} else if (pdc.has(TARDISWeepingAngels.OOD, PersistentDataType.INTEGER)) {
					monster = Monster.OOD;
					return;
				}
			}
		}
		monster = Monster.WEEPING_ANGEL;
	}

	public Monster getMonster() {
		return monster;
	}

	public int getPersist() {
		return persist;
	}

	public boolean isFollowing() {
		return following;
	}
}
