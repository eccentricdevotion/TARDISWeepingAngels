/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.weeping_angels;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author eccentric_nz
 */
public class Damage implements Listener {

    private final TARDISWeepingAngels plugin;
    private final Material mat;

    public Damage(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.mat = Material.valueOf(plugin.getConfig().getString("angels.weapon"));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBeatUpAngel(EntityDamageByEntityEvent event) {
        EntityType et = event.getEntityType();
        if (et.equals(EntityType.SKELETON)) {
            EntityEquipment ee = ((LivingEntity) event.getEntity()).getEquipment();
            Entity e = event.getDamager();
            if (ee.getHelmet().getType().equals(Material.WATER_LILY)) {
                if (e instanceof Arrow) {
                    event.setCancelled(true);
                }
                if (e instanceof Player) {
                    Player p = (Player) e;
                    if (!p.getItemInHand().getType().equals(mat)) {
                        event.setCancelled(true);
                    }
                }
                return;
            }
            Entity ent = event.getEntity();
            if (ee.getHelmet().getType().equals(Material.VINE) && DisguiseAPI.isDisguised(ent)) {
                if (e instanceof Player) {
                    ((Player) e).playSound(ent.getLocation(), "dalek_hit", 0.5f, 1.0f);
                }
            }
        }
        if (et.equals(EntityType.PLAYER)) {
            Entity e = event.getDamager();
            if (e instanceof Skeleton) {
                EntityEquipment ee = ((LivingEntity) e).getEquipment();
                if (ee.getHelmet().getType().equals(Material.WATER_LILY)) {
                    Entity t = event.getEntity();
                    Player p = (Player) t;
                    p.teleport(getRandomLocation(t.getWorld()));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 5));
                    if (plugin.angelsCanSteal()) {
                        stealKey(p);
                    }
                }
            }
        }
    }

    private Location getRandomLocation(World w) {
        Chunk[] chunks = w.getLoadedChunks();
        Chunk c = chunks[plugin.getRandom().nextInt(chunks.length)];
        int x = c.getX() * 16 + plugin.getRandom().nextInt(16);
        int z = c.getZ() * 16 + plugin.getRandom().nextInt(16);
        int y = w.getHighestBlockYAt(x, z);
        return new Location(w, x, y + 1, z);
    }

    @SuppressWarnings("deprecation")
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
