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
import me.eccentric_nz.tardisweepingangels.utils.ArmorStandFinder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class RemoveCommand {

    private final TardisWeepingAngelsPlugin plugin;

    public RemoveCommand(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    public void remove(CommandSender sender) {
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
            player.sendMessage(plugin.pluginName + "Please tell your follower to stay before removing it! /twa stay");
            return;
        }
        ArmorStand armorStand = ArmorStandFinder.getStand(player);
        if (armorStand == null || !armorStand.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.ownerUuid, TardisWeepingAngelsPlugin.persistentDataTypeUuid)) {
            player.sendMessage(plugin.pluginName + "You are not looking at a TARDISWeepingAngels entity!");
        } else {
            if (armorStand.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.judoon, PersistentDataType.INTEGER) && !player.hasPermission("tardisweepingangels.remove.judoon")) {
                player.sendMessage(plugin.pluginName + "You don't have permission to remove a Judoon!");
                return;
            } else if (armorStand.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.k9, PersistentDataType.INTEGER) && !player.hasPermission("tardisweepingangels.remove.k9")) {
                player.sendMessage(plugin.pluginName + "You don't have permission to remove K9!");
                return;
            } else if (armorStand.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.ood, PersistentDataType.INTEGER) && !player.hasPermission("tardisweepingangels.remove.ood")) {
                player.sendMessage(plugin.pluginName + "You don't have permission to remove an Ood!");
                return;
            }
            UUID storedUuid = armorStand.getPersistentDataContainer().get(TardisWeepingAngelsPlugin.ownerUuid, TardisWeepingAngelsPlugin.persistentDataTypeUuid);
            if (storedUuid != null && storedUuid.equals(uuid)) {
                armorStand.remove();
            } else {
                player.sendMessage(plugin.pluginName + "That is not your TARDISWeepingAngels entity!");
            }
        }
    }
}
