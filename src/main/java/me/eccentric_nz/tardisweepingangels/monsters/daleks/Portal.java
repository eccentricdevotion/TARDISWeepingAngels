/*
 *  Copyright 2015 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.daleks;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;

/**
 * @author eccentric_nz
 */
public class Portal implements Listener {

    private final TARDISWeepingAngels plugin;

    public Portal(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void dalekExitPortal(EntityPortalExitEvent event) {
        Entity e = event.getEntity();
        if (e.getType().equals(EntityType.SKELETON)) {
            Skeleton skeleton = (Skeleton) e;
            if (skeleton.getPersistentDataContainer().has(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER)) {
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    TARDISWeepingAngels.getEqipper().setDalekEquipment(skeleton);
                }, 5L);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void playerJoin(PlayerJoinEvent event) {
        World world = event.getPlayer().getWorld();
        redisguise(world);
    }

    @EventHandler(ignoreCancelled = true)
    public void playerRespawn(PlayerRespawnEvent event) {
        World world = event.getPlayer().getWorld();
        redisguise(world);
    }

    @EventHandler(ignoreCancelled = true)
    public void playerChangedWorld(PlayerChangedWorldEvent event) {
        World world = event.getPlayer().getWorld();
        redisguise(world);
    }

    private void redisguise(World w) {
        // get the current daleks
        Collection<Skeleton> daleks = w.getEntitiesByClass(Skeleton.class);
        daleks.forEach((d) -> {
            PersistentDataContainer pdc = d.getPersistentDataContainer();
            if (pdc.has(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER)) {
                if (d.getEquipment().getHelmet() == null) {
                    TARDISWeepingAngels.getEqipper().setDalekEquipment(d);
                }
            }
        });
    }
}
