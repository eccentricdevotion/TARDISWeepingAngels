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
import me.eccentric_nz.tardisweepingangels.monsters.judoon.JudoonFollow;
import me.eccentric_nz.tardisweepingangels.monsters.k9.K9Follow;
import me.eccentric_nz.tardisweepingangels.monsters.ood.OodFollow;
import me.eccentric_nz.tardisweepingangels.utils.ArmorStandFinder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class FollowCommand {

    private final TardisWeepingAngelsPlugin plugin;

    public FollowCommand(TardisWeepingAngelsPlugin plugin) {
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
        ArmorStand armorStand = ArmorStandFinder.getStand(player);
        if (armorStand == null) {
            player.sendMessage(plugin.pluginName + "You are not looking at an entity that can follow you!");
            return true;
        }
        PersistentDataContainer persistentDataContainer = armorStand.getPersistentDataContainer();
        if (persistentDataContainer.has(TardisWeepingAngelsPlugin.ood, PersistentDataType.INTEGER)) {
            OodFollow.run(plugin, player, armorStand, args);
        } else if (persistentDataContainer.has(TardisWeepingAngelsPlugin.judoon, PersistentDataType.INTEGER)) {
            JudoonFollow.run(plugin, player, armorStand, args);
        } else if (persistentDataContainer.has(TardisWeepingAngelsPlugin.k9, PersistentDataType.INTEGER)) {
            K9Follow.run(plugin, player, armorStand, args);
        } else {
            player.sendMessage(plugin.pluginName + "You are not looking at an entity that can follow you!");
        }
        return true;
    }
}
