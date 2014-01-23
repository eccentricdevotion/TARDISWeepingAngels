package me.eccentric_nz.tardisweepingangels;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TARDISWeepingAngelsUndisguise implements Listener {

    private final TARDISWeepingAngels plugin;

    public TARDISWeepingAngelsUndisguise(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onManualUndisguise(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        if (event.getSlotType().equals(SlotType.ARMOR)) {
            int slot = event.getRawSlot();
            if (slot > 4 && slot < 9) {
                ItemStack is = event.getCurrentItem();
                if (is != null) {
                    if (is.hasItemMeta()) {
                        ItemMeta im = is.getItemMeta();
                        if (im.hasDisplayName() && im.getDisplayName().startsWith("Weeping Angel")) {
                            event.setCancelled(true);
                            ((Player) event.getWhoClicked()).sendMessage(plugin.pluginName + "You must use the " + ChatColor.GREEN + "/angeldisguise off" + ChatColor.RESET + " command to remove this armour!");
                        }
                    }
                }
            }
        }
    }
}
