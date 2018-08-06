/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.utils;

import java.util.ArrayList;
import java.util.List;

/**
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

    static final List<Monster> values = new ArrayList<>();

    static {
        values.add(CYBERMAN);
        values.add(DALEK);
        values.add(EMPTY_CHILD);
        values.add(ICE_WARRIOR);
        values.add(SILENT);
        values.add(SILURIAN);
        values.add(SONTARAN);
        values.add(STRAX);
        values.add(VASHTA_NERADA);
        values.add(WEEPING_ANGEL);
        values.add(ZYGON);
    }

    public String name;

    private Monster(String name) {
        this.name = name;
    }

    public static List<Monster> getValues() {
        return values;
    }

    public String getName() {
        return name;
    }
}
