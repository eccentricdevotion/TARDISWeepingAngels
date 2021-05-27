package me.eccentric_nz.tardisweepingangels.monsters.ood;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class VillagerSpawnListener implements Listener {

	private final TARDISWeepingAngels plugin;

	public VillagerSpawnListener(TARDISWeepingAngels plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onVillagerSpawnEvent(CreatureSpawnEvent event) {
		Entity entity = event.getEntity();
		if (!entity.getType().equals(EntityType.VILLAGER)) {
			return;
		}
		World world = entity.getWorld();
		if (!plugin.getConfig().getBoolean("ood.worlds." + world.getName())) {
			return;
		}
		if (TARDISWeepingAngels.random.nextInt(100) < plugin.getConfig().getInt("ood.spawn_from_villager")) {
			Entity ood = world.spawnEntity(entity.getLocation(), EntityType.ARMOR_STAND);
			OodEquipment.set(null, ood, false);
			plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(ood, EntityType.ARMOR_STAND, Monster.OOD, entity.getLocation()));
			entity.remove();
		}
	}
}
