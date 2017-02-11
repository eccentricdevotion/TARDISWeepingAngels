package me.eccentric_nz.tardisweepingangels;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import me.eccentric_nz.tardisweepingangels.commands.AdminCommand;
import me.eccentric_nz.tardisweepingangels.commands.ArmourStandCommand;
import me.eccentric_nz.tardisweepingangels.commands.CountCommand;
import me.eccentric_nz.tardisweepingangels.commands.DalekCommand;
import me.eccentric_nz.tardisweepingangels.commands.DisguiseCommand;
import me.eccentric_nz.tardisweepingangels.commands.KillCommand;
import me.eccentric_nz.tardisweepingangels.commands.SpawnCommand;
import me.eccentric_nz.tardisweepingangels.commands.TabComplete;
import me.eccentric_nz.tardisweepingangels.death.Death;
import me.eccentric_nz.tardisweepingangels.death.PlayerDeath;
import me.eccentric_nz.tardisweepingangels.death.RainDamage;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import me.eccentric_nz.tardisweepingangels.equip.PlayerUndisguise;
import me.eccentric_nz.tardisweepingangels.monsters.CybermanRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.IceWarriorRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.VashtaNeradaListener;
import me.eccentric_nz.tardisweepingangels.monsters.ZygonRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.daleks.ChunkLoad;
import me.eccentric_nz.tardisweepingangels.monsters.daleks.DalekRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.daleks.Portal;
import me.eccentric_nz.tardisweepingangels.monsters.daleks.ReDisguise;
import me.eccentric_nz.tardisweepingangels.monsters.empty_child.EmptyChildRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.empty_child.GasMask;
import me.eccentric_nz.tardisweepingangels.monsters.silurians.SilurianRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.sontarans.Butler;
import me.eccentric_nz.tardisweepingangels.monsters.sontarans.SontaranRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.weeping_angels.Blink;
import me.eccentric_nz.tardisweepingangels.monsters.weeping_angels.Builder;
import me.eccentric_nz.tardisweepingangels.monsters.weeping_angels.Damage;
import me.eccentric_nz.tardisweepingangels.monsters.weeping_angels.ImageHolder;
import me.eccentric_nz.tardisweepingangels.monsters.weeping_angels.WeepingAngelsRunnable;
import me.eccentric_nz.tardisweepingangels.silent.AntiTeleport;
import me.eccentric_nz.tardisweepingangels.silent.SilentRunnable;
import me.eccentric_nz.tardisweepingangels.utils.Config;
import me.eccentric_nz.tardisweepingangels.utils.HelmetChecker;
import me.eccentric_nz.tardisweepingangels.utils.Sounds;
import me.eccentric_nz.tardisweepingangels.utils.Version;
import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TARDISWeepingAngels extends JavaPlugin {

    public String pluginName;
    private Random random;
    private boolean steal;
    private final List<UUID> empty = new ArrayList<UUID>();
    private final List<UUID> timesUp = new ArrayList<UUID>();
    private final List<Biome> notOnWater = new ArrayList<Biome>();
    private PluginManager pm;
    private boolean citizensEnabled;

    @Override
    public void onDisable() {
        // TODO: Place any custom disable code here.
    }

    @Override
    public void onEnable() {
        pm = getServer().getPluginManager();
        PluginDescriptionFile pdfFile = getDescription();
        pluginName = ChatColor.GOLD + "[" + pdfFile.getName() + "]" + ChatColor.RESET + " ";
        if (pm.isPluginEnabled("ProtocolLib") && pm.isPluginEnabled("LibsDisguises")) {
            citizensEnabled = pm.isPluginEnabled("Citizens");
            // check dependent plugin versions
            if (!checkPluginVersion("ProtocolLib", "4.2.0")) {
                getServer().getConsoleSender().sendMessage(pluginName + ChatColor.RED + "This plugin requires ProtocolLib to be v4.2.0 or higher, disabling...");
                pm.disablePlugin(this);
                return;
            }
            if (!checkPluginVersion("LibsDisguises", "9.2.4")) {
                getServer().getConsoleSender().sendMessage(pluginName + ChatColor.RED + "This plugin requires LibsDisguises to be v9.2.4 or higher, disabling...");
                pm.disablePlugin(this);
                return;
            }
            saveDefaultConfig();
            random = new Random();
            // update the config
            new Config(this).updateConfig();
            // register listeners
            pm.registerEvents(new Blink(this), this);
            if (getConfig().getBoolean("angels.can_build")) {
                pm.registerEvents(new Builder(this), this);
            }
            if (getConfig().getBoolean("angels.spawn_from_chat.enabled")) {
                pm.registerEvents(new ImageHolder(this), this);
            }
            pm.registerEvents(new Damage(this), this);
            pm.registerEvents(new VashtaNeradaListener(this), this);
            pm.registerEvents(new Death(this), this);
            pm.registerEvents(new PlayerDeath(this), this);
            pm.registerEvents(new PlayerUndisguise(this), this);
            pm.registerEvents(new Sounds(this), this);
            pm.registerEvents(new GasMask(this), this);
            pm.registerEvents(new Butler(this), this);
            pm.registerEvents(new HelmetChecker(), this);
            pm.registerEvents(new Portal(this), this);
            pm.registerEvents(new AntiTeleport(), this);
            pm.registerEvents(new RainDamage(), this);
            pm.registerEvents(new ChunkLoad(), this);
            // register commands
            getCommand("twa").setExecutor(new AdminCommand(this));
            getCommand("twac").setExecutor(new CountCommand(this));
            getCommand("twad").setExecutor(new DisguiseCommand(this));
            getCommand("twae").setExecutor(new ArmourStandCommand(this));
            getCommand("twak").setExecutor(new KillCommand(this));
            getCommand("twar").setExecutor(new DalekCommand(this));
            getCommand("twas").setExecutor(new SpawnCommand(this));
            // set tab completion
            TabCompleter tabCompleter = new TabComplete(this);
            getCommand("twa").setTabCompleter(tabCompleter);
            getCommand("twac").setTabCompleter(tabCompleter);
            getCommand("twad").setTabCompleter(tabCompleter);
            getCommand("twae").setTabCompleter(tabCompleter);
            getCommand("twak").setTabCompleter(tabCompleter);
            getCommand("twas").setTabCompleter(tabCompleter);
            // re-disguise Daleks
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new ReDisguise(this), 100L, 6000L);
            // start repeating spawn tasks
            long delay = getConfig().getLong("spawn_rate.how_often");
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new WeepingAngelsRunnable(this), delay, delay);
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new CybermanRunnable(this), delay, delay);
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new DalekRunnable(this), delay, delay);
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new EmptyChildRunnable(this), delay, delay);
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new IceWarriorRunnable(this), delay, delay);
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new SilurianRunnable(this), delay, delay);
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new SilentRunnable(this), delay, delay);
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new SontaranRunnable(this), delay, delay);
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new ZygonRunnable(this), delay, delay);
            steal = (getConfig().getBoolean("angels.can_steal"));
            this.notOnWater.add(Biome.DEEP_OCEAN);
            this.notOnWater.add(Biome.OCEAN);
            this.notOnWater.add(Biome.RIVER);
        } else {
            System.err.println("[TARDISWeepingAngels] This plugin requires ProtocolLib & LibsDisguises, disabling...");
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

    public List<Biome> getNotOnWater() {
        return notOnWater;
    }

    public boolean isCitizensEnabled() {
        return citizensEnabled;
    }

    /**
     * Outputs a message to the console.
     *
     * @param o the Object to print to the console
     */
    public void debug(Object o) {
        getServer().getConsoleSender().sendMessage(pluginName + "Debug: " + o);
    }

    private boolean checkPluginVersion(String plg, String min) {
        if (pm.isPluginEnabled(plg)) {
            Plugin check = pm.getPlugin(plg);
            Version minver = new Version(min);
            String preSplit = check.getDescription().getVersion();
            String[] split = preSplit.split("-");
            try {
                Version ver;
                if (check.getName().equals("TARDISChunkGenerator") && check.getDescription().getVersion().startsWith("1")) {
                    ver = new Version("1");
                } else {
                    ver = new Version(split[0]);
                }
                return (ver.compareTo(minver) >= 0);
            } catch (IllegalArgumentException e) {
                getServer().getLogger().log(Level.WARNING, "TARDIS failed to get the version for {0}.", plg);
                getServer().getLogger().log(Level.WARNING, "This could cause issues with enabling the plugin.");
                getServer().getLogger().log(Level.WARNING, "Please check you have at least v{0}", min);
                getServer().getLogger().log(Level.WARNING, "The invalid version format was {0}", preSplit);
                return true;
            }
        } else {
            return true;
        }
    }

    public MonsterEquipment getWeepingAngelsAPI() {
        return new MonsterEquipment();
    }
}
