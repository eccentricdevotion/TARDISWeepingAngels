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
package me.eccentric_nz.tardisweepingangels.monsters.judoon;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public class JudoonWalkRunnable implements Runnable {

    private final int[] walkCycle = new int[]{2, 5, 6, 5, 2, 7, 8, 7};
    private final ArmorStand armorStand;
    private final double speed;
    private final Player player;
    private int i = 0;

    public JudoonWalkRunnable(ArmorStand armorStand, double speed, Player player) {
        this.armorStand = armorStand;
        this.speed = speed;
        this.player = player;
    }

    @Override
    public void run() {
        Location location = armorStand.getLocation();
        Vector pos = location.toVector(); // TODO Figure out whether this is "position" or "pose".
        if (player != null) {
            EntityEquipment entityEquipment = armorStand.getEquipment();
            if (entityEquipment != null) {
                ItemStack head = entityEquipment.getHelmet();
                ItemMeta itemMeta = head.getItemMeta();
                itemMeta.setCustomModelData(walkCycle[i]);
                head.setItemMeta(itemMeta);
                entityEquipment.setHelmet(head);
                BoundingBox armorStandBoundingBox = armorStand.getBoundingBox();
                BoundingBox playerBoundingBox = player.getBoundingBox().expand(1.0);
                if (!armorStandBoundingBox.overlaps(playerBoundingBox) && location.getWorld() == player.getWorld()) {
                    Vector target = player.getLocation().toVector();
                    Vector velocity = target.subtract(pos);
                    armorStand.setVelocity(velocity.normalize().multiply(speed));
                    location.setDirection(velocity);
                    armorStand.setRotation(location.getYaw(), location.getPitch());
                    i++;
                    if (i == walkCycle.length) {
                        i = 0;
                    }
                } else {
                    i = 0;
                }
            }
        }
    }
}
