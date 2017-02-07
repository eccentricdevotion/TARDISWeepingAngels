/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.daleks;

import java.util.Collection;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Config;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.SnowmanWatcher;
import org.bukkit.World;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author eccentric_nz
 */
public class ReDisguise implements Runnable {

    private final TARDISWeepingAngels plugin;

    public ReDisguise(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (World w : plugin.getServer().getWorlds()) {
            // only configured worlds
            String name = Config.sanitiseName(w.getName());
            if (plugin.getConfig().getInt("daleks.worlds." + name) > 0) {
                // get the current daleks
                Collection<Skeleton> daleks = w.getEntitiesByClass(Skeleton.class);
                for (Skeleton d : daleks) {
                    // does it have a helmet with a display name
                    EntityEquipment ee = d.getEquipment();
                    ItemStack is = ee.getHelmet();
                    if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Dalek") && !DisguiseAPI.isDisguised(d)) {
                        MobDisguise mobDisguise = new MobDisguise(DisguiseType.SNOWMAN);
                        LivingWatcher livingWatcher = mobDisguise.getWatcher();
                        SnowmanWatcher snw = (SnowmanWatcher) livingWatcher;
                        snw.setDerp(true);
                        DisguiseAPI.disguiseToAll(d, mobDisguise);
                    }
                }
                Collection<Guardian> guardians = w.getEntitiesByClass(Guardian.class);
                for (Guardian g : guardians) {
                    // does it have invisibilty but not riding an Enderman
                    if (g.hasPotionEffect(PotionEffectType.INVISIBILITY) && g.getVehicle() == null) {
                        g.remove();
                    }
                }
            }
        }
    }
}
