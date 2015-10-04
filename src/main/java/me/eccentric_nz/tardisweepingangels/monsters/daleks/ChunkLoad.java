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
import org.bukkit.inventory.meta.ItemMeta;

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
                ItemStack is = ee.getHelmet();
                // check the helmet
                if (is != null && is.getType().equals(Material.VINE)) {
                    if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Dalek") && !DisguiseAPI.isDisguised(d)) {
                        MobDisguise mobDisguise = new MobDisguise(DisguiseType.SNOWMAN);
                        DisguiseAPI.disguiseToAll(d, mobDisguise);
                    }
                } else if (is != null && is.getType().equals(Material.WATER_LILY)) {
                    ItemStack head = new ItemStack(Material.STONE_BUTTON, 1);
                    ItemMeta hmeta = head.getItemMeta();
                    hmeta.setDisplayName("Weeping Angel Head");
                    head.setItemMeta(hmeta);
                    ee.setHelmet(head);
                } else if (is == null || is.getType().equals(Material.AIR) || is.getType().equals(Material.IRON_HELMET)) {
                    // check the chestplate
                    ItemStack chest = ee.getChestplate();
                    if (chest.hasItemMeta() && chest.getItemMeta().hasDisplayName() && chest.getItemMeta().getDisplayName().startsWith("Weeping Angel")) {
                        ItemStack head = new ItemStack(Material.STONE_BUTTON, 1);
                        ItemMeta hmeta = head.getItemMeta();
                        hmeta.setDisplayName("Weeping Angel Head");
                        head.setItemMeta(hmeta);
                        ee.setHelmet(head);
                    }
                }
            }
        }
    }
}
