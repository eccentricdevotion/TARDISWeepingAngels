package me.eccentric_nz.tardisweepingangels.commands;

import com.google.common.collect.ImmutableList;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.monsters.ood.OodWalkRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.weeping_angels.Blink;
import me.eccentric_nz.tardisweepingangels.utils.Vector3D;
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
                armorStand.getPersistentDataContainer().set(TARDISWeepingAngels.OOD_UUID, TARDISWeepingAngels.PersistentDataTypeUUID, uuid);
                ItemStack head = new ItemStack(Material.ROTTEN_FLESH);
                ItemMeta headMeta = head.getItemMeta();
                headMeta.setCustomModelData(2 + colour);
                head.setItemMeta(headMeta);
                ItemStack arm = new ItemStack(Material.ROTTEN_FLESH);
                ItemMeta armMeta = arm.getItemMeta();
                armMeta.setCustomModelData(3 + colour);
                arm.setItemMeta(armMeta);
                ItemStack ball_arm = new ItemStack(Material.ROTTEN_FLESH);
                ItemMeta ballMeta = ball_arm.getItemMeta();
                ballMeta.setCustomModelData(4 + colour);
                ball_arm.setItemMeta(ballMeta);
                EntityEquipment ee = armorStand.getEquipment();
                armorStand.setHelmet(head);
                ee.setItemInMainHand(ball_arm);
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
                ArmorStand stand = getStand(player);
                if (stand == null) {
                    player.sendMessage(plugin.pluginName + "You are not looking at an Ood!");
                    return true;
                }
                if (!stand.getPersistentDataContainer().has(TARDISWeepingAngels.OOD, PersistentDataType.INTEGER)) {
                    player.sendMessage(plugin.pluginName + "You are not looking at an Ood!");
                    return true;
                }
                if (stand.getPersistentDataContainer().has(TARDISWeepingAngels.OOD_UUID, TARDISWeepingAngels.PersistentDataTypeUUID)) {
                    UUID oodId = stand.getPersistentDataContainer().get(TARDISWeepingAngels.OOD_UUID, TARDISWeepingAngels.PersistentDataTypeUUID);
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
                ArmorStand stand = getStand(player);
                if (stand == null) {
                    player.sendMessage(plugin.pluginName + "You are not looking at an Ood!");
                    return true;
                }
                if (!stand.getPersistentDataContainer().has(TARDISWeepingAngels.OOD, PersistentDataType.INTEGER)) {
                    player.sendMessage(plugin.pluginName + "You are not looking at an Ood!");
                    return true;
                }
                if (stand.getPersistentDataContainer().has(TARDISWeepingAngels.OOD_UUID, TARDISWeepingAngels.PersistentDataTypeUUID)) {
                    UUID oodId = stand.getPersistentDataContainer().get(TARDISWeepingAngels.OOD_UUID, TARDISWeepingAngels.PersistentDataTypeUUID);
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

    private ArmorStand getStand(Player player) {
        ArmorStand stand = null;
        // get the armour stand player is looking at
        Location observerPos = player.getEyeLocation();
        Vector3D observerDir = new Vector3D(observerPos.getDirection());
        Vector3D observerStart = new Vector3D(observerPos);
        Vector3D observerEnd = observerStart.add(observerDir.multiply(16));
        // Get nearby entities
        for (Entity target : player.getNearbyEntities(8.0d, 8.0d, 8.0d)) {
            // Bounding box of the given player
            Vector3D targetPos = new Vector3D(target.getLocation());
            Vector3D minimum = targetPos.add(-0.5, 0, -0.5);
            Vector3D maximum = targetPos.add(0.5, 1.67, 0.5);
            if (target.getType().equals(EntityType.ARMOR_STAND) && Blink.hasIntersection(observerStart, observerEnd, minimum, maximum)) {
                if (stand == null || stand.getLocation().distanceSquared(observerPos) > target.getLocation().distanceSquared(observerPos)) {
                    return (ArmorStand) target;
                }
            }
        }
        return stand;
    }
}
