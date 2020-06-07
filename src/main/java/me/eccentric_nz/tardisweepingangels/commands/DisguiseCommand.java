package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.RemoveEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.cybermen.CybermanEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.daleks.DalekEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.empty_child.EmptyChildEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.hath.HathEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.ice_warriors.IceWarriorEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.judoon.JudoonEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.k9.K9Equipment;
import me.eccentric_nz.tardisweepingangels.monsters.ood.OodEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.silent.SilentEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.silurians.SilurianEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.sontarans.SontaranEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.sontarans.StraxEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.toclafane.ToclafaneEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.vashta_nerada.VashtaNeradaEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.weeping_angels.AngelEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.zygons.ZygonEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public class DisguiseCommand {

    private final TARDISWeepingAngels plugin;

    public DisguiseCommand(TARDISWeepingAngels plugin) {
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
        PlayerInventory inv = player.getInventory();
        if (args[2].equalsIgnoreCase("on") && (inv.getBoots() != null || inv.getChestplate() != null || inv.getHelmet() != null || inv.getLeggings() != null)) {
            player.sendMessage(plugin.pluginName + "Your armour slots must be empty before using this command!");
            return true;
        }
        if (args[2].equalsIgnoreCase("on")) {
            switch (monster) {
                case WEEPING_ANGEL:
                    AngelEquipment.set(player, true);
                    break;
                case CYBERMAN:
                    CybermanEquipment.set(player, true);
                    break;
                case DALEK:
                    DalekEquipment.set(player, true);
                    break;
                case EMPTY_CHILD:
                    EmptyChildEquipment.set(player, true);
                    break;
                case HATH:
                    HathEquipment.set(player, true);
                    break;
                case ICE_WARRIOR:
                    IceWarriorEquipment.set(player, true);
                    break;
                case JUDOON:
                    JudoonEquipment.set(null, player, true);
                    break;
                case K9:
                    K9Equipment.set(null, player, true);
                    break;
                case OOD:
                    OodEquipment.set(null, player, true);
                    break;
                case SILENT:
                    SilentEquipment.set(player, true);
                    break;
                case SILURIAN:
                    SilurianEquipment.set(player, true);
                    break;
                case SONTARAN:
                    SontaranEquipment.set(player, true);
                    break;
                case STRAX:
                    StraxEquipment.set(player, true);
                    break;
                case TOCLAFANE:
                    ToclafaneEquipment.set(player, true);
                    break;
                case VASHTA_NERADA:
                    VashtaNeradaEquipment.set(player, true);
                    break;
                case ZYGON:
                    ZygonEquipment.set(player, true);
                    break;
            }
        } else {
            RemoveEquipment.set(player);
        }
        return true;
    }
}
