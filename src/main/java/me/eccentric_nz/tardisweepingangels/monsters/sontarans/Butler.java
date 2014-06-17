/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.sontarans;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * The seemingly male Sontarans could be genespliced to produce milk. Strax was
 * very proud that he could produce "magnificent quantities" of lactic fluid and
 * offered to nurse Melody Pond.
 *
 * @author eccentric_nz
 */
public class Butler implements Listener {

    private final List<UUID> milkers = new ArrayList<UUID>();
    private final TARDISWeepingAngels plugin;

    public Butler(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSontaranInteract(PlayerInteractEntityEvent event) {
        Entity ent = event.getRightClicked();
        if (ent instanceof PigZombie) {
            PigZombie pz = (PigZombie) ent;
            EntityEquipment ee = pz.getEquipment();
            if (ee.getHelmet().getType().equals(Material.GOLD_HELMET)) {
                ItemStack h = ee.getHelmet();
                if (h.hasItemMeta() && h.getItemMeta().hasDisplayName() && h.getItemMeta().getDisplayName().startsWith("Sontaran")) {
                    Player p = event.getPlayer();
                    ItemStack is = p.getItemInHand();
                    if (is.getType().equals(Material.POTION) && is.getDurability() == (short) 8264) {
                        // calm the beast!
                        pz.setAngry(false);
                        // remove the potion
                        int a = p.getInventory().getItemInHand().getAmount();
                        int a2 = a - 1;
                        if (a2 > 0) {
                            p.getInventory().getItemInHand().setAmount(a2);
                        } else {
                            p.getInventory().removeItem(new ItemStack(Material.POTION, 1, (short) 8264));
                        }
                        // switch the armour to a butler uniform
                        new MonsterEquipment().setButlerEquipment(pz, false);
                    }
                    return;
                }
            }
            if (ee.getHelmet().getType().equals(Material.CHAINMAIL_HELMET)) {
                ItemStack h = ee.getHelmet();
                if (h.hasItemMeta() && h.getItemMeta().hasDisplayName() && h.getItemMeta().getDisplayName().startsWith("Strax")) {
                    Player p = event.getPlayer();
                    final UUID uuid = p.getUniqueId();
                    ItemStack is = p.getItemInHand();
                    if (is.getType().equals(Material.BUCKET)) {
                        if (!milkers.contains(uuid)) {
                            milkers.add(uuid);
                            p.playSound(pz.getLocation(), "milk", 1.0f, 1.0f);
                            ItemStack milk = new ItemStack(Material.MILK_BUCKET);
                            ItemMeta m = milk.getItemMeta();
                            m.setDisplayName("Sontaran Lactic Fluid");
                            milk.setItemMeta(m);
                            p.setItemInHand(milk);
                            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    milkers.remove(uuid);
                                }
                            }, 3000L);
                        } else {
                            p.sendMessage(plugin.pluginName + "Strax is not lactating right now, try again later.");
                        }
                    } else {
                        p.playSound(pz.getLocation(), "strax", 1.0f, 1.0f);
                    }
                }
            }
        }
    }
}
