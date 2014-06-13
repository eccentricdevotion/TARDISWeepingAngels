/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels;

import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;

/**
 *
 * @author eccentric_nz
 */
public class TARDISWeepingAngelsSnow implements Listener {

    private final TARDISWeepingAngels plugin;

    public TARDISWeepingAngelsSnow(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSnowForm(EntityBlockFormEvent event) {
        plugin.debug("block formed: " + event.getNewState().getType().toString());
        Entity ent = event.getEntity();
        if (ent.getType().equals(EntityType.SNOWMAN) && DisguiseAPI.isDisguised(ent)) {
            event.setCancelled(true);
        }
    }

}
