/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.utils;

/**
 * @author eccentric_nz
 */
public enum Monster {

    CYBERMAN("Cyberman", "cyberman", 2),
    DALEK("Dalek", "dalek", 3),
    EMPTY_CHILD("Empty Child", "empty", 4),
    ICE_WARRIOR("Ice Warrior", "ice", 5),
    JUDOON("Judoon", "judoon", 14),
    K9("K9", "k9", 15),
    OOD("Ood", "ood", 12),
    SILENT("Silent", "silent", 6),
    SILURIAN("Silurian", "silurian", 7),
    SONTARAN("Sontaran", "sontaran", 8),
    STRAX("Strax", "strax", 9),
    TOCLAFANE("Toclafane", "toclafane", 13),
    VASHTA_NERADA("Vashta Nerada", "vashta", 10),
    WEEPING_ANGEL("Weeping Angel", "angel", 1),
    ZYGON("Zygon", "zygon", 11);

    public String name;
    public String permission;
    public int persist;

    Monster(String name, String permission, int persist) {
        this.name = name;
        this.permission = permission;
        this.persist = persist;
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
}
