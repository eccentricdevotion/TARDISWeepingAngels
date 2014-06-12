package me.eccentric_nz.tardisweepingangels;

import java.util.Arrays;
import java.util.Collections;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;

public class TARDISWeepingAngelsCaveFinder {

    public static Location searchCave(Location random) {
        Location l = null;
        World w = random.getWorld();
        int startx = random.getBlockX();
        int startz = random.getBlockZ();
        if (worldCheck(w)) {
            int limitx = 2000;
            int limitz = 2000;
            int step = 25;
            // search in a random direction
            Integer[] directions = new Integer[]{0, 1, 2, 3};
            Collections.shuffle(Arrays.asList(directions));
            for (int i = 0; i < 4; i++) {
                switch (directions[i]) {
                    case 0:
                        // east
                        for (int east = startx; east < east + limitx; east += step) {
                            Check chk = isThereRoom(w, east, startz);
                            if (chk.isSafe()) {
                                return new Location(w, east, chk.getY(), startz);
                            }
                        }
                        break;
                    case 1:
                        // south
                        for (int south = startz; south < south + limitz; south += step) {
                            Check chk = isThereRoom(w, startx, south);
                            if (chk.isSafe()) {
                                return new Location(w, startx, chk.getY(), south);
                            }
                        }
                        break;
                    case 2:
                        // west
                        for (int west = startx; west > west - limitx; west -= step) {
                            Check chk = isThereRoom(w, west, startz);
                            if (chk.isSafe()) {
                                return new Location(w, west, chk.getY(), startz);
                            }
                        }
                        break;
                    case 3:
                        // north
                        for (int north = startz; north > north - limitz; north -= step) {
                            Check chk = isThereRoom(w, startx, north);
                            if (chk.isSafe()) {
                                return new Location(w, startx, chk.getY(), north);
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
        for (int y = 35; y > 14; y--) {
            if (w.getBlockAt(x, y, z).getType().equals(Material.AIR)) {
                int yy = getLowestAirBlock(w, x, y, z);
                // check there is enough height for the police box
                if (yy <= y - 3 && w.getBlockAt(x - 1, yy - 1, z - 1).getType().equals(Material.STONE)) {
                    // check there is room for the police box
                    if (w.getBlockAt(x - 1, yy, z - 1).getType().equals(Material.AIR)
                            && w.getBlockAt(x - 1, yy, z).getType().equals(Material.AIR)
                            && w.getBlockAt(x - 1, yy, z + 1).getType().equals(Material.AIR)
                            && w.getBlockAt(x, yy, z - 1).getType().equals(Material.AIR)
                            && w.getBlockAt(x, yy, z + 1).getType().equals(Material.AIR)
                            && w.getBlockAt(x + 1, yy, z - 1).getType().equals(Material.AIR)
                            && w.getBlockAt(x + 1, yy, z).getType().equals(Material.AIR)
                            && w.getBlockAt(x + 1, yy, z + 1).getType().equals(Material.AIR)) {
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
        while (w.getBlockAt(x, yy, z).getRelative(BlockFace.DOWN).getType().equals(Material.AIR)) {
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
