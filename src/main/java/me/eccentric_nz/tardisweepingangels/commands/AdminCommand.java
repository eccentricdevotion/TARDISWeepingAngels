package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class AdminCommand implements CommandExecutor {

    private final TARDISWeepingAngels plugin;
    private final HashMap<String, String> types = new HashMap<>();

    public AdminCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        types.put("a", "angels");
        types.put("c", "cybermen");
        types.put("d", "daleks");
        types.put("e", "empty_child");
        types.put("i", "ice_warriors");
        types.put("m", "silence");
        types.put("o", "sontarans");
        types.put("r", "ood");
        types.put("s", "silurians");
        types.put("v", "vashta_nerada");
        types.put("z", "zygons");
        types.put("all", "");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("twa")) {
            if (args.length < 3) {
                return false;
            }
            String which = args[0].toLowerCase();
            if (!types.containsKey(which)) {
                sender.sendMessage(plugin.pluginName + "Could not find a world with that name!");
                return false;
            }
            if (plugin.getServer().getWorld(args[1]) == null) {
                return false;
            }
            int m;
            try {
                m = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(plugin.pluginName + "Last argument must be a number!");
                return false;
            }
            if (which.equals("all")) {
                plugin.getConfig().set("angels.worlds." + args[1], m);
                plugin.getConfig().set("cybermen.worlds." + args[1], m);
                plugin.getConfig().set("daleks.worlds." + args[1], m);
                plugin.getConfig().set("empty_child.worlds." + args[1], m);
                plugin.getConfig().set("ice_warriors.worlds." + args[1], m);
                plugin.getConfig().set("silence.worlds." + args[1], m);
                plugin.getConfig().set("sontarans.worlds." + args[1], m);
                plugin.getConfig().set("ood.worlds." + args[1], m);
                plugin.getConfig().set("silurians.worlds." + args[1], m);
                plugin.getConfig().set("vashta_nerada.worlds." + args[1], m);
                plugin.getConfig().set("zygons.worlds." + args[1], m);
            } else {
                plugin.getConfig().set(types.get(which) + ".worlds." + args[1], m);
            }
            plugin.saveConfig();
            sender.sendMessage(plugin.pluginName + "Config updated!");
            return true;
        }
        return false;
    }
}
