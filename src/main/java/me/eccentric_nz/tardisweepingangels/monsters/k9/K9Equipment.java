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
package me.eccentric_nz.tardisweepingangels.monsters.k9;

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

public class K9Equipment {

    public static void set(Player player, Entity entity, boolean disguise) {
        ItemStack head = new ItemStack(Material.BONE);
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName("K9 Head");
        headMeta.setCustomModelData(1);
        head.setItemMeta(headMeta);
        if (entity instanceof ArmorStand armorStand && player != null) {
            UUID uuid;
            if (player != null) {
                uuid = player.getUniqueId();
            } else {
                uuid = TardisWeepingAngelsPlugin.unclaimed;
            }
            armorStand.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.k9, PersistentDataType.INTEGER, 0);
            armorStand.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.ownerUuid, TardisWeepingAngelsPlugin.persistentDataTypeUuid, uuid);
            EntityEquipment entityEquipment = armorStand.getEquipment();
            entityEquipment.setHelmet(head);
            entityEquipment.setItemInMainHand(null);
            entityEquipment.setItemInOffHand(null);
            armorStand.setVisible(false);
            armorStand.setSilent(true);
            armorStand.setCollidable(true);
        } else if (disguise) {
            Player k9 = (Player) entity;
            k9.getInventory().setHelmet(head);
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false);
            k9.addPotionEffect(potionEffect);
        }
    }
}
