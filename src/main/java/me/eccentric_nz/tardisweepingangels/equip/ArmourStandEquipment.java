package me.eccentric_nz.tardisweepingangels.equip;

/*
 *  Copyright 2014 eccentric_nz.
 */

import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author eccentric_nz
 */
public class ArmourStandEquipment {

    public void setStandEquipment(ArmorStand as, Monster monster, boolean small) {
        as.setSmall(small);
        as.setArms(false);
        ItemStack head = new ItemStack(monster.getMaterial(), 1);
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName(monster.getName() + " Head");
        headMeta.setCustomModelData(monster.getCustomModelData());
        head.setItemMeta(headMeta);
        setHelmetOnly(as, head);
    }

    private void setHelmetOnly(ArmorStand as, ItemStack is) {
        EntityEquipment ee = as.getEquipment();
        ee.setChestplate(null);
        ee.setLeggings(null);
        ee.setBoots(null);
        ee.setHelmet(is);
        ee.setItemInMainHand(null);
        ee.setItemInOffHand(null);
        as.setVisible(false);
    }
}
