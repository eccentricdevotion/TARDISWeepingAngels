/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.empty_child;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

/**
 * @author eccentric_nz
 */
public class GasMask implements Listener {

    private final TARDISWeepingAngels plugin;

    public GasMask(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!plugin.getEmpty().contains(uuid)) {
            return;
        }
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            PlayerInventory inv = player.getInventory();
            ItemStack helmet = inv.getHelmet();
            if (helmet != null) {
                // move it to the first free slot
                int free_slot = inv.firstEmpty();
                if (free_slot != -1) {
                    inv.setItem(free_slot, helmet);
                } else {
                    player.getWorld().dropItemNaturally(player.getLocation(), helmet);
                }
            }
            // set helmet to pumpkin
            inv.setHelmet(new ItemStack(Material.PUMPKIN, 1));
            player.updateInventory();
            // schedule delayed task
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                plugin.getEmpty().remove(uuid);
                plugin.getTimesUp().add(uuid);
            }, 600L);
        }, 5L);
    }

    @EventHandler
    public void onHelmetClick(InventoryClickEvent event) {
        if (event.getInventory().getType().equals(InventoryType.CRAFTING) && event.getRawSlot() == 5) {
            Player player = (Player) event.getWhoClicked();
            if (plugin.getEmpty().contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
            if (plugin.getTimesUp().contains(player.getUniqueId())) {
                event.setCancelled(true);
                player.getInventory().setHelmet(null);
                player.updateInventory();
                plugin.getTimesUp().remove(player.getUniqueId());
            }
        }
    }
}
