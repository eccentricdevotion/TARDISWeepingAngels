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
package me.eccentric_nz.tardisweepingangels.monsters.silurian;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;

import java.util.Arrays;
import java.util.Collections;

public class CaveFinder {

    public static Location searchCave(Location random) {
        Location location = null;
        World world = random.getWorld();
        int startX = random.getBlockX();
        int startZ = random.getBlockZ();
        if (worldCheck(world)) {
            int plusX = startX + 2000;
            int plusZ = startZ + 2000;
            int minusX = startX - 2000;
            int minusZ = startZ - 2000;
            int step = 25;
            // search in a random direction
            Integer[] directions = new Integer[]{0, 1, 2, 3};
            Collections.shuffle(Arrays.asList(directions));
            for (int i = 0; i < 4; i++) {
                switch (directions[i]) {
                    case 0:
                        // east
                        for (int east = startX; east < plusX; east += step) {
                            Check check = isThereRoom(world, east, startZ);
                            if (check.isSafe()) {
                                return new Location(world, east, check.getY(), startZ);
                            }
                        }
                        break;
                    case 1:
                        // south
                        for (int south = startZ; south < plusZ; south += step) {
                            Check check = isThereRoom(world, startX, south);
                            if (check.isSafe()) {
                                return new Location(world, startX, check.getY(), south);
                            }
                        }
                        break;
                    case 2:
                        // west
                        for (int west = startX; west > minusX; west -= step) {
                            Check check = isThereRoom(world, west, startZ);
                            if (check.isSafe()) {
                                return new Location(world, west, check.getY(), startZ);
                            }
                        }
                        break;
                    case 3:
                        // north
                        for (int north = startZ; north > minusZ; north -= step) {
                            Check check = isThereRoom(world, startX, north);
                            if (check.isSafe()) {
                                return new Location(world, startX, check.getY(), north);
                            }
                        }
                        break;
                }
            }
        }
        return location;
    }

    private static Check isThereRoom(World world, int x, int z) {
        Check check = new Check();
        check.setSafe(false);
        for (int y = 35; y > 14; y--) {
            if (world.getBlockAt(x, y, z).getType().isAir()) {
                int yy = getLowestAirBlock(world, x, y, z);
                // check there is enough height for the police box
                if (yy <= y - 2 && world.getBlockAt(x - 1, yy - 1, z - 1).getType().equals(Material.STONE)) {
                    // check there is room for the police box
                    if (world.getBlockAt(x - 1, yy, z - 1).getType().isAir() && world.getBlockAt(x - 1, yy, z).getType().isAir() && world.getBlockAt(x - 1, yy, z + 1).getType().isAir() && world.getBlockAt(x, yy, z - 1).getType().isAir() && world.getBlockAt(x, yy, z + 1).getType().isAir() && world.getBlockAt(x + 1, yy, z - 1).getType().isAir() && world.getBlockAt(x + 1, yy, z).getType().isAir() && world.getBlockAt(x + 1, yy, z + 1).getType().isAir()) {
                        check.setSafe(true);
                        check.setY(yy);
                    }
                }
            }
        }
        return check;
    }

    private static int getLowestAirBlock(World world, int x, int y, int z) {
        int yy = y;
        while (world.getBlockAt(x, yy, z).getRelative(BlockFace.DOWN).getType().isAir()) {
            yy--;
        }
        return yy;
    }

    private static boolean worldCheck(World world) {
        Location spawn = world.getSpawnLocation();
        int y = world.getHighestBlockYAt(spawn);
        if (y < 15) {
            return false;
        } else {
            // move 20 blocks north
            spawn.setZ(spawn.getBlockZ() - 20);
            int northY = world.getHighestBlockYAt(spawn);
            spawn.setX(spawn.getBlockZ() + 20);
            int eastY = world.getHighestBlockYAt(spawn);
            spawn.setZ(spawn.getBlockZ() + 20);
            int southY = world.getHighestBlockYAt(spawn);
            return (y != northY && y != eastY && y != southY);
        }
    }

    private static class Check {

        private boolean safe;
        private int y;

        public boolean isSafe() {
            return safe;
        }

        public void setSafe(boolean safe) {
            this.safe = safe;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
