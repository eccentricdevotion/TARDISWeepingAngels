package me.eccentric_nz.tardisweepingangels.commands;

import com.google.common.collect.ImmutableList;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.monsters.ood.OodWalkRunnable;
import me.eccentric_nz.tardisweepingangels.utils.ArmourStandFinder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.EulerAngle;

import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class OodCommand implements CommandExecutor {

    private final TARDISWeepingAngels plugin;
    private final HashMap<UUID, Integer> tasks = new HashMap<>();
    private final Random random = new Random();
    ImmutableList<String> OOD_SUBS = ImmutableList.of("spawn", "follow", "stay", "remove");

    public OodCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ood")) {
            Player player = null;
            if (sender instanceof Player) {
                player = (Player) sender;
            }
            if (player == null) {
                sender.sendMessage(plugin.pluginName + "Command can only be used by a player!");
                return true;
            }
            if (args.length < 1 || !OOD_SUBS.contains(args[0].toLowerCase(Locale.ENGLISH))) {
                return false;
            }
            UUID uuid = player.getUniqueId();
            if (args[0].equalsIgnoreCase("spawn")) {
                int colour = 0;
                int r = random.nextInt(100);
                if (r > 70) {
                    colour = (r > 85) ? 20 : 10;
                }
                Block block = player.getTargetBlock(null, 25);
                Location location = block.getLocation().add(0.5, 1.0, 0.5);
                location.setYaw(player.getLocation().getYaw() - 180.0f);
                Entity entity = block.getLocation().getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
                ArmorStand armorStand = (ArmorStand) entity;
                armorStand.getPersistentDataContainer().set(TARDISWeepingAngels.OOD, PersistentDataType.INTEGER, 0);
                armorStand.getPersistentDataContainer().set(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID, uuid);
                ItemStack head = new ItemStack(Material.ROTTEN_FLESH);
                ItemMeta headMeta = head.getItemMeta();
                headMeta.setDisplayName("Ood Head");
                headMeta.setCustomModelData(2 + colour);
                head.setItemMeta(headMeta);
                ItemStack arm = new ItemStack(Material.ROTTEN_FLESH);
                ItemMeta armMeta = arm.getItemMeta();
                armMeta.setDisplayName("Ood Arm");
                armMeta.setCustomModelData(3 + colour);
                arm.setItemMeta(armMeta);
                ItemStack brain_arm = new ItemStack(Material.ROTTEN_FLESH);
                ItemMeta brainMeta = brain_arm.getItemMeta();
                brainMeta.setDisplayName("Ood Brain Arm");
                brainMeta.setCustomModelData(4 + colour);
                brain_arm.setItemMeta(brainMeta);
                EntityEquipment ee = armorStand.getEquipment();
                armorStand.setHelmet(head);
                ee.setItemInMainHand(brain_arm);
                armorStand.setRightArmPose(new EulerAngle(-1d, 0, 0.175d));
                ee.setItemInOffHand(arm);
                armorStand.setVisible(false);
                armorStand.setSilent(true);
                armorStand.setCollidable(true);
                player.playSound(armorStand.getLocation(), "ood", 1.0f, 1.0f);
                return true;
            }
            if (args[0].equalsIgnoreCase("follow")) {
                if (tasks.containsKey(uuid)) {
                    player.sendMessage(plugin.pluginName + "An Ood is already following you!");
                    return true;
                }
                ArmorStand stand = ArmourStandFinder.getStand(player);
                if (stand == null) {
                    player.sendMessage(plugin.pluginName + "You are not looking at an Ood!");
                    return true;
                }
                if (!stand.getPersistentDataContainer().has(TARDISWeepingAngels.OOD, PersistentDataType.INTEGER)) {
                    player.sendMessage(plugin.pluginName + "You are not looking at an Ood!");
                    return true;
                }
                if (stand.getPersistentDataContainer().has(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID)) {
                    UUID oodId = stand.getPersistentDataContainer().get(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID);
                    if (oodId.equals(uuid)) {
                        double speed = (args.length == 2) ? Math.min(Double.parseDouble(args[1]) / 100.0d, 0.5d) : 0.15d;
                        int taskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new OodWalkRunnable(stand, speed, player), 2L, 2L);
                        tasks.put(uuid, taskId);
                    } else {
                        player.sendMessage(plugin.pluginName + "That is not your Ood!");
                    }
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("stay")) {
                if (tasks.containsKey(uuid)) {
                    plugin.getServer().getScheduler().cancelTask(tasks.get(uuid));
                    tasks.remove(uuid);
                } else {
                    player.sendMessage(plugin.pluginName + "An Ood is not following you!");
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("remove")) {
                if (tasks.containsKey(uuid)) {
                    player.sendMessage(plugin.pluginName + "Please tell your Ood to stay before removing it!");
                    return true;
                }
                ArmorStand stand = ArmourStandFinder.getStand(player);
                if (stand == null) {
                    player.sendMessage(plugin.pluginName + "You are not looking at an Ood!");
                    return true;
                }
                if (!stand.getPersistentDataContainer().has(TARDISWeepingAngels.OOD, PersistentDataType.INTEGER)) {
                    player.sendMessage(plugin.pluginName + "You are not looking at an Ood!");
                    return true;
                }
                if (stand.getPersistentDataContainer().has(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID)) {
                    UUID oodId = stand.getPersistentDataContainer().get(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID);
                    if (oodId.equals(uuid)) {
                        stand.remove();
                    } else {
                        player.sendMessage(plugin.pluginName + "That is not your Ood!");
                    }
                }
                return true;
            }
        }
        return true;
    }
}
