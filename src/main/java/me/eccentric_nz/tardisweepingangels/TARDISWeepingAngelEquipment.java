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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author eccentric_nz
 */
public class TARDISWeepingAngelEquipment {

    public void setAngelEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.WATER_LILY, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS, 1);
        if (disguise) {
            chestplate.setDurability((short) 75);
            leggings.setDurability((short) 70);
            boots.setDurability((short) 60);
        }
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Weeping Angel Wing");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Weeping Angel Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Weeping Angel Legs");
        leggings.setItemMeta(lmeta);
        ItemMeta bmeta = boots.getItemMeta();
        bmeta.setDisplayName("Weeping Angel Feet");
        boots.setItemMeta(bmeta);
        EntityEquipment ee = le.getEquipment();
        ee.setHelmet(helmet);
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(boots);
        if (!disguise) {
            ee.setItemInHand(null);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            ee.setBootsDropChance(0F);
        }
    }

    public void setWarriorEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.IRON_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemStack weapon = new ItemStack(Material.IRON_SWORD, 1);
        if (disguise) {
            helmet.setDurability((short) 160);
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Ice Warrior Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Ice Warrior Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Ice Warrior Legs");
        leggings.setItemMeta(lmeta);

        EntityEquipment ee = le.getEquipment();
        ee.setHelmet(helmet);
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        if (!disguise) {
            ItemMeta sword = weapon.getItemMeta();
            sword.setDisplayName("Ice Warrior Dagger");
            weapon.setItemMeta(sword);
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
        leggings.setItemMeta(lmeta);
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
        if (disguise) {
            helmet.setDurability((short) 160);
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Cyberman Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Cyberman Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Cyberman Legs");
        leggings.setItemMeta(lmeta);
        EntityEquipment ee = le.getEquipment();
        ee.setHelmet(helmet);
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
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
        leggings.setItemMeta(lmeta);
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

    public void setEmptyChildEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.IRON_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        if (disguise) {
            helmet.setDurability((short) 160);
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Empty Child Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Empty Child Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Empty Child Legs");
        leggings.setItemMeta(lmeta);

        EntityEquipment ee = le.getEquipment();
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        ee.setHelmet(helmet);
        if (!disguise) {
            PotionEffect p = new PotionEffect(PotionEffectType.SLOW, 36000, 1);
            le.removePotionEffect(PotionEffectType.SPEED);
            le.addPotionEffect(p);
            ee.setItemInHand(null);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
        }
    }

    public void setZygonEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.GOLD_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.GOLD_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLD_LEGGINGS, 1);
        if (disguise) {
            helmet.setDurability((short) 160);
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Zygon Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Zygon Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Zygon Legs");
        leggings.setItemMeta(lmeta);

        EntityEquipment ee = le.getEquipment();
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        ee.setHelmet(helmet);
        if (!disguise) {
            ee.setItemInHand(null);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
        }
    }

    public void setSilurianEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.GOLD_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.GOLD_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLD_LEGGINGS, 1);
        if (disguise) {
            helmet.setDurability((short) 160);
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Silurian Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Silurian Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Silurian Legs");
        leggings.setItemMeta(lmeta);

        EntityEquipment ee = le.getEquipment();
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        ee.setHelmet(helmet);
        if (!disguise) {
            ItemStack bow = new ItemStack(Material.BOW, 1);
            ItemMeta bmeta = bow.getItemMeta();
            bmeta.setDisplayName("Silurian Weapon");
            bow.setItemMeta(bmeta);
            ee.setItemInHand(bow);
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
