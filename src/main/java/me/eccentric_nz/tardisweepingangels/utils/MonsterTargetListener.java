package me.eccentric_nz.tardisweepingangels.utils;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class MonsterTargetListener implements Listener {

    private static boolean isWearingMonsterHead(Player player, Material material) {
        ItemStack is = player.getInventory().getHelmet();
        if (is != null && is.getType().equals(material) && is.hasItemMeta()) {
            return is.getItemMeta().getPersistentDataContainer().has(TARDISWeepingAngels.MONSTER_HEAD, PersistentDataType.INTEGER);
        }
        return false;
    }

    public static boolean monsterShouldIgnorePlayer(Entity entity, Player player) {
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        boolean ignore = false;
        switch (entity.getType()) {
            case ZOMBIE -> {
                // cyberman
                if (pdc.has(TARDISWeepingAngels.CYBERMAN, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Monster.CYBERMAN.getMaterial());
                }
                // empty child
                if (pdc.has(TARDISWeepingAngels.EMPTY, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Monster.EMPTY_CHILD.getMaterial());
                }
                // sontaran
                if (pdc.has(TARDISWeepingAngels.SONTARAN, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Monster.SONTARAN.getMaterial());
                }
                // vashta nerada
                if (pdc.has(TARDISWeepingAngels.VASHTA, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Monster.VASHTA_NERADA.getMaterial());
                }
                // zygon
                if (pdc.has(TARDISWeepingAngels.ZYGON, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Monster.ZYGON.getMaterial());
                }
            }
            case SKELETON -> {
                // dalek
                if (pdc.has(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Monster.DALEK.getMaterial());
                }
                // silent
                if (pdc.has(TARDISWeepingAngels.SILENT, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Monster.SILENT.getMaterial());
                }
                // silurian
                if (pdc.has(TARDISWeepingAngels.SILURIAN, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Monster.SILURIAN.getMaterial());
                }
                // weeping angel
                if (pdc.has(TARDISWeepingAngels.ANGEL, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Monster.WEEPING_ANGEL.getMaterial());
                }
            }
            case GUARDIAN -> {
                // silent
                if (pdc.has(TARDISWeepingAngels.SILENT, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Monster.SILENT.getMaterial());
                }
            }
            case ZOMBIFIED_PIGLIN -> {
                // hath
                if (pdc.has(TARDISWeepingAngels.HATH, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Monster.HATH.getMaterial());
                }
                // ice warrior
                if (pdc.has(TARDISWeepingAngels.WARRIOR, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Monster.ICE_WARRIOR.getMaterial());
                }
                // strax
                if (pdc.has(TARDISWeepingAngels.STRAX, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Monster.STRAX.getMaterial());
                }
            }
            default -> {
            }
        }
        return ignore;
    }

    @EventHandler
    public void onMonsterTargetEvent(EntityTargetLivingEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof org.bukkit.entity.Monster monster) {
            double range = (entity instanceof Zombie) ? 40.0d : 16.0d;
            Player closest = null;
            double distance = 1000.0f;
            Location locEnt = entity.getLocation();
            for (Entity e : entity.getNearbyEntities(range, range, range)) {
                if (e instanceof Player player) {
                    if (closest == null) {
                        closest = player;
                        distance = e.getLocation().distanceSquared(locEnt);
                    } else if (e.getLocation().distanceSquared(locEnt) < distance) {
                        closest = player;
                        distance = e.getLocation().distanceSquared(locEnt);
                    }
                }
            }
            if (closest != null && monsterShouldIgnorePlayer(entity, closest)) {
                event.setCancelled(true);
                monster.setTarget(null);
            }
        }
    }
}
