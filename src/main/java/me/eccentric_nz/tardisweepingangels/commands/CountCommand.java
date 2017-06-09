package me.eccentric_nz.tardisweepingangels.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class CountCommand implements CommandExecutor {

    private final TARDISWeepingAngels plugin;
    private final List<String> types = new ArrayList<>();

    public CountCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.types.add("a");
        this.types.add("c");
        this.types.add("d");
        this.types.add("e");
        this.types.add("i");
        this.types.add("m");
        this.types.add("o");
        this.types.add("s");
        this.types.add("v");
        this.types.add("z");
        this.types.add("g");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("twac")) {
            if (args.length < 2) {
                return false;
            }
            String which = args[0].toLowerCase();
            if (!types.contains(which)) {
                return false;
            }
            World w = plugin.getServer().getWorld(args[1]);
            if (w == null) {
                sender.sendMessage(plugin.pluginName + "Could not find a world with that name!");
                return true;
            }
            int count = 0;
            String what = "Angels";
            switch (which) {
                case "a":
                    Collection<Skeleton> angels = w.getEntitiesByClass(Skeleton.class);
                    for (Skeleton a : angels) {
                        EntityEquipment ee = a.getEquipment();
                        if (ee.getItemInMainHand().getType().equals(Material.BARRIER) || ee.getHelmet().getType().equals(Material.WATER_LILY)) {
                            count++;
                        }
                    }
                    break;
                case "c":
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
                    break;
                case "d":
                    what = "Daleks";
                    Collection<Skeleton> daleks = w.getEntitiesByClass(Skeleton.class);
                    for (Skeleton d : daleks) {
                        EntityEquipment ee = d.getEquipment();
                        if (ee.getHelmet().getType().equals(Material.VINE)) {
                            ItemStack is = ee.getHelmet();
                            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Dalek")) {
                                count++;
                            }
                        }
                    }
                    break;
                case "e":
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
                    break;
                case "i":
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
                    break;
                case "m":
                    what = "Silence";
                    Collection<Enderman> silence = w.getEntitiesByClass(Enderman.class);
                    for (Enderman m : silence) {
                        if (!m.getPassengers().isEmpty() && m.getPassengers().get(0) != null && m.getPassengers().get(0).getType().equals(EntityType.GUARDIAN)) {
                            count++;
                        }
                    }
                    break;
                case "o":
                    what = "Sontarans";
                    Collection<Zombie> sontarans = w.getEntitiesByClass(Zombie.class);
                    for (Zombie o : sontarans) {
                        EntityEquipment ee = o.getEquipment();
                        if (ee.getHelmet().getType().equals(Material.GOLD_HELMET)) {
                            ItemStack is = ee.getHelmet();
                            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Sontaran")) {
                                count++;
                            }
                        }
                    }
                    break;
                case "s":
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
                    break;
                case "v":
                    what = "Vashta Nerada";
                    Collection<Zombie> vashta = w.getEntitiesByClass(Zombie.class);
                    for (Zombie v : vashta) {
                        EntityEquipment ee = v.getEquipment();
                        if (ee.getHelmet().getType().equals(Material.GOLD_HELMET)) {
                            ItemStack is = ee.getHelmet();
                            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Vashta")) {
                                count++;
                            }
                        }
                    }
                    break;
                case "z":
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
                    break;
                case "g":
                    what = "Invisible Guardians without Endermen";
                    Collection<Guardian> guardians = w.getEntitiesByClass(Guardian.class);
                    for (Guardian g : guardians) {
                        if (g.hasPotionEffect(PotionEffectType.INVISIBILITY) && g.getVehicle() == null) {
                            count++;
                        }
                    }
                    break;
                default:
                    break;
            }
            sender.sendMessage(plugin.pluginName + "There are " + count + " " + what + " in " + w.getName());
            return true;
        }
        return false;
    }
}
