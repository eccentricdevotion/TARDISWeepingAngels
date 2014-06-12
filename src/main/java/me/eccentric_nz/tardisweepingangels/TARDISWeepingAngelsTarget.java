package me.eccentric_nz.tardisweepingangels;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class TARDISWeepingAngelsTarget implements Listener {

    private final TARDISWeepingAngels plugin;
    private final List<UUID> tracker = new ArrayList<UUID>();

    public TARDISWeepingAngelsTarget(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTargetPlayer(EntityTargetLivingEntityEvent event) {
        plugin.debug("targeting");
        Entity ent = event.getEntity();
        final UUID uuid = ent.getUniqueId();
        if (ent instanceof Zombie && !tracker.contains(uuid)) {
            plugin.debug("entity not in tracker");
            Zombie zombie = (Zombie) ent;
            EntityEquipment ee = zombie.getEquipment();
            ItemStack head = ee.getHelmet();
            if (head != null && head.hasItemMeta() && head.getItemMeta().hasDisplayName() && head.getType().equals(Material.IRON_HELMET)) {
                tracker.add(uuid);
                final LivingEntity le = event.getTarget();
                String dn = head.getItemMeta().getDisplayName();
                plugin.debug(dn);
                if (le instanceof Player) {
                    String tmp = "";
                    long delay = 30L;
                    if (zombie.isBaby() && dn.equals("Empty Child Head")) {
                        tmp = "are_you_my_mummy";
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
                            @SuppressWarnings("deprecation")
                            public void run() {
                                Player player = (Player) le;
                                player.playSound(player.getLocation(), sound, 1.0F, 1.0F);
                                tracker.remove(uuid);
                            }
                        }, delay);
                    }
                }
            }
        }
    }
}
