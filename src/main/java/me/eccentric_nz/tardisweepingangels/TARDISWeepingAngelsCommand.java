package me.eccentric_nz.tardisweepingangels;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class TARDISWeepingAngelsCommand implements CommandExecutor {

    private final TARDISWeepingAngels plugin;
    private final HashMap<String, String> types = new HashMap<String, String>();

    public TARDISWeepingAngelsCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.types.put("a", "angels");
        this.types.put("c", "cybermen");
        this.types.put("i", "ice_warriors");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("angel") || cmd.getName().equalsIgnoreCase("warrior") || cmd.getName().equalsIgnoreCase("cyberman")) {
            Player player = null;
            if (sender instanceof Player) {
                player = (Player) sender;
            }
            if (player == null) {
                sender.sendMessage(plugin.pluginName + "Command can only be used by a player!");
                return true;
            }
//            if (player.hasPermission("tardisweepingangels.spawn")) {
//                player.sendMessage(plugin.pluginName + "You do not have permission to use this command!");
//                return true;
//            }
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
//            if (player.hasPermission("tardisweepingangels.disguise")) {
//                player.sendMessage(plugin.pluginName + "You do not have permission to use this command!");
//                return true;
//            }
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
        if (cmd.getName().equalsIgnoreCase("angelcount")) {
//            if (sender.hasPermission("tardisweepingangels.count")) {
//                sender.sendMessage(plugin.pluginName + "You do not have permission to use this command!");
//                return true;
//            }
            if (args.length < 2) {
                return false;
            }
            String which = args[0].toLowerCase();
            if (!types.containsKey(which)) {
                return false;
            }
            World w = plugin.getServer().getWorld(args[1]);
            if (w == null) {
                sender.sendMessage(plugin.pluginName + "Could not find a world with that name!");
                return true;
            }
            int count = 0;
            String what = "Angels";
            if (which.equals("a")) {
                Collection<Skeleton> skellies = w.getEntitiesByClass(Skeleton.class);
                for (Skeleton s : skellies) {
                    EntityEquipment ee = s.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.WATER_LILY)) {
                        count++;
                    }
                }
            } else if (which.equals("c")) {
                what = "Cybermen";
                Collection<Zombie> zombies = w.getEntitiesByClass(Zombie.class);
                for (Zombie s : zombies) {
                    EntityEquipment ee = s.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.IRON_HELMET)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Cyberman")) {
                            count++;
                        }
                    }
                }
            } else {
                what = "Ice Warriors";
                Collection<PigZombie> skellies = w.getEntitiesByClass(PigZombie.class);
                for (PigZombie s : skellies) {
                    EntityEquipment ee = s.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.CHAINMAIL_HELMET)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Cyberman")) {
                            count++;
                        }
                    }
                }
            }
            sender.sendMessage(plugin.pluginName + "There are " + count + " " + what + " in " + w.getName());
            return true;
        }
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
