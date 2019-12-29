package me.eccentric_nz.tardisweepingangels.utils;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
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
            if (ent.getPassenger() != null && ent.getPassenger().getType().equals(EntityType.GUARDIAN)) {
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
        if (ent instanceof Zombie) {
            Zombie zombie = (Zombie) ent;
            EntityEquipment ee = zombie.getEquipment();
            ItemStack head = ee.getHelmet();
            if (head != null && head.hasItemMeta() && head.getItemMeta().hasDisplayName()) {
                tracker.add(uuid);
                LivingEntity le = event.getTarget();
                String dn = head.getItemMeta().getDisplayName();
                if (le instanceof Player) {
                    String tmp = "";
                    long delay = 50L;
                    if (zombie.isBaby() && dn.equals("Empty Child Head") && head.getType().equals(Material.SUGAR)) {
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
            Skeleton dalek = (Skeleton) ent;
            if (dalek.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
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
            }
        }
    }
}
