package me.eccentric_nz.tardisweepingangels.monsters.headless_monks;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class HeadlessShootRunnable implements Runnable {

    private final Entity monk;
    private final Entity target;
    private final boolean fireball;

    public HeadlessShootRunnable(Entity monk, Entity target, boolean fireball) {
        this.monk = monk;
        this.target = target;
        this.fireball = fireball;
    }

    @Override
    public void run() {
        Vector direction = target.getLocation().toVector().subtract(monk.getLocation().toVector());
        direction.normalize();
        Vector bulletVelocity = direction.multiply(3.0d);
        if (fireball) {
            ((LivingEntity) monk).launchProjectile(SmallFireball.class, bulletVelocity);
        } else {
            Snowball snowball = ((LivingEntity) monk).launchProjectile(Snowball.class, bulletVelocity);
            snowball.setItem(new ItemStack(Material.FIRE, 1));
        }
    }
}
