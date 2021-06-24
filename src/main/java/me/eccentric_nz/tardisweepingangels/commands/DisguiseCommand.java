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
import me.eccentric_nz.tardisweepingangels.equip.RemoveEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.cyberman.CybermanEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.dalek.DalekEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.emptychild.EmptyChildEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.hath.HathEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.icewarrior.IceWarriorEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.judoon.JudoonEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.k9.K9Equipment;
import me.eccentric_nz.tardisweepingangels.monsters.ood.OodEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.silent.SilentEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.silurian.SilurianEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.sontaran.SontaranEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.sontaran.StraxEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.toclafane.ToclafaneEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.vashtanerada.VashtaNeradaEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.weepingangel.AngelEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.zygon.ZygonEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public class DisguiseCommand {

    private final TardisWeepingAngelsPlugin plugin;

    public DisguiseCommand(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean disguise(CommandSender sender, String[] args) {
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
        if (sender instanceof ConsoleCommandSender) {
            // check argument length
            if (args.length < 4) {
                sender.sendMessage(plugin.pluginName + "You must supply a player UUID when using this command from the console!");
                return true;
            }
            UUID uuid = UUID.fromString(args[3]);
            player = plugin.getServer().getPlayer(uuid);
        }
        if (player == null) {
            sender.sendMessage(plugin.pluginName + "Command can only be used by a player, or a player UUID must be supplied!");
            return true;
        }
        if (args.length < 3 || (!args[2].equalsIgnoreCase("on") && !args[2].equalsIgnoreCase("off"))) {
            player.sendMessage(plugin.pluginName + "You need to specify if the disguise should be on or off!");
            return true;
        }
        PlayerInventory inventory = player.getInventory();
        if (args[2].equalsIgnoreCase("on") && (inventory.getBoots() != null || inventory.getChestplate() != null || inventory.getHelmet() != null || inventory.getLeggings() != null)) {
            player.sendMessage(plugin.pluginName + "Your armour slots must be empty before using this command!");
            return true;
        }
        if (args[2].equalsIgnoreCase("on")) {
            switch (monster) {
                case WEEPING_ANGEL -> AngelEquipment.set(player, true);
                case CYBERMAN -> CybermanEquipment.set(player, true);
                case DALEK -> DalekEquipment.set(player, true);
                case EMPTY_CHILD -> EmptyChildEquipment.set(player, true);
                case HATH -> HathEquipment.set(player, true);
                case ICE_WARRIOR -> IceWarriorEquipment.set(player, true);
                case JUDOON -> JudoonEquipment.set(null, player, true);
                case K9 -> K9Equipment.set(null, player, true);
                case OOD -> OodEquipment.set(null, player, true);
                case SILENT -> SilentEquipment.set(player, true);
                case SILURIAN -> SilurianEquipment.set(player, true);
                case SONTARAN -> SontaranEquipment.set(player, true);
                case STRAX -> StraxEquipment.set(player, true);
                case TOCLAFANE -> ToclafaneEquipment.set(player, true);
                case VASHTA_NERADA -> VashtaNeradaEquipment.set(player, true);
                case ZYGON -> ZygonEquipment.set(player, true);
            }
        } else {
            RemoveEquipment.set(player);
        }
        return true;
    }
}
