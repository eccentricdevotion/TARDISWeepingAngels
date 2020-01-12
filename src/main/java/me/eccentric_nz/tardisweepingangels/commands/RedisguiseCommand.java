package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.monsters.daleks.ReDisguise;

public class RedisguiseCommand {

    private final TARDISWeepingAngels plugin;

    public RedisguiseCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    public boolean redisguise() {
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new ReDisguise(plugin), 1L);
        return true;
    }
}
