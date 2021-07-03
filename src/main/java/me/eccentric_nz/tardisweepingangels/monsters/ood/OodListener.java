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
package me.eccentric_nz.tardisweepingangels.monsters.ood;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class OodListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamageOod(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof ArmorStand armorStand && event.getDamager() instanceof Player player) {
            if (armorStand.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.ood, PersistentDataType.INTEGER) && armorStand.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.ownerUuid, TardisWeepingAngelsPlugin.persistentDataTypeUuid)) {
                event.setCancelled(true);
                player.playSound(armorStand.getLocation(), "ood", 1.0f, 1.0f);
                if (!player.hasPermission("tardisweepingangels.ood")) {
                    return;
                }
                UUID oodId = armorStand.getPersistentDataContainer().get(TardisWeepingAngelsPlugin.ownerUuid, TardisWeepingAngelsPlugin.persistentDataTypeUuid);
                if (oodId.equals(player.getUniqueId())) {
                    EntityEquipment entityEquipment = armorStand.getEquipment();
                    if (entityEquipment != null) {
                        ItemStack head = entityEquipment.getHelmet();
                        ItemMeta itemMeta = head.getItemMeta();
                        int rage = armorStand.getPersistentDataContainer().get(TardisWeepingAngelsPlugin.ood, PersistentDataType.INTEGER);
                        int customModelData = itemMeta.getCustomModelData();
                        if (rage == 1) {
                            customModelData -= 100;
                            rage = 0;
                        } else {
                            customModelData += 100;
                            rage = 1;
                        }
                        itemMeta.setCustomModelData(customModelData);
                        head.setItemMeta(itemMeta);
                        entityEquipment.setHelmet(head);
                        armorStand.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.ood, PersistentDataType.INTEGER, rage);
                    }
                } else if (oodId.equals(TardisWeepingAngelsPlugin.unclaimed)) {
                    // claim the Ood
                    armorStand.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.ownerUuid, TardisWeepingAngelsPlugin.persistentDataTypeUuid, player.getUniqueId());
                    player.sendMessage(TardisWeepingAngelsPlugin.plugin.pluginName + "You have claimed this Ood!");
                }
            }
        }
    }
}
