package me.eccentric_nz.tardisweepingangels.utils;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Sounds implements Listener {

	private final TARDISWeepingAngels plugin;
	private final List<UUID> tracker = new ArrayList<>();

	public Sounds(TARDISWeepingAngels plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onTargetPlayer(EntityTargetLivingEntityEvent event) {
		Entity ent = event.getEntity();
		UUID uuid = ent.getUniqueId();
		if (tracker.contains(uuid)) {
			return;
		}
		if (ent instanceof Enderman) {
			if (ent.getPassengers().size() > 0 && ent.getPassengers().get(0).getType().equals(EntityType.GUARDIAN)) {
				tracker.add(uuid);
				LivingEntity le = event.getTarget();
				if (le instanceof Player) {
					long delay = 90L;
					// schedule delayed task
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
						Player player = (Player) le;
						player.playSound(ent.getLocation(), "silence", 1.0f, 1.0f);
						tracker.remove(uuid);
					}, delay);
				}
			}
		}
		if (ent instanceof Guardian) {
			if (ent.getVehicle() != null && ent.getVehicle().getType().equals(EntityType.ENDERMAN)) {
				tracker.add(uuid);
				LivingEntity le = event.getTarget();
				if (le instanceof Player) {
					long delay = 20L;
					// schedule delayed task
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
						Player player = (Player) le;
						player.playSound(ent.getLocation(), "silence", 1.0f, 1.0f);
						tracker.remove(uuid);
					}, delay);
				}
			}
		}
		if (ent instanceof PigZombie) {
			if (ent.getPersistentDataContainer().has(TARDISWeepingAngels.HATH, PersistentDataType.INTEGER)) {
				tracker.add(uuid);
				LivingEntity le = event.getTarget();
				if (le instanceof Player) {
					long delay = 100L;
					// schedule delayed task
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
						Player player = (Player) le;
						player.playSound(ent.getLocation(), "hath", 1.0f, 1.0f);
						tracker.remove(uuid);
					}, delay);
				}
			}
			if (ent.getPersistentDataContainer().has(TARDISWeepingAngels.WARRIOR, PersistentDataType.INTEGER)) {
				tracker.add(uuid);
				LivingEntity le = event.getTarget();
				if (le instanceof Player) {
					long delay = 50L;
					// schedule delayed task
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
						Player player = (Player) le;
						player.playSound(ent.getLocation(), "warrior", 1.0f, 1.0f);
						tracker.remove(uuid);
					}, delay);
				}
			}
		}
		if (ent instanceof Zombie zombie) {
			EntityEquipment ee = zombie.getEquipment();
			assert ee != null;
			ItemStack head = ee.getHelmet();
			if (head != null && head.hasItemMeta() && Objects.requireNonNull(head.getItemMeta()).hasDisplayName()) {
				tracker.add(uuid);
				LivingEntity le = event.getTarget();
				String dn = head.getItemMeta().getDisplayName();
				if (le instanceof Player) {
					String tmp = "";
					long delay = 50L;
					if (!zombie.isAdult() && dn.equals("Empty Child Head") && head.getType().equals(Material.SUGAR)) {
						tmp = "empty_child";
					}
					if (dn.equals("Cyberman Head") && head.getType().equals(Material.IRON_INGOT)) {
						tmp = "cyberman";
						delay = 80L;
					}
					if (dn.equals("Sontaran Head") && head.getType().equals(Material.POTATO)) {
						tmp = "sontaran";
						delay = 55L;
					}
					if (dn.equals("Vashta Nerada Head") && head.getType().equals(Material.BOOK)) {
						tmp = "vashta";
						delay = 30L;
					}
					if (dn.equals("Zygon Head") && head.getType().equals(Material.PAINTING)) {
						tmp = "zygon";
						delay = 100L;
					}
					if (!tmp.isEmpty()) {
						String sound = tmp;
						// schedule delayed task
						plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
							Player player = (Player) le;
							player.playSound(ent.getLocation(), sound, 1.0f, 1.0f);
							tracker.remove(uuid);
						}, delay);
					}
				}
			}
			return;
		}
		if (ent instanceof Skeleton) {
			PersistentDataContainer pdc = ent.getPersistentDataContainer();
			if (pdc.has(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER)) {
				tracker.add(uuid);
				LivingEntity le = event.getTarget();
				if (le instanceof Player) {
					long delay = 50L;
					// schedule delayed task
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
						Player player = (Player) le;
						player.playSound(ent.getLocation(), "dalek", 1.0f, 1.0f);
						tracker.remove(uuid);
					}, delay);
				}
			} else if (pdc.has(TARDISWeepingAngels.SILURIAN, PersistentDataType.INTEGER)) {
				tracker.add(uuid);
				LivingEntity le = event.getTarget();
				if (le instanceof Player) {
					long delay = 50L;
					// schedule delayed task
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
						Player player = (Player) le;
						player.playSound(ent.getLocation(), "silurian", 1.0f, 1.0f);
						tracker.remove(uuid);
					}, delay);
				}
			}
		}
	}
}
