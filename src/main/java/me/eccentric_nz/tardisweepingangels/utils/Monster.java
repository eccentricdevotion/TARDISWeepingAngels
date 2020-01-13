/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.utils;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;

/**
 * @author eccentric_nz
 */
public enum Monster {

    CYBERMAN("Cyberman", "cyberman", 2, 4),
    DALEK("Dalek", "dalek", 3, 10000005),
    EMPTY_CHILD("Empty Child", "empty", 4, 4),
    ICE_WARRIOR("Ice Warrior", "ice", 5, 5),
    JUDOON("Judoon", "judoon", 14, 10),
    K9("K9", "k9", 15, 1),
    OOD("Ood", "ood", 12, 29),
    SILENT("Silent", "silent", 6, 3),
    SILURIAN("Silurian", "silurian", 7, 4),
    SONTARAN("Sontaran", "sontaran", 8, 5),
    STRAX("Strax", "strax", 9, 4),
    TOCLAFANE("Toclafane", "toclafane", 13, 2),
    VASHTA_NERADA("Vashta Nerada", "vashta", 10, 5),
    WEEPING_ANGEL("Weeping Angel", "angel", 1, 5),
    ZYGON("Zygon", "zygon", 11, 4);

    public String name;
    public String permission;
    public int persist;
    public int customModelData;

    Monster(String name, String permission, int persist, int customModelData) {
        this.name = name;
        this.permission = permission;
        this.persist = persist;
        this.customModelData = customModelData;
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
}
