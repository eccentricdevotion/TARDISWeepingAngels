package me.eccentric_nz.tardisweepingangels.monsters.silent;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SilentEquipment {

    public static void setGuardian(LivingEntity le) {
        LivingEntity g = (LivingEntity) le.getLocation().getWorld().spawnEntity(le.getLocation(), EntityType.GUARDIAN);
        g.setSilent(true);
        PotionEffect p = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false);
        g.addPotionEffect(p);
        g.getPersistentDataContainer().set(TARDISWeepingAngels.SILENT, PersistentDataType.INTEGER, Monster.SILENT.getPersist());
        g.setInvulnerable(true);
        le.addPassenger(g);
    }
}
