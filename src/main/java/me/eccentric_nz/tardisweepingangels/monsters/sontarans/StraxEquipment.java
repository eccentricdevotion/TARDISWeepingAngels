package me.eccentric_nz.tardisweepingangels.monsters.sontarans;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class StraxEquipment {

    public static void set(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.BAKED_POTATO, 1);
        ItemStack arm = new ItemStack(Material.BAKED_POTATO, 1);
        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Strax Head");
        headMeta.setCustomModelData(3);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Strax Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Strax Chest");
        if (disguise) {
            Damageable damageable = (Damageable) chestMeta;
            damageable.setDamage(235);
        }
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Strax Legs");
        if (disguise) {
            Damageable legDamage = (Damageable) legMeta;
            legDamage.setDamage(220);
        }
        leggings.setItemMeta(legMeta);

        EntityEquipment ee = le.getEquipment();
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        ee.setHelmet(helmet);
        if (!disguise) {
            ee.setItemInMainHand(arm);
            ee.setItemInOffHand(arm.clone());
            ee.setItemInMainHandDropChance(0F);
            ee.setItemInOffHandDropChance(0F);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCustomName("Strax");
            le.setCanPickupItems(false);
        }
    }
}
