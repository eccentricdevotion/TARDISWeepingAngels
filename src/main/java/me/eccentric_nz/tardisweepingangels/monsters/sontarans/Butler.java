/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.sontarans;

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

/**
 *
 * @author eccentric_nz
 */
public class Butler implements Listener {

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
                }
            }
        }
    }
}
