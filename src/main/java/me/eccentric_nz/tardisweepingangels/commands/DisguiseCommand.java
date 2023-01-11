package me.eccentric_nz.tardisweepingangels.commands;

import java.util.UUID;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.Equipper;
import me.eccentric_nz.tardisweepingangels.equip.RemoveEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.daleks.DalekEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.judoon.JudoonEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.k9.K9Equipment;
import me.eccentric_nz.tardisweepingangels.monsters.ood.OodEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.toclafane.ToclafaneEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

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
                case WEEPING_ANGEL -> new Equipper(Monster.WEEPING_ANGEL, player, true, false).setHelmetAndInvisibilty();
                case CYBERMAN -> new Equipper(Monster.CYBERMAN, player, true, false).setHelmetAndInvisibilty();
                case DALEK -> DalekEquipment.set(player, true);
                case EMPTY_CHILD -> new Equipper(Monster.EMPTY_CHILD, player, true, false).setHelmetAndInvisibilty();
                case HATH -> new Equipper(Monster.HATH, player, true, false).setHelmetAndInvisibilty();
                case HEADLESS_MONK -> new Equipper(Monster.HEADLESS_MONK, player, true, false).setHelmetAndInvisibilty();
                case ICE_WARRIOR -> new Equipper(Monster.ICE_WARRIOR, player, true, false).setHelmetAndInvisibilty();
                case JUDOON -> JudoonEquipment.set(null, player, true);
                case K9 -> K9Equipment.set(null, player, true);
                case OOD -> OodEquipment.set(null, player, true);
                case SILENT -> new Equipper(Monster.SILENT, player, true, false).setHelmetAndInvisibilty();
                case SILURIAN -> new Equipper(Monster.SILURIAN, player, true, false).setHelmetAndInvisibilty();
                case SONTARAN -> new Equipper(Monster.SONTARAN, player, true, false).setHelmetAndInvisibilty();
                case STRAX -> new Equipper(Monster.STRAX, player, true, false).setHelmetAndInvisibilty();
                case TOCLAFANE -> ToclafaneEquipment.set(player, true);
                case VASHTA_NERADA -> new Equipper(Monster.VASHTA_NERADA, player, true, false).setHelmetAndInvisibilty();
                case ZYGON -> new Equipper(Monster.ZYGON, player, true, false).setHelmetAndInvisibilty();
            }
        } else {
            RemoveEquipment.set(player);
        }
        return true;
    }
}
