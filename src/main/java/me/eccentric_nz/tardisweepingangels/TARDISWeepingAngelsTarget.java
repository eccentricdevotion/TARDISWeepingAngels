package me.eccentric_nz.tardisweepingangels;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
        plugin.debug("entity targeted entity");
        Entity ent = event.getEntity();
        final UUID uuid = ent.getUniqueId();
        if (ent instanceof Zombie && !tracker.contains(uuid)) {
            plugin.debug("It was a zombie");
            tracker.add(uuid);
            Zombie zombie = (Zombie) ent;
            EntityEquipment ee = zombie.getEquipment();
            ItemStack head = ee.getHelmet();
            plugin.debug("head was " + head.getType());
            if (head.hasItemMeta() && head.getItemMeta().hasDisplayName()) {
                plugin.debug("it has a named helmet");
                final LivingEntity le = event.getTarget();
                if (le instanceof Player) {
                    plugin.debug("it targeted a player");
                    String tmp = "";
                    long delay = 30L;
                    if (head.getItemMeta().getDisplayName().startsWith("Empty Child")) {
                        plugin.debug("it was an empty child");
                        tmp = "are_you_my_mummy";
                    } else if (head.getItemMeta().getDisplayName().startsWith("Cyberman")) {
                        plugin.debug("it was a cyberman");
                        tmp = "cyberman";
                        delay = 80L;
                    }
                    // schedule delayed task
                    if (!tmp.isEmpty()) {
                        final String sound = tmp;
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
