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
package me.eccentric_nz.tardisweepingangels.equip;

import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author eccentric_nz
 */
public class ArmourStandEquipment {

    public void setStandEquipment(ArmorStand armorStand, Monster monster, boolean small) {
        armorStand.setSmall(small);
        armorStand.setArms(false);
        ItemStack head = switch (monster) {
            case CYBERMAN -> new ItemStack(Material.IRON_INGOT, 1);
            case DALEK -> new ItemStack(Material.SLIME_BALL, 1);
            case EMPTY_CHILD -> new ItemStack(Material.SUGAR, 1);
            case HATH -> new ItemStack(Material.PUFFERFISH, 1);
            case ICE_WARRIOR -> new ItemStack(Material.SNOWBALL, 1);
            case JUDOON -> new ItemStack(Material.YELLOW_DYE, 1);
            case K9 -> new ItemStack(Material.BONE, 1);
            case OOD -> new ItemStack(Material.ROTTEN_FLESH, 1);
            case SILENT -> new ItemStack(Material.END_STONE, 1);
            case SILURIAN -> new ItemStack(Material.FEATHER, 1);
            case SONTARAN -> new ItemStack(Material.POTATO, 1);
            case STRAX -> new ItemStack(Material.BAKED_POTATO, 1);
            case TOCLAFANE -> new ItemStack(Material.GUNPOWDER, 1);
            case VASHTA_NERADA -> new ItemStack(Material.BOOK, 1);
            case WEEPING_ANGEL -> new ItemStack(Material.BRICK, 1);
            default -> // ZYGON
                    new ItemStack(Material.PAINTING, 1);
        };
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName(monster.getName() + " Head");
        headMeta.setCustomModelData(monster.getCustomModelData());
        head.setItemMeta(headMeta);
        setHelmetOnly(armorStand, head);
    }

    private void setHelmetOnly(ArmorStand armorStand, ItemStack itemStack) {
        EntityEquipment entityEquipment = armorStand.getEquipment();
        entityEquipment.setChestplate(null);
        entityEquipment.setLeggings(null);
        entityEquipment.setBoots(null);
        entityEquipment.setHelmet(itemStack);
        entityEquipment.setItemInMainHand(null);
        entityEquipment.setItemInOffHand(null);
        armorStand.setVisible(false);
    }
}
