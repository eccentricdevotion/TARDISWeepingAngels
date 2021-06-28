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
package me.eccentric_nz.tardisweepingangels.monsters.silent;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class SilentEquipment {

    public static void set(LivingEntity livingEntity, boolean disguise) {
        if (!disguise) {
            LivingEntity guardian = (LivingEntity) Objects.requireNonNull(livingEntity.getLocation().getWorld()).spawnEntity(livingEntity.getLocation(), EntityType.GUARDIAN);
            guardian.setSilent(true);
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false);
            guardian.addPotionEffect(potionEffect);
            guardian.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.silent, PersistentDataType.INTEGER, Monster.SILENT.getPersist());
            livingEntity.addPassenger(guardian);
            livingEntity.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.silent, PersistentDataType.INTEGER, Monster.SILENT.getPersist());
        } else {
            ItemStack head = new ItemStack(Material.END_STONE);
            ItemMeta headMeta = head.getItemMeta();
            assert headMeta != null;
            headMeta.setDisplayName("Silent Head");
            headMeta.setCustomModelData((TardisWeepingAngelsPlugin.random.nextBoolean()) ? 3 : 2);
            head.setItemMeta(headMeta);
            Player player = (Player) livingEntity;
            player.getInventory().setHelmet(head);
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false);
            player.addPotionEffect(potionEffect);
        }
    }
}
