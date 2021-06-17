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
package me.eccentric_nz.tardisweepingangels.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.BukkitWorldConfiguration;
import com.sk89q.worldguard.config.ConfigurationManager;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class WorldGuardChecker {

    private static final Set<EntityType> MONSTER_TYPES = new HashSet<>() {
        {
            add(EntityType.ARMOR_STAND); // OOD, JUDOON, K9
            add(EntityType.BEE); // TOCLAFANE
            add(EntityType.ENDERMAN); // SILENT
            add(EntityType.GUARDIAN); // SILENT
            add(EntityType.SKELETON); // WEEPING ANGEL, SILURIAN, DALEK
            add(EntityType.ZOMBIE); // CYBERMAN, EMPTY CHILD, SONTARAN, VASHTA, ZYGON
            add(EntityType.ZOMBIFIED_PIGLIN); // HATH, ICE_WARRIOR, STRAX,
        }
    };

    public static boolean canSpawn(Location l) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
        if (plugin != null) {
            WorldGuardPlatform worldGuardPlatform = WorldGuard.getInstance().getPlatform();
            BlockVector3 vector = BukkitAdapter.asBlockVector(l);
            RegionManager regionManager = worldGuardPlatform.getRegionContainer().get(BukkitAdapter.adapt(Objects.requireNonNull(l.getWorld())));
            assert regionManager != null;
            ApplicableRegionSet regionSet = regionManager.getApplicableRegions(vector);
            if (regionSet.testState(null, Flags.MOB_SPAWNING)) {
                return regionSet.queryValue(null, Flags.DENY_SPAWN) == null;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean canExplode(Location l) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
        if (plugin != null) {
            WorldGuardPlatform worldGuardPlatform = WorldGuard.getInstance().getPlatform();
            ConfigurationManager configurationManager = worldGuardPlatform.getGlobalStateManager();
            World bukkitWorld = BukkitAdapter.adapt(Objects.requireNonNull(l.getWorld()));
            BukkitWorldConfiguration worldConfig = (BukkitWorldConfiguration) configurationManager.get(bukkitWorld);
            if (worldConfig.blockCreeperBlockDamage || worldConfig.blockTNTBlockDamage) {
                return false;
            }
            BlockVector3 vector = BukkitAdapter.asBlockVector(l);
            RegionManager regionManager = worldGuardPlatform.getRegionContainer().get(bukkitWorld);
            assert regionManager != null;
            ApplicableRegionSet regionSet = regionManager.getApplicableRegions(vector);
            return regionSet.testState(null, Flags.OTHER_EXPLOSION, Flags.CREEPER_EXPLOSION, Flags.TNT);
        } else {
            return true;
        }
    }
}
