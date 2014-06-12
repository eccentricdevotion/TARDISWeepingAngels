package me.eccentric_nz.tardisweepingangels;

import java.util.Collection;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class TARDISWeepingAngelsCountCommand implements CommandExecutor {

    private final TARDISWeepingAngels plugin;
    private final HashMap<String, String> types = new HashMap<String, String>();

    public TARDISWeepingAngelsCountCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.types.put("a", "angels");
        this.types.put("c", "cybermen");
        this.types.put("i", "ice_warriors");
        this.types.put("e", "empty_child");
        this.types.put("z", "zygons");
        this.types.put("s", "silurians");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("twac")) {
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
            } else if (which.equals("e")) {
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
            } else if (which.equals("s")) {
                what = "Silurians";
                Collection<Skeleton> skeletons = w.getEntitiesByClass(Skeleton.class);
                for (Skeleton s : skeletons) {
                    EntityEquipment ee = s.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.GOLD_HELMET)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Silurian")) {
                            count++;
                        }
                    }
                }
            }
            sender.sendMessage(plugin.pluginName + "There are " + count + " " + what + " in " + w.getName());
            return true;
        }
        return false;
    }
}
