/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.weeping_angels;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
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

    private final TARDISWeepingAngels plugin;
    private final Material mat;
    private final List<World> angel_tp_worlds = new ArrayList<>();

    public Damage(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        mat = Material.valueOf(plugin.getConfig().getString("angels.weapon"));
        plugin.getConfig().getStringList("angels.teleport_worlds").forEach((w) -> {
            World world = plugin.getServer().getWorld(w);
            if (w != null) {
                angel_tp_worlds.add(world);
            }
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBeatUpAngel(EntityDamageByEntityEvent event) {
        EntityType et = event.getEntityType();
        if (et.equals(EntityType.SKELETON)) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            Entity e = event.getDamager();
            if (entity.getPersistentDataContainer().has(TARDISWeepingAngels.ANGEL, PersistentDataType.INTEGER)) {
                if (e instanceof AbstractArrow) {
                    event.setCancelled(true);
                }
                if (e instanceof Player) {
                    Player p = (Player) e;
                    if (!p.getInventory().getItemInMainHand().getType().equals(mat)) {
                        event.setCancelled(true);
                    }
                }
                return;
            }
            if (entity.getPersistentDataContainer().has(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER) && (e instanceof Player)) {
                ((Player) e).playSound(entity.getLocation(), "dalek_hit", 0.5f, 1.0f);
            }
        }
        if (et.equals(EntityType.PLAYER)) {
            Entity e = event.getDamager();
            if (e instanceof Skeleton) {
                if (e.getPersistentDataContainer().has(TARDISWeepingAngels.ANGEL, PersistentDataType.INTEGER)) {
                    Entity t = event.getEntity();
                    Player p = (Player) t;
                    Location l = getRandomLocation(t.getWorld());
                    if (l != null) {
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            p.teleport(l);
                        }, 1L);
                    }
                    p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 5, true, false));
                    if (plugin.angelsCanSteal()) {
                        stealKey(p);
                    }
                }
            }
        }
    }

    private Location getRandomLocation(World w) {
        // is this world an allowable world? - we don't want Nether or TARDIS worlds
        if (!angel_tp_worlds.contains(w)) {
            // get a random teleport world
            w = angel_tp_worlds.get(TARDISWeepingAngels.random.nextInt(angel_tp_worlds.size()));
        }
        Chunk[] chunks = w.getLoadedChunks();
        Chunk c = chunks[TARDISWeepingAngels.random.nextInt(chunks.length)];
        int x = c.getX() * 16 + TARDISWeepingAngels.random.nextInt(16);
        int z = c.getZ() * 16 + TARDISWeepingAngels.random.nextInt(16);
        int y = w.getHighestBlockYAt(x, z);
        return new Location(w, x, y + 1, z);
    }

    private void stealKey(Player p) {
        // only works if the item is named "TARDIS Key"
        PlayerInventory inv = p.getInventory();
        for (ItemStack stack : inv.getContents()) {
            if (stack != null) {
                if (stack.hasItemMeta()) {
                    ItemMeta im = stack.getItemMeta();
                    if (im.hasDisplayName() && im.getDisplayName().equals("TARDIS Key")) {
                        int amount = stack.getAmount();
                        if (amount > 1) {
                            stack.setAmount(amount - 1);
                        } else {
                            int slot = inv.first(stack);
                            inv.setItem(slot, new ItemStack(Material.AIR));
                        }
                        p.updateInventory();
                        p.sendMessage(plugin.pluginName + "The Weeping Angels stole your TARDIS Key");
                        break;
                    }
                }
            }
        }
    }
}
