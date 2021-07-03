/*
 * Copyright (C) 2021 eccentric_nz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package me.eccentric_nz.tardisweepingangels;

import me.eccentric_nz.tardisweepingangels.commands.TabComplete;
import me.eccentric_nz.tardisweepingangels.commands.TardisWeepingAngelsCommand;
import me.eccentric_nz.tardisweepingangels.death.Death;
import me.eccentric_nz.tardisweepingangels.death.PlayerDeath;
import me.eccentric_nz.tardisweepingangels.death.RainDamage;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import me.eccentric_nz.tardisweepingangels.equip.PlayerUndisguise;
import me.eccentric_nz.tardisweepingangels.monsters.cyberman.CybermanRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.dalek.ChunkLoad;
import me.eccentric_nz.tardisweepingangels.monsters.dalek.DalekRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.emptychild.EmptyChildRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.emptychild.GasMask;
import me.eccentric_nz.tardisweepingangels.monsters.icewarrior.IceWarriorRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.judoon.JudoonAmmoRecipe;
import me.eccentric_nz.tardisweepingangels.monsters.judoon.JudoonBuilder;
import me.eccentric_nz.tardisweepingangels.monsters.judoon.JudoonGuardRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.judoon.JudoonListener;
import me.eccentric_nz.tardisweepingangels.monsters.k9.K9Builder;
import me.eccentric_nz.tardisweepingangels.monsters.k9.K9Listener;
import me.eccentric_nz.tardisweepingangels.monsters.k9.K9Recipe;
import me.eccentric_nz.tardisweepingangels.monsters.ood.OodListener;
import me.eccentric_nz.tardisweepingangels.monsters.ood.VillagerCuredListener;
import me.eccentric_nz.tardisweepingangels.monsters.ood.VillagerSpawnListener;
import me.eccentric_nz.tardisweepingangels.monsters.silent.AntiTeleport;
import me.eccentric_nz.tardisweepingangels.monsters.silent.CleanGuardians;
import me.eccentric_nz.tardisweepingangels.monsters.silent.SilentRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.silurian.SilurianSpawnerListener;
import me.eccentric_nz.tardisweepingangels.monsters.sontaran.SontaranRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.sontaran.Strax;
import me.eccentric_nz.tardisweepingangels.monsters.toclafane.BeeSpawnListener;
import me.eccentric_nz.tardisweepingangels.monsters.toclafane.ToclafaneListener;
import me.eccentric_nz.tardisweepingangels.monsters.toclafane.ToclafaneRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.vashtanerada.VashtaNeradaListener;
import me.eccentric_nz.tardisweepingangels.monsters.weepingangel.*;
import me.eccentric_nz.tardisweepingangels.monsters.zygon.ZygonRunnable;
import me.eccentric_nz.tardisweepingangels.utils.*;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class TardisWeepingAngelsPlugin extends JavaPlugin {

    public static TardisWeepingAngelsPlugin plugin;
    public static Random random = new Random();
    public static NamespacedKey weepingAngel;
    public static NamespacedKey cyberman;
    public static NamespacedKey dalek;
    public static NamespacedKey emptyChild;
    public static NamespacedKey hath;
    public static NamespacedKey judoon;
    public static NamespacedKey k9;
    public static NamespacedKey ood;
    public static NamespacedKey ownerUuid;
    public static NamespacedKey silent;
    public static NamespacedKey silurian;
    public static NamespacedKey sontaran;
    public static NamespacedKey strax;
    public static NamespacedKey vashtaNerada;
    public static NamespacedKey toclafane;
    public static NamespacedKey iceWarrior;
    public static NamespacedKey zygon;
    public static UUID unclaimed = UUID.fromString("00000000-aaaa-bbbb-cccc-000000000000");
    public static NamespacedKey monsterHead;
    public static PersistentDataType<byte[], UUID> persistentDataTypeUuid;
    public static MonsterEquipment api;
    private final List<UUID> empty = new ArrayList<>();
    private final List<UUID> timesUp = new ArrayList<>();
    private final List<UUID> guards = new ArrayList<>();
    private final List<UUID> playersWithGuards = new ArrayList<>();
    private final HashMap<UUID, Integer> followTasks = new HashMap<>();
    public String pluginName;
    private boolean steal;
    private PluginManager pluginManager;
    private boolean citizensEnabled = false;

    @Override
    public void onDisable() {
        // TODO Place any custom disable code here.
    }

    @Override
    public void onEnable() {
        plugin = this;
        pluginManager = getServer().getPluginManager();
        PluginDescriptionFile pdfFile = getDescription();
        pluginName = ChatColor.GOLD + "[" + pdfFile.getName() + "]" + ChatColor.RESET + " ";
        citizensEnabled = pluginManager.isPluginEnabled("Citizens");
        saveDefaultConfig();
        api = new MonsterEquipment();
        // update the config
        new Config(this).updateConfig();
        // initialise namespaced keys
        initKeys(this);
        // register listeners
        pluginManager.registerEvents(new Blink(this), this);
        if (getConfig().getBoolean("angels.can_build")) {
            pluginManager.registerEvents(new AngelBuilder(this), this);
        }
        if (getConfig().getBoolean("angels.spawn_from_chat.enabled")) {
            pluginManager.registerEvents(new ImageHolder(this), this);
        }
        if (getConfig().getBoolean("judoon.can_build")) {
            pluginManager.registerEvents(new JudoonBuilder(this), this);
        }
        if (getConfig().getBoolean("k9.can_build")) {
            pluginManager.registerEvents(new K9Builder(this), this);
        }
        pluginManager.registerEvents(new Damage(this), this);
        pluginManager.registerEvents(new VashtaNeradaListener(this), this);
        pluginManager.registerEvents(new Death(this), this);
        pluginManager.registerEvents(new PlayerDeath(this), this);
        pluginManager.registerEvents(new PlayerUndisguise(this), this);
        pluginManager.registerEvents(new Sounds(this), this);
        pluginManager.registerEvents(new GasMask(this), this);
        pluginManager.registerEvents(new Strax(this), this);
        pluginManager.registerEvents(new HelmetChecker(), this);
        pluginManager.registerEvents(new AntiTeleport(this), this);
        pluginManager.registerEvents(new K9Listener(this), this);
        pluginManager.registerEvents(new RainDamage(), this);
        pluginManager.registerEvents(new ChunkLoad(), this);
        pluginManager.registerEvents(new SilurianSpawnerListener(this), this);
        pluginManager.registerEvents(new OodListener(), this);
        pluginManager.registerEvents(new JudoonListener(this), this);
        pluginManager.registerEvents(new ToclafaneListener(this), this);
        pluginManager.registerEvents(new ArmorStandListener(), this);
        pluginManager.registerEvents(new MonsterTranformListener(this), this);
        pluginManager.registerEvents(new MonsterTargetListener(), this);
        pluginManager.registerEvents(new MonsterHeadEquipListener(this), this);
        if (plugin.getConfig().getInt("ood.spawn_from_villager") > 0) {
            pluginManager.registerEvents(new VillagerSpawnListener(this), this);
        }
        if (plugin.getConfig().getInt("ood.spawn_from_cured") > 0) {
            pluginManager.registerEvents(new VillagerCuredListener(this), this);
        }
        if (plugin.getConfig().getInt("toclafane.spawn_from_bee") > 0) {
            pluginManager.registerEvents(new BeeSpawnListener(this), this);
        }
        // register command
        getCommand("twa").setExecutor(new TardisWeepingAngelsCommand(this));
        // set tab completion
        getCommand("twa").setTabCompleter(new TabComplete(this));
        // remove invisible Guardians not riding an Enderman
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new CleanGuardians(this), 100L, 6000L);
        // start repeating spawn tasks
        long delay = getConfig().getLong("spawn_rate.how_often");
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new WeepingAngelsRunnable(this), delay, delay);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new CybermanRunnable(this), delay, delay);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new DalekRunnable(this), delay, delay);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new EmptyChildRunnable(this), delay, delay);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new IceWarriorRunnable(this), delay, delay);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new SilentRunnable(this), delay, delay);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new SontaranRunnable(this), delay, delay);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new ToclafaneRunnable(this), delay, delay);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new ZygonRunnable(this), delay, delay);
        steal = (getConfig().getBoolean("angels.angels_can_steal"));
        if (getConfig().getBoolean("judoon.guards")) {
            // add recipe
            new JudoonAmmoRecipe(this).addRecipe();
            // start guarding task
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new JudoonGuardRunnable(this), 20L, 20L);
        }
        new K9Recipe(this).addRecipe();
        // process worlds
        getServer().getScheduler().scheduleSyncDelayedTask(this, new WorldProcessor(this), 200L);
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

    public boolean isCitizensEnabled() {
        return citizensEnabled;
    }

    /**
     * Outputs a message to the console.
     *
     * @param object the Object to print to the console
     */
    public void debug(Object object) {
        getServer().getConsoleSender().sendMessage(pluginName + "Debug: " + object);
    }

    public MonsterEquipment getWeepingAngelsApi() {
        return api;
    }

    public List<UUID> getGuards() {
        return guards;
    }

    public List<UUID> getPlayersWithGuards() {
        return playersWithGuards;
    }

    public HashMap<UUID, Integer> getFollowTasks() {
        return followTasks;
    }

    private void initKeys(TardisWeepingAngelsPlugin plugin) {
        weepingAngel = new NamespacedKey(plugin, "angel");
        cyberman = new NamespacedKey(plugin, "cyberman");
        dalek = new NamespacedKey(plugin, "dalek");
        emptyChild = new NamespacedKey(plugin, "empty");
        hath = new NamespacedKey(plugin, "hath");
        judoon = new NamespacedKey(plugin, "judoon");
        k9 = new NamespacedKey(plugin, "k9");
        ood = new NamespacedKey(plugin, "ood");
        ownerUuid = new NamespacedKey(plugin, "ood_uuid");
        silent = new NamespacedKey(plugin, "silent");
        silurian = new NamespacedKey(plugin, "silurian");
        sontaran = new NamespacedKey(plugin, "sontaran");
        strax = new NamespacedKey(plugin, "strax");
        vashtaNerada = new NamespacedKey(plugin, "vashta");
        toclafane = new NamespacedKey(plugin, "toclafane");
        iceWarrior = new NamespacedKey(plugin, "warrior");
        zygon = new NamespacedKey(plugin, "zygon");
        monsterHead = new NamespacedKey(plugin, "monster_head");
        persistentDataTypeUuid = new UuidDataType();
    }
}
