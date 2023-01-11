package me.eccentric_nz.tardisweepingangels.monsters.headless_monks;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;

public class HeadlessMonkEquipment {

    public static void setTasks(LivingEntity le) {
        le.getPersistentDataContainer().set(TARDISWeepingAngels.HEADLESS_TASK, PersistentDataType.INTEGER, -1);
        le.getPersistentDataContainer().set(TARDISWeepingAngels.FLAME_TASK, PersistentDataType.INTEGER, -1);
    }
}
