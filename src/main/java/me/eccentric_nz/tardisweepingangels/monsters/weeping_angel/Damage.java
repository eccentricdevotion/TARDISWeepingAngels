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
package me.eccentric_nz.tardisweepingangels.monsters.weeping_angel;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.utils.MonsterTargetListener;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eccentric_nz
 */
public class Damage implements Listener {

    private final TardisWeepingAngelsPlugin plugin;
    private final Material material;
    private final List<World> angel_tp_worlds = new ArrayList<>();

    public Damage(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
        material = Material.valueOf(plugin.getConfig().getString("angels.weapon"));
        plugin.getConfig().getStringList("angels.teleport_worlds").forEach((w) -> {
            World world = plugin.getServer().getWorld(w);
            angel_tp_worlds.add(world);
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBeatUpAngel(EntityDamageByEntityEvent event) {
        EntityType entityType = event.getEntityType();
        if (entityType.equals(EntityType.SKELETON)) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            Entity damager = event.getDamager();
            if (entity.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.WEEPING_ANGEL, PersistentDataType.INTEGER)) {
                if (damager instanceof AbstractArrow) {
                    event.setCancelled(true);
                }
                if (damager instanceof Player player) {
                    if (!player.getInventory().getItemInMainHand().getType().equals(material)) {
                        event.setCancelled(true);
                    }
                }
                return;
            }
            if (entity.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.DALEK, PersistentDataType.INTEGER) && (damager instanceof Player)) {
                ((Player) damager).playSound(entity.getLocation(), "dalek_hit", 0.5f, 1.0f);
            }
        }
        if (entityType.equals(EntityType.PLAYER)) {
            Entity damager = event.getDamager();
            if (damager instanceof Monster && MonsterTargetListener.monsterShouldIgnorePlayer(damager, (Player) event.getEntity())) {
                event.setCancelled(true);
                ((Monster) damager).setTarget(null);
                return;
            }
            if (damager instanceof Skeleton) {
                if (damager.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.WEEPING_ANGEL, PersistentDataType.INTEGER)) {
                    Entity entity = event.getEntity();
                    Player player = (Player) entity;
                    Location location = getRandomLocation(entity.getWorld());
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> player.teleport(location), 1L);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 5, true, false));
                    if (plugin.angelsCanSteal()) {
                        stealKey(player);
                    }
                }
            }
        }
    }

    private Location getRandomLocation(World world) {
        // is this world an allowable world? - we don't want Nether or TARDIS worlds
        if (!angel_tp_worlds.contains(world)) {
            // get a random teleport world
            world = angel_tp_worlds.get(TardisWeepingAngelsPlugin.random.nextInt(angel_tp_worlds.size()));
        }
        Chunk[] chunks = world.getLoadedChunks();
        Chunk chunk = chunks[TardisWeepingAngelsPlugin.random.nextInt(chunks.length)];
        int x = chunk.getX() * 16 + TardisWeepingAngelsPlugin.random.nextInt(16);
        int z = chunk.getZ() * 16 + TardisWeepingAngelsPlugin.random.nextInt(16);
        int y = world.getHighestBlockYAt(x, z);
        return new Location(world, x, y + 1, z);
    }

    private void stealKey(Player player) {
        // only works if the item is named "TARDIS Key"
        PlayerInventory inventory = player.getInventory();
        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack != null) {
                if (itemStack.hasItemMeta()) {
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    assert itemMeta != null;
                    if (itemMeta.hasDisplayName() && itemMeta.getDisplayName().equals("TARDIS Key")) {
                        int amount = itemStack.getAmount();
                        if (amount > 1) {
                            itemStack.setAmount(amount - 1);
                        } else {
                            int slot = inventory.first(itemStack);
                            inventory.setItem(slot, new ItemStack(Material.AIR));
                        }
                        player.updateInventory();
                        player.sendMessage(plugin.pluginName + "The Weeping Angels stole your TARDIS Key");
                        break;
                    }
                }
            }
        }
    }
}
