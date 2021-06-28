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
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class CountCommand {

    private final TardisWeepingAngelsPlugin plugin;

    public CountCommand(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean count(CommandSender sender, String[] args) {
        if (args.length < 3) {
            return false;
        }
        String which = args[1].toLowerCase();
        String what = "Weeping Angels";
        int count = 0;
        World world = plugin.getServer().getWorld(args[2]);
        if (world == null) {
            sender.sendMessage(plugin.pluginName + "Could not find a world with that name!");
            return true;
        }
        if (which.equals("g")) {
            what = "Invisible Guardians without Endermen";
            Collection<Guardian> guardians = world.getEntitiesByClass(Guardian.class);
            for (Guardian guardian : guardians) {
                if (guardian.hasPotionEffect(PotionEffectType.INVISIBILITY) && guardian.getVehicle() == null) {
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
                    Collection<Skeleton> angels = world.getEntitiesByClass(Skeleton.class);
                    for (Skeleton angel : angels) {
                        if (angel.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.weepingAngel, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case CYBERMAN:
                    what = "Cybermen";
                    Collection<Zombie> cybermen = world.getEntitiesByClass(Zombie.class);
                    for (Zombie cyberman : cybermen) {
                        if (cyberman.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.cyberman, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case DALEK:
                    what = "Daleks";
                    Collection<Skeleton> daleks = world.getEntitiesByClass(Skeleton.class);
                    for (Skeleton dalek : daleks) {
                        if (dalek.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.dalek, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case EMPTY_CHILD:
                    what = "Empty Children";
                    Collection<Zombie> emptyChildren = world.getEntitiesByClass(Zombie.class);
                    for (Zombie emptyChild : emptyChildren) {
                        if (emptyChild.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.emptyChild, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case HATH:
                    what = "Hath";
                    Collection<PigZombie> haths = world.getEntitiesByClass(PigZombie.class);
                    for (PigZombie hath : haths) {
                        if (hath.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.hath, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case ICE_WARRIOR:
                    what = "Ice Warriors";
                    Collection<PigZombie> iceWarriors = world.getEntitiesByClass(PigZombie.class);
                    for (PigZombie iceWarrior : iceWarriors) {
                        if (iceWarrior.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.iceWarrior, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case JUDOON:
                    what = "Judoon";
                    Collection<ArmorStand> judoons = world.getEntitiesByClass(ArmorStand.class);
                    for (ArmorStand judoon : judoons) {
                        if (judoon.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.judoon, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case K9:
                    what = "K9";
                    Collection<ArmorStand> k9s = world.getEntitiesByClass(ArmorStand.class);
                    for (ArmorStand k9 : k9s) {
                        if (k9.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.k9, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case SILENT:
                    what = "Silents";
                    Collection<Enderman> silents = world.getEntitiesByClass(Enderman.class);
                    for (Enderman silent : silents) {
                        if (silent.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.silent, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case SONTARAN:
                    what = "Sontarans";
                    Collection<Zombie> sontarans = world.getEntitiesByClass(Zombie.class);
                    for (Zombie sontaran : sontarans) {
                        if (sontaran.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.sontaran, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case OOD:
                    what = "Ood";
                    Collection<ArmorStand> oods = world.getEntitiesByClass(ArmorStand.class);
                    for (ArmorStand ood : oods) {
                        if (ood.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.ood, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case SILURIAN:
                    what = "Silurians";
                    Collection<Skeleton> silurians = world.getEntitiesByClass(Skeleton.class);
                    for (Skeleton silurian : silurians) {
                        if (silurian.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.silurian, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case VASHTA_NERADA:
                    what = "Vashta Nerada";
                    Collection<Zombie> vashtaNeradas = world.getEntitiesByClass(Zombie.class);
                    for (Zombie vashtaNerada : vashtaNeradas) {
                        if (vashtaNerada.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.vashtaNerada, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                case ZYGON:
                    what = "Zygons";
                    Collection<Zombie> zygons = world.getEntitiesByClass(Zombie.class);
                    for (Zombie zygon : zygons) {
                        if (zygon.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.zygon, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        sender.sendMessage(plugin.pluginName + "There are " + count + " " + what + " in " + world.getName());
        return true;
    }
}
