package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.monsters.daleks.ReDisguise;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Set;

public class DalekCommand implements CommandExecutor {

    private final TARDISWeepingAngels plugin;
    private final Set<Material> trans = null;

    public DalekCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("twar")) {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new ReDisguise(plugin), 1L);
            return true;
        }
        return false;
    }
}
