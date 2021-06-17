/*
 * Copyright (C) 2021 eccentric_nz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package me.eccentric_nz.tardisweepingangels.utils;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class FollowerChecker {

    private Monster monster;
    private int persist = -1;
    private boolean following = false;

    public FollowerChecker(Entity entity, UUID playerUuid) {
        checkEntity(entity, playerUuid);
    }

    void checkEntity(Entity entity, UUID playerUUID) {
        if (!entity.getType().equals(EntityType.ARMOR_STAND)) {
            monster = Monster.WEEPING_ANGEL;
            return;
        }
        PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
        if (persistentDataContainer.has(TardisWeepingAngelsPlugin.OWNER_UUID, TardisWeepingAngelsPlugin.PersistentDataTypeUuid)) {
            UUID uuid = persistentDataContainer.get(TardisWeepingAngelsPlugin.OWNER_UUID, TardisWeepingAngelsPlugin.PersistentDataTypeUuid);
            if (playerUUID.equals(uuid)) {
                if (TardisWeepingAngelsPlugin.plugin.getFollowTasks().containsKey(playerUUID)) {
                    following = true;
                    // remove following task
                    TardisWeepingAngelsPlugin.plugin.getFollowTasks().remove(playerUUID);
                }
                if (persistentDataContainer.has(TardisWeepingAngelsPlugin.JUDOON, PersistentDataType.INTEGER)) {
                    monster = Monster.JUDOON;
                    persist = persistentDataContainer.get(TardisWeepingAngelsPlugin.JUDOON, PersistentDataType.INTEGER);
                    return;
                } else if (persistentDataContainer.has(TardisWeepingAngelsPlugin.K9, PersistentDataType.INTEGER)) {
                    monster = Monster.K9;
                    return;
                } else if (persistentDataContainer.has(TardisWeepingAngelsPlugin.OOD, PersistentDataType.INTEGER)) {
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
