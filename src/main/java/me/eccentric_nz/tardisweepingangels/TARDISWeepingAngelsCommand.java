package me.eccentric_nz.tardisweepingangels;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class TARDISWeepingAngelsCommand implements CommandExecutor {

    private final TARDISWeepingAngels plugin;

    public TARDISWeepingAngelsCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("tardisangel")) {
            Player player = null;
            if (sender instanceof Player) {
                player = (Player) sender;
            }
            if (player == null) {
                sender.sendMessage(plugin.pluginName + "Command can only be used by a player!");
                return true;
            }
            final Location eyeLocation = player.getTargetBlock(null, 50).getLocation();
            eyeLocation.setY(eyeLocation.getY() + 1);
            World world = eyeLocation.getWorld();
            TARDISWeepingAngelEquipment equip = new TARDISWeepingAngelEquipment();
            LivingEntity e = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.SKELETON);
            equip.setEquipment(e);
            return true;
        }
        return false;
    }
}
