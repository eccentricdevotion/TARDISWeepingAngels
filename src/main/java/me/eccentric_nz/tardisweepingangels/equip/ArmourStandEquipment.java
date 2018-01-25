package me.eccentric_nz.tardisweepingangels.equip;

/*
 *  Copyright 2014 eccentric_nz.
 */
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author eccentric_nz
 */
public class ArmourStandEquipment {

    public void setAngelEquipment(ArmorStand as) {
        as.setSmall(false);
        as.setArms(true);
        ItemStack wing = new ItemStack(Material.BARRIER, 1);
        ItemStack head = new ItemStack(Material.SKELETON_SKULL, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS, 1);
        chestplate.setDurability((short) 235);
        leggings.setDurability((short) 220);
        ItemMeta hmeta = head.getItemMeta();
        hmeta.setDisplayName("Weeping Angel Head");
        head.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Weeping Angel Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Weeping Angel Legs");
        leggings.setItemMeta(lmeta);
        ItemMeta bmeta = boots.getItemMeta();
        bmeta.setDisplayName("Weeping Angel Feet");
        boots.setItemMeta(bmeta);
        as.setHelmet(head);
        as.setChestplate(chestplate);
        as.setLeggings(leggings);
        as.setBoots(boots);
        as.setItemInHand(wing);
        as.getEquipment().setItemInOffHand(wing);
    }

    public void setWarriorEquipment(ArmorStand as) {
        as.setSmall(false);
        ItemStack helmet = new ItemStack(Material.IRON_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemStack weapon = new ItemStack(Material.IRON_SWORD, 1);
        helmet.setDurability((short) 160);
        chestplate.setDurability((short) 235);
        leggings.setDurability((short) 220);
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Ice Warrior Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Ice Warrior Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Ice Warrior Legs");
        leggings.setItemMeta(lmeta);
        as.setHelmet(helmet);
        as.setChestplate(chestplate);
        as.setLeggings(leggings);
        as.setBoots(null);
        ItemMeta sword = weapon.getItemMeta();
        sword.setDisplayName("Ice Warrior Dagger");
        weapon.setItemMeta(sword);
        as.setItemInHand(weapon);
        as.getEquipment().setItemInOffHand(null);
    }

    public void setCyberEquipment(ArmorStand as) {
        as.setSmall(false);
        ItemStack helmet = new ItemStack(Material.IRON_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        helmet.setDurability((short) 160);
        chestplate.setDurability((short) 235);
        leggings.setDurability((short) 220);
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Cyberman Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Cyberman Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Cyberman Legs");
        leggings.setItemMeta(lmeta);
        as.setHelmet(helmet);
        as.setChestplate(chestplate);
        as.setLeggings(leggings);
        as.setBoots(null);
        as.setItemInHand(null);
        as.getEquipment().setItemInOffHand(null);
    }

    public void setEmptyChildEquipment(ArmorStand as) {
        as.setSmall(true);
        ItemStack helmet = new ItemStack(Material.IRON_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        helmet.setDurability((short) 160);
        chestplate.setDurability((short) 235);
        leggings.setDurability((short) 220);
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Empty Child Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Empty Child Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Empty Child Legs");
        leggings.setItemMeta(lmeta);
        as.setChestplate(chestplate);
        as.setLeggings(leggings);
        as.setBoots(null);
        as.setHelmet(helmet);
        as.setItemInHand(null);
        as.getEquipment().setItemInOffHand(null);
    }

    public void setZygonEquipment(ArmorStand as) {
        as.setSmall(false);
        ItemStack helmet = new ItemStack(Material.GOLDEN_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
        helmet.setDurability((short) 160);
        chestplate.setDurability((short) 235);
        leggings.setDurability((short) 220);
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Zygon Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Zygon Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Zygon Legs");
        leggings.setItemMeta(lmeta);
        as.setChestplate(chestplate);
        as.setLeggings(leggings);
        as.setBoots(null);
        as.setHelmet(helmet);
        as.setItemInHand(null);
        as.getEquipment().setItemInOffHand(null);
    }

    public void setSilurianEquipment(ArmorStand as) {
        as.setSmall(false);
        as.setArms(true);
        ItemStack helmet = new ItemStack(Material.GOLDEN_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
        helmet.setDurability((short) 160);
        chestplate.setDurability((short) 235);
        leggings.setDurability((short) 220);
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Silurian Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Silurian Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Silurian Legs");
        leggings.setItemMeta(lmeta);
        as.setChestplate(chestplate);
        as.setLeggings(leggings);
        as.setBoots(null);
        as.setHelmet(helmet);
        ItemStack bow = new ItemStack(Material.BOW, 1);
        ItemMeta bmeta = bow.getItemMeta();
        bmeta.setDisplayName("Silurian Weapon");
        bow.setItemMeta(bmeta);
        as.setItemInHand(bow);
        as.getEquipment().setItemInOffHand(null);
    }

    public void setSontaranEquipment(ArmorStand as) {
        as.setSmall(false);
        ItemStack helmet = new ItemStack(Material.GOLDEN_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
        helmet.setDurability((short) 160);
        chestplate.setDurability((short) 235);
        leggings.setDurability((short) 220);
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Sontaran Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Sontaran Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Sontaran Legs");
        leggings.setItemMeta(lmeta);
        as.setChestplate(chestplate);
        as.setLeggings(leggings);
        as.setBoots(null);
        as.setHelmet(helmet);
        ItemStack sword = new ItemStack(Material.STONE_SWORD, 1);
        ItemMeta bmeta = sword.getItemMeta();
        bmeta.setDisplayName("Sontaran Weapon");
        sword.setItemMeta(bmeta);
        as.setItemInHand(sword);
        as.getEquipment().setItemInOffHand(null);
    }

    public void setButlerEquipment(ArmorStand as) {
        as.setSmall(false);
        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
        helmet.setDurability((short) 160);
        chestplate.setDurability((short) 235);
        leggings.setDurability((short) 220);
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Strax Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Strax Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Strax Legs");
        leggings.setItemMeta(lmeta);
        as.setChestplate(chestplate);
        as.setLeggings(leggings);
        as.setBoots(null);
        as.setHelmet(helmet);
        as.setItemInHand(null);
        as.getEquipment().setItemInOffHand(null);
    }

    public void setVashtaNeradaEquipment(ArmorStand as) {
        as.setSmall(false);
        ItemStack helmet = new ItemStack(Material.GOLDEN_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
        helmet.setDurability((short) 160);
        chestplate.setDurability((short) 235);
        leggings.setDurability((short) 220);
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Vashta Nerada Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Vashta Nerada Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Vashta Nerada Legs");
        leggings.setItemMeta(lmeta);
        as.setChestplate(chestplate);
        as.setLeggings(leggings);
        as.setBoots(null);
        as.setHelmet(helmet);
        as.setItemInHand(null);
        as.getEquipment().setItemInOffHand(null);
    }
}
