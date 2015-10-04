/*
 *  Copyright 2015 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.daleks;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author eccentric_nz
 */
public class ChunkLoad implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChunkLoad(ChunkLoadEvent event) {
        for (Entity d : event.getChunk().getEntities()) {
            if (d instanceof Skeleton) {
                EntityEquipment ee = ((Skeleton) d).getEquipment();
                if (ee.getHelmet().getType().equals(Material.VINE)) {
                    ItemStack is = ee.getHelmet();
                    if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Dalek") && !DisguiseAPI.isDisguised(d)) {
                        MobDisguise mobDisguise = new MobDisguise(DisguiseType.SNOWMAN);
                        DisguiseAPI.disguiseToAll(d, mobDisguise);
                    }
                }
            }
        }
    }
}
