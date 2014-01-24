package me.eccentric_nz.tardisweepingangels;

import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TARDISWeepingAngels extends JavaPlugin {

    public String pluginName;
    private Random random;
    private boolean steal;

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
        pm.registerEvents(new TARDISWeepingAngelsUndisguise(this), this);
        TARDISWeepingAngelsCommand command = new TARDISWeepingAngelsCommand(this);
        getCommand("angel").setExecutor(command);
        getCommand("warrior").setExecutor(command);
        getCommand("angeldisguise").setExecutor(command);
        getCommand("icedisguise").setExecutor(command);
        long angeldelay = getConfig().getLong("angels.spawn_rate.how_often");
        long icedelay = getConfig().getLong("angels.spawn_rate.how_often");
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new TARDISWeepingAngelsRunnable(this), angeldelay, angeldelay);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new TARDISIceWarriorRunnable(this), icedelay, icedelay);
        steal = (getConfig().getBoolean("angels.angels_can_steal") && pm.isPluginEnabled("TARDIS"));
    }

    public Random getRandom() {
        return random;
    }

    public boolean angelsCanSteal() {
        return steal;
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
