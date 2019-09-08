package me.eccentric_nz.tardisweepingangels.monsters.daleks;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class WeightedChoice<E> {

    private final NavigableMap<Double, E> map = new TreeMap<>();
    private final Random random;
    private double total = 0;

    public WeightedChoice() {
        this(new Random());
    }

    public WeightedChoice(Random random) {
        this.random = random;
    }

    public WeightedChoice<E> add(double weight, E result) {
        if (weight <= 0) {
            return this;
        }
        total += weight;
        map.put(total, result);
        return this;
    }

    public E next() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }
}
