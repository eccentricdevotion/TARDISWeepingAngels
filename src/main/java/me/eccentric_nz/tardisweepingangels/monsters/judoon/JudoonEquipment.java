package me.eccentric_nz.tardisweepingangels.monsters.judoon;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class JudoonEquipment {

    public static void set(Player player, Entity entity, boolean disguise) {
        ItemStack head = new ItemStack(Material.YELLOW_DYE);
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName("Judoon Head");
        headMeta.setCustomModelData((disguise) ? 10 : 2);
        head.setItemMeta(headMeta);
        if (!disguise) {
            UUID uuid;
            if (player != null) {
                uuid = player.getUniqueId();
            } else {
                uuid = TARDISWeepingAngels.UNCLAIMED;
            }
            ArmorStand armorStand = (ArmorStand) entity;
            armorStand.getPersistentDataContainer().set(TARDISWeepingAngels.JUDOON, PersistentDataType.INTEGER, 0);
            armorStand.getPersistentDataContainer().set(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID, uuid);
            ItemStack arm = new ItemStack(Material.YELLOW_DYE);
            ItemMeta armMeta = arm.getItemMeta();
            armMeta.setDisplayName("Judoon Arm");
            armMeta.setCustomModelData(3);
            arm.setItemMeta(armMeta);
            ItemStack weapon_arm = new ItemStack(Material.YELLOW_DYE);
            ItemMeta weaponMeta = weapon_arm.getItemMeta();
            weaponMeta.setDisplayName("Judoon Weapon Arm");
            weaponMeta.setCustomModelData(4);
            weapon_arm.setItemMeta(weaponMeta);
            EntityEquipment ee = armorStand.getEquipment();
            armorStand.setHelmet(head);
            ee.setItemInMainHand(weapon_arm);
            ee.setItemInOffHand(arm);
            armorStand.setVisible(false);
            armorStand.setSilent(true);
            armorStand.setCollidable(true);
        } else {
            Player p = (Player) entity;
            p.getInventory().setHelmet(head);
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false);
            p.addPotionEffect(potionEffect);
        }
    }
}
