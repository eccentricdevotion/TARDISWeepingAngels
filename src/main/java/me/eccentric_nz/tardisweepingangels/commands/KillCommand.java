package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;

public class KillCommand {

    private final TARDISWeepingAngels plugin;

    public KillCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    public boolean kill(CommandSender sender, String[] args) {
        if (args.length < 3) {
            return false;
        }
        String which = args[1].toUpperCase();
        World w = plugin.getServer().getWorld(args[2]);
        if (w == null) {
            sender.sendMessage(plugin.pluginName + "Could not find a world with that name!");
            return true;
        }
        int count = 0;
        String what = "Angels";
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
                    EntityEquipment ee = a.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.BRICK) || ee.getHelmet().getType().equals(Material.STONE_BUTTON)) {
                        a.remove();
                        count++;
                    }
                }
                break;
            case CYBERMAN:
                what = "Cybermen";
                Collection<Zombie> cybermen = w.getEntitiesByClass(Zombie.class);
                for (Zombie c : cybermen) {
                    EntityEquipment ee = c.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.IRON_INGOT)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Cyberman")) {
                            c.remove();
                            count++;
                        }
                    }
                }
                break;
            case DALEK:
                what = "Daleks";
                Collection<Skeleton> daleks = w.getEntitiesByClass(Skeleton.class);
                for (Skeleton d : daleks) {
                    EntityEquipment ee = d.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.SLIME_BALL)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Dalek")) {
                            d.remove();
                            count++;
                        }
                    }
                }
                break;
            case EMPTY_CHILD:
                what = "Empty Children";
                Collection<Zombie> kids = w.getEntitiesByClass(Zombie.class);
                for (Zombie e : kids) {
                    EntityEquipment ee = e.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.SUGAR)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Empty Child")) {
                            e.remove();
                            count++;
                        }
                    }
                }
                break;
            case HATH:
                what = "Hath";
                Collection<PigZombie> fish = w.getEntitiesByClass(PigZombie.class);
                for (Zombie h : fish) {
                    EntityEquipment ee = h.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.PUFFERFISH)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Hath")) {
                            h.remove();
                            count++;
                        }
                    }
                }
                break;
            case ICE_WARRIOR:
            case STRAX:
                Collection<PigZombie> warriors = w.getEntitiesByClass(PigZombie.class);
                for (PigZombie i : warriors) {
                    EntityEquipment ee = i.getEquipment();
                    ItemStack is = ee.getHelmet();
                    if (is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
                        if (ee.getHelmet().getType().equals(Material.SNOWBALL)) {
                            if (is.getItemMeta().getDisplayName().startsWith("Ice Warrior")) {
                                what = "Ice Warriors";
                                i.remove();
                                count++;
                            }
                        } else if (ee.getHelmet().getType().equals(Material.BAKED_POTATO)) {
                            if (is.getItemMeta().getDisplayName().startsWith("Strax")) {
                                what = "Strax";
                                i.remove();
                                count++;
                            }
                        }
                    }
                }
                break;
            case SILURIAN:
                what = "Silurians";
                Collection<Skeleton> silurians = w.getEntitiesByClass(Skeleton.class);
                for (Skeleton s : silurians) {
                    EntityEquipment ee = s.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.FEATHER)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Silurian")) {
                            s.remove();
                            count++;
                        }
                    }
                }
                break;
            case SILENT:
                what = "Silence";
                Collection<Enderman> silence = w.getEntitiesByClass(Enderman.class);
                for (Enderman m : silence) {
                    if (!m.getPassengers().isEmpty() && m.getPassengers().get(0) != null && m.getPassengers().get(0).getType().equals(EntityType.GUARDIAN)) {
                        m.getPassengers().get(0).remove();
                        m.remove();
                        count++;
                    }
                }
                break;
            case SONTARAN:
                what = "Sontarans";
                Collection<Zombie> sontarans = w.getEntitiesByClass(Zombie.class);
                for (Zombie o : sontarans) {
                    EntityEquipment ee = o.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.POTATO)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Sontaran")) {
                            o.remove();
                            count++;
                        }
                    }
                }
                break;
            case OOD:
            case JUDOON:
            case K9:
            case TOCLAFANE:
                Collection<ArmorStand> ood = w.getEntitiesByClass(ArmorStand.class);
                for (ArmorStand o : ood) {
                    if (o.getPersistentDataContainer().has(TARDISWeepingAngels.OOD, PersistentDataType.INTEGER)) {
                        what = "Ood";
                        o.remove();
                        count++;
                    } else if (o.getPersistentDataContainer().has(TARDISWeepingAngels.JUDOON, PersistentDataType.INTEGER)) {
                        what = "Judoon";
                        o.remove();
                        count++;
                    } else if (o.getPersistentDataContainer().has(TARDISWeepingAngels.K9, PersistentDataType.INTEGER)) {
                        what = "K9s";
                        o.remove();
                        count++;
                    } else if (o.getPersistentDataContainer().has(TARDISWeepingAngels.TOCLAFANE, PersistentDataType.INTEGER)) {
                        what = "Toclafane";
                        // also remove the bee
                        if (o.getVehicle() != null) {
                            Entity bee = o.getVehicle();
                            if (bee instanceof Bee) {
                                o.remove();
                                bee.remove();
                            }
                        } else {
                            o.remove();
                        }
                        count++;
                    }
                }
                break;
            case VASHTA_NERADA:
                what = "Vashta Nerada";
                Collection<Zombie> vashta = w.getEntitiesByClass(Zombie.class);
                for (Zombie v : vashta) {
                    EntityEquipment ee = v.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.BOOK)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Vashta")) {
                            v.remove();
                            count++;
                        }
                    }
                }
                break;
            case ZYGON:
                what = "Zygons";
                Collection<Zombie> zygons = w.getEntitiesByClass(Zombie.class);
                for (Zombie z : zygons) {
                    EntityEquipment ee = z.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.PAINTING)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Zygon")) {
                            z.remove();
                            count++;
                        }
                    }
                }
                break;
            default:
                break;
        }
        sender.sendMessage(plugin.pluginName + "Removed " + count + " " + what + " in " + w.getName());
        return true;
    }
}
