package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TARDISWeepingAngelsCommand implements CommandExecutor {

    private final TARDISWeepingAngels plugin;

    public TARDISWeepingAngelsCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("twa")) {
            if (args.length == 0) {
                return false;
            }
            String sub = args[0].toLowerCase();
            switch (sub) {
                case "spawn" -> {
                    return new SpawnCommand(plugin).spawn(sender, args);
                }
                case "disguise" -> {
                    return new DisguiseCommand(plugin).disguise(sender, args);
                }
                case "equip" -> {
                    return new EquipCommand(plugin).equip(sender, args);
                }
                case "count" -> {
                    return new CountCommand(plugin).count(sender, args);
                }
                case "kill" -> {
                    return new KillCommand(plugin).kill(sender, args);
                }
                case "set" -> {
                    return new AdminCommand(plugin).set(sender, args);
                }
                case "follow" -> {
                    return new FollowCommand(plugin).follow(sender, args);
                }
                case "stay" -> {
                    return new StayCommand(plugin).stay(sender);
                }
                case "remove" -> {
                    return new RemoveCommand(plugin).remove(sender);
                }
                case "give" -> {
                    return new GiveCommand(plugin).give(sender, args);
                }
                default -> {
                    // unknown command
                    sender.sendMessage(plugin.pluginName + "Invalid command! Try using tab completion.");
                    return true;
                }
            }
        }
        return true;
    }
}
