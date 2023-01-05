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
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 *
 * @author macgeek
 */
public class MonsterMoveListener implements Listener {

    @EventHandler
    public void onMonsterMove(EntityMoveEvent event) {
        Entity entity = event.getEntity();
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        if (pdc.has(TARDISWeepingAngels.CYBERMAN, PersistentDataType.INTEGER)
                || pdc.has(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER)
                || pdc.has(TARDISWeepingAngels.EMPTY, PersistentDataType.INTEGER)
                || pdc.has(TARDISWeepingAngels.WARRIOR, PersistentDataType.INTEGER)
                || pdc.has(TARDISWeepingAngels.SILURIAN, PersistentDataType.INTEGER)
                || pdc.has(TARDISWeepingAngels.SONTARAN, PersistentDataType.INTEGER)
                || pdc.has(TARDISWeepingAngels.STRAX, PersistentDataType.INTEGER)
                || pdc.has(TARDISWeepingAngels.VASHTA, PersistentDataType.INTEGER)
                || pdc.has(TARDISWeepingAngels.ANGEL, PersistentDataType.INTEGER)
                || pdc.has(TARDISWeepingAngels.ZYGON, PersistentDataType.INTEGER)) {
            if (!event.hasChangedBlock()) {
                // get taskID of runnable from entity's PDC
                // cancel runnable
                // show static model
            } else {
                // start an animation runnable
                // store the taskID in the entity's PDC
            }
        }
    }
}
