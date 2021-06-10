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
        assert headMeta != null;
        headMeta.setDisplayName(monster.getName() + " Head");
        headMeta.setCustomModelData(monster.getCustomModelData());
        head.setItemMeta(headMeta);
        setHelmetOnly(as, head);
    }

    private void setHelmetOnly(ArmorStand as, ItemStack is) {
        EntityEquipment ee = as.getEquipment();
        assert ee != null;
        ee.setChestplate(null);
        ee.setLeggings(null);
        ee.setBoots(null);
        ee.setHelmet(is);
        ee.setItemInMainHand(null);
        ee.setItemInOffHand(null);
        as.setVisible(false);
    }
}
