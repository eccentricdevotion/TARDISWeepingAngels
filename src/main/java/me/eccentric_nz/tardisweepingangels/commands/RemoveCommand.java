package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.ArmourStandFinder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class RemoveCommand {

    private final TARDISWeepingAngels plugin;

    public RemoveCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    public boolean remove(CommandSender sender) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        if (player == null) {
            sender.sendMessage(plugin.pluginName + "Command can only be used by a player!");
            return true;
        }
        UUID uuid = player.getUniqueId();
        if (plugin.getFollowTasks().containsKey(uuid)) {
            player.sendMessage(plugin.pluginName + "Please tell your follower to stay before removing it! /twa stay");
            return true;
        }
        ArmorStand stand = ArmourStandFinder.getStand(player);
        if (stand == null || !stand.getPersistentDataContainer().has(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID)) {
            player.sendMessage(plugin.pluginName + "You are not looking at a TARDISWeepingAngels entity!");
            return true;
        } else {
            if (stand.getPersistentDataContainer().has(TARDISWeepingAngels.JUDOON, PersistentDataType.INTEGER) && !player.hasPermission("tardisweepingangels.remove.judoon")) {
                player.sendMessage(plugin.pluginName + "You don't have permission to remove a Judoon!");
                return true;
            } else if (stand.getPersistentDataContainer().has(TARDISWeepingAngels.K9, PersistentDataType.INTEGER) && !player.hasPermission("tardisweepingangels.remove.k9")) {
                player.sendMessage(plugin.pluginName + "You don't have permission to remove K9!");
                return true;
            } else if (stand.getPersistentDataContainer().has(TARDISWeepingAngels.OOD, PersistentDataType.INTEGER) && !player.hasPermission("tardisweepingangels.remove.ood")) {
                player.sendMessage(plugin.pluginName + "You don't have permission to remove an Ood!");
                return true;
            }
            UUID storedUuid = stand.getPersistentDataContainer().get(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID);
            if (storedUuid != null && storedUuid.equals(uuid)) {
                stand.remove();
            } else {
                player.sendMessage(plugin.pluginName + "That is not your TARDISWeepingAngels entity!");
            }
        }
        return true;
    }
}
