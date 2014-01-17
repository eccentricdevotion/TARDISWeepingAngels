/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 *
 * @author eccentric_nz
 */
public class TARDISWeepingAngelEquipment {

    private final ItemStack helmet;
    private final ItemStack chestplate;
    private final ItemStack leggings;
    private final ItemStack boots;
    private final ItemStack weapon;

    public TARDISWeepingAngelEquipment() {
        this.helmet = new ItemStack(Material.WATER_LILY, 1);
        ItemStack c = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta cmeta = (LeatherArmorMeta) c.getItemMeta();
        cmeta.setColor(Color.fromRGB(143, 143, 143));
        c.setItemMeta(cmeta);
        this.chestplate = c;
        ItemStack l = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta lmeta = (LeatherArmorMeta) l.getItemMeta();
        lmeta.setColor(Color.fromRGB(143, 143, 143));
        l.setItemMeta(cmeta);
        this.leggings = l;
        ItemStack b = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta bmeta = (LeatherArmorMeta) b.getItemMeta();
        bmeta.setColor(Color.fromRGB(143, 143, 143));
        b.setItemMeta(cmeta);
        this.boots = b;
        this.weapon = new ItemStack(Material.AIR, 1);
    }

    public void setEquipment(LivingEntity le) {
        EntityEquipment ee = le.getEquipment();
        ee.setHelmet(helmet);
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(boots);
        ee.setItemInHand(weapon);
        ee.setHelmetDropChance(0F);
        ee.setChestplateDropChance(0F);
        ee.setLeggingsDropChance(0F);
        ee.setBootsDropChance(0F);
    }
}
