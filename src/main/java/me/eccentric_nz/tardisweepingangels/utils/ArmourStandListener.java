package me.eccentric_nz.tardisweepingangels.utils;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class ArmourStandListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onManipulateFakeMonster(PlayerArmorStandManipulateEvent event) {
		if (event.getRightClicked().getPersistentDataContainer().has(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID)) {
			event.setCancelled(true);
		}
	}
}
