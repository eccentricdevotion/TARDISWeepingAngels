package me.eccentric_nz.tardisweepingangels;

import me.eccentric_nz.tardisweepingangels.commands.*;
import me.eccentric_nz.tardisweepingangels.death.Death;
import me.eccentric_nz.tardisweepingangels.death.PlayerDeath;
import me.eccentric_nz.tardisweepingangels.death.RainDamage;
import me.eccentric_nz.tardisweepingangels.equip.K9TameOrBreed;
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
import me.eccentric_nz.tardisweepingangels.monsters.judoon.JudoonAmmoRecipe;
import me.eccentric_nz.tardisweepingangels.monsters.judoon.JudoonGuardRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.judoon.JudoonListener;
import me.eccentric_nz.tardisweepingangels.monsters.ood.OodListener;
import me.eccentric_nz.tardisweepingangels.monsters.silent.AntiTeleport;
import me.eccentric_nz.tardisweepingangels.monsters.silent.SilentRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.silurians.SilurianSpawnerListener;
import me.eccentric_nz.tardisweepingangels.monsters.sontarans.Butler;
import me.eccentric_nz.tardisweepingangels.monsters.sontarans.SontaranRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.weeping_angels.*;
import me.eccentric_nz.tardisweepingangels.utils.Config;
import me.eccentric_nz.tardisweepingangels.utils.HelmetChecker;
import me.eccentric_nz.tardisweepingangels.utils.Sounds;
import me.eccentric_nz.tardisweepingangels.utils.UUIDDataType;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.command.TabCompleter;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TARDISWeepingAngels extends JavaPlugin {

    public static TARDISWeepingAngels plugin;
    private final List<UUID> empty = new ArrayList<>();
    private final List<UUID> timesUp = new ArrayList<>();
    private final List<Biome> notOnWater = new ArrayList<>();
    public String pluginName;
    private Random random;
    private boolean steal;
    private PluginManager pm;
    private boolean citizensEnabled = false;
    public static NamespacedKey ANGEL;
    public static NamespacedKey CYBERMAN;
    public static NamespacedKey DALEK;
    public static NamespacedKey EMPTY;
    public static NamespacedKey JUDOON;
    public static NamespacedKey K9;
    public static NamespacedKey OOD;
    public static NamespacedKey OWNER_UUID;
    public static NamespacedKey SILENT;
    public static NamespacedKey SILURIAN;
    public static NamespacedKey SONTARAN;
    public static NamespacedKey STRAX;
    public static NamespacedKey VASHTA;
    public static NamespacedKey WARRIOR;
    public static NamespacedKey ZYGON;
    public static PersistentDataType<byte[], UUID> PersistentDataTypeUUID;
    public static MonsterEquipment eqipper;
    private final List<UUID> guards = new ArrayList<>();
    private final List<UUID> playersWithGuards = new ArrayList<>();

    @Override
    public void onDisable() {
        // TODO: Place any custom disable code here.
    }

    @Override
    public void onEnable() {
        plugin = this;
        pm = getServer().getPluginManager();
        PluginDescriptionFile pdfFile = getDescription();
        pluginName = ChatColor.GOLD + "[" + pdfFile.getName() + "]" + ChatColor.RESET + " ";
        citizensEnabled = pm.isPluginEnabled("Citizens");
        saveDefaultConfig();
        random = new Random();
        eqipper = new MonsterEquipment();
        // update the config
        new Config(this).updateConfig();
        // initialise namespaced keys
        initKeys(this);
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
        pm.registerEvents(new AntiTeleport(this), this);
        pm.registerEvents(new K9TameOrBreed(), this);
        pm.registerEvents(new RainDamage(), this);
        pm.registerEvents(new ChunkLoad(), this);
        pm.registerEvents(new SilurianSpawnerListener(this), this);
        pm.registerEvents(new OodListener(), this);
        pm.registerEvents(new JudoonListener(this), this);
        // register commands
        getCommand("twa").setExecutor(new AdminCommand(this));
        getCommand("twac").setExecutor(new CountCommand(this));
        getCommand("twad").setExecutor(new DisguiseCommand(this));
        getCommand("twae").setExecutor(new ArmourStandCommand(this));
        getCommand("twak").setExecutor(new KillCommand(this));
        getCommand("twar").setExecutor(new DalekCommand(this));
        getCommand("twas").setExecutor(new SpawnCommand(this));
        getCommand("ood").setExecutor(new OodCommand(this));
        getCommand("judoon").setExecutor(new JudoonCommand(this));
        // set tab completion
        TabCompleter tabCompleter = new TabComplete(this);
        getCommand("twa").setTabCompleter(tabCompleter);
        getCommand("twac").setTabCompleter(tabCompleter);
        getCommand("twad").setTabCompleter(tabCompleter);
        getCommand("twae").setTabCompleter(tabCompleter);
        getCommand("twak").setTabCompleter(tabCompleter);
        getCommand("twas").setTabCompleter(tabCompleter);
        getCommand("ood").setTabCompleter(tabCompleter);
        getCommand("judoon").setTabCompleter(tabCompleter);
        // re-disguise Daleks
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new ReDisguise(this), 100L, 6000L);
        // start repeating spawn tasks
        long delay = getConfig().getLong("spawn_rate.how_often");
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new WeepingAngelsRunnable(this), delay, delay);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new CybermanRunnable(this), delay, delay);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new DalekRunnable(this), delay, delay);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new EmptyChildRunnable(this), delay, delay);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new IceWarriorRunnable(this), delay, delay);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new SilentRunnable(this), delay, delay);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new SontaranRunnable(this), delay, delay);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new ZygonRunnable(this), delay, delay);
        steal = (getConfig().getBoolean("angels.angels_can_steal"));
        notOnWater.add(Biome.OCEAN);
        notOnWater.add(Biome.DEEP_OCEAN);
        notOnWater.add(Biome.COLD_OCEAN);
        notOnWater.add(Biome.DEEP_COLD_OCEAN);
        notOnWater.add(Biome.LUKEWARM_OCEAN);
        notOnWater.add(Biome.DEEP_LUKEWARM_OCEAN);
        notOnWater.add(Biome.WARM_OCEAN);
        notOnWater.add(Biome.DEEP_WARM_OCEAN);
        notOnWater.add(Biome.RIVER);
        if (getConfig().getBoolean("judoon.guards")) {
            // add recipe
            new JudoonAmmoRecipe(this).addRecipe();
            // start guarding task
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new JudoonGuardRunnable(this), 20L, 20L);
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

    public static MonsterEquipment getEqipper() {
        return eqipper;
    }

    public MonsterEquipment getWeepingAngelsAPI() {
        return eqipper;
    }

    public List<UUID> getGuards() {
        return guards;
    }

    public List<UUID> getPlayersWithGuards() {
        return playersWithGuards;
    }

    private void initKeys(TARDISWeepingAngels plugin) {
        ANGEL = new NamespacedKey(plugin, "angel");
        CYBERMAN = new NamespacedKey(plugin, "cyberman");
        DALEK = new NamespacedKey(plugin, "dalek");
        EMPTY = new NamespacedKey(plugin, "empty");
        JUDOON = new NamespacedKey(plugin, "judoon");
        K9 = new NamespacedKey(plugin, "k9");
        OOD = new NamespacedKey(plugin, "ood");
        OWNER_UUID = new NamespacedKey(plugin, "ood_uuid");
        SILENT = new NamespacedKey(plugin, "silent");
        SILURIAN = new NamespacedKey(plugin, "silurian");
        SONTARAN = new NamespacedKey(plugin, "sontaran");
        STRAX = new NamespacedKey(plugin, "strax");
        VASHTA = new NamespacedKey(plugin, "vashta");
        WARRIOR = new NamespacedKey(plugin, "warrior");
        ZYGON = new NamespacedKey(plugin, "zygon");
        PersistentDataTypeUUID = new UUIDDataType();
    }
}
