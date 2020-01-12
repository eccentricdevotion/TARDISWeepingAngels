package me.eccentric_nz.tardisweepingangels.monsters.k9;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class K9Recipe {

    private final TARDISWeepingAngels plugin;

    public K9Recipe(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    public void addRecipe() {
        ItemStack is = new ItemStack(Material.BONE);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("K9");
        im.setCustomModelData(1);
        is.setItemMeta(im);
        ShapedRecipe recipe = new ShapedRecipe(TARDISWeepingAngels.K9, is);
        recipe.shape("III", "RRR", "BBB");
        // set ingredients
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('B', Material.BONE);
        plugin.getServer().addRecipe(recipe);
    }
}
