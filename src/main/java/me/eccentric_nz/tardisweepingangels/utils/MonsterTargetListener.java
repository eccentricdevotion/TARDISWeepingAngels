package me.eccentric_nz.tardisweepingangels.utils;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
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
                    ignore = isWearingMonsterHead(player, Material.IRON_INGOT);
                }
                // empty child
                if (pdc.has(TARDISWeepingAngels.EMPTY, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.SUGAR);
                }
                // sontaran
                if (pdc.has(TARDISWeepingAngels.SONTARAN, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.POTATO);
                }
                // vashta nerada
                if (pdc.has(TARDISWeepingAngels.VASHTA, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.BOOK);
                }
                // zygon
                if (pdc.has(TARDISWeepingAngels.ZYGON, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.PAINTING);
                }
            }
            case SKELETON -> {
                // dalek
                if (pdc.has(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.SLIME_BALL);
                }
                // silent
                if (pdc.has(TARDISWeepingAngels.SILENT, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.END_STONE);
                }
                // silurian
                if (pdc.has(TARDISWeepingAngels.SILURIAN, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.FEATHER);
                }
                // weeping angel
                if (pdc.has(TARDISWeepingAngels.ANGEL, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.BRICK);
                }
            }
            case GUARDIAN -> {
                // silent
                if (pdc.has(TARDISWeepingAngels.SILENT, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.END_STONE);
                }
            }
            case ZOMBIFIED_PIGLIN -> {
                // hath
                if (pdc.has(TARDISWeepingAngels.HATH, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.PUFFERFISH);
                }
                // ice warrior
                if (pdc.has(TARDISWeepingAngels.WARRIOR, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.SNOWBALL);
                }
                // strax
                if (pdc.has(TARDISWeepingAngels.STRAX, PersistentDataType.INTEGER)) {
                    ignore = isWearingMonsterHead(player, Material.BAKED_POTATO);
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
        if (entity instanceof Monster monster) {
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
