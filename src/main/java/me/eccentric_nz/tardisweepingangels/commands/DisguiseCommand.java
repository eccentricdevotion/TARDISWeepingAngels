package me.eccentric_nz.tardisweepingangels.commands;

import java.util.UUID;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

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
            if (upper.equals("DALEK")) {
                sender.sendMessage(plugin.pluginName + "You cannot disguise as a Dalek!");
                return true;
            }
            if (upper.equals("SILENT")) {
                sender.sendMessage(plugin.pluginName + "You cannot disguise as a Silent!");
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
            MonsterEquipment equip = new MonsterEquipment();
            if (args[1].equalsIgnoreCase("on")) {
                switch (monster) {
                    case ANGEL:
                    case WEEPING_ANGEL:
                        equip.setAngelEquipment(player, true);
                        break;
                    case CYBERMAN:
                        equip.setCyberEquipment(player, true);
                        break;
                    case ICE:
                    case ICE_WARRIOR:
                    case WARRIOR:
                        equip.setWarriorEquipment(player, true);
                        break;
                    case CHILD:
                    case EMPTY:
                    case EMPTY_CHILD:
                        equip.setEmptyChildEquipment(player, true);
                        break;
                    case SILURIAN:
                        equip.setSilurianEquipment(player, true);
                        break;
                    case SONTARAN:
                        equip.setSontaranEquipment(player, true);
                        break;
                    case STRAX:
                        equip.setButlerEquipment(player, true);
                        break;
                    case VASHTA:
                    case VASHTA_NERADA:
                        equip.setVashtaNeradaEquipment(player, true);
                        break;
                    case ZYGON:
                        equip.setZygonEquipment(player, true);
                        break;
                }
            } else {
                equip.removeEquipment(player);
            }
            return true;
        }
        return false;
    }
}
