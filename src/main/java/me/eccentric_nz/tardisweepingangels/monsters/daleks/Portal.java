/*
 *  Copyright 2015 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.daleks;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.SnowmanWatcher;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

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
            EntityEquipment ee = skeleton.getEquipment();
            ItemStack is = ee.getHelmet();
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                if (is.getType().equals(Material.VINE) && !DisguiseAPI.isDisguised(skeleton)) {
                    MobDisguise mobDisguise = new MobDisguise(DisguiseType.SNOWMAN);
                    LivingWatcher livingWatcher = mobDisguise.getWatcher();
                    SnowmanWatcher snw = (SnowmanWatcher) livingWatcher;
                    snw.setDerp(true);
                    DisguiseAPI.disguiseToAll(skeleton, mobDisguise);
                }
            }, 5L);
        }
    }
}
