package me.eccentric_nz.tardisweepingangels;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class TARDISWeepingAngelsCommand implements CommandExecutor {

    private final TARDISWeepingAngels plugin;

    public TARDISWeepingAngelsCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("angel") || cmd.getName().equalsIgnoreCase("warrior") || cmd.getName().equalsIgnoreCase("cyberman")) {
            Player player = null;
            if (sender instanceof Player) {
                player = (Player) sender;
            }
            if (player == null) {
                sender.sendMessage(plugin.pluginName + "Command can only be used by a player!");
                return true;
            }
            final Location eyeLocation = player.getTargetBlock(null, 50).getLocation();
            eyeLocation.setX(eyeLocation.getX() + 0.5F);
            eyeLocation.setY(eyeLocation.getY() + 1);
            eyeLocation.setZ(eyeLocation.getZ() + 0.5F);
            World world = eyeLocation.getWorld();
            TARDISWeepingAngelEquipment equip = new TARDISWeepingAngelEquipment();
            if (cmd.getName().equalsIgnoreCase("angel")) {
                LivingEntity e = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.SKELETON);
                equip.setAngelEquipment(e, false);
            } else if (cmd.getName().equalsIgnoreCase("cyberman")) {
                LivingEntity e = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                equip.setCyberEquipment(e, false);
            } else {
                LivingEntity e = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.PIG_ZOMBIE);
                equip.setWarriorEquipment(e, false);
                PigZombie pigman = (PigZombie) e;
                pigman.setAngry(true);
                pigman.setAnger(Integer.MAX_VALUE);
            }
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("angeldisguise") || cmd.getName().equalsIgnoreCase("icedisguise") || cmd.getName().equalsIgnoreCase("cyberdisguise")) {
            if (args.length < 1) {
                return false;
            }
            Player player = null;
            if (sender instanceof Player) {
                player = (Player) sender;
            }
            if (player == null) {
                sender.sendMessage(plugin.pluginName + "Command can only be used by a player!");
                return true;
            }
            if (!args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("off")) {
                player.sendMessage(plugin.pluginName + "You need to specify if the disguise should be on or off!");
                return true;
            }
            PlayerInventory inv = player.getInventory();
            if (args[0].equalsIgnoreCase("on") && (inv.getBoots() != null || inv.getChestplate() != null || inv.getHelmet() != null || inv.getLeggings() != null)) {
                player.sendMessage(plugin.pluginName + "Your armour slots must be empty before using this command!");
                return true;
            }
            TARDISWeepingAngelEquipment equip = new TARDISWeepingAngelEquipment();
            if (args[0].equalsIgnoreCase("on")) {
                if (cmd.getName().equalsIgnoreCase("angeldisguise")) {
                    equip.setAngelEquipment(player, true);
                } else if (cmd.getName().equalsIgnoreCase("cyberdisguise")) {
                    equip.setCyberEquipment(player, true);
                } else {
                    equip.setWarriorEquipment(player, true);
                }
            } else {
                equip.removeEquipment(player);
            }
            return true;
        }
        return false;
    }
}
