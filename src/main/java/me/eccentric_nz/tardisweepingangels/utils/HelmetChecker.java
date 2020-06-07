/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.utils;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eccentric_nz
 */
public class HelmetChecker implements Listener {

    private final List<EntityType> heads = new ArrayList<>();

    public HelmetChecker() {
        heads.add(EntityType.PIG_ZOMBIE);
        heads.add(EntityType.SKELETON);
        heads.add(EntityType.ZOMBIE);
    }

    @EventHandler
    public void onLoseHead(EntityCombustEvent event) {
        Entity e = event.getEntity();
        EntityType et = e.getType();
        EntityEquipment ee;
        if (heads.contains(et)) {
            switch (et) {
                case PIG_ZOMBIE:
                    PigZombie pz = (PigZombie) e;
                    ee = pz.getEquipment();
                    break;
                case SKELETON:
                    Skeleton s = (Skeleton) e;
                    ee = s.getEquipment();
                    break;
                default:
                    Zombie z = (Zombie) e;
                    ee = z.getEquipment();
                    break;
            }
            // check chestplate
            ItemStack c = ee.getChestplate();
            if (c.hasItemMeta() && c.getItemMeta().hasDisplayName()) {
                String dn = c.getItemMeta().getDisplayName();
                if (dn.startsWith("Cyberman") || dn.startsWith("Ice") || dn.startsWith("Silurian") || dn.startsWith("Sontaran") || dn.startsWith("Vashta") || dn.startsWith("Zygon")) {
                    event.setCancelled(true);
                    // restore head
                    ItemStack helmet;
                    String name;
                    Monster monster;
                    if (dn.startsWith("Cyberman")) {
                        helmet = new ItemStack(Material.IRON_INGOT, 1);
                        name = "Cyberman Head";
                        monster = Monster.CYBERMAN;
                    } else if (dn.startsWith("Hath")) {
                        helmet = new ItemStack(Material.PUFFERFISH, 1);
                        name = "Hath Head";
                        monster = Monster.HATH;
                    } else if (dn.startsWith("Ice")) {
                        helmet = new ItemStack(Material.SNOWBALL, 1);
                        name = "Ice Warrior Head";
                        monster = Monster.ICE_WARRIOR;
                    } else if (dn.startsWith("Silurian")) {
                        helmet = new ItemStack(Material.FEATHER, 1);
                        name = "Silurian Head";
                        monster = Monster.SILURIAN;
                    } else if (dn.startsWith("Sontaran")) {
                        helmet = new ItemStack(Material.POTATO, 1);
                        name = "Sontaran Head";
                        monster = Monster.SONTARAN;
                    } else if (dn.startsWith("Vashta")) {
                        helmet = new ItemStack(Material.BOOK, 1);
                        name = "Vashta Nerada Head";
                        monster = Monster.VASHTA_NERADA;
                    } else {
                        helmet = new ItemStack(Material.PAINTING, 1);
                        name = "Zygon Head";
                        monster = Monster.ZYGON;
                    }
                    ItemMeta headMeta = helmet.getItemMeta();
                    headMeta.setDisplayName(name);
                    headMeta.setCustomModelData(monster.getCustomModelData());
                    helmet.setItemMeta(headMeta);
                    ee.setHelmet(helmet);
                    ee.setHelmetDropChance(0F);
                }
            }
        }
    }
}
