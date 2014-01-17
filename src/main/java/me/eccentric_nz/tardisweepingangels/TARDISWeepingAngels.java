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
        pm.registerEvents(new TARDISWeepingAngelsBlink(this), this);
        pm.registerEvents(new TARDISWeepingAngelsDamage(this), this);
        pm.registerEvents(new TARDISWeepingAngelsDeath(this), this);
        getCommand("tardisangel").setExecutor(new TARDISWeepingAngelsCommand(this));
        long delay = getConfig().getLong("spawn_rate.how_often");
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new TARDISWeepingAngelsRunnable(this), delay, delay);
        steal = (getConfig().getBoolean("angels_can_steal") && pm.isPluginEnabled("TARDIS"));
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
