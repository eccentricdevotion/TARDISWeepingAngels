/*
 * Copyright (C) 2023 eccentric_nz
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
package me.eccentric_nz.tardisweepingangels.move;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 *
 * @author macgeek
 */
public class MoveSession {
    
    private boolean staleLocation;
    private Location loc;

    public MoveSession(Entity entity) {
        setLocation(entity.getLocation());
    }

    public boolean isStaleLocation() {
        return staleLocation;
    }

    private void setStaleLocation(boolean active) {
        staleLocation = active;
    }

    void setStaleLocation(Location loc) {

        // If the entity has not moved, they have a stale location
        if (getLocation().getX() == loc.getX() && getLocation().getY() == loc.getY() && getLocation().getZ() == loc.getZ()) {
            setStaleLocation(true);
        } else {
            // Update the entity's Session to the new Location.
            setLocation(loc);
            // The location is no longer stale.
            setStaleLocation(false);
        }
    }

    private Location getLocation() {
        return loc;
    }

    private void setLocation(Location loc) {
        this.loc = loc;
    }
}