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
package me.eccentric_nz.tardisweepingangels.monsters.sontaran;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The seemingly male Sontarans could be genespliced to produce milk. Strax was very proud that he could produce
 * "magnificent quantities" of lactic fluid and offered to nurse Melody Pond.
 *
 * @author eccentric_nz
 */
public class Strax implements Listener {

    private final List<UUID> milkers = new ArrayList<>();
    private final TardisWeepingAngelsPlugin plugin;

    public Strax(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSontaranInteract(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (entity instanceof Zombie zombie) {
            EntityEquipment entityEquipment = zombie.getEquipment();
            assert entityEquipment != null;
            if (Objects.requireNonNull(entityEquipment.getHelmet()).getType().equals(Material.POTATO)) {
                ItemStack helmet = entityEquipment.getHelmet();
                if (helmet.hasItemMeta() && Objects.requireNonNull(helmet.getItemMeta()).hasDisplayName() && helmet.getItemMeta().getDisplayName().startsWith("Sontaran")) {
                    Player player = event.getPlayer();
                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                    if (itemStack.getType().equals(Material.POTION)) {
                        PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
                        if (potionMeta != null && potionMeta.getBasePotionData().getType().equals(PotionType.WEAKNESS)) {
                            // remove the potion
                            int amount = player.getInventory().getItemInMainHand().getAmount();
                            int amount2 = amount - 1;
                            if (amount2 > 0) {
                                player.getInventory().getItemInMainHand().setAmount(amount2);
                            } else {
                                player.getInventory().removeItem(itemStack);
                            }
                            // switch the armour to a butler uniform
                            Location location = zombie.getLocation();
                            zombie.remove();
                            PigZombie strax = (PigZombie) Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.ZOMBIFIED_PIGLIN);
                            strax.setSilent(true);
                            strax.setAngry(false);
                            strax.setAdult();
                            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                                StraxEquipment.set(strax, false);
                                strax.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.STRAX, PersistentDataType.INTEGER, Monster.STRAX.getPersist());
                                strax.getPersistentDataContainer().remove(TardisWeepingAngelsPlugin.SONTARAN);
                                plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(strax, EntityType.ZOMBIFIED_PIGLIN, Monster.STRAX, location));
                            }, 2L);
                        }
                    }
                    return;
                }
            }
            if (entityEquipment.getHelmet().getType().equals(Material.BAKED_POTATO)) {
                ItemStack helmet = entityEquipment.getHelmet();
                if (helmet.hasItemMeta() && Objects.requireNonNull(helmet.getItemMeta()).hasDisplayName() && helmet.getItemMeta().getDisplayName().startsWith("Strax")) {
                    Player player = event.getPlayer();
                    UUID uuid = player.getUniqueId();
                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                    if (itemStack.getType().equals(Material.BUCKET)) {
                        if (!milkers.contains(uuid)) {
                            milkers.add(uuid);
                            player.playSound(zombie.getLocation(), "milk", 1.0f, 1.0f);
                            ItemStack milk = new ItemStack(Material.MILK_BUCKET);
                            ItemMeta milkMeta = milk.getItemMeta();
                            assert milkMeta != null;
                            milkMeta.setDisplayName("Sontaran Lactic Fluid");
                            milk.setItemMeta(milkMeta);
                            Objects.requireNonNull(player.getEquipment()).setItemInMainHand(milk);
                            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> milkers.remove(uuid), 3000L);
                        } else {
                            player.sendMessage(plugin.pluginName + "Strax is not lactating right now, try again later.");
                        }
                    } else if (event.getHand().equals(EquipmentSlot.HAND)) {
                        player.playSound(zombie.getLocation(), "strax", 1.0f, 1.0f);
                    }
                }
            }
        }
    }
}
