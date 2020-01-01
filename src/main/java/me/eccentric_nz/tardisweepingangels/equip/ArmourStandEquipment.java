package me.eccentric_nz.tardisweepingangels.equip;

/*
 *  Copyright 2014 eccentric_nz.
 */

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author eccentric_nz
 */
public class ArmourStandEquipment {

    private final TARDISWeepingAngels plugin;

    public ArmourStandEquipment(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    public void setAngelEquipment(ArmorStand as) {
        as.setSmall(false);
        as.setArms(true);
        ItemStack head = new ItemStack(Material.BRICK, 1);
        ItemStack arm = new ItemStack(Material.BRICK, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS, 1);
        chestplate.setDurability((short) 235);
        leggings.setDurability((short) 220);
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName("Weeping Angel Head");
        headMeta.setCustomModelData(4);
        head.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Weeping Angel Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Weeping Angel Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Weeping Angel Legs");
        leggings.setItemMeta(legMeta);
        ItemMeta waeponMeta = boots.getItemMeta();
        waeponMeta.setDisplayName("Weeping Angel Feet");
        boots.setItemMeta(waeponMeta);
        as.setHelmet(head);
        as.setChestplate(chestplate);
        as.setLeggings(leggings);
        as.setBoots(boots);
        as.getEquipment().setItemInMainHand(arm);
        as.getEquipment().setItemInOffHand(arm.clone());
        as.setVisible(false);
    }

    public void setWarriorEquipment(ArmorStand as) {
        as.setSmall(false);
        ItemStack helmet = new ItemStack(Material.SNOWBALL, 1);
        ItemStack arm = new ItemStack(Material.SNOWBALL, 1);
        ItemStack weapon = new ItemStack(Material.SNOWBALL, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        chestplate.setDurability((short) 235);
        leggings.setDurability((short) 220);
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Ice Warrior Head");
        headMeta.setCustomModelData(4);
        helmet.setItemMeta(headMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Ice Warrior Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Ice Warrior Legs");
        leggings.setItemMeta(legMeta);
        as.setHelmet(helmet);
        as.setChestplate(chestplate);
        as.setLeggings(leggings);
        as.setBoots(null);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Ice Warrior Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta sword = weapon.getItemMeta();
        sword.setDisplayName("Ice Warrior Dagger");
        sword.setCustomModelData(3);
        weapon.setItemMeta(sword);
        as.setItemInHand(weapon);
        as.getEquipment().setItemInOffHand(arm);
        as.setVisible(false);
    }

    public void setCyberEquipment(ArmorStand as) {
        as.setSmall(false);
        ItemStack helmet = new ItemStack(Material.IRON_INGOT, 1);
        ItemStack arm = new ItemStack(Material.IRON_INGOT, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        chestplate.setDurability((short) 235);
        leggings.setDurability((short) 220);
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Cyberman Head");
        headMeta.setCustomModelData(3);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Cyberman Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Cyberman Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Cyberman Legs");
        leggings.setItemMeta(legMeta);
        as.setHelmet(helmet);
        as.setChestplate(chestplate);
        as.setLeggings(leggings);
        as.setBoots(null);
        as.getEquipment().setItemInMainHand(arm);
        as.getEquipment().setItemInOffHand(arm.clone());
        as.setVisible(false);
    }

    public void setDalekEquipment(ArmorStand as) {
        as.setSmall(false);
        as.setArms(false);
        ItemStack helmet = new ItemStack(Material.MUSHROOM_STEM, 1);
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Dalek Head");
        headMeta.setCustomModelData(10000005 + plugin.getRandom().nextInt(16));
        helmet.setItemMeta(headMeta);
        as.setHelmet(helmet);
        as.setChestplate(null);
        as.setLeggings(null);
        as.setBoots(null);
        as.getEquipment().setItemInMainHand(null);
        as.getEquipment().setItemInOffHand(null);
        as.setVisible(false);
    }

    public void setEmptyChildEquipment(ArmorStand as) {
        as.setSmall(true);
        ItemStack helmet = new ItemStack(Material.SUGAR, 1);
        ItemStack arm = new ItemStack(Material.SUGAR, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        chestplate.setDurability((short) 235);
        leggings.setDurability((short) 220);
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Empty Child Head");
        headMeta.setCustomModelData(3);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Empty Child Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Empty Child Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Empty Child Legs");
        leggings.setItemMeta(legMeta);
        as.setChestplate(chestplate);
        as.setLeggings(leggings);
        as.setBoots(null);
        as.setHelmet(helmet);
        as.getEquipment().setItemInMainHand(arm);
        as.getEquipment().setItemInOffHand(arm.clone());
        as.setVisible(false);
    }

    public void setZygonEquipment(ArmorStand as) {
        as.setSmall(false);
        ItemStack helmet = new ItemStack(Material.PAINTING, 1);
        ItemStack arm = new ItemStack(Material.PAINTING, 1);
        ItemStack chestplate = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
        chestplate.setDurability((short) 235);
        leggings.setDurability((short) 220);
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Zygon Head");
        headMeta.setCustomModelData(3);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Zygon Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Zygon Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Zygon Legs");
        leggings.setItemMeta(legMeta);
        as.setChestplate(chestplate);
        as.setLeggings(leggings);
        as.setBoots(null);
        as.setHelmet(helmet);
        as.getEquipment().setItemInMainHand(arm);
        as.getEquipment().setItemInOffHand(arm.clone());
        as.setVisible(false);
    }

    public void setSilurianEquipment(ArmorStand as) {
        as.setSmall(false);
        as.setArms(true);
        ItemStack helmet = new ItemStack(Material.FEATHER, 1);
        ItemStack arm = new ItemStack(Material.FEATHER, 1);
        ItemStack chestplate = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
        chestplate.setDurability((short) 235);
        leggings.setDurability((short) 220);
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Silurian Head");
        headMeta.setCustomModelData(3);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Silurian Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Silurian Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Silurian Legs");
        leggings.setItemMeta(legMeta);
        as.setChestplate(chestplate);
        as.setLeggings(leggings);
        as.setBoots(null);
        as.setHelmet(helmet);
        ItemStack bow = new ItemStack(Material.BOW, 1);
        ItemMeta waeponMeta = bow.getItemMeta();
        waeponMeta.setDisplayName("Silurian Weapon");
        waeponMeta.setCustomModelData(3);
        bow.setItemMeta(waeponMeta);
        as.getEquipment().setItemInMainHand(bow);
        as.getEquipment().setItemInOffHand(arm);
        as.setVisible(false);
    }

    public void setSontaranEquipment(ArmorStand as) {
        as.setSmall(false);
        ItemStack helmet = new ItemStack(Material.POTATO, 1);
        ItemStack arm = new ItemStack(Material.POTATO, 1);
        ItemStack chestplate = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
        chestplate.setDurability((short) 235);
        leggings.setDurability((short) 220);
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Sontaran Head");
        headMeta.setCustomModelData(4);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Sontaran Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Sontaran Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Sontaran Legs");
        leggings.setItemMeta(legMeta);
        as.setChestplate(chestplate);
        as.setLeggings(leggings);
        as.setBoots(null);
        as.setHelmet(helmet);
        ItemStack sword = new ItemStack(Material.POTATO, 1);
        ItemMeta waeponMeta = sword.getItemMeta();
        waeponMeta.setDisplayName("Sontaran Weapon");
        waeponMeta.setCustomModelData(3);
        sword.setItemMeta(waeponMeta);
        as.getEquipment().setItemInMainHand(sword);
        as.getEquipment().setItemInOffHand(arm);
        as.setVisible(false);
    }

    public void setButlerEquipment(ArmorStand as) {
        as.setSmall(false);
        ItemStack helmet = new ItemStack(Material.BAKED_POTATO, 1);
        ItemStack arm = new ItemStack(Material.BAKED_POTATO, 1);
        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
        chestplate.setDurability((short) 235);
        leggings.setDurability((short) 220);
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Strax Head");
        headMeta.setCustomModelData(3);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Strax Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Strax Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Strax Legs");
        leggings.setItemMeta(legMeta);
        as.setChestplate(chestplate);
        as.setLeggings(leggings);
        as.setBoots(null);
        as.setHelmet(helmet);
        as.getEquipment().setItemInMainHand(arm);
        as.getEquipment().setItemInOffHand(arm.clone());
        as.setVisible(false);
    }

    public void setVashtaNeradaEquipment(ArmorStand as) {
        as.setSmall(false);
        ItemStack helmet = new ItemStack(Material.BOOK, 1);
        ItemStack arm = new ItemStack(Material.BOOK, 1);
        ItemStack chestplate = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
        chestplate.setDurability((short) 235);
        leggings.setDurability((short) 220);
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Vashta Nerada Head");
        headMeta.setCustomModelData(4);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Vashta Nerada Arm");
        armMeta.setCustomModelData(3);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Vashta Nerada Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Vashta Nerada Legs");
        leggings.setItemMeta(legMeta);
        as.setChestplate(chestplate);
        as.setLeggings(leggings);
        as.setBoots(null);
        as.setHelmet(helmet);
        as.getEquipment().setItemInMainHand(arm);
        as.getEquipment().setItemInOffHand(arm.clone());
        as.setVisible(false);
    }
}
