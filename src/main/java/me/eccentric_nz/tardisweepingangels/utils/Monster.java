/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.utils;

/**
 *
 * @author eccentric_nz
 */
public enum Monster {

    ANGEL("Weeping Angel"),
    CHILD("Empty Child"),
    CYBERMAN("Cyberman"),
    DALEK("Dalek"),
    EMPTY("Empty Child"),
    EMPTY_CHILD("Empty Child"),
    ICE("Ice Warrior"),
    ICE_WARRIOR("Ice Warrior"),
    SILENT("Silent"),
    SILURIAN("Silurian"),
    SONTARAN("Sontaran"),
    STRAX("Strax"),
    VASHTA("Vashta Nerada"),
    VASHTA_NERADA("Vashta Nerada"),
    WARRIOR("Ice Warrior"),
    WEEPING_ANGEL("Weeping Angel"),
    ZYGON("Zygon");

    public String name;

    private Monster(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
