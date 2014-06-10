package me.eccentric_nz.tardisweepingangels;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TARDISWeepingAngels extends JavaPlugin {

    public String pluginName;
    private Random random;
    private boolean steal;
    private final List<UUID> empty = new ArrayList<UUID>();
    private final List<UUID> timesUp = new ArrayList<UUID>();

    @Override
    public void onDisable() {
        // TODO: Place any custom disable code here.
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        random = new Random();
        PluginManager pm = getServer().getPluginManager();
        PluginDescriptionFile pdfFile = getDescription();
        pluginName = ChatColor.GOLD + "[" + pdfFile.getName() + "]" + ChatColor.RESET + " ";
        new TARDISWeepingAngelsConfig(this).updateConfig();
        pm.registerEvents(new TARDISWeepingAngelsBlink(this), this);
        pm.registerEvents(new TARDISWeepingAngelsDamage(this), this);
        pm.registerEvents(new TARDISWeepingAngelsDeath(this), this);
        pm.registerEvents(new TARDISWeepingAngelsPlayerDeath(this), this);
        pm.registerEvents(new TARDISWeepingAngelsUndisguise(this), this);
        pm.registerEvents(new TARDISWeepingAngelsTarget(this), this);
        pm.registerEvents(new TARDISWeepingAngelsRespawn(this), this);
        TARDISWeepingAngelsCommand command = new TARDISWeepingAngelsCommand(this);
        getCommand("angel").setExecutor(command);
        getCommand("warrior").setExecutor(command);
        getCommand("cyberman").setExecutor(command);
        getCommand("child").setExecutor(command);
        getCommand("zygon").setExecutor(command);
        getCommand("angeldisguise").setExecutor(command);
        getCommand("icedisguise").setExecutor(command);
        getCommand("cyberdisguise").setExecutor(command);
        getCommand("childdisguise").setExecutor(command);
        getCommand("zygondisguise").setExecutor(command);
        getCommand("angelcount").setExecutor(command);
        getCommand("twa").setExecutor(command);
        long angeldelay = getConfig().getLong("angels.spawn_rate.how_often");
        long icedelay = getConfig().getLong("angels.spawn_rate.how_often");
        long cyberdelay = getConfig().getLong("cybermen.spawn_rate.how_often");
        long emptydelay = getConfig().getLong("empty_child.spawn_rate.how_often");
        long zygondelay = getConfig().getLong("zygon.spawn_rate.how_often");
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new TARDISWeepingAngelsRunnable(this), angeldelay, angeldelay);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new TARDISIceWarriorRunnable(this), icedelay, icedelay);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new TARDISCybermanRunnable(this), cyberdelay, cyberdelay);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new TARDISEmptyChildRunnable(this), emptydelay, emptydelay);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new TARDISZygonRunnable(this), zygondelay, zygondelay);
        steal = (getConfig().getBoolean("angels.can_steal") && pm.isPluginEnabled("TARDIS"));
    }

    public Random getRandom() {
        return random;
    }

    public boolean angelsCanSteal() {
        return steal;
    }

    public List<UUID> getEmpty() {
        return empty;
    }

    public List<UUID> getTimesUp() {
        return timesUp;
    }

    /**
     * Outputs a message to the console.
     *
     * @param o the Object to print to the console
     */
    public void debug(Object o) {
        getServer().getConsoleSender().sendMessage(pluginName + "Debug: " + o);
    }
}
