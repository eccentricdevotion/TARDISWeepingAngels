package me.eccentric_nz.tardisweepingangels.monsters.silurians;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class SilurianEquipment {

    public static void set(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.FEATHER, 1);
        ItemStack bow = new ItemStack(Material.BOW, 1);
        ItemStack chestplate = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Silurian Head");
        headMeta.setCustomModelData(3);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = bow.getItemMeta();
        armMeta.setDisplayName("Silurian Arm");
        armMeta.setCustomModelData(2);
        bow.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Silurian Chest");
        if (disguise) {
            Damageable chestDamage = (Damageable) chestMeta;
            chestDamage.setDamage(235);
        }
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Silurian Legs");
        if (disguise) {
            Damageable legDamage = (Damageable) legMeta;
            legDamage.setDamage(220);
        }
        leggings.setItemMeta(legMeta);
        // set equipment
        EntityEquipment ee = le.getEquipment();
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        ee.setHelmet(helmet);
        if (!disguise) {
            ItemStack arm = new ItemStack(Material.FEATHER, 2);
            ItemMeta weaponMeta = arm.getItemMeta();
            weaponMeta.setDisplayName("Silurian Weapon");
            weaponMeta.setCustomModelData(3);
            arm.setItemMeta(weaponMeta);
            ee.setItemInMainHand(arm);
            ee.setItemInOffHand(bow);
            ee.setItemInMainHandDropChance(0F);
            ee.setItemInOffHandDropChance(0F);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
            le.getPersistentDataContainer().set(TARDISWeepingAngels.SILURIAN, PersistentDataType.INTEGER, Monster.SILURIAN.getPersist());
        }
    }
}
