/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.sontarans;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
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
import java.util.UUID;

/**
 * The seemingly male Sontarans could be genespliced to produce milk. Strax was very proud that he could produce
 * "magnificent quantities" of lactic fluid and offered to nurse Melody Pond.
 *
 * @author eccentric_nz
 */
public class Butler implements Listener {

    private final List<UUID> milkers = new ArrayList<>();
    private final TARDISWeepingAngels plugin;

    public Butler(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSontaranInteract(PlayerInteractEntityEvent event) {
        Entity ent = event.getRightClicked();
        if (ent instanceof Zombie) {
            Zombie zom = (Zombie) ent;
            EntityEquipment ee = zom.getEquipment();
            if (ee.getHelmet().getType().equals(Material.POTATO)) {
                ItemStack h = ee.getHelmet();
                if (h.hasItemMeta() && h.getItemMeta().hasDisplayName() && h.getItemMeta().getDisplayName().startsWith("Sontaran")) {
                    Player p = event.getPlayer();
                    ItemStack is = p.getInventory().getItemInMainHand();
                    if (is.getType().equals(Material.POTION)) {
                        PotionMeta potionMeta = (PotionMeta) is.getItemMeta();
                        if (potionMeta != null && potionMeta.getBasePotionData().getType().equals(PotionType.WEAKNESS)) {
                            // remove the potion
                            int a = p.getInventory().getItemInMainHand().getAmount();
                            int a2 = a - 1;
                            if (a2 > 0) {
                                p.getInventory().getItemInMainHand().setAmount(a2);
                            } else {
                                p.getInventory().removeItem(is);
                            }
                            // switch the armour to a butler uniform
                            Location l = zom.getLocation();
                            zom.remove();
                            PigZombie pz = (PigZombie) l.getWorld().spawnEntity(l, EntityType.PIG_ZOMBIE);
                            pz.setSilent(true);
                            pz.setBaby(false);
                            pz.setAngry(false);
                            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                                TARDISWeepingAngels.getEqipper().setStraxEquipment(pz, false);
                                pz.getPersistentDataContainer().set(TARDISWeepingAngels.STRAX, PersistentDataType.INTEGER, Monster.STRAX.getPersist());
                                pz.getPersistentDataContainer().remove(TARDISWeepingAngels.SONTARAN);
                                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(pz, EntityType.PIG_ZOMBIE, Monster.STRAX, l));
                            }, 2L);
                        }
                    }
                    return;
                }
            }
            if (ee.getHelmet().getType().equals(Material.BAKED_POTATO)) {
                ItemStack h = ee.getHelmet();
                if (h.hasItemMeta() && h.getItemMeta().hasDisplayName() && h.getItemMeta().getDisplayName().startsWith("Strax")) {
                    Player p = event.getPlayer();
                    UUID uuid = p.getUniqueId();
                    ItemStack is = p.getInventory().getItemInMainHand();
                    if (is.getType().equals(Material.BUCKET)) {
                        if (!milkers.contains(uuid)) {
                            milkers.add(uuid);
                            p.playSound(zom.getLocation(), "milk", 1.0f, 1.0f);
                            ItemStack milk = new ItemStack(Material.MILK_BUCKET);
                            ItemMeta m = milk.getItemMeta();
                            m.setDisplayName("Sontaran Lactic Fluid");
                            milk.setItemMeta(m);
                            p.setItemInHand(milk);
                            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                                milkers.remove(uuid);
                            }, 3000L);
                        } else {
                            p.sendMessage(plugin.pluginName + "Strax is not lactating right now, try again later.");
                        }
                    } else if (event.getHand().equals(EquipmentSlot.HAND)) {
                        p.playSound(zom.getLocation(), "strax", 1.0f, 1.0f);
                    }
                }
            }
        }
    }
}
