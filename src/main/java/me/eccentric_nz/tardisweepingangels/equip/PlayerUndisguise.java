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
package me.eccentric_nz.tardisweepingangels.equip;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class PlayerUndisguise implements Listener {

    private final TardisWeepingAngelsPlugin plugin;

    public PlayerUndisguise(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onManualUndisguise(InventoryClickEvent event) {
        if (event.getSlotType().equals(SlotType.ARMOR)) {
            int slot = event.getRawSlot();
            if (slot > 4 && slot < 9) {
                ItemStack itemStack = event.getCurrentItem();
                if (itemStack != null) {
                    if (itemStack.hasItemMeta()) {
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        if (!itemMeta.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER)) {
                            if (itemMeta.hasDisplayName() && (itemMeta.getDisplayName().startsWith("Weeping Angel") || itemMeta.getDisplayName().startsWith("Ice Warrior") || itemMeta.getDisplayName().startsWith("Cyberman") || itemMeta.getDisplayName().startsWith("Empty Child") || itemMeta.getDisplayName().startsWith("Hath") || itemMeta.getDisplayName().startsWith("Silurian") || itemMeta.getDisplayName().startsWith("Sontaran") || itemMeta.getDisplayName().startsWith("Strax") || itemMeta.getDisplayName().startsWith("Zygon") || itemMeta.getDisplayName().startsWith("Vashta"))) {
                                event.setCancelled(true);
                                (event.getWhoClicked()).sendMessage(plugin.pluginName + "You must use the '/twad [monster] off' command to remove this armour!");
                            }
                        }
                    }
                }
            }
        }
    }
}
