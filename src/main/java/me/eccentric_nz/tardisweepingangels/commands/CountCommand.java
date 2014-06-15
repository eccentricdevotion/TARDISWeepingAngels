package me.eccentric_nz.tardisweepingangels.commands;

import java.util.Collection;
import java.util.HashMap;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
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

public class CountCommand implements CommandExecutor {

    private final TARDISWeepingAngels plugin;
    private final HashMap<String, String> types = new HashMap<String, String>();

    public CountCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.types.put("a", "angels");
        this.types.put("c", "cybermen");
        this.types.put("d", "daleks");
        this.types.put("e", "empty_child");
        this.types.put("i", "ice_warriors");
        this.types.put("o", "sontarans");
        this.types.put("s", "silurians");
        this.types.put("z", "zygons");
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
                Collection<Skeleton> angels = w.getEntitiesByClass(Skeleton.class);
                for (Skeleton a : angels) {
                    EntityEquipment ee = a.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.WATER_LILY)) {
                        count++;
                    }
                }
            } else if (which.equals("c")) {
                what = "Cybermen";
                Collection<Zombie> cybermen = w.getEntitiesByClass(Zombie.class);
                for (Zombie c : cybermen) {
                    EntityEquipment ee = c.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.IRON_HELMET)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Cyberman")) {
                            count++;
                        }
                    }
                }
            } else if (which.equals("d")) {
                what = "Daleks";
                Collection<Skeleton> daleks = w.getEntitiesByClass(Skeleton.class);
                for (Skeleton d : daleks) {
                    EntityEquipment ee = d.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.LEATHER_HELMET)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Dalek")) {
                            count++;
                        }
                    }
                }
            } else if (which.equals("e")) {
                what = "Empty Children";
                Collection<Zombie> kids = w.getEntitiesByClass(Zombie.class);
                for (Zombie e : kids) {
                    EntityEquipment ee = e.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.IRON_HELMET)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Empty Child")) {
                            count++;
                        }
                    }
                }
            } else if (which.equals("i")) {
                what = "Ice Warriors";
                Collection<PigZombie> warriors = w.getEntitiesByClass(PigZombie.class);
                for (PigZombie i : warriors) {
                    EntityEquipment ee = i.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.IRON_HELMET)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Cyberman")) {
                            count++;
                        }
                    }
                }
            } else if (which.equals("s")) {
                what = "Silurians";
                Collection<Skeleton> silurians = w.getEntitiesByClass(Skeleton.class);
                for (Skeleton s : silurians) {
                    EntityEquipment ee = s.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.GOLD_HELMET)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Silurian")) {
                            count++;
                        }
                    }
                }
            } else if (which.equals("o")) {
                what = "Sontarans";
                Collection<PigZombie> sontarans = w.getEntitiesByClass(PigZombie.class);
                for (PigZombie o : sontarans) {
                    EntityEquipment ee = o.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.GOLD_HELMET)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Sontaran")) {
                            count++;
                        }
                    }
                }
            } else if (which.equals("z")) {
                what = "Zygons";
                Collection<Zombie> zygons = w.getEntitiesByClass(Zombie.class);
                for (Zombie z : zygons) {
                    EntityEquipment ee = z.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.GOLD_HELMET)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Zygon")) {
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