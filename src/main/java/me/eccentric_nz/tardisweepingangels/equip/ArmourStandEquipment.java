package me.eccentric_nz.tardisweepingangels.equip;

/*
 *  Copyright 2014 eccentric_nz.
 */

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

    public void setStandEquipment(ArmorStand as, Monster monster, boolean small) {
        as.setSmall(small);
        as.setArms(false);
        ItemStack head;
        switch (monster) {
            case CYBERMAN:
                head = new ItemStack(Material.IRON_INGOT, 1);
                break;
            case DALEK:
                head = new ItemStack(Material.SLIME_BALL, 1);
                break;
            case EMPTY_CHILD:
                head = new ItemStack(Material.SUGAR, 1);
                break;
            case ICE_WARRIOR:
                head = new ItemStack(Material.SNOWBALL, 1);
                break;
            case JUDOON:
                head = new ItemStack(Material.YELLOW_DYE, 1);
                break;
            case K9:
                head = new ItemStack(Material.BONE, 1);
                break;
            case OOD:
                head = new ItemStack(Material.ROTTEN_FLESH, 1);
                break;
            case SILENT:
                head = new ItemStack(Material.END_STONE, 1);
                break;
            case SILURIAN:
                head = new ItemStack(Material.FEATHER, 1);
                break;
            case SONTARAN:
                head = new ItemStack(Material.POTATO, 1);
                break;
            case STRAX:
                head = new ItemStack(Material.BAKED_POTATO, 1);
                break;
            case TOCLAFANE:
                head = new ItemStack(Material.GUNPOWDER, 1);
                break;
            case VASHTA_NERADA:
                head = new ItemStack(Material.BOOK, 1);
                break;
            case WEEPING_ANGEL:
                head = new ItemStack(Material.BRICK, 1);
                break;
            default: // ZYGON
                head = new ItemStack(Material.PAINTING, 1);
                break;
        }
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName(monster.getName() + " Head");
        headMeta.setCustomModelData(monster.getCustomModelData());
        head.setItemMeta(headMeta);
        setHelmetOnly(as, head);
    }

    private void setHelmetOnly(ArmorStand as, ItemStack is) {
        EntityEquipment ee = as.getEquipment();
        ee.setChestplate(null);
        ee.setLeggings(null);
        ee.setBoots(null);
        ee.setHelmet(is);
        ee.setItemInMainHand(null);
        ee.setItemInOffHand(null);
        as.setVisible(false);
    }
}
