/*
 * Copyright (C) 2023 eccentric_nz
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
package me.eccentric_nz.tardisweepingangels.equip;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author macgeek
 */
public class Equipper {

    private final Monster monster;
    private final LivingEntity le;
    private final boolean disguise;
    private final boolean bow;

    public Equipper(Monster monster, LivingEntity le, boolean disguise, boolean bow) {
        this.monster = monster;
        this.le = le;
        this.disguise = disguise;
        this.bow = bow;
    }

    public void setHelmetAndInvisibilty() {
        // make an monster item
        ItemStack helmet = new ItemStack(monster.getMaterial(), 1);
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName(monster.getName() + " Head");
        // 9 = static model
        headMeta.setCustomModelData(9);
        helmet.setItemMeta(headMeta);
        // set equipment
        EntityEquipment ee = le.getEquipment();
        // make sure the monster doesn't spawn with any armour
        ee.setChestplate(null);
        ee.setLeggings(null);
        ee.setBoots(null);
        // set the helmet to the static monster model
        ee.setHelmet(helmet);
        // make the entity invisible
        Bukkit.getScheduler().scheduleSyncDelayedTask(TARDISWeepingAngels.plugin, () -> {
            PotionEffect invisibilty = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false);
            le.addPotionEffect(invisibilty);
        });
        if (!disguise) {
            // make sure the monster doesn't spawn with items in hand unless should have a bow
            if (bow) {
                // invisible bow
                ItemStack bow = new ItemStack(Material.BOW, 1);
                ItemMeta bim = bow.getItemMeta();
                bim.setCustomModelData(1);
                bow.setItemMeta(bim);
                ee.setItemInMainHand(bow);
            } else {
                ee.setItemInMainHand(null);
            }
            ee.setItemInOffHand(null);
            // don't drop items when killed
            ee.setItemInMainHandDropChance(0);
            ee.setItemInOffHandDropChance(0);
            ee.setHelmetDropChance(0);
            // don't pickup items
            le.setCanPickupItems(false);
            // set TWA data
            le.getPersistentDataContainer().set(monster.getKey(), PersistentDataType.INTEGER, monster.getPersist());
        }
    }
}
