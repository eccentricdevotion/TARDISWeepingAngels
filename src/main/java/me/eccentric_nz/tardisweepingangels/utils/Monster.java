/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.utils;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

/**
 * @author eccentric_nz
 */
public enum Monster {

    CYBERMAN("Cyberman", "cyberman", 2, 4, Material.IRON_INGOT, TARDISWeepingAngels.CYBERMAN),
    DALEK("Dalek", "dalek", 3, 10000005, Material.SLIME_BALL, TARDISWeepingAngels.DALEK, 10000004),
    EMPTY_CHILD("Empty Child", "empty", 4, 4, Material.SUGAR, TARDISWeepingAngels.EMPTY),
    HATH("Hath", "hath", 16, 5, Material.PUFFERFISH, TARDISWeepingAngels.HATH, 4),
    HEADLESS_MONK("Headless Monk", "monk", 17, 4, Material.RED_CANDLE, TARDISWeepingAngels.MONK),
    ICE_WARRIOR("Ice Warrior", "ice", 5, 5, Material.SNOWBALL, TARDISWeepingAngels.WARRIOR, 4),
    JUDOON("Judoon", "judoon", 14, 10, Material.YELLOW_DYE, TARDISWeepingAngels.JUDOON, 11),
    K9("K9", "k9", 15, 1, Material.BONE, TARDISWeepingAngels.K9),
    OOD("Ood", "ood", 12, 29, Material.ROTTEN_FLESH, TARDISWeepingAngels.OOD, 30),
    SILENT("Silent", "silent", 6, 11, Material.END_STONE, TARDISWeepingAngels.SILENT, 5),
    SILURIAN("Silurian", "silurian", 7, 4, Material.FEATHER, TARDISWeepingAngels.SILURIAN),
    SONTARAN("Sontaran", "sontaran", 8, 5, Material.POTATO, TARDISWeepingAngels.SONTARAN, 4),
    STRAX("Strax", "strax", 9, 4, Material.BAKED_POTATO, TARDISWeepingAngels.STRAX),
    TOCLAFANE("Toclafane", "toclafane", 13, 2, Material.GUNPOWDER, TARDISWeepingAngels.TOCLAFANE),
    VASHTA_NERADA("Vashta Nerada", "vashta", 10, 5, Material.BOOK, TARDISWeepingAngels.VASHTA, 4),
    WEEPING_ANGEL("Weeping Angel", "angel", 1, 5, Material.BRICK, TARDISWeepingAngels.ANGEL),
    ZYGON("Zygon", "zygon", 11, 4, Material.PAINTING, TARDISWeepingAngels.ZYGON);

    private final String name;
    private final String permission;
    private final int persist;
    private final int customModelData;
    private final Material material;
    private final NamespacedKey key;
    private final int headModelData;

    Monster(String name, String permission, int persist, int customModelData, Material material, NamespacedKey key) {
        this.name = name;
        this.permission = permission;
        this.persist = persist;
        this.customModelData = customModelData;
        this.material = material;
        this.key = key;
        this.headModelData = 3;
    }

    Monster(String name, String permission, int persist, int customModelData, Material material, NamespacedKey key, int headModelData) {
        this.name = name;
        this.permission = permission;
        this.persist = persist;
        this.customModelData = customModelData;
        this.material = material;
        this.key = key;
        this.headModelData = headModelData;
    }
    
    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public int getPersist() {
        return persist;
    }

    public int getCustomModelData() {
        return (this == DALEK) ? customModelData + TARDISWeepingAngels.random.nextInt(16) : customModelData;
    }

    public Material getMaterial() {
        return material;
    }

    public NamespacedKey getKey() {
        return key;
    }
    
    public int getHeadModelData() {
        return headModelData;
    }
}
