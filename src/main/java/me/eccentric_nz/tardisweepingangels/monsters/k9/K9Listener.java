/*
 *  Copyright 2017 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.k9;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;
import java.util.UUID;

/**
 * @author eccentric_nz
 */
public class K9Listener implements Listener {

    private final TARDISWeepingAngels plugin;

    public K9Listener(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onWolfTame(EntityTameEvent event) {
        if (event.getOwner() instanceof Player player) {
            if (!((Player) event.getOwner()).hasPermission("tardisweepingangels.k9")) {
                return;
            }
            LivingEntity ent = event.getEntity();
            if (ent.getType().equals(EntityType.WOLF) && plugin.getConfig().getBoolean("k9.by_taming")) {
                Location location = ent.getLocation();
                World world = location.getWorld();
                assert world != null;
                if (!plugin.getConfig().getBoolean("k9.worlds." + world.getName())) {
                    return;
                }
                Entity k9 = world.spawnEntity(location, EntityType.ARMOR_STAND);
                K9Equipment.set(player, k9, false);
                ent.remove();
                player.playSound(k9.getLocation(), "k9", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(k9, EntityType.ARMOR_STAND, Monster.K9, location));
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onK9Interact(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("tardisweepingangels.k9")) {
            return;
        }
        Entity ent = event.getRightClicked();
        if (ent.getType().equals(EntityType.ARMOR_STAND) && ent.getPersistentDataContainer().has(TARDISWeepingAngels.K9, PersistentDataType.INTEGER)) {
            if (ent.getPersistentDataContainer().has(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID)) {
                UUID uuid = player.getUniqueId();
                UUID k9Id = ent.getPersistentDataContainer().get(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID);
                assert k9Id != null;
                if (k9Id.equals(uuid)) {
                    player.playSound(ent.getLocation(), "k9", 1.0f, 1.0f);
                    if (plugin.getFollowTasks().containsKey(uuid)) {
                        // stay
                        plugin.getServer().getScheduler().cancelTask(plugin.getFollowTasks().get(uuid));
                        plugin.getFollowTasks().remove(uuid);
                    } else {
                        // follow
                        if (player.hasPermission("tardisweepingangels.follow.k9")) {
                            int taskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new K9WalkRunnable((ArmorStand) ent, 0.15d, player), 2L, 2L);
                            plugin.getFollowTasks().put(uuid, taskId);
                        }
                    }
                } else {
                    player.sendMessage(plugin.pluginName + "That is not your K9!");
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onK9BoneEgg(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            ItemStack is = event.getItem();
            if (is != null && is.getType().equals(Material.BONE) && is.hasItemMeta()) {
                ItemMeta im = is.getItemMeta();
                assert im != null;
                if (im.hasDisplayName() && im.getDisplayName().equals("K9") && im.hasCustomModelData()) {
                    event.setCancelled(true);
                    Player player = event.getPlayer();
                    Location location = Objects.requireNonNull(event.getClickedBlock()).getLocation().add(0.5d, 1.0d, 0.5d);
                    World world = location.getWorld();
                    assert world != null;
                    if (!plugin.getConfig().getBoolean("k9.worlds." + world.getName())) {
                        player.sendMessage(plugin.pluginName + "You cannot spawn a K9 in this world!");
                        return;
                    }
                    // remove egg form inventory
                    if (is.getAmount() == 1) {
                        player.getInventory().setItemInMainHand(null);
                    } else {
                        is.setAmount(is.getAmount() - 1);
                        player.getInventory().setItemInMainHand(is);
                    }
                    // spawn a K9 instead
                    Entity k9 = world.spawnEntity(location, EntityType.ARMOR_STAND);
                    K9Equipment.set(player, k9, false);
                    player.playSound(k9.getLocation(), "k9", 1.0f, 1.0f);
                    plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(k9, EntityType.ARMOR_STAND, Monster.K9, location));
                }
            }
        }
    }
}
