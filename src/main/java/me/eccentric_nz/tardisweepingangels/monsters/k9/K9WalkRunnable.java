package me.eccentric_nz.tardisweepingangels.monsters.k9;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public class K9WalkRunnable implements Runnable {

    private final int[] walkCycle = new int[]{1, 2, 3, 2, 1, 4, 5, 4};
    private final ArmorStand stand;
    private final double speed;
    private final Player player;
    private int i = 0;

    public K9WalkRunnable(ArmorStand stand, double speed, Player player) {
        this.stand = stand;
        this.speed = speed;
        this.player = player;
    }

    @Override
    public void run() {
        Location location = stand.getLocation();
        Vector pos = location.toVector();
        if (player != null) {
            EntityEquipment ee = stand.getEquipment();
            if (ee != null) {
                ItemStack head = ee.getHelmet();
                ItemMeta im = head.getItemMeta();
                im.setCustomModelData(walkCycle[i]);
                head.setItemMeta(im);
                ee.setHelmet(head);
                BoundingBox asBox = stand.getBoundingBox();
                BoundingBox pBox = player.getBoundingBox().expand(1.0);
                if (!asBox.overlaps(pBox) && location.getWorld() == player.getWorld()) {
                    Vector target = player.getLocation().toVector();
                    Vector velocity = target.subtract(pos);
                    stand.setVelocity(velocity.normalize().multiply(speed));
                    location.setDirection(velocity);
                    stand.teleport(location);
                    i++;
                    if (i == walkCycle.length) {
                        i = 0;
                    }
                } else {
                    i = 0;
                }
            }
        }
    }
}
