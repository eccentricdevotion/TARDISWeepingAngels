/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.weepingangel;

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
    private final List<World> angelTpWorlds = new ArrayList<>();

    public Damage(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
        material = Material.valueOf(plugin.getConfig().getString("angels.weapon"));
        plugin.getConfig().getStringList("angels.teleport_worlds").forEach((worldName) -> {
            World world = plugin.getServer().getWorld(worldName);
            if (worldName != null) {
                angelTpWorlds.add(world);
            }
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBeatUpAngel(EntityDamageByEntityEvent event) {
        EntityType entityType = event.getEntityType();
        if (entityType.equals(EntityType.SKELETON)) {
            LivingEntity livingEntity = (LivingEntity) event.getEntity();
            Entity entity = event.getDamager();
            if (livingEntity.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.weepingAngel, PersistentDataType.INTEGER)) {
                if (entity instanceof AbstractArrow) {
                    event.setCancelled(true);
                }
                if (entity instanceof Player player) {
                    if (!player.getInventory().getItemInMainHand().getType().equals(material)) {
                        event.setCancelled(true);
                    }
                }
                return;
            }
            if (livingEntity.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.dalek, PersistentDataType.INTEGER) && (entity instanceof Player player)) {
                player.playSound(livingEntity.getLocation(), "dalek_hit", 0.5f, 1.0f);
            }
        }
        if (entityType.equals(EntityType.PLAYER)) {
            Entity entity = event.getDamager();
            if (entity instanceof Monster monster && MonsterTargetListener.monsterShouldIgnorePlayer(entity, (Player) event.getEntity())) {
                event.setCancelled(true);
                monster.setTarget(null);
                return;
            }
            if (entity instanceof Skeleton) {
                if (entity.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.weepingAngel, PersistentDataType.INTEGER)) {
                    Entity target = event.getEntity();
                    Player player = (Player) target;
                    Location location = getRandomLocation(target.getWorld());
                    if (location != null) {
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> player.teleport(location), 1L);
                    }
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
        if (!angelTpWorlds.contains(world)) {
            // get a random teleport world
            world = angelTpWorlds.get(TardisWeepingAngelsPlugin.random.nextInt(angelTpWorlds.size()));
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
