/*
 * Copyright (C) 2021 eccentric_nz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package me.eccentric_nz.tardisweepingangels.monsters.judoon;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class JudoonAmmoRecipe {

    private final TardisWeepingAngelsPlugin plugin;

    public JudoonAmmoRecipe(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    public void addRecipe() {
        ItemStack itemStack = new ItemStack(Material.ARROW, 2);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Judoon Ammunition");
        itemMeta.setCustomModelData(13);
        itemStack.setItemMeta(itemMeta);
        NamespacedKey namespacedKey = new NamespacedKey(plugin, "judoon_ammunition");
        ShapelessRecipe recipe = new ShapelessRecipe(namespacedKey, itemStack);
        recipe.addIngredient(Material.ARROW);
        recipe.addIngredient(Material.GUNPOWDER);
        plugin.getServer().addRecipe(recipe);
    }
}
