/*
 * Copyright (C) 2021 eccentric_nz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package me.eccentric_nz.tardisweepingangels.monsters.judoon;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
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
        assert headMeta != null;
        headMeta.setDisplayName("Judoon Head");
        headMeta.setCustomModelData((disguise) ? 10 : 2);
        head.setItemMeta(headMeta);
        if (!disguise) {
            UUID uuid;
            if (player != null) {
                uuid = player.getUniqueId();
            } else {
                uuid = TardisWeepingAngelsPlugin.UNCLAIMED;
            }
            ArmorStand armorStand = (ArmorStand) entity;
            armorStand.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.JUDOON, PersistentDataType.INTEGER, 0);
            armorStand.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.OWNER_UUID, TardisWeepingAngelsPlugin.PersistentDataTypeUuid, uuid);
            ItemStack arm = new ItemStack(Material.YELLOW_DYE);
            ItemMeta armMeta = arm.getItemMeta();
            assert armMeta != null;
            armMeta.setDisplayName("Judoon Arm");
            armMeta.setCustomModelData(3);
            arm.setItemMeta(armMeta);
            ItemStack weaponArm = new ItemStack(Material.YELLOW_DYE);
            ItemMeta weaponMeta = weaponArm.getItemMeta();
            assert weaponMeta != null;
            weaponMeta.setDisplayName("Judoon Weapon Arm");
            weaponMeta.setCustomModelData(4);
            weaponArm.setItemMeta(weaponMeta);
            EntityEquipment entityEquipment = armorStand.getEquipment();
            assert entityEquipment != null;
            entityEquipment.setHelmet(head);
            entityEquipment.setItemInMainHand(weaponArm);
            entityEquipment.setItemInOffHand(arm);
            armorStand.setVisible(false);
            armorStand.setSilent(true);
            armorStand.setCollidable(true);
        } else {
            Player judoon = (Player) entity;
            judoon.getInventory().setHelmet(head);
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false);
            judoon.addPotionEffect(potionEffect);
        }
    }
}
