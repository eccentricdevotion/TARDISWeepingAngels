/*
 *  Copyright 2014 eccentric_nz.
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
            if (entityEquipment.getHelmet().getType().equals(Material.POTATO)) {
                ItemStack helmet = entityEquipment.getHelmet();
                if (helmet.hasItemMeta() && helmet.getItemMeta().hasDisplayName() && helmet.getItemMeta().getDisplayName().startsWith("Sontaran")) {
                    Player player = event.getPlayer();
                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                    if (itemStack.getType().equals(Material.POTION)) {
                        PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
                        if (potionMeta != null && potionMeta.getBasePotionData().getType().equals(PotionType.WEAKNESS)) {
                            // remove the potion
                            int amount1 = player.getInventory().getItemInMainHand().getAmount();
                            int amount2 = amount1 - 1;
                            if (amount2 > 0) {
                                player.getInventory().getItemInMainHand().setAmount(amount2);
                            } else {
                                player.getInventory().removeItem(itemStack);
                            }
                            // switch the armour to a butler uniform
                            Location location = zombie.getLocation();
                            zombie.remove();
                            PigZombie pigZombie = (PigZombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIFIED_PIGLIN);
                            pigZombie.setSilent(true);
                            pigZombie.setAngry(false);
                            pigZombie.setAdult();
                            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                                StraxEquipment.set(pigZombie, false);
                                pigZombie.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.strax, PersistentDataType.INTEGER, Monster.STRAX.getPersist());
                                pigZombie.getPersistentDataContainer().remove(TardisWeepingAngelsPlugin.sontaran);
                                plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(pigZombie, EntityType.ZOMBIFIED_PIGLIN, Monster.STRAX, location));
                            }, 2L);
                        }
                    }
                    return;
                }
            }
            if (entityEquipment.getHelmet().getType().equals(Material.BAKED_POTATO)) {
                ItemStack helmet = entityEquipment.getHelmet();
                if (helmet.hasItemMeta() && helmet.getItemMeta().hasDisplayName() && helmet.getItemMeta().getDisplayName().startsWith("Strax")) {
                    Player player = event.getPlayer();
                    UUID uuid = player.getUniqueId();
                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                    if (itemStack.getType().equals(Material.BUCKET)) {
                        if (!milkers.contains(uuid)) {
                            milkers.add(uuid);
                            player.playSound(zombie.getLocation(), "milk", 1.0f, 1.0f);
                            ItemStack milk = new ItemStack(Material.MILK_BUCKET);
                            ItemMeta milkMeta = milk.getItemMeta();
                            milkMeta.setDisplayName("Sontaran Lactic Fluid");
                            milk.setItemMeta(milkMeta);
                            player.getEquipment().setItemInMainHand(milk);
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
