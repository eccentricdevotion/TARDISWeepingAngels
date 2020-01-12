package me.eccentric_nz.tardisweepingangels.monsters.zygons;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ZygonEquipment {

    public static void set(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.PAINTING, 1);
        ItemStack arm = new ItemStack(Material.PAINTING, 1);
        ItemStack chestplate = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
        if (disguise) {
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Zygon Head");
        headMeta.setCustomModelData(3);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Zygon Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Zygon Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Zygon Legs");
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
            le.setCanPickupItems(false);
            le.getPersistentDataContainer().set(TARDISWeepingAngels.ZYGON, PersistentDataType.INTEGER, Monster.ZYGON.getPersist());
        }
    }
}
