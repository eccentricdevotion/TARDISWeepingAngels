package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public class DisguiseCommand implements CommandExecutor {

    private final TARDISWeepingAngels plugin;

    public DisguiseCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("twad")) {
            if (args.length < 2) {
                return false;
            }
            // check monster type
            String upper = args[0].toUpperCase();
            if (upper.equals("SILENT")) {
                sender.sendMessage(plugin.pluginName + "You cannot disguise as a Silent!");
                return true;
            }
            if (upper.equals("OOD")) {
                sender.sendMessage(plugin.pluginName + "You cannot disguise as an Ood!");
                return true;
            }
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
                if (args.length < 3) {
                    sender.sendMessage(plugin.pluginName + "You must supply a player UUID when using this command from the console!");
                    return true;
                }
                UUID uuid = UUID.fromString(args[2]);
                player = plugin.getServer().getPlayer(uuid);
            }
            if (player == null) {
                sender.sendMessage(plugin.pluginName + "Command can only be used by a player, or a player UUID must be supplied!");
                return true;
            }
            if (!args[1].equalsIgnoreCase("on") && !args[1].equalsIgnoreCase("off")) {
                player.sendMessage(plugin.pluginName + "You need to specify if the disguise should be on or off!");
                return true;
            }
            PlayerInventory inv = player.getInventory();
            if (args[1].equalsIgnoreCase("on") && (inv.getBoots() != null || inv.getChestplate() != null || inv.getHelmet() != null || inv.getLeggings() != null)) {
                player.sendMessage(plugin.pluginName + "Your armour slots must be empty before using this command!");
                return true;
            }
            if (args[1].equalsIgnoreCase("on")) {
                switch (monster) {
                    case ANGEL:
                    case WEEPING_ANGEL:
                        TARDISWeepingAngels.getEqipper().setAngelEquipment(player, true);
                        break;
                    case CYBERMAN:
                        TARDISWeepingAngels.getEqipper().setCyberEquipment(player, true);
                        break;
                    case DALEK:
                        TARDISWeepingAngels.getEqipper().setDalekEquipment(player, true);
                        break;
                    case ICE:
                    case ICE_WARRIOR:
                    case WARRIOR:
                        TARDISWeepingAngels.getEqipper().setWarriorEquipment(player, true);
                        break;
                    case CHILD:
                    case EMPTY:
                    case EMPTY_CHILD:
                        TARDISWeepingAngels.getEqipper().setEmptyChildEquipment(player, true);
                        break;
                    case SILURIAN:
                        TARDISWeepingAngels.getEqipper().setSilurianEquipment(player, true);
                        break;
                    case SONTARAN:
                        TARDISWeepingAngels.getEqipper().setSontaranEquipment(player, true);
                        break;
                    case STRAX:
                        TARDISWeepingAngels.getEqipper().setButlerEquipment(player, true);
                        break;
                    case VASHTA:
                    case VASHTA_NERADA:
                        TARDISWeepingAngels.getEqipper().setVashtaNeradaEquipment(player, true);
                        break;
                    case ZYGON:
                        TARDISWeepingAngels.getEqipper().setZygonEquipment(player, true);
                        break;
                }
            } else {
                TARDISWeepingAngels.getEqipper().removeEquipment(player);
            }
            return true;
        }
        return false;
    }
}
