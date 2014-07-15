package me.eccentric_nz.tardisweepingangels.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class Sounds implements Listener {

    private final TARDISWeepingAngels plugin;
    private final List<UUID> tracker = new ArrayList<UUID>();

    public Sounds(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onTargetPlayer(EntityTargetLivingEntityEvent event) {
        final Entity ent = event.getEntity();
        final UUID uuid = ent.getUniqueId();
        if (tracker.contains(uuid)) {
            return;
        }
        if (ent instanceof Zombie) {
            Zombie zombie = (Zombie) ent;
            EntityEquipment ee = zombie.getEquipment();
            ItemStack head = ee.getHelmet();
            if (head != null && head.hasItemMeta() && head.getItemMeta().hasDisplayName()) {
                tracker.add(uuid);
                final LivingEntity le = event.getTarget();
                String dn = head.getItemMeta().getDisplayName();
                if (le instanceof Player) {
                    String tmp = "";
                    long delay = 50L;
                    if (zombie.isBaby() && dn.equals("Empty Child Head") && head.getType().equals(Material.IRON_HELMET)) {
                        tmp = "empty_child";
                    }
                    if (dn.equals("Cyberman Head") && head.getType().equals(Material.IRON_HELMET)) {
                        tmp = "cyberman";
                        delay = 80L;
                    }
                    if (dn.equals("Sontaran Head") && head.getType().equals(Material.GOLD_HELMET)) {
                        tmp = "sontaran";
                        delay = 55L;
                    }
                    if (dn.equals("Vashta Nerada Head") && head.getType().equals(Material.GOLD_HELMET)) {
                        tmp = "vashta";
                        delay = 30L;
                    }
                    if (!tmp.isEmpty()) {
                        final String sound = tmp;
                        // schedule delayed task
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            @Override
                            public void run() {
                                Player player = (Player) le;
                                player.playSound(ent.getLocation(), sound, 1.0f, 1.0f);
                                tracker.remove(uuid);
                            }
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
                final LivingEntity le = event.getTarget();
                if (le instanceof Player) {
                    long delay = 50L;
                    // schedule delayed task
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            Player player = (Player) le;
                            player.playSound(ent.getLocation(), "dalek", 1.0f, 1.0f);
                            tracker.remove(uuid);
                        }
                    }, delay);
                }
            }
        }
    }
//
//    private void fireArrow(Location first, Location second) {
//        Vector vector = second.toVector().subtract(first.toVector());
//        first.getWorld().spawnArrow(first, vector, 0.6f, 6.0f);
//    }
}
