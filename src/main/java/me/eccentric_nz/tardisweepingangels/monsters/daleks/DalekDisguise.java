package me.eccentric_nz.tardisweepingangels.monsters.daleks;

import me.eccentric_nz.tardischunkgenerator.TARDISHelper;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class DalekDisguise {

    private static final TARDISHelper TARDIS_HELPER = (TARDISHelper) Bukkit.getServer().getPluginManager().getPlugin("TARDISChunkGenerator");

    public static void disguise(Entity entity) {
        TARDIS_HELPER.disguiseDalek(entity);
    }

    public static void redisguise(Entity entity, World world) {
        TARDIS_HELPER.redisguiseDalek(entity, world);
    }

    public static boolean isDisguised(Entity entity) {
        return TARDIS_HELPER.isDisguised(entity);
    }
}
