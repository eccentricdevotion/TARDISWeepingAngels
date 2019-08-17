package me.eccentric_nz.tardisweepingangels.monsters.daleks;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.SnowmanWatcher;
import org.bukkit.entity.Entity;

public class DalekDisguiseLibs {

    public static void disguise(Entity d) {
        MobDisguise mobDisguise = new MobDisguise(DisguiseType.SNOWMAN);
        LivingWatcher livingWatcher = mobDisguise.getWatcher();
        SnowmanWatcher snw = (SnowmanWatcher) livingWatcher;
        snw.setDerp(true);
        DisguiseAPI.disguiseToAll(d, mobDisguise);
    }

    public static boolean isDisguised(Entity entity) {
        return DisguiseAPI.isDisguised(entity);
    }
}
