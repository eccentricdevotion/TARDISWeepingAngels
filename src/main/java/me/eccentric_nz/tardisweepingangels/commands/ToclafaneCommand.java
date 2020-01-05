package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardischunkgenerator.TARDISHelper;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class ToclafaneCommand implements CommandExecutor {

    private final TARDISWeepingAngels plugin;

    public ToclafaneCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("toclafane")) {
            Player player = null;
            if (sender instanceof Player) {
                player = (Player) sender;
            }
            if (player == null) {
                sender.sendMessage(plugin.pluginName + "Command can only be used by a player!");
                return true;
            }
            if (args.length < 1) {
                return false;
            }
            UUID uuid = player.getUniqueId();
            if (args[0].equalsIgnoreCase("spawn")) {
                Block block = player.getTargetBlock(null, 25);
                Location location = block.getLocation().add(0.5, 1.0, 0.5);
                String world = location.getWorld().getName();
                if (!plugin.getConfig().getBoolean("toclafane.worlds." + world)) {
                    player.sendMessage(plugin.pluginName + "You cannot spawn a Toclafane in this world!");
                    return true;
                }
                location.setYaw(player.getLocation().getYaw() - 180.0f);
                Entity entity = block.getLocation().getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
                ArmorStand armorStand = (ArmorStand) entity;
                armorStand.getPersistentDataContainer().set(TARDISWeepingAngels.TOCLAFANE, PersistentDataType.INTEGER, 0);
                armorStand.getPersistentDataContainer().set(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID, uuid);
                ItemStack head = new ItemStack(Material.GUNPOWDER);
                ItemMeta headMeta = head.getItemMeta();
                headMeta.setDisplayName("Toclafane");
                headMeta.setCustomModelData(1);
                head.setItemMeta(headMeta);
                armorStand.setHelmet(head);
                armorStand.setVisible(false);
                armorStand.setSilent(true);
                armorStand.setCollidable(true);
                player.playSound(armorStand.getLocation(), "toclafane", 1.0f, 1.0f);
                Bee bee = (Bee) block.getLocation().getWorld().spawnEntity(location, EntityType.BEE);
                if (plugin.getServer().getPluginManager().isPluginEnabled("TARDISChunkGenerator")) {
                    TARDISHelper tardisHelper = (TARDISHelper) plugin.getServer().getPluginManager().getPlugin("TARDISChunkGenerator");
                    tardisHelper.setBeeTicks(bee, Integer.MAX_VALUE);
                }
                PotionEffect p = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false);
                bee.addPotionEffect(p);
                bee.addPassenger(entity);
                return true;
            }
            if (args[0].equalsIgnoreCase("remove")) {
                for (Entity entity : player.getLocation().getChunk().getEntities()) {
                    if (entity instanceof Bee) {
                        Bee bee = (Bee) entity;
                        if (bee.getPassengers().size() > 0) {
                            Entity passenger = bee.getPassengers().get(0);
                            if (passenger instanceof ArmorStand && passenger.getPersistentDataContainer().has(TARDISWeepingAngels.TOCLAFANE, PersistentDataType.INTEGER)) {
                                passenger.remove();
                                bee.remove();
                            }
                        }
                    }
                }
                return true;
            }
        }
        return true;
    }
}
