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
import me.eccentric_nz.tardisweepingangels.equip.ArmourStandEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.weeping_angel.Blink;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import me.eccentric_nz.tardisweepingangels.utils.Vector3d;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class EquipCommand {

    private final TardisWeepingAngelsPlugin plugin;

    public EquipCommand(TardisWeepingAngelsPlugin plugin) {
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
        Location observerPose = player.getEyeLocation();
        Vector3d observerDirection = new Vector3d(observerPose.getDirection());
        Vector3d observerStart = new Vector3d(observerPose);
        Vector3d observerEnd = observerStart.add(observerDirection.multiply(16));
        ArmorStand armorStand = null;
        // Get nearby entities
        for (Entity target : player.getNearbyEntities(8.0d, 8.0d, 8.0d)) {
            // Bounding box of the given player
            Vector3d targetPos = new Vector3d(target.getLocation());
            Vector3d minimum = targetPos.add(-0.5, 0, -0.5);
            Vector3d maximum = targetPos.add(0.5, 1.67, 0.5);
            if (target.getType().equals(EntityType.ARMOR_STAND) && Blink.hasIntersection(observerStart, observerEnd, minimum, maximum)) {
                if (armorStand == null || armorStand.getLocation().distanceSquared(observerPose) > target.getLocation().distanceSquared(observerPose)) {
                    armorStand = (ArmorStand) target;
                }
            }
        }
        if (armorStand != null) {
            new ArmourStandEquipment().setStandEquipment(armorStand, monster, (monster == Monster.EMPTY_CHILD));
        } else {
            sender.sendMessage(plugin.pluginName + "You are not looking at an armour stand within 8 blocks!");
            return true;
        }
        return true;
    }
}
