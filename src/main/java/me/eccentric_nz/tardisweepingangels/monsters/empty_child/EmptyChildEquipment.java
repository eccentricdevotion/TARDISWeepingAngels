package me.eccentric_nz.tardisweepingangels.monsters.empty_child;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EmptyChildEquipment {

    public static void set(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.SUGAR, 1);
        ItemStack arm = new ItemStack(Material.SUGAR, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        if (disguise) {
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Empty Child Head");
        headMeta.setCustomModelData(3);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Empty Child Arm");
        armMeta.setCustomModelData(3);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Empty Child Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Empty Child Legs");
        leggings.setItemMeta(legMeta);

        EntityEquipment ee = le.getEquipment();
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        ee.setHelmet(helmet);
        if (!disguise) {
            PotionEffect p = new PotionEffect(PotionEffectType.SLOW, 360000, 1, true, false);
            le.removePotionEffect(PotionEffectType.SPEED);
            le.addPotionEffect(p);
            le.setCanPickupItems(false);
            ee.setItemInMainHand(arm);
            ee.setItemInOffHand(arm.clone());
            ee.setItemInMainHandDropChance(0F);
            ee.setItemInOffHandDropChance(0F);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.getPersistentDataContainer().set(TARDISWeepingAngels.EMPTY, PersistentDataType.INTEGER, Monster.EMPTY_CHILD.getPersist());
        }
    }
}
