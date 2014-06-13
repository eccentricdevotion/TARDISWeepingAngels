/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels;

import java.util.Collection;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.World;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author eccentric_nz
 */
public class TARDISWeepingAngelsDisguise implements Runnable {

    private final TARDISWeepingAngels plugin;

    public TARDISWeepingAngelsDisguise(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.debug("Checking Daleks for disguises...");
        int count = 0;
        for (World w : plugin.getServer().getWorlds()) {
            // only configured worlds
            if (plugin.getConfig().getStringList("daleks.worlds").contains(w.getName())) {
                // get the current daleks
                Collection<Skeleton> daleks = w.getEntitiesByClass(Skeleton.class);
                for (Skeleton d : daleks) {
                    // does it have a leather helmet
                    EntityEquipment ee = d.getEquipment();
                    ItemStack is = ee.getHelmet();
                    if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName()
                            && is.getItemMeta().getDisplayName().startsWith("Dalek") && !DisguiseAPI.isDisguised(d)) {
                        MobDisguise mobDisguise = new MobDisguise(DisguiseType.SNOWMAN);
                        DisguiseAPI.disguiseToAll(d, mobDisguise);
                        count++;
                    }
                }
            }
        }
        plugin.debug("Found & re-disguised " + count + " Daleks");
    }
}
