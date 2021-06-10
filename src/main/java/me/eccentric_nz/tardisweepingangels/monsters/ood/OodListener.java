package me.eccentric_nz.tardisweepingangels.monsters.ood;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class OodListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamageOod(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof ArmorStand stand && event.getDamager() instanceof Player) {
            if (stand.getPersistentDataContainer().has(TARDISWeepingAngels.OOD, PersistentDataType.INTEGER) && stand.getPersistentDataContainer().has(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID)) {
                event.setCancelled(true);
                Player player = (Player) event.getDamager();
                player.playSound(stand.getLocation(), "ood", 1.0f, 1.0f);
                if (!player.hasPermission("tardisweepingangels.ood")) {
                    return;
                }
                UUID oodId = stand.getPersistentDataContainer().get(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID);
                assert oodId != null;
                if (oodId.equals(player.getUniqueId())) {
                    EntityEquipment ee = stand.getEquipment();
                    if (ee != null) {
                        ItemStack head = ee.getHelmet();
                        assert head != null;
                        ItemMeta im = head.getItemMeta();
                        int rage = stand.getPersistentDataContainer().get(TARDISWeepingAngels.OOD, PersistentDataType.INTEGER);
                        assert im != null;
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
                        ee.setHelmet(head);
                        stand.getPersistentDataContainer().set(TARDISWeepingAngels.OOD, PersistentDataType.INTEGER, rage);
                    }
                } else if (oodId.equals(TARDISWeepingAngels.UNCLAIMED)) {
                    // claim the Ood
                    stand.getPersistentDataContainer().set(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID, player.getUniqueId());
                    player.sendMessage(TARDISWeepingAngels.plugin.pluginName + "You have claimed this Ood!");
                }
            }
        }
    }
}
