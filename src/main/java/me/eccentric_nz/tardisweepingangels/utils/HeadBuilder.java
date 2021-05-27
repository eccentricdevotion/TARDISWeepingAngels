package me.eccentric_nz.tardisweepingangels.utils;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class HeadBuilder {

	public static ItemStack getItemStack(Monster monster) {
		Material material = null;
		int cmd = 3;
		if (monster != Monster.K9 && monster != Monster.TOCLAFANE) {
			material = monster.getMaterial();
			cmd = monster.getHeadModelData();
		}
		if (material == null) {
			return null;
		}
		ItemStack is = new ItemStack(material, 1);
		ItemMeta im = is.getItemMeta();
		assert im != null;
		im.getPersistentDataContainer().set(TARDISWeepingAngels.MONSTER_HEAD, PersistentDataType.INTEGER, 99);
		im.setDisplayName(monster.getName() + " Head");
		im.setCustomModelData(cmd);
		is.setItemMeta(im);
		return is;
	}
}
