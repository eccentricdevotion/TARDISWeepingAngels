package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CountCommand implements CommandExecutor {

    private final TARDISWeepingAngels plugin;
    private final List<String> types = new ArrayList<>();

    public CountCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        types.add("a");
        types.add("c");
        types.add("d");
        types.add("e");
        types.add("i");
        types.add("m");
        types.add("r");
        types.add("o");
        types.add("s");
        types.add("v");
        types.add("z");
        types.add("g");
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
                        if (a.getPersistentDataContainer().has(TARDISWeepingAngels.ANGEL, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case "c":
                    what = "Cybermen";
                    Collection<Zombie> cybermen = w.getEntitiesByClass(Zombie.class);
                    for (Zombie c : cybermen) {
                        if (c.getPersistentDataContainer().has(TARDISWeepingAngels.CYBERMAN, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case "d":
                    what = "Daleks";
                    Collection<Skeleton> daleks = w.getEntitiesByClass(Skeleton.class);
                    for (Skeleton d : daleks) {
                        if (d.getPersistentDataContainer().has(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case "e":
                    what = "Empty Children";
                    Collection<Zombie> kids = w.getEntitiesByClass(Zombie.class);
                    for (Zombie e : kids) {
                        if (e.getPersistentDataContainer().has(TARDISWeepingAngels.EMPTY, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case "i":
                    what = "Ice Warriors";
                    Collection<PigZombie> warriors = w.getEntitiesByClass(PigZombie.class);
                    for (PigZombie i : warriors) {
                        if (i.getPersistentDataContainer().has(TARDISWeepingAngels.WARRIOR, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case "m":
                    what = "Silence";
                    Collection<Enderman> silence = w.getEntitiesByClass(Enderman.class);
                    for (Enderman m : silence) {
                        if (m.getPersistentDataContainer().has(TARDISWeepingAngels.SILENT, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case "o":
                    what = "Sontarans";
                    Collection<Zombie> sontarans = w.getEntitiesByClass(Zombie.class);
                    for (Zombie o : sontarans) {
                        if (o.getPersistentDataContainer().has(TARDISWeepingAngels.SONTARAN, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case "r":
                    what = "Ood";
                    Collection<ArmorStand> ood = w.getEntitiesByClass(ArmorStand.class);
                    for (ArmorStand o : ood) {
                        if (o.getPersistentDataContainer().has(TARDISWeepingAngels.OOD, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case "s":
                    what = "Silurians";
                    Collection<Skeleton> silurians = w.getEntitiesByClass(Skeleton.class);
                    for (Skeleton s : silurians) {
                        if (s.getPersistentDataContainer().has(TARDISWeepingAngels.SILURIAN, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case "v":
                    what = "Vashta Nerada";
                    Collection<Zombie> vashta = w.getEntitiesByClass(Zombie.class);
                    for (Zombie v : vashta) {
                        if (v.getPersistentDataContainer().has(TARDISWeepingAngels.VASHTA, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case "z":
                    what = "Zygons";
                    Collection<Zombie> zygons = w.getEntitiesByClass(Zombie.class);
                    for (Zombie z : zygons) {
                        if (z.getPersistentDataContainer().has(TARDISWeepingAngels.ZYGON, PersistentDataType.INTEGER)) {
                            count++;
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
