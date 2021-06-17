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

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;
import java.util.UUID;

public class JudoonListener implements Listener {

    private final TardisWeepingAngelsPlugin plugin;

    public JudoonListener(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamageJudoon(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof ArmorStand armorStand && event.getDamager() instanceof Player) {
            if (armorStand.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.OWNER_UUID, TardisWeepingAngelsPlugin.PersistentDataTypeUuid) && armorStand.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.JUDOON, PersistentDataType.INTEGER)) {
                event.setCancelled(true);
                Player player = (Player) event.getDamager();
                player.playSound(armorStand.getLocation(), "judoon", 1.0f, 1.0f);
                if (!player.hasPermission("tardisweepingangels.judoon")) {
                    return;
                }
                UUID judoonId = armorStand.getPersistentDataContainer().get(TardisWeepingAngelsPlugin.OWNER_UUID, TardisWeepingAngelsPlugin.PersistentDataTypeUuid);
                if (player.getUniqueId().equals(judoonId)) {
                    int ammo = armorStand.getPersistentDataContainer().get(TardisWeepingAngelsPlugin.JUDOON, PersistentDataType.INTEGER);
                    if (Tag.SHULKER_BOXES.isTagged(player.getInventory().getItemInMainHand().getType())) {
                        // top up ammo
                        ItemStack box = player.getInventory().getItemInMainHand();
                        BlockStateMeta boxItemMeta = (BlockStateMeta) box.getItemMeta();
                        assert boxItemMeta != null;
                        ShulkerBox shulkerBox = (ShulkerBox) boxItemMeta.getBlockState();
                        Inventory inventory = shulkerBox.getInventory();
                        if (inventory.contains(Material.ARROW)) {
                            int arrow = inventory.first(Material.ARROW);
                            ItemStack arrows = inventory.getItem(arrow);
                            assert arrows != null;
                            ItemMeta arrowsItemMeta = arrows.getItemMeta();
                            assert arrowsItemMeta != null;
                            if (arrowsItemMeta.hasCustomModelData() && arrowsItemMeta.getCustomModelData() == 13) {
                                int remove = plugin.getConfig().getInt("judoon.ammunition") - ammo;
                                if (arrows.getAmount() > remove) {
                                    arrows.setAmount(arrows.getAmount() - remove);
                                } else {
                                    remove = arrows.getAmount();
                                    arrows = null;
                                }
                                inventory.setItem(arrow, arrows);
                                shulkerBox.update();
                                boxItemMeta.setBlockState(shulkerBox);
                                box.setItemMeta(boxItemMeta);
                                armorStand.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.JUDOON, PersistentDataType.INTEGER, (ammo + remove));
                                armorStand.setCustomName("Ammunition: " + (ammo + remove));
                                player.sendMessage(plugin.pluginName + "You reloaded " + remove + " Judoon ammunition.");
                            }
                        }
                    } else {
                        ItemStack arm = Objects.requireNonNull(armorStand.getEquipment()).getItemInMainHand();
                        ItemMeta itemMeta = arm.getItemMeta();
                        assert itemMeta != null;
                        int customModelData = itemMeta.getCustomModelData();
                        if (customModelData == 4 && ammo > 0) {
                            if (!plugin.getPlayersWithGuards().contains(player.getUniqueId())) {
                                player.sendMessage(plugin.pluginName + "Judoon ready for action.");
                                // add to repeating task
                                plugin.getGuards().add(armorStand.getUniqueId());
                                plugin.getPlayersWithGuards().add(player.getUniqueId());
                            } else {
                                player.sendMessage(plugin.pluginName + "You already have a Judoon guard!");
                            }
                            // point weapon
                            customModelData = 9;
                            armorStand.setCustomName("Ammunition: " + ammo);
                            armorStand.setCustomNameVisible(true);
                        } else {
                            // stand easy
                            customModelData = 4;
                            armorStand.setCustomNameVisible(false);
                            player.sendMessage(plugin.pluginName + "Judoon standing at ease.");
                            // end guarding task
                            plugin.getGuards().remove(armorStand.getUniqueId());
                            plugin.getPlayersWithGuards().remove(player.getUniqueId());
                        }
                        itemMeta.setCustomModelData(customModelData);
                        arm.setItemMeta(itemMeta);
                        armorStand.getEquipment().setItemInMainHand(arm);
                    }
                } else {
                    assert judoonId != null;
                    if (judoonId.equals(TardisWeepingAngelsPlugin.UNCLAIMED)) {
                        // claim the Judoon
                        armorStand.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.OWNER_UUID, TardisWeepingAngelsPlugin.PersistentDataTypeUuid, player.getUniqueId());
                        player.sendMessage(TardisWeepingAngelsPlugin.plugin.pluginName + "You have claimed this Judoon!");
                    }
                }
            }
        }
    }
}
