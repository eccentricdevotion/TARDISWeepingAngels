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

import me.eccentric_nz.tardischunkgenerator.TardisHelperPlugin;
import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.monsters.cyberman.CybermanEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.dalek.DalekEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.empty_child.EmptyChildEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.hath.HathEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.ice_warrior.IceWarriorEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.judoon.JudoonEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.k9.K9Equipment;
import me.eccentric_nz.tardisweepingangels.monsters.ood.OodEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.silent.SilentEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.silurian.SilurianEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.sontaran.SontaranEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.sontaran.StraxEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.toclafane.ToclafaneEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.vashta_nerada.VashtaNeradaEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.weeping_angel.AngelEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.zygon.ZygonEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class SpawnCommand {

    private final TardisWeepingAngelsPlugin plugin;
    private final Set<Material> trans = null;

    public SpawnCommand(TardisWeepingAngelsPlugin plugin) {
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
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        if (player == null) {
            sender.sendMessage(plugin.pluginName + "Command can only be used by a player!");
            return true;
        }
        // check player has permission for this monster
        if (!player.hasPermission("tardisweepingangels.spawn." + monster.getPermission())) {
            sender.sendMessage(plugin.pluginName + "You don't have permission to spawn a " + monster + "!");
            return true;
        }
        Location eyeLocation = player.getTargetBlock(trans, 50).getLocation();
        eyeLocation.add(0.5, 1.0, 0.5);
        eyeLocation.setYaw(player.getLocation().getYaw() - 180.0f);
        World world = eyeLocation.getWorld();
        switch (monster) {
            case WEEPING_ANGEL -> {
                assert world != null;
                LivingEntity weepingAngel = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.SKELETON);
                weepingAngel.setSilent(true);
                weepingAngel.setNoDamageTicks(75);
                AngelEquipment.set(weepingAngel, false);
                player.playSound(weepingAngel.getLocation(), "blink", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(weepingAngel, EntityType.SKELETON, Monster.WEEPING_ANGEL, eyeLocation));
            }
            case CYBERMAN -> {
                assert world != null;
                LivingEntity cyberman = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                cyberman.setSilent(true);
                cyberman.setNoDamageTicks(75);
                Ageable ageable = (Ageable) cyberman;
                ageable.setAdult();
                player.playSound(cyberman.getLocation(), "cyberman", 1.0f, 1.0f);
                CybermanEquipment.set(cyberman, false);
                plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(cyberman, EntityType.ZOMBIE, Monster.CYBERMAN, eyeLocation));
            }
            case DALEK -> {
                assert world != null;
                LivingEntity dalek = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.SKELETON);
                dalek.setSilent(true);
                dalek.setNoDamageTicks(75);
                DalekEquipment.set(dalek, false);
                player.playSound(dalek.getLocation(), "dalek", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(dalek, EntityType.SKELETON, Monster.DALEK, eyeLocation));
                if (args.length > 1 && args[1].equalsIgnoreCase("flying") && plugin.getServer().getPluginManager().isPluginEnabled("TARDISChunkGenerator")) {
                    TardisHelperPlugin tardisHelper = (TardisHelperPlugin) plugin.getServer().getPluginManager().getPlugin("TARDISChunkGenerator");
                    // make the Dalek fly
                    EntityEquipment entityEquipment = dalek.getEquipment();
                    assert entityEquipment != null;
                    entityEquipment.setChestplate(new ItemStack(Material.ELYTRA, 1));
                    // teleport them straight up
                    dalek.teleport(dalek.getLocation().add(0.0d, 20.0d, 0.0d));
                    dalek.setGliding(true);
                    assert tardisHelper != null;
                    tardisHelper.setFallFlyingTag(dalek);
                    entityEquipment.setChestplate(new ItemStack(Material.AIR));
                }
            }
            case EMPTY_CHILD -> {
                assert world != null;
                LivingEntity emptyChild = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                emptyChild.setSilent(true);
                emptyChild.setNoDamageTicks(75);
                Ageable ageable = (Ageable) emptyChild;
                ageable.setBaby();
                EmptyChildEquipment.set(emptyChild, false);
                player.playSound(emptyChild.getLocation(), "empty_child", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(emptyChild, EntityType.ZOMBIE, Monster.EMPTY_CHILD, eyeLocation));
            }
            case HATH -> {
                assert world != null;
                LivingEntity hath = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIFIED_PIGLIN);
                hath.setSilent(true);
                hath.setNoDamageTicks(75);
                HathEquipment.set(hath, false);
                player.playSound(hath.getLocation(), "hath", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(hath, EntityType.ZOMBIFIED_PIGLIN, Monster.HATH, eyeLocation));
            }
            case ICE_WARRIOR -> {
                assert world != null;
                LivingEntity iceWarrior = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIFIED_PIGLIN);
                iceWarrior.setSilent(true);
                IceWarriorEquipment.set(iceWarrior, false);
                player.playSound(iceWarrior.getLocation(), "warrior", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(iceWarrior, EntityType.ZOMBIFIED_PIGLIN, Monster.ICE_WARRIOR, eyeLocation));
                PigZombie pigZombie = (PigZombie) iceWarrior;
                pigZombie.setAngry(true);
                pigZombie.setAnger(Integer.MAX_VALUE);
                Ageable ageable = (Ageable) iceWarrior;
                ageable.setAdult();
            }
            case JUDOON -> {
                assert world != null;
                Entity judoon = world.spawnEntity(eyeLocation, EntityType.ARMOR_STAND);
                JudoonEquipment.set(null, judoon, false);
                player.playSound(judoon.getLocation(), "judoon", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(judoon, EntityType.ARMOR_STAND, Monster.JUDOON, eyeLocation));
            }
            case K9 -> {
                assert world != null;
                Entity k9 = world.spawnEntity(eyeLocation, EntityType.ARMOR_STAND);
                K9Equipment.set(player, k9, false);
                player.playSound(k9.getLocation(), "k9", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(k9, EntityType.ARMOR_STAND, Monster.K9, eyeLocation));
            }
            case OOD -> {
                assert world != null;
                Entity ood = world.spawnEntity(eyeLocation, EntityType.ARMOR_STAND);
                OodEquipment.set(null, ood, false);
                player.playSound(ood.getLocation(), "ood", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(ood, EntityType.ARMOR_STAND, Monster.OOD, eyeLocation));
            }
            case SILENT -> {
                assert world != null;
                LivingEntity silent = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ENDERMAN);
                silent.setSilent(true);
                SilentEquipment.set(silent, false);
                player.playSound(silent.getLocation(), "silence", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(silent, EntityType.ENDERMAN, Monster.SILENT, eyeLocation));
            }
            case SILURIAN -> {
                assert world != null;
                LivingEntity silurian = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.SKELETON);
                silurian.setSilent(true);
                silurian.setNoDamageTicks(75);
                SilurianEquipment.set(silurian, false);
                player.playSound(silurian.getLocation(), "silurian", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(silurian, EntityType.SKELETON, Monster.SILURIAN, eyeLocation));
            }
            case SONTARAN -> {
                assert world != null;
                LivingEntity sontaran = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                sontaran.setSilent(true);
                sontaran.setNoDamageTicks(75);
                Ageable ageable = (Ageable) sontaran;
                ageable.setAdult();
                SontaranEquipment.set(sontaran, false);
                player.playSound(sontaran.getLocation(), "sontaran", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(sontaran, EntityType.ZOMBIE, Monster.SONTARAN, eyeLocation));
            }
            case STRAX -> {
                assert world != null;
                LivingEntity strax = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIFIED_PIGLIN);
                strax.setSilent(true);
                strax.setNoDamageTicks(75);
                PigZombie pigZombie = (PigZombie) strax;
                pigZombie.setAngry(false);
                StraxEquipment.set(strax, false);
                Ageable ageable = (Ageable) strax;
                ageable.setAdult();
                player.playSound(strax.getLocation(), "strax", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(strax, EntityType.ZOMBIFIED_PIGLIN, Monster.STRAX, eyeLocation));
            }
            case TOCLAFANE -> {
                assert world != null;
                Entity toclafane = world.spawnEntity(eyeLocation, EntityType.ARMOR_STAND);
                ToclafaneEquipment.set(toclafane, false);
                player.playSound(toclafane.getLocation(), "toclafane", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(toclafane, EntityType.ARMOR_STAND, Monster.TOCLAFANE, eyeLocation));
            }
            case VASHTA_NERADA -> {
                assert world != null;
                LivingEntity vashtaNerada = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                vashtaNerada.setSilent(true);
                vashtaNerada.setNoDamageTicks(75);
                Ageable ageable = (Ageable) vashtaNerada;
                ageable.setAdult();
                VashtaNeradaEquipment.set(vashtaNerada, false);
                player.playSound(vashtaNerada.getLocation(), "vashta", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(vashtaNerada, EntityType.ZOMBIE, Monster.VASHTA_NERADA, eyeLocation));
            }
            case ZYGON -> {
                assert world != null;
                LivingEntity zygon = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                zygon.setSilent(true);
                zygon.setNoDamageTicks(75);
                Ageable ageable = (Ageable) zygon;
                ageable.setAdult();
                player.playSound(zygon.getLocation(), "zygon", 1.0f, 1.0f);
                ZygonEquipment.set(zygon, false);
                plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(zygon, EntityType.ZOMBIE, Monster.ZYGON, eyeLocation));
            }
        }
        return true;
    }
}
