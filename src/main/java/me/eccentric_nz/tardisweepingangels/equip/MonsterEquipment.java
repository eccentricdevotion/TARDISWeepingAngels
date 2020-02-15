/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.equip;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsAPI;
import me.eccentric_nz.tardisweepingangels.monsters.cybermen.CybermanEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.daleks.DalekEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.empty_child.EmptyChildEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.ice_warriors.IceWarriorEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.judoon.JudoonEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.k9.K9Equipment;
import me.eccentric_nz.tardisweepingangels.monsters.ood.OodEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.silent.SilentEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.silurians.SilurianEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.sontarans.SontaranEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.sontarans.StraxEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.toclafane.ToclafaneEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.vashta_nerada.VashtaNeradaEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.weeping_angels.AngelEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.zygons.ZygonEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * @author eccentric_nz
 */
public class MonsterEquipment implements TARDISWeepingAngelsAPI {

    @Override
    public void setAngelEquipment(LivingEntity le, boolean disguise) {
        AngelEquipment.set(le, disguise);
    }

    @Override
    public void setWarriorEquipment(LivingEntity le, boolean disguise) {
        IceWarriorEquipment.set(le, disguise);
    }

    @Override
    public void setCyberEquipment(LivingEntity le, boolean disguise) {
        CybermanEquipment.set(le, disguise);
    }

    @Override
    public void setDalekEquipment(LivingEntity le, boolean disguise) {
        DalekEquipment.set(le, disguise);
    }

    @Override
    public void setEmptyChildEquipment(LivingEntity le, boolean disguise) {
        EmptyChildEquipment.set(le, disguise);
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
    public void setSilentEquipment(LivingEntity le) {
        SilentEquipment.set(le, false);
    }

    @Override
    public void setSilentEquipment(LivingEntity le, boolean disguise) {
        SilentEquipment.set(le, disguise);
    }

    @Override
    public void setSilurianEquipment(LivingEntity le, boolean disguise) {
        SilurianEquipment.set(le, disguise);
    }

    @Override
    public void setSontaranEquipment(LivingEntity le, boolean disguise) {
        SontaranEquipment.set(le, disguise);
    }

    @Override
    public void setStraxEquipment(LivingEntity le, boolean disguise) {
        StraxEquipment.set(le, disguise);
    }

    @Override
    public void setToclafaneEquipment(Entity armorStand, boolean disguise) {
        ToclafaneEquipment.set(armorStand, disguise);
    }

    @Override
    public void setVashtaNeradaEquipment(LivingEntity le, boolean disguise) {
        VashtaNeradaEquipment.set(le, disguise);
    }

    @Override
    public void setZygonEquipment(LivingEntity le, boolean disguise) {
        ZygonEquipment.set(le, disguise);
    }

    @Override
    public void removeEquipment(Player p) {
        RemoveEquipment.set(p);
    }

    @Override
    public boolean isWeepingAngelMonster(Entity entity) {
        if (entity instanceof Zombie || entity instanceof PigZombie || entity instanceof Skeleton) {
            EntityEquipment ee = ((LivingEntity) entity).getEquipment();
            ItemStack is = ee.getHelmet();
            if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
                String dn = is.getItemMeta().getDisplayName();
                if (dn.startsWith("Cyberman") || dn.startsWith("Dalek") || dn.startsWith("Empty Child") || dn.startsWith("Ice Warrior") || dn.startsWith("Silurian") || dn.startsWith("Sontaran") || dn.startsWith("Strax") || dn.startsWith("Vashta") || dn.startsWith("Weeping Angel") || dn.startsWith("Zygon")) {
                    return true;
                }
            }
        }
        if (entity instanceof Enderman) {
            if (!entity.getPassengers().isEmpty()) {
                Entity passenger = entity.getPassengers().get(0);
                if (passenger != null && passenger.getType().equals(EntityType.GUARDIAN)) {
                    return true;
                }
            }
        }
        if (entity instanceof Bee) {
            if (!entity.getPassengers().isEmpty()) {
                Entity passenger = entity.getPassengers().get(0);
                if (passenger != null && passenger.getType().equals(EntityType.ARMOR_STAND)) {
                    return true;
                }
            }
        }
        if (entity instanceof ArmorStand) {
            if (entity.getPersistentDataContainer().has(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Monster getWeepingAngelMonsterType(Entity entity) {
        if (entity instanceof Zombie || entity instanceof PigZombie || entity instanceof Skeleton || entity instanceof Enderman) {
            PersistentDataContainer pdc = entity.getPersistentDataContainer();
            if (pdc.has(TARDISWeepingAngels.CYBERMAN, PersistentDataType.INTEGER)) {
                return Monster.CYBERMAN;
            }
            if (pdc.has(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER)) {
                return Monster.DALEK;
            }
            if (pdc.has(TARDISWeepingAngels.EMPTY, PersistentDataType.INTEGER)) {
                return Monster.EMPTY_CHILD;
            }
            if (pdc.has(TARDISWeepingAngels.WARRIOR, PersistentDataType.INTEGER)) {
                return Monster.ICE_WARRIOR;
            }
            if (pdc.has(TARDISWeepingAngels.SILURIAN, PersistentDataType.INTEGER)) {
                return Monster.SILURIAN;
            }
            if (pdc.has(TARDISWeepingAngels.SONTARAN, PersistentDataType.INTEGER)) {
                return Monster.SONTARAN;
            }
            if (pdc.has(TARDISWeepingAngels.STRAX, PersistentDataType.INTEGER)) {
                return Monster.STRAX;
            }
            if (pdc.has(TARDISWeepingAngels.VASHTA, PersistentDataType.INTEGER)) {
                return Monster.VASHTA_NERADA;
            }
            if (pdc.has(TARDISWeepingAngels.ANGEL, PersistentDataType.INTEGER)) {
                return Monster.WEEPING_ANGEL;
            }
            if (pdc.has(TARDISWeepingAngels.ZYGON, PersistentDataType.INTEGER)) {
                return Monster.ZYGON;
            }
            if (pdc.has(TARDISWeepingAngels.SILENT, PersistentDataType.INTEGER)) {
                return Monster.SILENT;
            }
        }
        return null;
    }

    @Override
    public NamespacedKey getJudoonKey() {
        return TARDISWeepingAngels.JUDOON;
    }

    @Override
    public NamespacedKey getK9Key() {
        return TARDISWeepingAngels.K9;
    }

    @Override
    public NamespacedKey getOodKey() {
        return TARDISWeepingAngels.OOD;
    }

    @Override
    public NamespacedKey getOwnerUuidKey() {
        return TARDISWeepingAngels.OWNER_UUID;
    }

    @Override
    public PersistentDataType<byte[], UUID> getPersistentDataTypeUUID() {
        return TARDISWeepingAngels.PersistentDataTypeUUID;
    }
}
