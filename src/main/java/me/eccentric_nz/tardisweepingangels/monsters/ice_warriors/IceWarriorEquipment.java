package me.eccentric_nz.tardisweepingangels.monsters.ice_warriors;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class IceWarriorEquipment {

    public static void set(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.SNOWBALL, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemStack arm = new ItemStack(Material.SNOWBALL, 1);
        ItemStack weapon = new ItemStack(Material.SNOWBALL, 1);
        if (disguise) {
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Ice Warrior Head");
        headMeta.setCustomModelData(4);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Ice Warrior Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Ice Warrior Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Ice Warrior Legs");
        leggings.setItemMeta(legMeta);

        EntityEquipment ee = le.getEquipment();
        ee.setHelmet(helmet);
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        if (!disguise) {
            ItemMeta sword = weapon.getItemMeta();
            sword.setDisplayName("Ice Warrior Dagger");
            sword.setCustomModelData(3);
            weapon.setItemMeta(sword);
            ee.setItemInMainHand(weapon);
            ee.setItemInOffHand(arm);
            ee.setItemInMainHandDropChance(0F);
            ee.setItemInOffHandDropChance(0F);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
            le.getPersistentDataContainer().set(TARDISWeepingAngels.WARRIOR, PersistentDataType.INTEGER, Monster.ICE_WARRIOR.getPersist());
        }
    }
}
