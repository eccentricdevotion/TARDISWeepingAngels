package me.eccentric_nz.tardisweepingangels;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import me.eccentric_nz.tardisweepingangels.commands.AdminCommand;
import me.eccentric_nz.tardisweepingangels.commands.CountCommand;
import me.eccentric_nz.tardisweepingangels.commands.DalekCommand;
import me.eccentric_nz.tardisweepingangels.commands.DisguiseCommand;
import me.eccentric_nz.tardisweepingangels.commands.KillCommand;
import me.eccentric_nz.tardisweepingangels.commands.SpawnCommand;
import me.eccentric_nz.tardisweepingangels.commands.TabComplete;
import me.eccentric_nz.tardisweepingangels.death.Death;
import me.eccentric_nz.tardisweepingangels.death.PlayerDeath;
import me.eccentric_nz.tardisweepingangels.death.RainDamage;
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
import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
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
    private final List<Biome> notOnWater = new ArrayList<Biome>();

    @Override
    public void onDisable() {
        // TODO: Place any custom disable code here.
    }

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        if (pm.isPluginEnabled("ProtocolLib") && pm.isPluginEnabled("LibsDisguises")) {
            saveDefaultConfig();
            random = new Random();
            PluginDescriptionFile pdfFile = getDescription();
            pluginName = ChatColor.GOLD + "[" + pdfFile.getName() + "]" + ChatColor.RESET + " ";
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
            getCommand("twas").setExecutor(new SpawnCommand(this));
            getCommand("twad").setExecutor(new DisguiseCommand(this));
            getCommand("twac").setExecutor(new CountCommand(this));
            getCommand("twak").setExecutor(new KillCommand(this));
            getCommand("twa").setExecutor(new AdminCommand(this));
            getCommand("twar").setExecutor(new DalekCommand(this));
            // set tab completion
            TabCompleter tabCompleter = new TabComplete(this);
            getCommand("twas").setTabCompleter(tabCompleter);
            getCommand("twad").setTabCompleter(tabCompleter);
            getCommand("twac").setTabCompleter(tabCompleter);
            getCommand("twak").setTabCompleter(tabCompleter);
            getCommand("twa").setTabCompleter(tabCompleter);
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
            steal = (getConfig().getBoolean("angels.can_steal") && pm.isPluginEnabled("TARDIS"));
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

    /**
     * Outputs a message to the console.
     *
     * @param o the Object to print to the console
     */
    public void debug(Object o) {
        getServer().getConsoleSender().sendMessage(pluginName + "Debug: " + o);
    }
}
