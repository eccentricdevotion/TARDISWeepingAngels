/*
 *  Copyright 2016 eccentric_nz.
 */

package me.eccentric_nz.tardisweepingangels;

import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author eccentric_nz
 */

public final class TARDISWeepingAngelSpawnEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Entity entity;
    private final EntityType entityType;
    private final Monster monsterType;
    private final Location location;

    public TARDISWeepingAngelSpawnEvent(Entity entity, EntityType entityType, Monster monsterType, Location location) {
        this.entity = entity;
        this.entityType = entityType;
        this.monsterType = monsterType;
        this.location = location;
    }

    public Entity getEntity() {
        return entity;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Monster getMonsterType() {
        return monsterType;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
