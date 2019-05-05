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

    ANGEL("Weeping Angel", 1),
    CHILD("Empty Child", 4),
    CYBERMAN("Cyberman", 2),
    DALEK("Dalek", 3),
    EMPTY("Empty Child", 4),
    EMPTY_CHILD("Empty Child", 4),
    ICE("Ice Warrior", 5),
    ICE_WARRIOR("Ice Warrior", 5),
    SILENT("Silent", 6),
    SILURIAN("Silurian", 7),
    SONTARAN("Sontaran", 8),
    STRAX("Strax", 9),
    VASHTA("Vashta Nerada", 10),
    VASHTA_NERADA("Vashta Nerada", 10),
    WARRIOR("Ice Warrior", 5),
    WEEPING_ANGEL("Weeping Angel", 1),
    ZYGON("Zygon", 11);

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
    public int persist;

    Monster(String name, int persist) {
        this.name = name;
    }

    public static List<Monster> getValues() {
        return values;
    }

    public String getName() {
        return name;
    }

    public int getPersist() {
        return persist;
    }
}
