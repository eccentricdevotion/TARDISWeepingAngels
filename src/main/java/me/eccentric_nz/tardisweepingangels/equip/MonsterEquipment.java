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
package me.eccentric_nz.tardisweepingangels.equip;

import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.TardisWeepingAngelsApi;
import me.eccentric_nz.tardisweepingangels.monsters.cyberman.CybermanEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.dalek.DalekEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.emptychild.EmptyChildEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.hath.HathEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.icewarrior.IceWarriorEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.judoon.JudoonEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.judoon.JudoonWalkRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.k9.K9Equipment;
import me.eccentric_nz.tardisweepingangels.monsters.ood.OodEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.silent.SilentEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.silurian.SilurianEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.sontaran.SontaranEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.sontaran.StraxEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.toclafane.ToclafaneEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.vashtanerada.VashtaNeradaEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.weepingangel.AngelEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.zygon.ZygonEquipment;
import me.eccentric_nz.tardisweepingangels.utils.FollowerChecker;
import me.eccentric_nz.tardisweepingangels.utils.HeadBuilder;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * @author eccentric_nz
 */
public class MonsterEquipment implements TardisWeepingAngelsApi {

    @Override
    public void setAngelEquipment(LivingEntity livingEntity, boolean disguise) {
        AngelEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setWarriorEquipment(LivingEntity livingEntity, boolean disguise) {
        IceWarriorEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setCyberEquipment(LivingEntity livingEntity, boolean disguise) {
        CybermanEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setDalekEquipment(LivingEntity livingEntity, boolean disguise) {
        DalekEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setEmptyChildEquipment(LivingEntity livingEntity, boolean disguise) {
        EmptyChildEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setHathEquipment(LivingEntity livingEntity, boolean disguise) {
        HathEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setJudoonEquipment(@Nullable Player player, Entity armorStand, boolean disguise) {
        JudoonEquipment.set(player, armorStand, disguise);
    }

    @Override
    public void setK9Equipment(@Nullable Player player, Entity armorStand, boolean disguise) {
        K9Equipment.set(player, armorStand, disguise);
    }

    @Override
    public void setOodEquipment(@Nullable Player player, Entity armorStand, boolean disguise) {
        OodEquipment.set(player, armorStand, disguise);
    }

    @Override
    public void setSilentEquipment(LivingEntity livingEntity) {
        SilentEquipment.set(livingEntity, false);
    }

    @Override
    public void setSilentEquipment(LivingEntity livingEntity, boolean disguise) {
        SilentEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setSilurianEquipment(LivingEntity livingEntity, boolean disguise) {
        SilurianEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setSontaranEquipment(LivingEntity livingEntity, boolean disguise) {
        SontaranEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setStraxEquipment(LivingEntity livingEntity, boolean disguise) {
        StraxEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setToclafaneEquipment(Entity armorStand, boolean disguise) {
        ToclafaneEquipment.set(armorStand, disguise);
    }

    @Override
    public void setVashtaNeradaEquipment(LivingEntity livingEntity, boolean disguise) {
        VashtaNeradaEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setZygonEquipment(LivingEntity livingEntity, boolean disguise) {
        ZygonEquipment.set(livingEntity, disguise);
    }

    @Override
    public void removeEquipment(Player player) {
        RemoveEquipment.set(player);
    }

    @Override
    public boolean isWeepingAngelMonster(Entity entity) {
        if (entity instanceof Zombie || entity instanceof PigZombie || entity instanceof Skeleton) {
            PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
            return persistentDataContainer.has(TardisWeepingAngelsPlugin.CYBERMAN, PersistentDataType.INTEGER) || persistentDataContainer.has(TardisWeepingAngelsPlugin.DALEK, PersistentDataType.INTEGER) || persistentDataContainer.has(TardisWeepingAngelsPlugin.EMPTY, PersistentDataType.INTEGER) || persistentDataContainer.has(TardisWeepingAngelsPlugin.ICE_WARRIOR, PersistentDataType.INTEGER) || persistentDataContainer.has(TardisWeepingAngelsPlugin.SILURIAN, PersistentDataType.INTEGER) || persistentDataContainer.has(TardisWeepingAngelsPlugin.SONTARAN, PersistentDataType.INTEGER) || persistentDataContainer.has(TardisWeepingAngelsPlugin.STRAX, PersistentDataType.INTEGER) || persistentDataContainer.has(TardisWeepingAngelsPlugin.VASHTA_NERADA, PersistentDataType.INTEGER) || persistentDataContainer.has(TardisWeepingAngelsPlugin.WEEPING_ANGEL, PersistentDataType.INTEGER) || persistentDataContainer.has(TardisWeepingAngelsPlugin.ZYGON, PersistentDataType.INTEGER);
        } else if (entity instanceof Enderman) {
            if (!entity.getPassengers().isEmpty()) {
                Entity passenger = entity.getPassengers().get(0);
                return passenger != null && passenger.getType().equals(EntityType.GUARDIAN);
            }
        } else if (entity instanceof Bee) {
            if (!entity.getPassengers().isEmpty()) {
                Entity passenger = entity.getPassengers().get(0);
                return passenger != null && passenger.getType().equals(EntityType.ARMOR_STAND);
            }
        } else if (entity instanceof ArmorStand) {
            return entity.getPersistentDataContainer().has(TardisWeepingAngelsPlugin.OWNER_UUID, TardisWeepingAngelsPlugin.PersistentDataTypeUuid);
        }
        return false;
    }

    @Override
    public Monster getWeepingAngelMonsterType(Entity entity) {
        if (entity instanceof Zombie || entity instanceof PigZombie || entity instanceof Skeleton || entity instanceof Enderman) {
            PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.CYBERMAN, PersistentDataType.INTEGER)) {
                return Monster.CYBERMAN;
            }
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.DALEK, PersistentDataType.INTEGER)) {
                return Monster.DALEK;
            }
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.EMPTY, PersistentDataType.INTEGER)) {
                return Monster.EMPTY_CHILD;
            }
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.ICE_WARRIOR, PersistentDataType.INTEGER)) {
                return Monster.ICE_WARRIOR;
            }
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.SILURIAN, PersistentDataType.INTEGER)) {
                return Monster.SILURIAN;
            }
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.SONTARAN, PersistentDataType.INTEGER)) {
                return Monster.SONTARAN;
            }
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.STRAX, PersistentDataType.INTEGER)) {
                return Monster.STRAX;
            }
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.VASHTA_NERADA, PersistentDataType.INTEGER)) {
                return Monster.VASHTA_NERADA;
            }
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.WEEPING_ANGEL, PersistentDataType.INTEGER)) {
                return Monster.WEEPING_ANGEL;
            }
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.ZYGON, PersistentDataType.INTEGER)) {
                return Monster.ZYGON;
            }
            if (persistentDataContainer.has(TardisWeepingAngelsPlugin.SILENT, PersistentDataType.INTEGER)) {
                return Monster.SILENT;
            }
        }
        return null;
    }

    @Override
    public FollowerChecker isClaimedMonster(Entity entity, UUID uuid) {
        return new FollowerChecker(entity, uuid);
    }

    @Override
    public void setJudoonEquipment(Player player, Entity armorStand, int ammunition) {
        setJudoonEquipment(player, armorStand, false);
        armorStand.getPersistentDataContainer().set(TardisWeepingAngelsPlugin.JUDOON, PersistentDataType.INTEGER, ammunition);
    }

    @Override
    public void setFollowing(ArmorStand armorStand, Player player) {
        int taskId = TardisWeepingAngelsPlugin.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(TardisWeepingAngelsPlugin.plugin, new JudoonWalkRunnable(armorStand, 0.15d, player), 2L, 2L);
        TardisWeepingAngelsPlugin.plugin.getFollowTasks().put(player.getUniqueId(), taskId);
    }

    @Override
    public ItemStack getHead(Monster monster) {
        return HeadBuilder.getItemStack(monster);
    }

    @Override
    public ItemStack getK9() {
        ItemStack is = new ItemStack(Material.BONE);
        ItemMeta im = is.getItemMeta();
        assert im != null;
        im.setDisplayName("K9");
        im.setCustomModelData(1);
        is.setItemMeta(im);
        return is;
    }
}
