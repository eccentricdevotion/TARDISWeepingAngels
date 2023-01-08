/*
 * Copyright (C) 2022 eccentric_nz
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

import io.papermc.paper.event.entity.EntityMoveEvent;
import java.util.HashMap;
import java.util.UUID;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 *
 * @author macgeek
 */
public class MonsterMoveListener implements Listener {

    private final HashMap<UUID, MoveSession> moveSessions = new HashMap<>();

    @EventHandler
    public void onMonsterMove(EntityMoveEvent event) {
        Entity entity = event.getEntity();
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        if (pdc.has(TARDISWeepingAngels.ANGEL, PersistentDataType.INTEGER)
                || pdc.has(TARDISWeepingAngels.CYBERMAN, PersistentDataType.INTEGER)
                || pdc.has(TARDISWeepingAngels.EMPTY, PersistentDataType.INTEGER)
                || pdc.has(TARDISWeepingAngels.HATH, PersistentDataType.INTEGER)
                || pdc.has(TARDISWeepingAngels.SILENT, PersistentDataType.INTEGER)
                || pdc.has(TARDISWeepingAngels.SILURIAN, PersistentDataType.INTEGER)
                || pdc.has(TARDISWeepingAngels.SONTARAN, PersistentDataType.INTEGER)
                || pdc.has(TARDISWeepingAngels.STRAX, PersistentDataType.INTEGER)
                || pdc.has(TARDISWeepingAngels.VASHTA, PersistentDataType.INTEGER)
                || pdc.has(TARDISWeepingAngels.WARRIOR, PersistentDataType.INTEGER)
                || pdc.has(TARDISWeepingAngels.ZYGON, PersistentDataType.INTEGER)) {

            MoveSession tms = getMoveSession(entity);
            tms.setStaleLocation(entity.getLocation());

            EntityEquipment ee = ((LivingEntity) entity).getEquipment();
            ItemStack helmet = ee.getHelmet();
            if (helmet != null) {
                ItemMeta meta = helmet.getItemMeta();
                if (meta != null && meta.hasCustomModelData()) {
                    boolean hasChanged = false;
                    int cmd = meta.getCustomModelData();
                    // if the location is stale, ie: the entity isn't actually moving xyz coords, they're looking around
                    if (tms.isStaleLocation() || !event.hasChangedPosition()) {
                        if (!entity.getPassengers().isEmpty()) {
                            Guardian guardian = (Guardian) entity.getPassengers().get(0);
                            // show animated ATTACKING model - silent doesn't move when beaming
                            if (guardian.hasLaser() && cmd != 11) {
                                meta.setCustomModelData(11);
                                hasChanged = true;
                            }
                        } else {
                            // show static model
                            if (cmd != 9) {
                                meta.setCustomModelData(9);
                                hasChanged = true;
                            }
                        }

                    } else {
                        Monster monster = (Monster) entity;
                        if (monster.getTarget() != null) {
                            // show animated ATTACKING model
                            if (cmd != 11) {
                                meta.setCustomModelData(11);
                                hasChanged = true;
                            }
                        } else {
                            // show animated WALKING model
                            if (cmd != 10) {
                                meta.setCustomModelData(10);
                                hasChanged = true;
                            }
                        }
                    }
                    if (hasChanged) {
                        helmet.setItemMeta(meta);
                        ee.setHelmet(helmet);
                    }
                }
            }
        }
    }

    /**
     * Gets the Move Session for a player, this is used to see if they have
     * actually moved
     *
     * @param entity the player to track
     * @return the session for the player
     */
    public MoveSession getMoveSession(Entity entity) {
        if (moveSessions.containsKey(entity.getUniqueId())) {
            return moveSessions.get(entity.getUniqueId());
        }
        MoveSession session = new MoveSession(entity);
        moveSessions.put(entity.getUniqueId(), session);
        return session;
    }
}
