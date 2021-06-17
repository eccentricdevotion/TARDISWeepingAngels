/*
 * Freeze players
 *
 * Copyright 2012 Kristian S. Stangeland (Comphenix)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

package me.eccentric_nz.tardisweepingangels.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Vector3d {

    /**
     * Represents the null (0, 0, 0) origin.
     */
    public static final Vector3d ORIGIN = new Vector3d(0, 0, 0);

    // Use protected members, like Bukkit
    public final double x;
    public final double y;
    public final double z;

    /**
     * Construct an immutable 3D vector.
     */
    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Construct an immutable floating point 3D vector from a location object.
     *
     * @param location - the location to copy.
     */
    public Vector3d(Location location) {
        this(location.toVector());
    }

    /**
     * Construct an immutable floating point 3D vector from a mutable Bukkit vector.
     *
     * @param vector - the mutable real Bukkit vector to copy.
     */
    public Vector3d(Vector vector) {
        if (vector == null) {
            throw new IllegalArgumentException("Vector cannot be NULL.");
        }
        x = vector.getX();
        y = vector.getY();
        z = vector.getZ();
    }

    /**
     * Adds the current vector and a given position vector, producing a result vector.
     *
     * @param other - the other vector.
     * @return The new result vector.
     */
    public Vector3d add(Vector3d other) {
        if (other == null) {
            throw new IllegalArgumentException("other cannot be NULL");
        }
        return new Vector3d(x + other.x, y + other.y, z + other.z);
    }

    /**
     * Adds the current vector and a given vector together, producing a result vector.
     *
     * @return The new result vector.
     */
    public Vector3d add(double x, double y, double z) {
        return new Vector3d(this.x + x, this.y + y, this.z + z);
    }

    /**
     * Substracts the current vector and a given vector, producing a result position.
     *
     * @param other - the other position.
     * @return The new result position.
     */
    public Vector3d subtract(Vector3d other) {
        if (other == null) {
            throw new IllegalArgumentException("other cannot be NULL");
        }
        return new Vector3d(x - other.x, y - other.y, z - other.z);
    }

    /**
     * Multiply each dimension in the current vector by the given factor.
     *
     * @param factor - multiplier.
     * @return The new result.
     */
    public Vector3d multiply(int factor) {
        return new Vector3d(x * factor, y * factor, z * factor);
    }

    /**
     * Multiply each dimension in the current vector by the given factor.
     *
     * @param factor - multiplier.
     * @return The new result.
     */
    public Vector3d multiply(double factor) {
        return new Vector3d(x * factor, y * factor, z * factor);
    }

    /**
     * Retrieve the absolute value of this vector.
     *
     * @return The new result.
     */
    public Vector3d abs() {
        return new Vector3d(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    @Override
    public String toString() {
        return String.format("[x: %s, y: %s, z: %s]", x, y, z);
    }
}
