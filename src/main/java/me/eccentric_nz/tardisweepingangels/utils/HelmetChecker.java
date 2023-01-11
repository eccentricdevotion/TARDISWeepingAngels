/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.utils;

import me.eccentric_nz.tardisweepingangels.equip.Equipper;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.daleks.DalekEquipment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

/**
 * @author eccentric_nz
 */
public class HelmetChecker implements Listener {

    @EventHandler
    public void onLoseHead(EntityCombustEvent event) {
        Entity e = event.getEntity();
        // is it a TARDISWeepingAngels monster?
        Monster monster = MonsterEquipment.getMonsterType(e);
        if (monster != null) {
            event.setCancelled(true);
            // restore head
            switch (monster) {
                case DALEK -> DalekEquipment.set((LivingEntity) e, false);
                default -> new Equipper(monster, (LivingEntity) e, false, (monster.equals(Monster.SILURIAN)));
            }
        }
    }
}
