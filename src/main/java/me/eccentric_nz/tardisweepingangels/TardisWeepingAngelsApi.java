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
package me.eccentric_nz.tardisweepingangels;

import me.eccentric_nz.tardisweepingangels.utils.FollowerChecker;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * @author eccentric_nz
 */
public interface TardisWeepingAngelsApi {

    /**
     * Sets an entity as a Weeping Angel.
     *
     * @param livingEntity The LivingEntity to disguise
     * @param disguise     A boolean to determine if this is a player disguise
     */
    void setWeepingAngelEquipment(LivingEntity livingEntity, boolean disguise);

    /**
     * Sets an entity as an Ice Warrior.
     *
     * @param livingEntity The LivingEntity to disguise
     * @param disguise     A boolean to determine if this is a player disguise
     */
    void setIceWarriorEquipment(LivingEntity livingEntity, boolean disguise);

    /**
     * Sets an entity as a Cyberman.
     *
     * @param livingEntity The LivingEntity to disguise
     * @param disguise     A boolean to determine if this is a player disguise
     */
    void setCyberEquipment(LivingEntity livingEntity, boolean disguise);

    /**
     * Sets an entity as a Dalek.
     *
     * @param livingEntity The LivingEntity to disguise
     * @param disguise     A boolean to determine if this is a player disguise
     */
    void setDalekEquipment(LivingEntity livingEntity, boolean disguise);

    /**
     * Sets an entity as a Empty Child.
     *
     * @param livingEntity The LivingEntity to disguise
     * @param disguise     A boolean to determine if this is a player disguise
     */
    void setEmptyChildEquipment(LivingEntity livingEntity, boolean disguise);

    /**
     * Sets an entity as a Hath.
     *
     * @param livingEntity The LivingEntity to disguise
     * @param disguise     A boolean to determine if this is a player disguise
     */
    void setHathEquipment(LivingEntity livingEntity, boolean disguise);

    /**
     * Sets an armour stand, or disguises a player as a Judoon.
     *
     * @param player     The player that will own this Judoon - may be null
     * @param armorStand The armour stand or player to disguise
     * @param disguise   A boolean to determine if this is a player disguise
     */
    void setJudoonEquipment(@Nullable Player player, Entity armorStand, boolean disguise);

    /**
     * Sets an armour stand, or disguises a player as K9.
     *
     * @param player     The player that will own this K9 - may be null
     * @param armorStand The armour stand or player to disguise
     * @param disguise   A boolean to determine if this is a player disguise
     */
    void setK9Equipment(@Nullable Player player, Entity armorStand, boolean disguise);

    /**
     * Sets an armour stand, or disguises a player as an Ood.
     *
     * @param player     The player that will own this Ood - may be null
     * @param armorStand The armour stand or player to disguise
     * @param disguise   A boolean to determine if this is a player disguise
     */
    void setOodEquipment(@Nullable Player player, Entity armorStand, boolean disguise);

    /**
     * Sets an entity as a Silent.
     *
     * @param livingEntity The LivingEntity to disguise
     */
    void setSilentEquipment(LivingEntity livingEntity);

    /**
     * Sets an entity as a Silent.
     *
     * @param livingEntity The LivingEntity to disguise
     * @param disguise     A boolean to determine if this is a player disguise
     */
    void setSilentEquipment(LivingEntity livingEntity, boolean disguise);

    /**
     * Sets an entity as a Silurian.
     *
     * @param livingEntity The LivingEntity to disguise
     * @param disguise     A boolean to determine if this is a player disguise
     */
    void setSilurianEquipment(LivingEntity livingEntity, boolean disguise);

    /**
     * Sets an entity as a Sontaran.
     *
     * @param livingEntity The LivingEntity to disguise
     * @param disguise     A boolean to determine if this is a player disguise
     */
    void setSontaranEquipment(LivingEntity livingEntity, boolean disguise);

    /**
     * Sets an entity as Strax (a Sontaran butler).
     *
     * @param livingEntity The LivingEntity to disguise
     * @param disguise     A boolean to determine if this is a player disguise
     */
    void setStraxEquipment(LivingEntity livingEntity, boolean disguise);

    /**
     * Sets an armour stand, or disguises a player as a Toclafane.
     *
     * @param armorStand The armour stand to disguise
     * @param disguise   A boolean to determine if this is a player disguise
     */
    void setToclafaneEquipment(Entity armorStand, boolean disguise);

    /**
     * Sets an entity as a Vashta Nerada.
     *
     * @param livingEntity The LivingEntity to disguise
     * @param disguise     A boolean to determine if this is a player disguise
     */
    void setVashtaNeradaEquipment(LivingEntity livingEntity, boolean disguise);

    /**
     * Sets an entity as a Zygon.
     *
     * @param livingEntity The LivingEntity to disguise
     * @param disguise     A boolean to determine if this is a player disguise
     */
    void setZygonEquipment(LivingEntity livingEntity, boolean disguise);

    /**
     * Removes a disguise from a Player.
     *
     * @param player The Player to un-disguise
     */
    void removeEquipment(Player player);

    /**
     * Returns whether an entity is a TARDISWeepingAngels entity.
     *
     * @param entity the entity to check
     * @return true if the entity is a TARDISWeepingAngels entity
     */
    boolean isWeepingAngelMonster(Entity entity);

    /**
     * Returns the Monster type for a TARDISWeepingAngels entity.
     *
     * @param entity the entity to get the Monster type for
     * @return the Monster type
     */
    Monster getWeepingAngelMonsterType(Entity entity);

    /**
     * Returns whether the specified entity is a claimed TARDISWeepingAngels monster.
     *
     * @param entity the entity to check
     * @param uuid   the UUID of the claiming player
     * @return a FollowerChecker containing the type of TARDISWeepingAngels monster (JUDOON, K9, OOD) - if the monster
     * is not claimable it will return WEEPING_ANGEL - and an integer from its persistent data container
     */
    FollowerChecker isClaimedMonster(Entity entity, UUID uuid);

    /**
     * Set the entity entity equipment and ammunition count for a claimed Judoon
     *
     * @param player     the player that will own this Judoon
     * @param armorStand the armour stand to apply the equipment to
     * @param ammunition the persistent data container value with the amount of ammunition
     */
    void setJudoonEquipment(Player player, Entity armorStand, int ammunition);

    /**
     * Start a following task for a claimed monster
     *
     * @param armorStand the armour stand that will follow the player
     * @param player     the player that owns this Judoon / Ood / K9
     */
    void setFollowing(ArmorStand armorStand, Player player);

    /**
     * Get a TARDISWeepingAngels monster head
     *
     * @param monster the type of monster head to get
     * @return a monster head itemstack
     */
    ItemStack getHead(Monster monster);

    /**
     * Get a K9 item
     *
     * @return a K9 itemstack
     */
    ItemStack getK9();
}
