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
package me.eccentric_nz.tardisweepingangels.death;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.monsters.cyberman.CybermanEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author eccentric_nz
 */
public class Death implements Listener {

    private final TardisWeepingAngelsPlugin plugin;
    private final List<Material> weepingAngelDrops = new ArrayList<>();
    private final List<Material> cybermanDrops = new ArrayList<>();
    private final List<Material> dalekDrops = new ArrayList<>();
    private final List<Material> emptyChildDrops = new ArrayList<>();
    private final List<Material> hathDrops = new ArrayList<>();
    private final List<Material> silentDrops = new ArrayList<>();
    private final List<Material> iceWarriorDrops = new ArrayList<>();
    private final List<Material> silurianDrops = new ArrayList<>();
    private final List<Material> sontaranDrops = new ArrayList<>();
    private final List<Material> vashtaNeradaDrops = new ArrayList<>();
    private final List<Material> zygonDrops = new ArrayList<>();

    public Death(TardisWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
        plugin.getConfig().getStringList("angels.drops").forEach((a) -> weepingAngelDrops.add(Material.valueOf(a)));
        plugin.getConfig().getStringList("cybermen.drops").forEach((c) -> cybermanDrops.add(Material.valueOf(c)));
        plugin.getConfig().getStringList("daleks.drops").forEach((d) -> dalekDrops.add(Material.valueOf(d)));
        plugin.getConfig().getStringList("empty_child.drops").forEach((e) -> emptyChildDrops.add(Material.valueOf(e)));
        plugin.getConfig().getStringList("hath.drops").forEach((e) -> hathDrops.add(Material.valueOf(e)));
        plugin.getConfig().getStringList("ice_warriors.drops").forEach((i) -> iceWarriorDrops.add(Material.valueOf(i)));
        plugin.getConfig().getStringList("sontarans.drops").forEach((o) -> sontaranDrops.add(Material.valueOf(o)));
        plugin.getConfig().getStringList("silent.drops").forEach((m) -> silentDrops.add(Material.valueOf(m)));
        plugin.getConfig().getStringList("silurians.drops").forEach((s) -> silurianDrops.add(Material.valueOf(s)));
        plugin.getConfig().getStringList("vashta_nerada.drops").forEach((v) -> vashtaNeradaDrops.add(Material.valueOf(v)));
        plugin.getConfig().getStringList("zygons.drops").forEach((z) -> zygonDrops.add(Material.valueOf(z)));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent event) {
        PersistentDataContainer persistentDataContainer = event.getEntity().getPersistentDataContainer();
        if (event.getEntityType().equals(EntityType.SKELETON)) {
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.weepingAngel, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                ItemStack itemStack;
                if (TardisWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.BRICK, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    assert itemMeta != null;
                    itemMeta.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Weeping Angel Head");
                    itemMeta.setCustomModelData(3);
                    itemStack.setItemMeta(itemMeta);
                } else {
                    itemStack = new ItemStack(weepingAngelDrops.get(TardisWeepingAngelsPlugin.random.nextInt(weepingAngelDrops.size())), TardisWeepingAngelsPlugin.random.nextInt(1) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
                return;
            }
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.silurian, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                ItemStack itemStack;
                if (TardisWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.FEATHER, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    assert itemMeta != null;
                    itemMeta.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Silurian Head");
                    itemMeta.setCustomModelData(3);
                    itemStack.setItemMeta(itemMeta);
                } else {
                    itemStack = new ItemStack(silurianDrops.get(TardisWeepingAngelsPlugin.random.nextInt(silurianDrops.size())), TardisWeepingAngelsPlugin.random.nextInt(2) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
                return;
            }
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.dalek, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                ItemStack itemStack;
                if (TardisWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.SLIME_BALL, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    assert itemMeta != null;
                    itemMeta.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Dalek Head");
                    itemMeta.setCustomModelData(10000004);
                    itemStack.setItemMeta(itemMeta);
                } else {
                    itemStack = new ItemStack(dalekDrops.get(TardisWeepingAngelsPlugin.random.nextInt(dalekDrops.size())), 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
                return;
            }
        }
        if (event.getEntityType().equals(EntityType.ZOMBIFIED_PIGLIN)) {
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.iceWarrior, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                ItemStack itemStack;
                if (TardisWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.SNOWBALL, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    assert itemMeta != null;
                    itemMeta.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Ice Warrior Head");
                    itemMeta.setCustomModelData(4);
                    itemStack.setItemMeta(itemMeta);
                } else {
                    itemStack = new ItemStack(iceWarriorDrops.get(TardisWeepingAngelsPlugin.random.nextInt(iceWarriorDrops.size())), TardisWeepingAngelsPlugin.random.nextInt(1) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
                return;
            }
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.hath, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                ItemStack itemStack;
                if (TardisWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.PUFFERFISH, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    assert itemMeta != null;
                    itemMeta.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Hath Head");
                    itemMeta.setCustomModelData(4);
                    itemStack.setItemMeta(itemMeta);
                } else {
                    itemStack = new ItemStack(hathDrops.get(TardisWeepingAngelsPlugin.random.nextInt(hathDrops.size())), TardisWeepingAngelsPlugin.random.nextInt(1) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
                return;
            }
        }
        if (event.getEntityType().equals(EntityType.ZOMBIE)) {
            ItemStack itemStack;
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.cyberman, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                if (TardisWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.IRON_INGOT, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    assert itemMeta != null;
                    itemMeta.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Cyberman Head");
                    itemMeta.setCustomModelData(3);
                    itemStack.setItemMeta(itemMeta);
                } else if (TardisWeepingAngelsPlugin.random.nextInt(100) < 6) {
                    itemStack = new ItemStack(Material.IRON_INGOT, 1);
                } else {
                    itemStack = new ItemStack(cybermanDrops.get(TardisWeepingAngelsPlugin.random.nextInt(cybermanDrops.size())), 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
                return;
            }
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.emptyChild, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                if (TardisWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.SUGAR, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    assert itemMeta != null;
                    itemMeta.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Empty Child Head");
                    itemMeta.setCustomModelData(3);
                    itemStack.setItemMeta(itemMeta);
                } else if (TardisWeepingAngelsPlugin.random.nextInt(100) < 6) {
                    itemStack = new ItemStack(Material.POTION);
                    PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
                    assert potionMeta != null;
                    potionMeta.setBasePotionData(new PotionData(PotionType.REGEN));
                    itemStack.setItemMeta(potionMeta);
                } else {
                    itemStack = new ItemStack(emptyChildDrops.get(TardisWeepingAngelsPlugin.random.nextInt(emptyChildDrops.size())), TardisWeepingAngelsPlugin.random.nextInt(1) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
                return;
            }
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.sontaran, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                if (TardisWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.POTATO, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    assert itemMeta != null;
                    itemMeta.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Sontaran Head");
                    itemMeta.setCustomModelData(4);
                    itemStack.setItemMeta(itemMeta);
                } else if (TardisWeepingAngelsPlugin.random.nextInt(100) < 6) {
                    itemStack = new ItemStack(Material.MILK_BUCKET, 1);
                } else {
                    itemStack = new ItemStack(sontaranDrops.get(TardisWeepingAngelsPlugin.random.nextInt(sontaranDrops.size())), 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
                return;
            }
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.vashtaNerada, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                if (TardisWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.BOOK, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    assert itemMeta != null;
                    itemMeta.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Vashta Nerada Head");
                    itemMeta.setCustomModelData(4);
                    itemStack.setItemMeta(itemMeta);
                } else {
                    itemStack = new ItemStack(vashtaNeradaDrops.get(TardisWeepingAngelsPlugin.random.nextInt(vashtaNeradaDrops.size())), TardisWeepingAngelsPlugin.random.nextInt(2) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
            }
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.zygon, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                if (TardisWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.PAINTING, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    assert itemMeta != null;
                    itemMeta.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Zygon Head");
                    itemMeta.setCustomModelData(3);
                    itemStack.setItemMeta(itemMeta);
                } else {
                    itemStack = new ItemStack(zygonDrops.get(TardisWeepingAngelsPlugin.random.nextInt(zygonDrops.size())), TardisWeepingAngelsPlugin.random.nextInt(1) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
                return;
            }
        }
        if (event.getEntityType().equals(EntityType.VILLAGER) || event.getEntityType().equals(EntityType.PLAYER)) {
            if (!plugin.getConfig().getBoolean("cybermen.can_upgrade")) {
                return;
            }
            if (plugin.isCitizensEnabled() && CitizensAPI.getNPCRegistry().isNPC(event.getEntity())) {
                return;
            }
            EntityDamageEvent damage = event.getEntity().getLastDamageCause();
            if (damage != null && damage.getCause().equals(DamageCause.ENTITY_ATTACK)) {
                Entity attacker = (((EntityDamageByEntityEvent) damage).getDamager());
                PersistentDataContainer attackerPersistentDataContainer = attacker.getPersistentDataContainer();
                if (attacker instanceof Zombie && attackerPersistentDataContainer.has(TardisWeepingAngelsPlugin.cyberman, PersistentDataType.INTEGER)) {
                    Location loc = event.getEntity().getLocation();
                    LivingEntity livingEntity = (LivingEntity) Objects.requireNonNull(loc.getWorld()).spawnEntity(loc, EntityType.ZOMBIE);
                    livingEntity.setSilent(true);
                    CybermanEquipment.set(livingEntity, false);
                    plugin.getServer().getPluginManager().callEvent(new TardisWeepingAngelSpawnEvent(livingEntity, EntityType.ZOMBIE, Monster.CYBERMAN, loc));
                    if (event.getEntity() instanceof Player) {
                        String name = event.getEntity().getName();
                        livingEntity.setCustomName(name);
                        livingEntity.setCustomNameVisible(true);
                    }
                    return;
                }
                if (attackerPersistentDataContainer.has(TardisWeepingAngelsPlugin.emptyChild, PersistentDataType.INTEGER)) {
                    if (event.getEntity() instanceof Player player) {
                        plugin.getEmpty().add(player.getUniqueId());
                    }
                }
            }
        }
        if (event.getEntityType().equals(EntityType.ENDERMAN)) {
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.silent, PersistentDataType.INTEGER)) {
                // remove the guardian as well
                Entity guardian = (event.getEntity().getPassengers().size() > 0) ? event.getEntity().getPassengers().get(0) : null;
                if (guardian != null) {
                    guardian.remove();
                }
                event.getDrops().clear();
                ItemStack itemStack;
                if (TardisWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.END_STONE, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    assert itemMeta != null;
                    itemMeta.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Silent Head");
                    itemMeta.setCustomModelData(3);
                    itemStack.setItemMeta(itemMeta);
                } else {
                    itemStack = new ItemStack(silentDrops.get(TardisWeepingAngelsPlugin.random.nextInt(silentDrops.size())), TardisWeepingAngelsPlugin.random.nextInt(1) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
            }
        }
    }
}
