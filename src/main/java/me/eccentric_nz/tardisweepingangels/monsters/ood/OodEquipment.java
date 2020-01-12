package me.eccentric_nz.tardisweepingangels.monsters.ood;

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
import org.bukkit.util.EulerAngle;

import java.util.UUID;

public class OodEquipment {

    public static void set(Player player, Entity entity, boolean disguise) {
        int colour = 0;
        int r = TARDISWeepingAngels.random.nextInt(100);
        if (r > 70) {
            colour = (r > 85) ? 20 : 10;
        }
        ItemStack head = new ItemStack(Material.ROTTEN_FLESH);
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName("Ood Head");
        headMeta.setCustomModelData((disguise) ? 29 : 2 + colour);
        head.setItemMeta(headMeta);
        if (!disguise) {
            UUID uuid;
            if (player != null) {
                uuid = player.getUniqueId();
            } else {
                uuid = TARDISWeepingAngels.UNCLAIMED;
            }
            ArmorStand armorStand = (ArmorStand) entity;
            armorStand.getPersistentDataContainer().set(TARDISWeepingAngels.OOD, PersistentDataType.INTEGER, 0);
            armorStand.getPersistentDataContainer().set(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID, uuid);
            ItemStack arm = new ItemStack(Material.ROTTEN_FLESH);
            ItemMeta armMeta = arm.getItemMeta();
            armMeta.setDisplayName("Ood Arm");
            armMeta.setCustomModelData(3 + colour);
            arm.setItemMeta(armMeta);
            ItemStack brain_arm = new ItemStack(Material.ROTTEN_FLESH);
            ItemMeta brainMeta = brain_arm.getItemMeta();
            brainMeta.setDisplayName("Ood Brain Arm");
            brainMeta.setCustomModelData(4 + colour);
            brain_arm.setItemMeta(brainMeta);
            EntityEquipment ee = armorStand.getEquipment();
            armorStand.setHelmet(head);
            ee.setItemInMainHand(brain_arm);
            armorStand.setRightArmPose(new EulerAngle(-1d, 0, 0.175d));
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
