package me.eccentric_nz.tardisweepingangels.monsters.empty_child;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EmptyChildEquipment {

	public static void set(LivingEntity le, boolean disguise) {
		ItemStack helmet = new ItemStack(Material.SUGAR, 1);
		ItemStack arm = new ItemStack(Material.SUGAR, 1);
		ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
		ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
		ItemMeta headMeta = helmet.getItemMeta();
		assert headMeta != null;
		headMeta.setDisplayName("Empty Child Head");
		headMeta.setCustomModelData(3);
		helmet.setItemMeta(headMeta);
		ItemMeta armMeta = arm.getItemMeta();
		assert armMeta != null;
		armMeta.setDisplayName("Empty Child Arm");
		armMeta.setCustomModelData(2);
		arm.setItemMeta(armMeta);
		ItemMeta chestMeta = chestplate.getItemMeta();
		assert chestMeta != null;
		chestMeta.setDisplayName("Empty Child Chest");
		if (disguise) {
			Damageable damageable = (Damageable) chestMeta;
			damageable.setDamage(235);
		}
		chestplate.setItemMeta(chestMeta);
		ItemMeta legMeta = leggings.getItemMeta();
		assert legMeta != null;
		legMeta.setDisplayName("Empty Child Legs");
		if (disguise) {
			Damageable legDamage = (Damageable) legMeta;
			legDamage.setDamage(220);
		}
		leggings.setItemMeta(legMeta);

		EntityEquipment ee = le.getEquipment();
		assert ee != null;
		ee.setChestplate(chestplate);
		ee.setLeggings(leggings);
		ee.setBoots(null);
		ee.setHelmet(helmet);
		if (!disguise) {
			PotionEffect p = new PotionEffect(PotionEffectType.SLOW, 360000, 1, true, false);
			le.removePotionEffect(PotionEffectType.SPEED);
			le.addPotionEffect(p);
			le.setCanPickupItems(false);
			ee.setItemInMainHand(arm);
			ee.setItemInOffHand(arm.clone());
			ee.setItemInMainHandDropChance(0F);
			ee.setItemInOffHandDropChance(0F);
			ee.setHelmetDropChance(0F);
			ee.setChestplateDropChance(0F);
			ee.setLeggingsDropChance(0F);
			le.getPersistentDataContainer().set(TARDISWeepingAngels.EMPTY, PersistentDataType.INTEGER, Monster.EMPTY_CHILD.getPersist());
		}
	}
}
