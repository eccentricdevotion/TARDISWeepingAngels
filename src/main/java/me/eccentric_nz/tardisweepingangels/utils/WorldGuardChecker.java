package me.eccentric_nz.tardisweepingangels.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.BukkitWorldConfiguration;
import com.sk89q.worldguard.config.ConfigurationManager;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class WorldGuardChecker {

    public static boolean canSpawn(Location l) {
        Plugin p = Bukkit.getPluginManager().getPlugin("WorldGuard");
        if (p != null) {
            WorldGuardPlatform wg = WorldGuard.getInstance().getPlatform();
            BlockVector3 vector = BlockVector3.at(l.getX(), l.getY(), l.getZ());
            RegionManager rm = wg.getRegionContainer().get(new BukkitWorld(l.getWorld()));
            ApplicableRegionSet rs = rm.getApplicableRegions(vector);
            return rs.testState(null, Flags.MOB_SPAWNING);
        } else {
            return true;
        }
    }

    public static boolean canExplode(Location l) {
        Plugin p = Bukkit.getPluginManager().getPlugin("WorldGuard");
        if (p != null) {
            WorldGuardPlatform wg = WorldGuard.getInstance().getPlatform();
            ConfigurationManager cfg = wg.getGlobalStateManager();
            BukkitWorldConfiguration wcfg = (BukkitWorldConfiguration) cfg.get(BukkitAdapter.adapt(l.getWorld()));
            if (wcfg.blockCreeperBlockDamage || wcfg.blockTNTBlockDamage) {
                return false;
            }
            BlockVector3 vector = BlockVector3.at(l.getX(), l.getY(), l.getZ());
            RegionManager rm = wg.getRegionContainer().get(new BukkitWorld(l.getWorld()));
            ApplicableRegionSet rs = rm.getApplicableRegions(vector);
            return rs.testState(null, Flags.OTHER_EXPLOSION, Flags.CREEPER_EXPLOSION, Flags.TNT, Flags.BUILD);
        } else {
            return true;
        }
    }
}
