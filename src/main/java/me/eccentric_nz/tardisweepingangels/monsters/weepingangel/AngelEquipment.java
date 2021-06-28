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
package me.eccentric_nz.tardisweepingangels.monsters.weepingangel;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class AngelEquipment {

    public static void set(LivingEntity livingEntity, boolean disguise) {
        ItemStack head = new ItemStack(Material.BRICK, 1);
        ItemStack arm = new ItemStack(Material.BRICK, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS, 1);
        ItemMeta headMeta = head.getItemMeta();
        assert headMeta != null;
        headMeta.setDisplayName("Weeping Angel Head");
        headMeta.setCustomModelData(4);
        head.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        assert armMeta != null;
        armMeta.setDisplayName("Weeping Angel Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        assert chestMeta != null;
        chestMeta.setDisplayName("Weeping Angel Chest");
        if (disguise) {
            Damageable damageable = (Damageable) chestMeta;
            damageable.setDamage(235);
        }
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        assert legMeta != null;
        legMeta.setDisplayName("Weeping Angel Legs");
        if (disguise) {
            Damageable legDamage = (Damageable) legMeta;
            legDamage.setDamage(220);
        }
        leggings.setItemMeta(legMeta);
        ItemMeta weaponMeta = boots.getItemMeta();
        assert weaponMeta != null;
        weaponMeta.setDisplayName("Weeping Angel Feet");
        boots.setItemMeta(weaponMeta);
        EntityEquipment entityEquipment = livingEntity.getEquipment();
        assert entityEquipment != null;
        entityEquipment.setHelmet(head);
        entityEquipment.setChestplate(chestplate);
        entityEquipment.setLeggings(leggings);
        entityEquipment.setBoots(boots);
        if (!disguise) {
            entityEquipment.setItemInMainHand(arm);
            entityEquipment.setItemInOffHand(arm.clone());
            entityEquipment.setItemInMainHandDropChance(0F);
            entityEquipment.setItemInOffHandDropChance(0F);
            entityEquipment.setHelmetDropChance(0F);
            entityEquipment.setChestplateDropChance(0F);
            entityEquipment.setLeggingsDropChance(0F);
            entityEquipment.setBootsDropChance(0F);
            livingEntity.setCanPickupItems(false);
            livingEntity.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.weepingAngel, PersistentDataType.INTEGER, Monster.WEEPING_ANGEL.getPersist());
        }
    }
}
