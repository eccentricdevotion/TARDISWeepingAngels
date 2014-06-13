package me.eccentric_nz.tardisweepingangels;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.command.TabCompleter;
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
        PluginManager pm = getServer().getPluginManager();
        if (pm.isPluginEnabled("LibsDisguises")) {
            saveDefaultConfig();
            random = new Random();
            PluginDescriptionFile pdfFile = getDescription();
            pluginName = ChatColor.GOLD + "[" + pdfFile.getName() + "]" + ChatColor.RESET + " ";
            // update the config
            new TARDISWeepingAngelsConfig(this).updateConfig();
            // register listeners
            pm.registerEvents(new TARDISWeepingAngelsBlink(this), this);
            pm.registerEvents(new TARDISWeepingAngelsDamage(this), this);
            pm.registerEvents(new TARDISWeepingAngelsDeath(this), this);
            pm.registerEvents(new TARDISWeepingAngelsPlayerDeath(this), this);
            pm.registerEvents(new TARDISWeepingAngelsUndisguise(this), this);
            pm.registerEvents(new TARDISWeepingAngelsTarget(this), this);
            pm.registerEvents(new TARDISWeepingAngelsRespawn(this), this);
            // register commands
            getCommand("twas").setExecutor(new TARDISWeepingAngelsSpawnCommand(this));
            getCommand("twad").setExecutor(new TARDISWeepingAngelsDisguiseCommand(this));
            getCommand("twac").setExecutor(new TARDISWeepingAngelsCountCommand(this));
            getCommand("twa").setExecutor(new TARDISWeepingAngelsAdminCommand(this));
            // set tab completion
            TabCompleter tabCompleter = new TARDISWeepingAngelsTabComplete(this);
            getCommand("twas").setTabCompleter(tabCompleter);
            getCommand("twad").setTabCompleter(tabCompleter);
            getCommand("twac").setTabCompleter(tabCompleter);
            getCommand("twa").setTabCompleter(tabCompleter);
            // start repeating spawn tasks
            long angeldelay = getConfig().getLong("angels.spawn_rate.how_often");
            long icedelay = getConfig().getLong("angels.spawn_rate.how_often");
            long cyberdelay = getConfig().getLong("cybermen.spawn_rate.how_often");
            long emptydelay = getConfig().getLong("empty_child.spawn_rate.how_often");
            long zygondelay = getConfig().getLong("zygons.spawn_rate.how_often");
            long siluriandelay = getConfig().getLong("silurians.spawn_rate.how_often");
            long dalekdelay = getConfig().getLong("daleks.spawn_rate.how_often");
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new TARDISWeepingAngelsRunnable(this), angeldelay, angeldelay);
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new TARDISIceWarriorRunnable(this), icedelay, icedelay);
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new TARDISCybermanRunnable(this), cyberdelay, cyberdelay);
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new TARDISEmptyChildRunnable(this), emptydelay, emptydelay);
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new TARDISZygonRunnable(this), zygondelay, zygondelay);
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new TARDISSilurianRunnable(this), siluriandelay, siluriandelay);
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new TARDISDalekRunnable(this), dalekdelay, dalekdelay);
            steal = (getConfig().getBoolean("angels.can_steal") && pm.isPluginEnabled("TARDIS"));
        } else {
            System.err.println(pluginName + ChatColor.RED + "This plugin requires LibsDisguises (http://dev.bukkit.org/bukkit-plugins/libs-disguises/), disabling...");
            pm.disablePlugin(this);
        }
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
