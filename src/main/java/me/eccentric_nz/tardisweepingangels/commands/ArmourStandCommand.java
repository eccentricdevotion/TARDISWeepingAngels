package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.ArmourStandEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.weeping_angels.Blink;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import me.eccentric_nz.tardisweepingangels.utils.Vector3D;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class ArmourStandCommand implements CommandExecutor {

    private final TARDISWeepingAngels plugin;

    public ArmourStandCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("twae")) {
            if (args.length < 1) {
                return false;
            }
            // check monster type
            String upper = args[0].toUpperCase();
            if (upper.equals("DALEK")) {
                sender.sendMessage(plugin.pluginName + "You cannot equip an armour stand with a Dalek disguise!");
                return true;
            }
            if (upper.equals("SILENT")) {
                sender.sendMessage(plugin.pluginName + "You cannot equip an armour stand with a Silent disguise!");
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
            if (player == null) {
                sender.sendMessage(plugin.pluginName + "Command can only be used by a player!");
                return true;
            }
            // get the armour stand player is looking at
            Location observerPos = player.getEyeLocation();
            Vector3D observerDir = new Vector3D(observerPos.getDirection());
            Vector3D observerStart = new Vector3D(observerPos);
            Vector3D observerEnd = observerStart.add(observerDir.multiply(16));
            ArmorStand as = null;
            // Get nearby entities
            for (Entity target : player.getNearbyEntities(8.0d, 8.0d, 8.0d)) {
                // Bounding box of the given player
                Vector3D targetPos = new Vector3D(target.getLocation());
                Vector3D minimum = targetPos.add(-0.5, 0, -0.5);
                Vector3D maximum = targetPos.add(0.5, 1.67, 0.5);
                if (target.getType().equals(EntityType.ARMOR_STAND) && Blink.hasIntersection(observerStart, observerEnd, minimum, maximum)) {
                    if (as == null || as.getLocation().distanceSquared(observerPos) > target.getLocation().distanceSquared(observerPos)) {
                        as = (ArmorStand) target;
                    }
                }
            }
            if (as != null) {
                ArmourStandEquipment equip = new ArmourStandEquipment();
                switch (monster) {
                    case ANGEL:
                    case WEEPING_ANGEL:
                        equip.setAngelEquipment(as);
                        break;
                    case CYBERMAN:
                        equip.setCyberEquipment(as);
                        break;
                    case ICE:
                    case ICE_WARRIOR:
                    case WARRIOR:
                        equip.setWarriorEquipment(as);
                        break;
                    case CHILD:
                    case EMPTY:
                    case EMPTY_CHILD:
                        equip.setEmptyChildEquipment(as);
                        break;
                    case SILURIAN:
                        equip.setSilurianEquipment(as);
                        break;
                    case SONTARAN:
                        equip.setSontaranEquipment(as);
                        break;
                    case STRAX:
                        equip.setButlerEquipment(as);
                        break;
                    case VASHTA:
                    case VASHTA_NERADA:
                        equip.setVashtaNeradaEquipment(as);
                        break;
                    case ZYGON:
                        equip.setZygonEquipment(as);
                        break;
                }
            } else {
                sender.sendMessage(plugin.pluginName + "You are not looking at an armour stand within 8 blocks!");
                return true;
            }
            return true;
        }

        return false;
    }
}
