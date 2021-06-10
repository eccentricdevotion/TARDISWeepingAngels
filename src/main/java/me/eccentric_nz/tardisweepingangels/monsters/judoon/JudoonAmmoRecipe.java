package me.eccentric_nz.tardisweepingangels.monsters.judoon;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class JudoonAmmoRecipe {

    private final TARDISWeepingAngels plugin;

    public JudoonAmmoRecipe(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    public void addRecipe() {
        ItemStack is = new ItemStack(Material.ARROW, 2);
        ItemMeta im = is.getItemMeta();
        assert im != null;
        im.setDisplayName("Judoon Ammunition");
        im.setCustomModelData(13);
        is.setItemMeta(im);
        NamespacedKey key = new NamespacedKey(plugin, "judoon_ammunition");
        ShapelessRecipe r = new ShapelessRecipe(key, is);
        r.addIngredient(Material.ARROW);
        r.addIngredient(Material.GUNPOWDER);
        plugin.getServer().addRecipe(r);
    }
}
