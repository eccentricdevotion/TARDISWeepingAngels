package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class CountCommand {

    private final TARDISWeepingAngels plugin;

    public CountCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    public boolean count(CommandSender sender, String[] args) {
        if (args.length < 3) {
            return false;
        }
        String which = args[1].toUpperCase();
        String what = "Angels";
        int count = 0;
        World w = plugin.getServer().getWorld(args[2]);
        if (w == null) {
            sender.sendMessage(plugin.pluginName + "Could not find a world with that name!");
            return true;
        }
        if (which.equals("g")) {
            what = "Invisible Guardians without Endermen";
            Collection<Guardian> guardians = w.getEntitiesByClass(Guardian.class);
            for (Guardian g : guardians) {
                if (g.hasPotionEffect(PotionEffectType.INVISIBILITY) && g.getVehicle() == null) {
                    count++;
                }
            }
        } else {
            Monster monster;
            try {
                monster = Monster.valueOf(which);
            } catch (IllegalArgumentException e) {
                sender.sendMessage(plugin.pluginName + "Invalid monster type!");
                return true;
            }
            switch (monster) {
                case WEEPING_ANGEL:
                    Collection<Skeleton> angels = w.getEntitiesByClass(Skeleton.class);
                    for (Skeleton a : angels) {
                        if (a.getPersistentDataContainer().has(TARDISWeepingAngels.ANGEL, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case CYBERMAN:
                    what = "Cybermen";
                    Collection<Zombie> cybermen = w.getEntitiesByClass(Zombie.class);
                    for (Zombie c : cybermen) {
                        if (c.getPersistentDataContainer().has(TARDISWeepingAngels.CYBERMAN, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case DALEK:
                    what = "Daleks";
                    Collection<Skeleton> daleks = w.getEntitiesByClass(Skeleton.class);
                    for (Skeleton d : daleks) {
                        if (d.getPersistentDataContainer().has(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case EMPTY_CHILD:
                    what = "Empty Children";
                    Collection<Zombie> kids = w.getEntitiesByClass(Zombie.class);
                    for (Zombie e : kids) {
                        if (e.getPersistentDataContainer().has(TARDISWeepingAngels.EMPTY, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case HATH:
                    what = "Hath";
                    Collection<PigZombie> fish = w.getEntitiesByClass(PigZombie.class);
                    for (PigZombie h : fish) {
                        if (h.getPersistentDataContainer().has(TARDISWeepingAngels.HATH, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case ICE_WARRIOR:
                    what = "Ice Warriors";
                    Collection<PigZombie> warriors = w.getEntitiesByClass(PigZombie.class);
                    for (PigZombie i : warriors) {
                        if (i.getPersistentDataContainer().has(TARDISWeepingAngels.WARRIOR, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case JUDOON:
                    what = "Judoon";
                    Collection<ArmorStand> galactic_police = w.getEntitiesByClass(ArmorStand.class);
                    for (ArmorStand g : galactic_police) {
                        if (g.getPersistentDataContainer().has(TARDISWeepingAngels.JUDOON, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case K9:
                    what = "K9";
                    Collection<ArmorStand> companions = w.getEntitiesByClass(ArmorStand.class);
                    for (ArmorStand k : companions) {
                        if (k.getPersistentDataContainer().has(TARDISWeepingAngels.K9, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case SILENT:
                    what = "Silence";
                    Collection<Enderman> silence = w.getEntitiesByClass(Enderman.class);
                    for (Enderman m : silence) {
                        if (m.getPersistentDataContainer().has(TARDISWeepingAngels.SILENT, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case SONTARAN:
                    what = "Sontarans";
                    Collection<Zombie> sontarans = w.getEntitiesByClass(Zombie.class);
                    for (Zombie o : sontarans) {
                        if (o.getPersistentDataContainer().has(TARDISWeepingAngels.SONTARAN, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case OOD:
                    what = "Ood";
                    Collection<ArmorStand> ood = w.getEntitiesByClass(ArmorStand.class);
                    for (ArmorStand o : ood) {
                        if (o.getPersistentDataContainer().has(TARDISWeepingAngels.OOD, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case SILURIAN:
                    what = "Silurians";
                    Collection<Skeleton> silurians = w.getEntitiesByClass(Skeleton.class);
                    for (Skeleton s : silurians) {
                        if (s.getPersistentDataContainer().has(TARDISWeepingAngels.SILURIAN, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case VASHTA_NERADA:
                    what = "Vashta Nerada";
                    Collection<Zombie> vashta = w.getEntitiesByClass(Zombie.class);
                    for (Zombie v : vashta) {
                        if (v.getPersistentDataContainer().has(TARDISWeepingAngels.VASHTA, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case ZYGON:
                    what = "Zygons";
                    Collection<Zombie> zygons = w.getEntitiesByClass(Zombie.class);
                    for (Zombie z : zygons) {
                        if (z.getPersistentDataContainer().has(TARDISWeepingAngels.ZYGON, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        sender.sendMessage(plugin.pluginName + "There are " + count + " " + what + " in " + w.getName());
        return true;
    }
}
