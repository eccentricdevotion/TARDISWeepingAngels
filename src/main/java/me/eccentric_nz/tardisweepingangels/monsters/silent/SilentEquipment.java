package me.eccentric_nz.tardisweepingangels.monsters.silent;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SilentEquipment {

    public static void set(LivingEntity le, boolean disguise) {
        ItemStack head = new ItemStack(Material.END_STONE);
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName("Silent Head");
//        headMeta.setCustomModelData((TARDISWeepingAngels.random.nextBoolean()) ? 3 : 2);
        headMeta.setCustomModelData(5);
        head.setItemMeta(headMeta);
        if (!disguise) {
            LivingEntity g = (LivingEntity) le.getLocation().getWorld().spawnEntity(le.getLocation(), EntityType.GUARDIAN);
            g.setSilent(true);
            PotionEffect p = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false);
            g.addPotionEffect(p);
            g.getPersistentDataContainer().set(TARDISWeepingAngels.SILENT, PersistentDataType.INTEGER, Monster.SILENT.getPersist());
            g.setInvulnerable(true);
            le.addPassenger(g);
            le.getPersistentDataContainer().set(TARDISWeepingAngels.SILENT, PersistentDataType.INTEGER, Monster.SILENT.getPersist());
            // armour
            ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
            ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
            ItemMeta chestMeta = chestplate.getItemMeta();
            chestMeta.setDisplayName("Silent Chest");
            if (disguise) {
                Damageable damageable = (Damageable) chestMeta;
                damageable.setDamage(235);
            }
            chestplate.setItemMeta(chestMeta);
            ItemMeta legMeta = leggings.getItemMeta();
            legMeta.setDisplayName("Silent Legs");
            if (disguise) {
                Damageable legDamage = (Damageable) legMeta;
                legDamage.setDamage(220);
            }
            leggings.setItemMeta(legMeta);
            // arms
            ItemStack armRight = new ItemStack(Material.END_STONE, 1);
            ItemMeta armRightMeta = armRight.getItemMeta();
            armRightMeta.setDisplayName("Silent Arm");
            armRightMeta.setCustomModelData(7);
            armRight.setItemMeta(armRightMeta);
            ItemStack armLeft = new ItemStack(Material.END_STONE, 1);
            ItemMeta armLeftMeta = armLeft.getItemMeta();
            armLeftMeta.setDisplayName("Silent Arm");
            armLeftMeta.setCustomModelData(8);
            armLeft.setItemMeta(armLeftMeta);
            // set equipment
            EntityEquipment ee = le.getEquipment();
            ee.setHelmet(head);
            ee.setChestplate(chestplate);
            ee.setLeggings(leggings);
            ee.setBoots(null);
            ee.setItemInMainHandDropChance(0F);
            ee.setItemInOffHandDropChance(0F);
            ee.setItemInMainHand(armRight);
            ee.setItemInOffHand(armLeft);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
        } else {
            Player p = (Player) le;
            p.getInventory().setHelmet(head);
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false);
            p.addPotionEffect(potionEffect);
        }
    }
}
