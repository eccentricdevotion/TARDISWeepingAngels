package me.eccentric_nz.tardisweepingangels.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class WaterChecker {

    public static boolean isNotWater(Location location) {
        Block block = location.clone().add(0, -1.0d, 0).getBlock();
        return block.getType() != Material.WATER;
    }
}
