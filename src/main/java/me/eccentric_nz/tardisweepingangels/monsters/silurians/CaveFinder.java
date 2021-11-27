package me.eccentric_nz.tardisweepingangels.monsters.silurians;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;

import java.util.Arrays;
import java.util.Collections;

public class CaveFinder {

    public static Location searchCave(Location random) {
        Location l = null;
        World w = random.getWorld();
        int startX = random.getBlockX();
        int startZ = random.getBlockZ();
        if (worldCheck(w)) {
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
                            Check chk = isThereRoom(w, east, startZ);
                            if (chk.isSafe()) {
                                return new Location(w, east, chk.getY(), startZ);
                            }
                        }
                        break;
                    case 1:
                        // south
                        for (int south = startZ; south < plusZ; south += step) {
                            Check chk = isThereRoom(w, startX, south);
                            if (chk.isSafe()) {
                                return new Location(w, startX, chk.getY(), south);
                            }
                        }
                        break;
                    case 2:
                        // west
                        for (int west = startX; west > minusX; west -= step) {
                            Check chk = isThereRoom(w, west, startZ);
                            if (chk.isSafe()) {
                                return new Location(w, west, chk.getY(), startZ);
                            }
                        }
                        break;
                    case 3:
                        // north
                        for (int north = startZ; north > minusZ; north -= step) {
                            Check chk = isThereRoom(w, startX, north);
                            if (chk.isSafe()) {
                                return new Location(w, startX, chk.getY(), north);
                            }
                        }
                        break;
                }
            }
        }
        return l;
    }

    private static Check isThereRoom(World w, int x, int z) {
        Check ret = new Check();
        ret.setSafe(false);
        for (int y = 35; y > -50; y--) {
            if (w.getBlockAt(x, y, z).getType().isAir()) {
                int yy = getLowestAirBlock(w, x, y, z);
                // check there is enough height for the police box
                if (yy <= y - 2 && w.getBlockAt(x - 1, yy - 1, z - 1).getType().equals(Material.STONE)) {
                    // check there is room for the police box
                    if (w.getBlockAt(x - 1, yy, z - 1).getType().isAir()
                            && w.getBlockAt(x - 1, yy, z).getType().isAir()
                            && w.getBlockAt(x - 1, yy, z + 1).getType().isAir()
                            && w.getBlockAt(x, yy, z - 1).getType().isAir()
                            && w.getBlockAt(x, yy, z + 1).getType().isAir()
                            && w.getBlockAt(x + 1, yy, z - 1).getType().isAir()
                            && w.getBlockAt(x + 1, yy, z).getType().isAir()
                            && w.getBlockAt(x + 1, yy, z + 1).getType().isAir()) {
                        ret.setSafe(true);
                        ret.setY(yy);
                    }
                }
            }
        }
        return ret;
    }

    private static int getLowestAirBlock(World w, int x, int y, int z) {
        int yy = y;
        while (w.getBlockAt(x, yy, z).getRelative(BlockFace.DOWN).getType().isAir()) {
            yy--;
        }
        return yy;
    }

    private static boolean worldCheck(World w) {
        Location spawn = w.getSpawnLocation();
        int y = w.getHighestBlockYAt(spawn);
        if (y < 15) {
            return false;
        } else {
            // move 20 blocks north
            spawn.setZ(spawn.getBlockZ() - 20);
            int ny = w.getHighestBlockYAt(spawn);
            spawn.setX(spawn.getBlockZ() + 20);
            int ey = w.getHighestBlockYAt(spawn);
            spawn.setZ(spawn.getBlockZ() + 20);
            int sy = w.getHighestBlockYAt(spawn);
            return (y != ny && y != ey && y != sy);
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
