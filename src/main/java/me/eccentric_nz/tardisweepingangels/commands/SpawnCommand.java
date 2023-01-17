/*
 * Copyright (C) 2023 eccentric_nz
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

import java.util.Set;
import me.eccentric_nz.tardischunkgenerator.TARDISHelper;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.Equipper;
import me.eccentric_nz.tardisweepingangels.monsters.daleks.DalekEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.empty_child.EmptyChildEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.headless_monks.HeadlessFlameRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.headless_monks.HeadlessMonkEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.judoon.JudoonEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.k9.K9Equipment;
import me.eccentric_nz.tardisweepingangels.monsters.ood.OodEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.silent.SilentEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.toclafane.ToclafaneEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import static me.eccentric_nz.tardisweepingangels.utils.Monster.HEADLESS_MONK;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class SpawnCommand {

    private final TARDISWeepingAngels plugin;
    private final Set<Material> trans = null;

    public SpawnCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    public boolean spawn(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return false;
        }
        String upper = args[1].toUpperCase();
        // check monster type
        Monster monster;
        try {
            monster = Monster.valueOf(upper);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(plugin.pluginName + "Invalid monster type!");
            return true;
        }
        if (sender instanceof Player player) {
            // check player has permission for this monster
            if (!player.hasPermission("tardisweepingangels.spawn." + monster.getPermission())) {
                sender.sendMessage(plugin.pluginName + "You don't have permission to spawn a " + monster.toString() + "!");
                return true;
            }
            Location eyeLocation = player.getTargetBlock(trans, 50).getLocation();
            eyeLocation.add(0.5, 1.0, 0.5);
            eyeLocation.setYaw(player.getLocation().getYaw() - 180.0f);
            World world = eyeLocation.getWorld();
            LivingEntity a = (LivingEntity) world.spawnEntity(eyeLocation, monster.getEntityType());
            a.setSilent(true);
            a.setNoDamageTicks(75);
            switch (monster) {
                case WEEPING_ANGEL ->  new Equipper(Monster.WEEPING_ANGEL, a, false, false).setHelmetAndInvisibilty();
                case CYBERMAN ->  new Equipper(Monster.CYBERMAN, a, false, false).setHelmetAndInvisibilty();
                case DALEK -> {
                    DalekEquipment.set(a, false);
                    if (args.length > 2 && args[2].equalsIgnoreCase("flying") && plugin.getServer().getPluginManager().isPluginEnabled("TARDISChunkGenerator")) {
                        TARDISHelper tardisHelper = (TARDISHelper) plugin.getServer().getPluginManager().getPlugin("TARDISChunkGenerator");
                        // make the Dalek fly
                        EntityEquipment ee = a.getEquipment();
                        ee.setChestplate(new ItemStack(Material.ELYTRA, 1));
                        // teleport them straight up
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            a.teleport(a.getLocation().add(0.0d, 20.0d, 0.0d));
                            a.setGliding(true);
                            tardisHelper.setFallFlyingTag(a);
                        }, 2L);
                    }
                }
                case EMPTY_CHILD -> {
                    Ageable child = (Ageable) a;
                    child.setBaby();
                    new Equipper(monster, a, false, false).setHelmetAndInvisibilty();
                    EmptyChildEquipment.setSpeed(a);
                }
                case HATH -> new Equipper(monster, a, false, false).setHelmetAndInvisibilty();
                case HEADLESS_MONK -> {
                    new Equipper(monster, a, false, false).setHelmetAndInvisibilty();
                    HeadlessMonkEquipment.setTasks(a);
                    // start flame runnable
                    int flameID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new HeadlessFlameRunnable(a), 1, 20);
                    a.getPersistentDataContainer().set(TARDISWeepingAngels.FLAME_TASK, PersistentDataType.INTEGER, flameID);
                }
                case ICE_WARRIOR -> {
                    new Equipper(monster, a, false, false).setHelmetAndInvisibilty();
                    PigZombie pigman = (PigZombie) a;
                    pigman.setAngry(true);
                    pigman.setAnger(Integer.MAX_VALUE);
                }
                case JUDOON -> JudoonEquipment.set(null, a, false);
                case K9 -> K9Equipment.set(player, a, false);
                case OOD -> OodEquipment.set(null, a, false);
                case SILENT -> {
                    new Equipper(monster, a, false, false).setHelmetAndInvisibilty();
                    SilentEquipment.setGuardian(a);
                }
                case SILURIAN -> new Equipper(monster, a, false, true).setHelmetAndInvisibilty();
                case SONTARAN -> new Equipper(monster, a, false, false).setHelmetAndInvisibilty();
                case STRAX -> {
                    PigZombie strax = (PigZombie) a;
                    strax.setAngry(false);
                    new Equipper(monster, a, false, false).setHelmetAndInvisibilty();
                    a.setCustomName("Strax");
                }
                case THE_MIRE -> {
                    new Equipper(monster, a, false, true).setHelmetAndInvisibilty();
//                    player.playSound(a.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1.0f, 1.0f);
                }
                case TOCLAFANE -> ToclafaneEquipment.set(a, false);
                case VASHTA_NERADA -> new Equipper(monster, a, false, false).setHelmetAndInvisibilty();
                case ZYGON -> new Equipper(monster, a, false, false).setHelmetAndInvisibilty();
            }
            if ((monster.getEntityType() == EntityType.ZOMBIE || monster.getEntityType() == EntityType.ZOMBIFIED_PIGLIN) && monster != Monster.EMPTY_CHILD) {
                Ageable ageable = (Ageable) a;
                ageable.setAdult();
            }
            String sound = switch (monster) {
                case EMPTY_CHILD -> "empty";
                case HEADLESS_MONK -> "headliess_monk";
                case ICE_WARRIOR -> "warrior";
                case SILENT -> "silence";
                case THE_MIRE -> "item.trident.thunder";
                case WEEPING_ANGEL -> "blink";
                default ->  monster.getPermission();
            };
            player.playSound(a.getLocation(), sound, 1.0f, 1.0f);
            plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(a, monster.getEntityType(), monster, eyeLocation));
        } else {
            sender.sendMessage(plugin.pluginName + "Command can only be used by a player!");
        }
        return true;
    }
}
