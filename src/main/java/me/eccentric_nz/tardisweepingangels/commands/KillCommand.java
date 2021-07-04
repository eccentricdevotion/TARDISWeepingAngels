/*
 * Copyright (C) 2021 eccentric_nz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
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

    private final TardisWeepingAngelsPlugin plugin;

    public KillCommand(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean kill(CommandSender sender, String[] args) {
        if (args.length < 3) {
            return false;
        }
        String which = args[1].toUpperCase();
        World world = plugin.getServer().getWorld(args[2]);
        if (world == null) {
            sender.sendMessage(plugin.pluginName + "Could not find a world with that name!");
            return true;
        }
        int count = 0;
        String what = "Weeping Angels";
        Monster monster;
        try {
            monster = Monster.valueOf(which);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(plugin.pluginName + "Invalid monster type!");
            return true;
        }
        switch (monster) {
            case WEEPING_ANGEL:
                Collection<Skeleton> weepingAngels = world.getEntitiesByClass(Skeleton.class);
                for (Skeleton weepingAngel : weepingAngels) {
                    EntityEquipment entityEquipment = weepingAngel.getEquipment();
                    if (entityEquipment.getHelmet().getType().equals(Material.BRICK) || entityEquipment.getHelmet().getType().equals(Material.STONE_BUTTON)) {
                        weepingAngel.remove();
                        count++;
                    }
                }
                break;
            case CYBERMAN:
                what = "Cybermen";
                Collection<Zombie> cybermen = world.getEntitiesByClass(Zombie.class);
                for (Zombie cyberman : cybermen) {
                    EntityEquipment entityEquipment = cyberman.getEquipment();
                    if (entityEquipment.getHelmet().getType().equals(Material.IRON_INGOT)) {
                        ItemStack itemStack = entityEquipment.getHelmet();
                        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().startsWith("Cyberman")) {
                            cyberman.remove();
                            count++;
                        }
                    }
                }
                break;
            case DALEK:
                what = "Daleks";
                Collection<Skeleton> daleks = world.getEntitiesByClass(Skeleton.class);
                for (Skeleton dalek : daleks) {
                    EntityEquipment entityEquipment = dalek.getEquipment();
                    if (entityEquipment.getHelmet().getType().equals(Material.SLIME_BALL)) {
                        ItemStack itemStack = entityEquipment.getHelmet();
                        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().startsWith("Dalek")) {
                            dalek.remove();
                            count++;
                        }
                    }
                }
                break;
            case EMPTY_CHILD:
                what = "Empty Children";
                Collection<Zombie> emptyChildren = world.getEntitiesByClass(Zombie.class);
                for (Zombie emptyChild : emptyChildren) {
                    EntityEquipment entityEquipment = emptyChild.getEquipment();
                    if (entityEquipment.getHelmet().getType().equals(Material.SUGAR)) {
                        ItemStack itemStack = entityEquipment.getHelmet();
                        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().startsWith("Empty Child")) {
                            emptyChild.remove();
                            count++;
                        }
                    }
                }
                break;
            case HATH:
                what = "Hath";
                Collection<PigZombie> haths = world.getEntitiesByClass(PigZombie.class);
                for (Zombie hath : haths) {
                    EntityEquipment entityEquipment = hath.getEquipment();
                    if (entityEquipment.getHelmet().getType().equals(Material.PUFFERFISH)) {
                        ItemStack itemStack = entityEquipment.getHelmet();
                        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().startsWith("Hath")) {
                            hath.remove();
                            count++;
                        }
                    }
                }
                break;
            case ICE_WARRIOR:
            case STRAX:
                Collection<PigZombie> warriors = world.getEntitiesByClass(PigZombie.class);
                for (PigZombie warrior : warriors) {
                    EntityEquipment entityEquipment = warrior.getEquipment();
                    ItemStack itemStack = entityEquipment.getHelmet();
                    if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()) {
                        if (entityEquipment.getHelmet().getType().equals(Material.SNOWBALL)) {
                            if (itemStack.getItemMeta().getDisplayName().startsWith("Ice Warrior")) {
                                what = "Ice Warriors";
                                warrior.remove();
                                count++;
                            }
                        } else if (entityEquipment.getHelmet().getType().equals(Material.BAKED_POTATO)) {
                            if (itemStack.getItemMeta().getDisplayName().startsWith("Strax")) {
                                what = "Strax";
                                warrior.remove();
                                count++;
                            }
                        }
                    }
                }
                break;
            case SILURIAN:
                what = "Silurians";
                Collection<Skeleton> silurians = world.getEntitiesByClass(Skeleton.class);
                for (Skeleton silurian : silurians) {
                    EntityEquipment entityEquipment = silurian.getEquipment();
                    if (entityEquipment.getHelmet().getType().equals(Material.FEATHER)) {
                        ItemStack itemStack = entityEquipment.getHelmet();
                        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().startsWith("Silurian")) {
                            silurian.remove();
                            count++;
                        }
                    }
                }
                break;
            case SILENT:
                what = "Silents";
                Collection<Enderman> silents = world.getEntitiesByClass(Enderman.class);
                for (Enderman silent : silents) {
                    if (!silent.getPassengers().isEmpty() && silent.getPassengers().get(0) != null && silent.getPassengers().get(0).getType().equals(EntityType.GUARDIAN)) {
                        silent.getPassengers().get(0).remove();
                        silent.remove();
                        count++;
                    }
                }
                break;
            case SONTARAN:
                what = "Sontarans";
                Collection<Zombie> sontarans = world.getEntitiesByClass(Zombie.class);
                for (Zombie sontaran : sontarans) {
                    EntityEquipment entityEquipment = sontaran.getEquipment();
                    if (entityEquipment.getHelmet().getType().equals(Material.POTATO)) {
                        ItemStack itemStack = entityEquipment.getHelmet();
                        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().startsWith("Sontaran")) {
                            sontaran.remove();
                            count++;
                        }
                    }
                }
                break;
            case OOD:
            case JUDOON:
            case K9:
            case TOCLAFANE:
                Collection<ArmorStand> oods = world.getEntitiesByClass(ArmorStand.class);
                for (ArmorStand ood : oods) {
                    if (ood.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.ood, PersistentDataType.INTEGER)) {
                        what = "Ood";
                        ood.remove();
                        count++;
                    } else if (ood.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.judoon, PersistentDataType.INTEGER)) {
                        what = "Judoon";
                        ood.remove();
                        count++;
                    } else if (ood.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.k9, PersistentDataType.INTEGER)) {
                        what = "K9s";
                        ood.remove();
                        count++;
                    } else if (ood.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.toclafane, PersistentDataType.INTEGER)) {
                        what = "Toclafane";
                        // also remove the bee
                        if (ood.getVehicle() != null) {
                            Entity bee = ood.getVehicle();
                            if (bee instanceof Bee) {
                                ood.remove();
                                bee.remove();
                            }
                        } else {
                            ood.remove();
                        }
                        count++;
                    }
                }
                break;
            case VASHTA_NERADA:
                what = "Vashta Nerada";
                Collection<Zombie> vashtaNeradas = world.getEntitiesByClass(Zombie.class);
                for (Zombie vashtaNerada : vashtaNeradas) {
                    EntityEquipment entityEquipment = vashtaNerada.getEquipment();
                    if (entityEquipment.getHelmet().getType().equals(Material.BOOK)) {
                        ItemStack itemStack = entityEquipment.getHelmet();
                        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().startsWith("Vashta")) {
                            vashtaNerada.remove();
                            count++;
                        }
                    }
                }
                break;
            case ZYGON:
                what = "Zygons";
                Collection<Zombie> zygons = world.getEntitiesByClass(Zombie.class);
                for (Zombie zygon : zygons) {
                    EntityEquipment entityEquipment = zygon.getEquipment();
                    if (entityEquipment.getHelmet().getType().equals(Material.PAINTING)) {
                        ItemStack itemStack = entityEquipment.getHelmet();
                        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().startsWith("Zygon")) {
                            zygon.remove();
                            count++;
                        }
                    }
                }
                break;
            default:
                break;
        }
        sender.sendMessage(plugin.pluginName + "Removed " + count + " " + what + " in " + world.getName());
        return true;
    }
}
