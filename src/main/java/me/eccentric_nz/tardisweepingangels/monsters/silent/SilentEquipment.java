package me.eccentric_nz.tardisweepingangels.monsters.silent;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class SilentEquipment {

	public static void set(LivingEntity le, boolean disguise) {
		if (!disguise) {
			LivingEntity g = (LivingEntity) Objects.requireNonNull(le.getLocation().getWorld()).spawnEntity(le.getLocation(), EntityType.GUARDIAN);
			g.setSilent(true);
			PotionEffect p = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false);
			g.addPotionEffect(p);
			g.getPersistentDataContainer().set(TARDISWeepingAngels.SILENT, PersistentDataType.INTEGER, Monster.SILENT.getPersist());
			le.addPassenger(g);
			le.getPersistentDataContainer().set(TARDISWeepingAngels.SILENT, PersistentDataType.INTEGER, Monster.SILENT.getPersist());
		} else {
			ItemStack head = new ItemStack(Material.END_STONE);
			ItemMeta headMeta = head.getItemMeta();
			assert headMeta != null;
			headMeta.setDisplayName("Silent Head");
			headMeta.setCustomModelData((TARDISWeepingAngels.random.nextBoolean()) ? 3 : 2);
			head.setItemMeta(headMeta);
			Player p = (Player) le;
			p.getInventory().setHelmet(head);
			PotionEffect potionEffect = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false);
			p.addPotionEffect(potionEffect);
		}
	}
}
