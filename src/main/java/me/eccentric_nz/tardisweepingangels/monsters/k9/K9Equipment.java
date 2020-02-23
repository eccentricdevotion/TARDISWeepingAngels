package me.eccentric_nz.tardisweepingangels.monsters.k9;

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

public class K9Equipment {

    public static void set(Player player, Entity entity, boolean disguise) {
        ItemStack head = new ItemStack(Material.BONE);
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName("K9 Head");
        headMeta.setCustomModelData(1);
        head.setItemMeta(headMeta);
        if (entity instanceof ArmorStand && player != null) {
            UUID uuid;
            if (player != null) {
                uuid = player.getUniqueId();
            } else {
                uuid = TARDISWeepingAngels.UNCLAIMED;
            }
            ArmorStand armorStand = (ArmorStand) entity;
            armorStand.getPersistentDataContainer().set(TARDISWeepingAngels.K9, PersistentDataType.INTEGER, 0);
            armorStand.getPersistentDataContainer().set(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID, uuid);
            EntityEquipment ee = armorStand.getEquipment();
            ee.setHelmet(head);
            ee.setItemInMainHand(null);
            ee.setItemInOffHand(null);
            armorStand.setVisible(false);
            armorStand.setSilent(true);
            armorStand.setCollidable(true);
        } else if (disguise) {
            Player p = (Player) entity;
            p.getInventory().setHelmet(head);
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false);
            p.addPotionEffect(potionEffect);
        }
    }
}
