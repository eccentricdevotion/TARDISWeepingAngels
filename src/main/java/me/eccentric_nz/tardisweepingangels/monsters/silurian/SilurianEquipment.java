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
package me.eccentric_nz.tardisweepingangels.monsters.silurian;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class SilurianEquipment {

    public static void set(LivingEntity livingEntity, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.FEATHER, 1);
        ItemStack arm = new ItemStack(Material.BOW, 1);
        ItemStack chestplate = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
        ItemMeta headMeta = helmet.getItemMeta();
        assert headMeta != null;
        headMeta.setDisplayName("Silurian Head");
        headMeta.setCustomModelData(3);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        assert armMeta != null;
        armMeta.setDisplayName("Silurian Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        assert chestMeta != null;
        chestMeta.setDisplayName("Silurian Chest");
        if (disguise) {
            Damageable chestDamage = (Damageable) chestMeta;
            chestDamage.setDamage(235);
        }
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        assert legMeta != null;
        legMeta.setDisplayName("Silurian Legs");
        if (disguise) {
            Damageable legDamage = (Damageable) legMeta;
            legDamage.setDamage(220);
        }
        leggings.setItemMeta(legMeta);

        EntityEquipment entityEquipment = livingEntity.getEquipment();
        assert entityEquipment != null;
        entityEquipment.setChestplate(chestplate);
        entityEquipment.setLeggings(leggings);
        entityEquipment.setBoots(null);
        entityEquipment.setHelmet(helmet);
        if (!disguise) {
            ItemStack bow = new ItemStack(Material.BOW, 1);
            ItemMeta weaponMeta = bow.getItemMeta();
            assert weaponMeta != null;
            weaponMeta.setDisplayName("Silurian Weapon");
            weaponMeta.setCustomModelData(3);
            bow.setItemMeta(weaponMeta);
            entityEquipment.setItemInMainHand(bow);
            entityEquipment.setItemInOffHand(arm);
            entityEquipment.setItemInMainHandDropChance(0F);
            entityEquipment.setItemInOffHandDropChance(0F);
            entityEquipment.setHelmetDropChance(0F);
            entityEquipment.setChestplateDropChance(0F);
            entityEquipment.setLeggingsDropChance(0F);
            livingEntity.setCanPickupItems(false);
            livingEntity.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.silurian, PersistentDataType.INTEGER, Monster.SILURIAN.getPersist());
        }
    }
}
