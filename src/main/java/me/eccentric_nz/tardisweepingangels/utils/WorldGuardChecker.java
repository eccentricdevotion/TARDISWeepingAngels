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
        Plugin p = Bukkit.getPluginManager().getPlugin("WorldGuard");
        if (p != null) {
            WorldGuardPlatform wg = WorldGuard.getInstance().getPlatform();
            BlockVector3 vector = BukkitAdapter.asBlockVector(l);
            RegionManager rm = wg.getRegionContainer().get(BukkitAdapter.adapt(Objects.requireNonNull(l.getWorld())));
            assert rm != null;
            ApplicableRegionSet rs = rm.getApplicableRegions(vector);
            if (rs.testState(null, Flags.MOB_SPAWNING)) {
                return rs.queryValue(null, Flags.DENY_SPAWN) == null;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean canExplode(Location l) {
        Plugin p = Bukkit.getPluginManager().getPlugin("WorldGuard");
        if (p != null) {
            WorldGuardPlatform wg = WorldGuard.getInstance().getPlatform();
            ConfigurationManager cfg = wg.getGlobalStateManager();
            World bw = BukkitAdapter.adapt(Objects.requireNonNull(l.getWorld()));
            BukkitWorldConfiguration wcfg = (BukkitWorldConfiguration) cfg.get(bw);
            if (wcfg.blockCreeperBlockDamage || wcfg.blockTNTBlockDamage) {
                return false;
            }
            BlockVector3 vector = BukkitAdapter.asBlockVector(l);
            RegionManager rm = wg.getRegionContainer().get(bw);
            assert rm != null;
            ApplicableRegionSet rs = rm.getApplicableRegions(vector);
            return rs.testState(null, Flags.OTHER_EXPLOSION, Flags.CREEPER_EXPLOSION, Flags.TNT);
        } else {
            return true;
        }
    }
}
