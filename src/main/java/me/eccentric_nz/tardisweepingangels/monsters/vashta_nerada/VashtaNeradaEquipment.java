package me.eccentric_nz.tardisweepingangels.monsters.vashta_nerada;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class VashtaNeradaEquipment {

	public static void set(LivingEntity le, boolean disguise) {
		ItemStack helmet = new ItemStack(Material.BOOK, 1);
		ItemStack arm = new ItemStack(Material.BOOK, 1);
		ItemStack chestplate = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
		ItemStack leggings = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
		ItemMeta headMeta = helmet.getItemMeta();
		assert headMeta != null;
		headMeta.setDisplayName("Vashta Nerada Head");
		headMeta.setCustomModelData(4);
		helmet.setItemMeta(headMeta);
		ItemMeta armMeta = arm.getItemMeta();
		assert armMeta != null;
		armMeta.setDisplayName("Vashta Nerada Arm");
		armMeta.setCustomModelData(3);
		arm.setItemMeta(armMeta);
		ItemMeta chestMeta = chestplate.getItemMeta();
		assert chestMeta != null;
		chestMeta.setDisplayName("Vashta Nerada Chest");
		if (disguise) {
			Damageable damageable = (Damageable) chestMeta;
			damageable.setDamage(235);
		}
		chestplate.setItemMeta(chestMeta);
		ItemMeta legMeta = leggings.getItemMeta();
		assert legMeta != null;
		legMeta.setDisplayName("Vashta Nerada Legs");
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
			ee.setItemInMainHand(arm);
			ee.setItemInOffHand(arm.clone());
			ee.setItemInMainHandDropChance(0F);
			ee.setItemInOffHandDropChance(0F);
			ee.setHelmetDropChance(0F);
			ee.setChestplateDropChance(0F);
			ee.setLeggingsDropChance(0F);
			le.setCanPickupItems(false);
			le.getPersistentDataContainer().set(TARDISWeepingAngels.VASHTA, PersistentDataType.INTEGER, Monster.VASHTA_NERADA.getPersist());
		}
	}
}
