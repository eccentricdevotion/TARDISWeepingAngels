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
package me.eccentric_nz.tardisweepingangels.monsters.judoon;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.UUID;

public class JudoonFollow {

    public static boolean run(TardisWeepingAngelsPlugin plugin, Player player, ArmorStand armorStand, String[] args) {
        if (!player.hasPermission("tardisweepingangels.follow.judoon")) {
            player.sendMessage(plugin.pluginName + "You don't have permission to make a Judoon follow you!");
            return true;
        }
        if (armorStand.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.OWNER_UUID, TardisWeepingAngelsPlugin.PersistentDataTypeUuid)) {
            UUID uuid = player.getUniqueId();
            UUID judoonId = armorStand.getPersistentDataContainer().get(TardisWeepingAngelsPlugin.OWNER_UUID, TardisWeepingAngelsPlugin.PersistentDataTypeUuid);
            assert judoonId != null;
            if (judoonId.equals(uuid)) {
                double speed = (args.length == 2) ? Math.min(Double.parseDouble(args[1]) / 100.0d, 0.5d) : 0.15d;
                int taskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new JudoonWalkRunnable(armorStand, speed, player), 2L, 2L);
                plugin.getFollowTasks().put(uuid, taskId);
            } else {
                player.sendMessage(plugin.pluginName + "That is not your Judoon!");
            }
        } else {
            player.sendMessage(plugin.pluginName + "That is a broken Judoon :(");
        }
        return true;
    }
}
