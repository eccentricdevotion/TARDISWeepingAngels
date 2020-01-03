package me.eccentric_nz.tardisweepingangels.monsters.judoon;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public class JudoonWalkRunnable implements Runnable {

    private final int[] walkCycle = new int[]{2, 5, 6, 5, 2, 7, 8, 7};
    private final ArmorStand stand;
    private final double speed;
    private final Player player;
    private int i = 0;

    public JudoonWalkRunnable(ArmorStand stand, double speed, Player player) {
        this.stand = stand;
        this.speed = speed;
        this.player = player;
    }

    @Override
    public void run() {
        Location location = stand.getLocation();
        Vector pos = location.toVector();
        if (player != null) {
            ItemStack head = stand.getHelmet();
            ItemMeta im = head.getItemMeta();
            im.setCustomModelData(walkCycle[i]);
            head.setItemMeta(im);
            stand.setHelmet(head);
            BoundingBox asBox = stand.getBoundingBox();
            BoundingBox pBox = player.getBoundingBox().expand(1.0);
            if (!asBox.overlaps(pBox)) {
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
