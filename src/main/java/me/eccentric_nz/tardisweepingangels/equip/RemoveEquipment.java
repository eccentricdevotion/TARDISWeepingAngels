package me.eccentric_nz.tardisweepingangels.equip;

import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;

public class RemoveEquipment {

    public static void set(Player p) {
        PlayerInventory inv = p.getInventory();
        if (p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
            p.removePotionEffect(PotionEffectType.INVISIBILITY);
            if (inv.getItemInOffHand() != null) {
                inv.setItemInOffHand(null);
            }
        }
        inv.setHelmet(null);
        inv.setChestplate(null);
        inv.setLeggings(null);
        inv.setBoots(null);
        p.updateInventory();
    }
}
