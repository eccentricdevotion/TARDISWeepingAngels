package me.eccentric_nz.tardisweepingangels;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TARDISWeepingAngelsAdminCommand implements CommandExecutor {

    private final TARDISWeepingAngels plugin;
    private final HashMap<String, String> types = new HashMap<String, String>();

    public TARDISWeepingAngelsAdminCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.types.put("a", "angels");
        this.types.put("c", "cybermen");
        this.types.put("i", "ice_warriors");
        this.types.put("e", "empty_child");
        this.types.put("z", "zygons");
        this.types.put("s", "silurians");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("twa")) {
            if (args.length < 3) {
                return false;
            }
            String which = args[0].toLowerCase();
            if (!types.containsKey(which)) {
                return false;
            }
            String ar = args[1].toLowerCase();
            if (!Arrays.asList(new String[]{"add", "remove"}).contains(ar)) {
                return false;
            }
            List<String> worlds = plugin.getConfig().getStringList(types.get(which) + ".worlds");
            if (ar.equals("add")) {
                World w = plugin.getServer().getWorld(args[2]);
                if (w == null) {
                    sender.sendMessage(plugin.pluginName + "Could not find a world with that name!");
                    return true;
                }
                worlds.add(args[2]);
            } else {
                if (worlds.contains(args[2])) {
                    worlds.remove(args[2]);
                } else {
                    sender.sendMessage(plugin.pluginName + "World is not in config, no action taken...");
                    return true;
                }
            }
            plugin.getConfig().set(types.get(which) + ".worlds", worlds);
            plugin.saveConfig();
            sender.sendMessage(plugin.pluginName + "Config updated!");
            return true;
        }
        return false;
    }
}
