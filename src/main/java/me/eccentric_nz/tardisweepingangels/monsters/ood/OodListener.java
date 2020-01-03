package me.eccentric_nz.tardisweepingangels.monsters.ood;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class OodListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onManipulate(PlayerArmorStandManipulateEvent event) {
        if (event.getRightClicked().getPersistentDataContainer().has(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof ArmorStand && event.getDamager() instanceof Player) {
            ArmorStand stand = (ArmorStand) event.getEntity();
            if (stand.getPersistentDataContainer().has(TARDISWeepingAngels.OOD, PersistentDataType.INTEGER) && stand.getPersistentDataContainer().has(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID)) {
                event.setCancelled(true);
                Player player = (Player) event.getDamager();
                player.playSound(stand.getLocation(), "ood", 1.0f, 1.0f);
                if (!player.hasPermission("tardisweepingangels.ood")) {
                    return;
                }
                UUID oodId = stand.getPersistentDataContainer().get(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID);
                if (oodId.equals(player.getUniqueId())) {
                    ItemStack head = stand.getHelmet();
                    ItemMeta im = head.getItemMeta();
                    int rage = stand.getPersistentDataContainer().get(TARDISWeepingAngels.OOD, PersistentDataType.INTEGER);
                    int cmd = im.getCustomModelData();
                    if (rage == 1) {
                        cmd -= 100;
                        rage = 0;
                    } else {
                        cmd += 100;
                        rage = 1;
                    }
                    im.setCustomModelData(cmd);
                    head.setItemMeta(im);
                    stand.setHelmet(head);
                    stand.getPersistentDataContainer().set(TARDISWeepingAngels.OOD, PersistentDataType.INTEGER, rage);
                }
            }
        }
    }
}
