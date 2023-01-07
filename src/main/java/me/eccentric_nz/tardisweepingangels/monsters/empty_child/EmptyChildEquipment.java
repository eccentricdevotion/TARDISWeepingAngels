package me.eccentric_nz.tardisweepingangels.monsters.empty_child;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EmptyChildEquipment {

    public static void setSpeed(LivingEntity le) {
            PotionEffect p = new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1, true, false);
            le.removePotionEffect(PotionEffectType.SPEED);
            le.addPotionEffect(p);
    }
}
