/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 *
 * @author eccentric_nz
 */
public class TARDISWeepingAngelEquipment {

    public void setAngelEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.WATER_LILY, 1);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta cmeta = (LeatherArmorMeta) chestplate.getItemMeta();
        cmeta.setColor(Color.fromRGB(143, 143, 143));
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta lmeta = (LeatherArmorMeta) leggings.getItemMeta();
        lmeta.setColor(Color.fromRGB(143, 143, 143));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta bmeta = (LeatherArmorMeta) boots.getItemMeta();
        bmeta.setColor(Color.fromRGB(143, 143, 143));
        ItemStack weapon = new ItemStack(Material.AIR, 1);
        if (disguise) {
            ItemMeta him = helmet.getItemMeta();
            him.setDisplayName("Weeping Angel Wing");
            helmet.setItemMeta(him);
            cmeta.setDisplayName("Weeping Angel Chest");
            lmeta.setDisplayName("Weeping Angel Legs");
            bmeta.setDisplayName("Weeping Angel Feet");
            chestplate.setDurability((short) 75);
            leggings.setDurability((short) 70);
            boots.setDurability((short) 60);
        }
        chestplate.setItemMeta(cmeta);
        leggings.setItemMeta(cmeta);
        boots.setItemMeta(cmeta);

        EntityEquipment ee = le.getEquipment();
        ee.setHelmet(helmet);
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(boots);
        if (!disguise) {
            ee.setItemInHand(weapon);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            ee.setBootsDropChance(0F);
        }
    }

    public void setWarriorEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
        ItemStack weapon = new ItemStack(Material.FIREWORK_CHARGE, 1);
        ItemMeta him = helmet.getItemMeta();
        him.setDisplayName("Ice Warrior Head");
        helmet.setItemMeta(him);
        if (disguise) {
            helmet.setDurability((short) 160);
            ItemMeta cmeta = helmet.getItemMeta();
            cmeta.setDisplayName("Ice Warrior Chest");
            chestplate.setItemMeta(cmeta);
            chestplate.setDurability((short) 235);
            ItemMeta lmeta = helmet.getItemMeta();
            lmeta.setDisplayName("Ice Warrior Legs");
            leggings.setItemMeta(cmeta);
            leggings.setDurability((short) 220);
        }
        EntityEquipment ee = le.getEquipment();
        ee.setHelmet(helmet);
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        if (!disguise) {
            ee.setItemInHand(weapon);
            ee.setItemInHandDropChance(0F);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
        }
    }

    public void setWarriorLeatherEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);
        LeatherArmorMeta hmeta = (LeatherArmorMeta) helmet.getItemMeta();
        hmeta.setColor(Color.fromRGB(51, 102, 51));
        hmeta.setDisplayName("Ice Warrior Head");
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta cmeta = (LeatherArmorMeta) chestplate.getItemMeta();
        cmeta.setColor(Color.fromRGB(51, 102, 51));
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta lmeta = (LeatherArmorMeta) leggings.getItemMeta();
        lmeta.setColor(Color.fromRGB(51, 102, 51));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta bmeta = (LeatherArmorMeta) boots.getItemMeta();
        bmeta.setColor(Color.fromRGB(51, 102, 51));
        ItemStack weapon = new ItemStack(Material.AIR, 1);
        if (disguise) {
            helmet.setDurability((short) 50);
            cmeta.setDisplayName("Ice Warrior Chest");
            chestplate.setDurability((short) 75);
            lmeta.setDisplayName("Ice Warrior Legs");
            leggings.setDurability((short) 70);
            bmeta.setDisplayName("Ice Warrior Feet");
            boots.setDurability((short) 60);
        }
        helmet.setItemMeta(hmeta);
        chestplate.setItemMeta(cmeta);
        leggings.setItemMeta(cmeta);
        boots.setItemMeta(bmeta);

        EntityEquipment ee = le.getEquipment();
        ee.setHelmet(helmet);
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(boots);
        if (!disguise) {
            ee.setItemInHand(weapon);
            ee.setItemInHandDropChance(0F);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
        }
    }

    public void setCyberEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.IRON_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemMeta him = helmet.getItemMeta();
        him.setDisplayName("Cyberman Head");
        if (disguise) {
            helmet.setDurability((short) 160);
            ItemMeta cmeta = helmet.getItemMeta();
            cmeta.setDisplayName("Cyberman Chest");
            chestplate.setItemMeta(cmeta);
            chestplate.setDurability((short) 235);
            ItemMeta lmeta = helmet.getItemMeta();
            lmeta.setDisplayName("Cyberman Legs");
            leggings.setItemMeta(cmeta);
            leggings.setDurability((short) 220);
        }
        helmet.setItemMeta(him);
        EntityEquipment ee = le.getEquipment();
        ee.setHelmet(helmet);
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        if (!disguise) {
            ee.setItemInHandDropChance(0F);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
        }
    }

    public void setCyberLeatherEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);
        LeatherArmorMeta hmeta = (LeatherArmorMeta) helmet.getItemMeta();
        hmeta.setColor(Color.fromRGB(255, 255, 255));
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta cmeta = (LeatherArmorMeta) chestplate.getItemMeta();
        cmeta.setColor(Color.fromRGB(255, 255, 255));
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta lmeta = (LeatherArmorMeta) leggings.getItemMeta();
        lmeta.setColor(Color.fromRGB(255, 255, 255));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta bmeta = (LeatherArmorMeta) boots.getItemMeta();
        bmeta.setColor(Color.fromRGB(255, 255, 255));
        hmeta.setDisplayName("Cyberman Head");
        if (disguise) {
            helmet.setDurability((short) 50);
            cmeta.setDisplayName("Cyberman Chest");
            chestplate.setDurability((short) 75);
            lmeta.setDisplayName("Cyberman Legs");
            leggings.setDurability((short) 70);
            bmeta.setDisplayName("Cyberman Feet");
            boots.setDurability((short) 60);
        }
        helmet.setItemMeta(hmeta);
        chestplate.setItemMeta(cmeta);
        leggings.setItemMeta(cmeta);
        boots.setItemMeta(bmeta);

        EntityEquipment ee = le.getEquipment();
        ee.setHelmet(helmet);
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(boots);
        if (!disguise) {
            ee.setItemInHandDropChance(0F);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
        }
    }

    public void removeEquipment(Player p) {
        PlayerInventory inv = p.getInventory();
        inv.setHelmet(null);
        inv.setChestplate(null);
        inv.setLeggings(null);
        inv.setBoots(null);
    }
}
