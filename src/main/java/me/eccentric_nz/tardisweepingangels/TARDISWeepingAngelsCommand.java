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
        this.types.put("e", "empty_child");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("angel") || cmd.getName().equalsIgnoreCase("warrior") || cmd.getName().equalsIgnoreCase("cyberman") || cmd.getName().equalsIgnoreCase("empty")) {
            Player player = null;
            if (sender instanceof Player) {
                player = (Player) sender;
            }
            if (player == null) {
                sender.sendMessage(plugin.pluginName + "Command can only be used by a player!");
                return true;
            }
            @SuppressWarnings("deprecation")
            final Location eyeLocation = player.getTargetBlock(null, 50).getLocation();
            eyeLocation.setX(eyeLocation.getX() + 0.5F);
            eyeLocation.setY(eyeLocation.getY() + 1);
            eyeLocation.setZ(eyeLocation.getZ() + 0.5F);
            World world = eyeLocation.getWorld();
            final TARDISWeepingAngelEquipment equip = new TARDISWeepingAngelEquipment();
            if (cmd.getName().equalsIgnoreCase("angel")) {
                final LivingEntity e = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.SKELETON);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        equip.setAngelEquipment(e, false);
                    }
                }, 5L);
            } else if (cmd.getName().equalsIgnoreCase("cyberman")) {
                final LivingEntity e = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (plugin.getConfig().getBoolean("always_use_leather")) {
                            equip.setCyberLeatherEquipment(e, false);
                        } else {
                            equip.setCyberEquipment(e, false);
                        }
                    }
                }, 5L);
            } else if (cmd.getName().equalsIgnoreCase("warrior")) {
                final LivingEntity e = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.PIG_ZOMBIE);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (plugin.getConfig().getBoolean("always_use_leather")) {
                            equip.setWarriorLeatherEquipment(e, false);
                        } else {
                            equip.setWarriorEquipment(e, false);
                        }
                    }
                }, 5L);
                PigZombie pigman = (PigZombie) e;
                pigman.setAngry(true);
                pigman.setAnger(Integer.MAX_VALUE);
            } else if (cmd.getName().equalsIgnoreCase("empty")) {
                final LivingEntity e = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                Zombie child = (Zombie) e;
                child.setVillager(false);
                child.setBaby(true);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        equip.setEmptyChildEquipment(e, false);
                    }
                }, 5L);
            }
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("angeldisguise") || cmd.getName().equalsIgnoreCase("icedisguise") || cmd.getName().equalsIgnoreCase("cyberdisguise") || cmd.getName().equalsIgnoreCase("emptydisguise")) {
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
                    if (plugin.getConfig().getBoolean("always_use_leather")) {
                        equip.setCyberLeatherEquipment(player, true);
                    } else {
                        equip.setCyberEquipment(player, true);
                    }
                } else if (cmd.getName().equalsIgnoreCase("icedisguise")) {
                    if (plugin.getConfig().getBoolean("always_use_leather")) {
                        equip.setWarriorLeatherEquipment(player, true);
                    } else {
                        equip.setWarriorEquipment(player, true);
                    }
                } else if (cmd.getName().equalsIgnoreCase("emptydisguise")) {
                    equip.setEmptyChildEquipment(player, true);
                }
            } else {
                equip.removeEquipment(player);
            }
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("angelcount")) {
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
            } else if (which.equals("i")) {
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
            } else {
                what = "Empty Children";
                Collection<Zombie> zombies = w.getEntitiesByClass(Zombie.class);
                for (Zombie s : zombies) {
                    EntityEquipment ee = s.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.IRON_HELMET)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Empty Child")) {
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
