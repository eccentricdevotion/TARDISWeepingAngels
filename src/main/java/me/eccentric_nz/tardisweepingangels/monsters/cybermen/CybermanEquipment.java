package me.eccentric_nz.tardisweepingangels.monsters.cybermen;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class CybermanEquipment {

	public static void set(LivingEntity le, boolean disguise) {
		ItemStack helmet = new ItemStack(Material.IRON_INGOT, 1);
		ItemStack arm = new ItemStack(Material.IRON_INGOT, 1);
		ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
		ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
		ItemMeta headMeta = helmet.getItemMeta();
		assert headMeta != null;
		headMeta.setDisplayName("Cyberman Head");
		headMeta.setCustomModelData(3);
		helmet.setItemMeta(headMeta);
		ItemMeta armMeta = arm.getItemMeta();
		assert armMeta != null;
		armMeta.setDisplayName("Cyberman Arm");
		armMeta.setCustomModelData(2);
		arm.setItemMeta(armMeta);
		ItemMeta chestMeta = chestplate.getItemMeta();
		assert chestMeta != null;
		chestMeta.setDisplayName("Cyberman Chest");
		if (disguise) {
			Damageable damageable = (Damageable) chestMeta;
			damageable.setDamage(235);
		}
		chestplate.setItemMeta(chestMeta);
		ItemMeta legMeta = leggings.getItemMeta();
		assert legMeta != null;
		legMeta.setDisplayName("Cyberman Legs");
		if (disguise) {
			Damageable legDamage = (Damageable) legMeta;
			legDamage.setDamage(220);
		}
		leggings.setItemMeta(legMeta);
		EntityEquipment ee = le.getEquipment();
		assert ee != null;
		ee.setHelmet(helmet);
		ee.setChestplate(chestplate);
		ee.setLeggings(leggings);
		ee.setBoots(null);
		if (!disguise) {
			ee.setItemInMainHandDropChance(0F);
			ee.setItemInOffHandDropChance(0F);
			ee.setItemInMainHand(arm);
			ee.setItemInOffHand(arm.clone());
			ee.setHelmetDropChance(0F);
			ee.setChestplateDropChance(0F);
			ee.setLeggingsDropChance(0F);
			le.setCanPickupItems(false);
			le.getPersistentDataContainer().set(TARDISWeepingAngels.CYBERMAN, PersistentDataType.INTEGER, Monster.CYBERMAN.getPersist());
		}
	}
}
