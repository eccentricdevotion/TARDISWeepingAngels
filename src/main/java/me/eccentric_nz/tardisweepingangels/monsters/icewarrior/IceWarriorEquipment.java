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
package me.eccentric_nz.tardisweepingangels.monsters.icewarrior;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class IceWarriorEquipment {

    public static void set(LivingEntity livingEntity, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.SNOWBALL, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemStack arm = new ItemStack(Material.SNOWBALL, 1);
        ItemStack weapon = new ItemStack(Material.SNOWBALL, 1);
        ItemMeta headMeta = helmet.getItemMeta();
        assert headMeta != null;
        headMeta.setDisplayName("Ice Warrior Head");
        headMeta.setCustomModelData(4);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        assert armMeta != null;
        armMeta.setDisplayName("Ice Warrior Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        assert chestMeta != null;
        chestMeta.setDisplayName("Ice Warrior Chest");
        if (disguise) {
            Damageable damageable = (Damageable) chestMeta;
            damageable.setDamage(235);
        }
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        assert legMeta != null;
        legMeta.setDisplayName("Ice Warrior Legs");
        if (disguise) {
            Damageable legDamage = (Damageable) legMeta;
            legDamage.setDamage(220);
        }
        leggings.setItemMeta(legMeta);

        EntityEquipment entityEquipment = livingEntity.getEquipment();
        assert entityEquipment != null;
        entityEquipment.setHelmet(helmet);
        entityEquipment.setChestplate(chestplate);
        entityEquipment.setLeggings(leggings);
        entityEquipment.setBoots(null);
        if (!disguise) {
            ItemMeta sword = weapon.getItemMeta();
            assert sword != null;
            sword.setDisplayName("Ice Warrior Dagger");
            sword.setCustomModelData(3);
            weapon.setItemMeta(sword);
            entityEquipment.setItemInMainHand(weapon);
            entityEquipment.setItemInOffHand(arm);
            entityEquipment.setItemInMainHandDropChance(0F);
            entityEquipment.setItemInOffHandDropChance(0F);
            entityEquipment.setHelmetDropChance(0F);
            entityEquipment.setChestplateDropChance(0F);
            entityEquipment.setLeggingsDropChance(0F);
            livingEntity.setCanPickupItems(false);
            livingEntity.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.iceWarrior, PersistentDataType.INTEGER, Monster.ICE_WARRIOR.getPersist());
        }
    }
}
