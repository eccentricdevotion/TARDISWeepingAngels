/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.utils;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Material;

/**
 * @author eccentric_nz
 */
public enum Monster {

	CYBERMAN("Cyberman", "cyberman", 2, 4, Material.IRON_INGOT),
	DALEK("Dalek", "dalek", 3, 10000005, Material.SLIME_BALL, 10000004),
	EMPTY_CHILD("Empty Child", "empty", 4, 4, Material.SUGAR),
	HATH("Hath", "hath", 16, 5, Material.PUFFERFISH, 4),
	ICE_WARRIOR("Ice Warrior", "ice", 5, 5, Material.SNOWBALL, 4),
	JUDOON("Judoon", "judoon", 14, 10, Material.YELLOW_DYE, 11),
	K9("K9", "k9", 15, 1, Material.BONE),
	OOD("Ood", "ood", 12, 29, Material.ROTTEN_FLESH, 30),
	SILENT("Silent", "silent", 6, 3, Material.END_STONE, 5),
	SILURIAN("Silurian", "silurian", 7, 4, Material.FEATHER),
	SONTARAN("Sontaran", "sontaran", 8, 5, Material.POTATO, 4),
	STRAX("Strax", "strax", 9, 4, Material.BAKED_POTATO),
	TOCLAFANE("Toclafane", "toclafane", 13, 2, Material.GUNPOWDER),
	VASHTA_NERADA("Vashta Nerada", "vashta", 10, 5, Material.BOOK, 4),
	WEEPING_ANGEL("Weeping Angel", "angel", 1, 5, Material.BRICK),
	ZYGON("Zygon", "zygon", 11, 4, Material.PAINTING);

	private final String name;
	private final String permission;
	private final int persist;
	private final int customModelData;
	private final Material material;
	private final int headModelData;

	Monster(String name, String permission, int persist, int customModelData, Material material) {
		this.name = name;
		this.permission = permission;
		this.persist = persist;
		this.customModelData = customModelData;
		this.material = material;
		headModelData = 3;
	}

	Monster(String name, String permission, int persist, int customModelData, Material material, int headModelData) {
		this.name = name;
		this.permission = permission;
		this.persist = persist;
		this.customModelData = customModelData;
		this.material = material;
		this.headModelData = headModelData;
	}

	public String getName() {
		return name;
	}

	public String getPermission() {
		return permission;
	}

	public int getPersist() {
		return persist;
	}

	public int getCustomModelData() {
		return (this == DALEK) ? customModelData + TARDISWeepingAngels.random.nextInt(16) : customModelData;
	}

	public Material getMaterial() {
		return material;
	}

	public int getHeadModelData() {
		return headModelData;
	}
}
