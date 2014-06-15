package me.eccentric_nz.tardisweepingangels;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class TARDISWeepingAngelsTarget implements Listener {

    private final TARDISWeepingAngels plugin;
    private final List<UUID> tracker = new ArrayList<UUID>();

    public TARDISWeepingAngelsTarget(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onTargetPlayer(EntityTargetLivingEntityEvent event) {
        final Entity ent = event.getEntity();
        final UUID uuid = ent.getUniqueId();
        if (ent instanceof Zombie && !tracker.contains(uuid)) {
            Zombie zombie = (Zombie) ent;
            EntityEquipment ee = zombie.getEquipment();
            ItemStack head = ee.getHelmet();
            if (head != null && head.hasItemMeta() && head.getItemMeta().hasDisplayName() && head.getType().equals(Material.IRON_HELMET)) {
                tracker.add(uuid);
                final LivingEntity le = event.getTarget();
                String dn = head.getItemMeta().getDisplayName();
                if (le instanceof Player) {
                    String tmp = "";
                    long delay = 50L;
                    if (zombie.isBaby() && dn.equals("Empty Child Head")) {
                        tmp = "empty_child";
                    }
                    if (dn.equals("Cyberman Head")) {
                        tmp = "cyberman";
                        delay = 80L;
                    }
                    if (!tmp.isEmpty()) {
                        final String sound = tmp;
                        // schedule delayed task
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            @Override
                            public void run() {
                                Player player = (Player) le;
                                player.playSound(ent.getLocation(), sound, 1.0F, 1.0F);
                                tracker.remove(uuid);
                            }
                        }, delay);
                    }
                }
            }
        }
        if (ent instanceof Skeleton && !tracker.contains(uuid)) {
            Skeleton dalek = (Skeleton) ent;
            if (dalek.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
                tracker.add(uuid);
                final LivingEntity le = event.getTarget();
                if (le instanceof Player) {
                    long delay = 50L;
                    // schedule delayed task
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            Player player = (Player) le;
                            player.playSound(ent.getLocation(), "dalek", 1.0F, 1.0F);
                            tracker.remove(uuid);
                        }
                    }, delay);
                }
            }
        }
        if (ent instanceof PigZombie && !tracker.contains(uuid)) {
            PigZombie pigz = (PigZombie) ent;
            EntityEquipment ee = pigz.getEquipment();
            ItemStack head = ee.getHelmet();
            if (head != null && head.getType().equals(Material.GOLD_HELMET) && head.hasItemMeta() && head.getItemMeta().hasDisplayName() && head.getItemMeta().getDisplayName().startsWith("Sontaran")) {
                tracker.add(uuid);
                final LivingEntity le = event.getTarget();
                if (le instanceof Player) {
                    // schedule delayed task
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            Player player = (Player) le;
                            player.playSound(ent.getLocation(), "sontaran", 1.0F, 1.0F);
                            tracker.remove(uuid);
                        }
                    }, 55L);
                }
            }
        }
    }

    private void fireArrow(Location first, Location second) {
        Vector vector = second.toVector().subtract(first.toVector());
        first.getWorld().spawnArrow(first, vector, 0.6f, 6.0f);
    }
}
