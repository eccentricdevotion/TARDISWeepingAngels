package me.eccentric_nz.tardisweepingangels.monsters.toclafane;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ToclafaneEquipment {

    public static void set(Entity entity, boolean disguise) {
        ItemStack head = new ItemStack(Material.GUNPOWDER);
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName("Toclafane");
        headMeta.setCustomModelData((disguise) ? 3 : 2);
        head.setItemMeta(headMeta);
        if (!disguise) {
            ArmorStand armorStand = (ArmorStand) entity;
            Location location = armorStand.getLocation();
            int difficulty = (location.getWorld().getDifficulty().ordinal() * 6) + 1;
            armorStand.getPersistentDataContainer().set(TARDISWeepingAngels.TOCLAFANE, PersistentDataType.INTEGER, difficulty);
            armorStand.getPersistentDataContainer().set(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID, TARDISWeepingAngels.UNCLAIMED);
            armorStand.setHelmet(head);
            armorStand.setVisible(false);
            armorStand.setSilent(true);
            armorStand.setCollidable(true);
            Bee bee = (Bee) location.getWorld().spawnEntity(location, EntityType.BEE);
            bee.setCannotEnterHiveTicks(Integer.MAX_VALUE);
            PotionEffect p = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false);
            bee.addPotionEffect(p);
            bee.addPassenger(entity);
            bee.setSilent(true);
        } else {
            Player p = (Player) entity;
            p.getInventory().setHelmet(head);
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false);
            p.addPotionEffect(potionEffect);
        }
    }
}
