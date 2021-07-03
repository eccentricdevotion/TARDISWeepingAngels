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
import java.util.Objects;

/**
 * @author eccentric_nz
 */
public class HelmetChecker implements Listener {

    private final List<EntityType> heads = new ArrayList<>();

    public HelmetChecker() {
        heads.add(EntityType.ZOMBIFIED_PIGLIN);
        heads.add(EntityType.SKELETON);
        heads.add(EntityType.ZOMBIE);
    }

    @EventHandler
    public void onLoseHead(EntityCombustEvent event) {
        Entity entity = event.getEntity();
        EntityType entityType = entity.getType();
        EntityEquipment entityEquipment;
        if (heads.contains(entityType)) {
            switch (entityType) {
                case ZOMBIFIED_PIGLIN -> {
                    PigZombie pigZombie = (PigZombie) entity;
                    entityEquipment = pigZombie.getEquipment();
                }
                case SKELETON -> {
                    Skeleton skeleton = (Skeleton) entity;
                    entityEquipment = skeleton.getEquipment();
                }
                default -> {
                    Zombie zombie = (Zombie) entity;
                    entityEquipment = zombie.getEquipment();
                }
            }
            // check chestplate
            ItemStack chestplate = entityEquipment.getChestplate();
            if (chestplate.hasItemMeta() && chestplate.getItemMeta().hasDisplayName()) {
                String displayName = chestplate.getItemMeta().getDisplayName();
                if (displayName.startsWith("Cyberman") || displayName.startsWith("Ice") || displayName.startsWith("Silurian") || displayName.startsWith("Sontaran") || displayName.startsWith("Vashta") || displayName.startsWith("Zygon")) {
                    event.setCancelled(true);
                    // restore head
                    ItemStack helmet;
                    String name;
                    Monster monster;
                    if (displayName.startsWith("Cyberman")) {
                        helmet = new ItemStack(Material.IRON_INGOT, 1);
                        name = "Cyberman Head";
                        monster = Monster.CYBERMAN;
                    } else if (displayName.startsWith("Hath")) {
                        helmet = new ItemStack(Material.PUFFERFISH, 1);
                        name = "Hath Head";
                        monster = Monster.HATH;
                    } else if (displayName.startsWith("Ice")) {
                        helmet = new ItemStack(Material.SNOWBALL, 1);
                        name = "Ice Warrior Head";
                        monster = Monster.ICE_WARRIOR;
                    } else if (displayName.startsWith("Silurian")) {
                        helmet = new ItemStack(Material.FEATHER, 1);
                        name = "Silurian Head";
                        monster = Monster.SILURIAN;
                    } else if (displayName.startsWith("Sontaran")) {
                        helmet = new ItemStack(Material.POTATO, 1);
                        name = "Sontaran Head";
                        monster = Monster.SONTARAN;
                    } else if (displayName.startsWith("Vashta")) {
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
                    entityEquipment.setHelmet(helmet);
                    entityEquipment.setHelmetDropChance(0F);
                }
            }
        }
    }
}
