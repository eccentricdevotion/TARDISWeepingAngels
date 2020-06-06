package me.eccentric_nz.tardisweepingangels.utils;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class MonsterHeadEquipListener implements Listener {

    private final TARDISWeepingAngels plugin;

    public MonsterHeadEquipListener(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHelmetSlotEquip(InventoryClickEvent event) {
        Inventory inv = event.getClickedInventory();
        if (inv != null) {
            InventoryType inventoryType = inv.getType();
            if (inventoryType == InventoryType.PLAYER && event.getRawSlot() == 5) {
                ItemStack cursor = event.getCursor();
                if (cursor != null && cursor.hasItemMeta() && cursor.getItemMeta().getPersistentDataContainer().has(TARDISWeepingAngels.MONSTER_HEAD, PersistentDataType.INTEGER) && isNullOrAir(event.getCurrentItem())) {
                    event.setCurrentItem(cursor);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> event.setCursor(new ItemStack(Material.AIR)), 1L);
                    event.setCancelled(true);
                }
            }
        }
    }

    private boolean isNullOrAir(ItemStack item) {
        return item == null || item.getType().isAir();
    }
}
