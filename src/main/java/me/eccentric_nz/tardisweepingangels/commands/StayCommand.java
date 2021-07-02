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
package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class StayCommand {

    private final TardisWeepingAngelsPlugin plugin;

    public StayCommand(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    public void stay(CommandSender sender) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        if (player == null) {
            sender.sendMessage(plugin.pluginName + "Command can only be used by a player!");
            return;
        }
        UUID uuid = player.getUniqueId();
        if (plugin.getFollowTasks().containsKey(uuid)) {
            plugin.getServer().getScheduler().cancelTask(plugin.getFollowTasks().get(uuid));
            plugin.getFollowTasks().remove(uuid);
        } else {
            player.sendMessage(plugin.pluginName + "An entity is not following you!");
        }
    }
}
