/*
 *  Copyright 2016 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels;

import me.eccentric_nz.tardisweepingangels.utils.FollowerChecker;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * @author eccentric_nz
 */
public interface TARDISWeepingAngelsAPI {

    /**
     * Sets an entity as a Weeping Angel.
     *
     * @param le       The LivingEntity to disguise
     * @param disguise A boolean to determine if this is a player disguise
     */
    public void setAngelEquipment(LivingEntity le, boolean disguise);

    /**
     * Sets an entity as an Ice Warrior.
     *
     * @param le       The LivingEntity to disguise
     * @param disguise A boolean to determine if this is a player disguise
     */
    public void setWarriorEquipment(LivingEntity le, boolean disguise);

    /**
     * Sets an entity as a Cyberman.
     *
     * @param le       The LivingEntity to disguise
     * @param disguise A boolean to determine if this is a player disguise
     */
    public void setCyberEquipment(LivingEntity le, boolean disguise);

    /**
     * Sets an entity as a Dalek.
     *
     * @param le       The LivingEntity to disguise
     * @param disguise A boolean to determine if this is a player disguise
     */
    public void setDalekEquipment(LivingEntity le, boolean disguise);

    /**
     * Sets an entity as a Empty Child.
     *
     * @param le       The LivingEntity to disguise
     * @param disguise A boolean to determine if this is a player disguise
     */
    public void setEmptyChildEquipment(LivingEntity le, boolean disguise);

    /**
     * Sets an entity as a Hath.
     *
     * @param le       The LivingEntity to disguise
     * @param disguise A boolean to determine if this is a player disguise
     */
    public void setHathEquipment(LivingEntity le, boolean disguise);

    /**
     * Sets an armour stand, or disguises a player as a Judoon.
     *
     * @param player     The player that will own this Judoon - may be null
     * @param armorStand The armour stand or player to disguise
     * @param disguise   A boolean to determine if this is a player disguise
     */
    public void setJudoonEquipment(@Nullable Player player, Entity armorStand, boolean disguise);

    /**
     * Sets an armour stand, or disguises a player as K9.
     *
     * @param player     The player that will own this K9 - may be null
     * @param armorStand The armour stand or player to disguise
     * @param disguise   A boolean to determine if this is a player disguise
     */
    public void setK9Equipment(@Nullable Player player, Entity armorStand, boolean disguise);

    /**
     * Sets an armour stand, or disguises a player as an Ood.
     *
     * @param player     The player that will own this Ood - may be null
     * @param armorStand The armour stand or player to disguise
     * @param disguise   A boolean to determine if this is a player disguise
     */
    public void setOodEquipment(@Nullable Player player, Entity armorStand, boolean disguise);

    /**
     * Sets an entity as a Silent.
     *
     * @param le The LivingEntity to disguise
     */
    public void setSilentEquipment(LivingEntity le);

    /**
     * Sets an entity as a Silent.
     *
     * @param le       The LivingEntity to disguise
     * @param disguise A boolean to determine if this is a player disguise
     */
    public void setSilentEquipment(LivingEntity le, boolean disguise);

    /**
     * Sets an entity as a Silurian.
     *
     * @param le       The LivingEntity to disguise
     * @param disguise A boolean to determine if this is a player disguise
     */
    public void setSilurianEquipment(LivingEntity le, boolean disguise);

    /**
     * Sets an entity as a Sontaran.
     *
     * @param le       The LivingEntity to disguise
     * @param disguise A boolean to determine if this is a player disguise
     */
    public void setSontaranEquipment(LivingEntity le, boolean disguise);

    /**
     * Sets an entity as Strax (a Sontaran butler).
     *
     * @param le       The LivingEntity to disguise
     * @param disguise A boolean to determine if this is a player disguise
     */
    public void setStraxEquipment(LivingEntity le, boolean disguise);

    /**
     * Sets an armour stand, or disguises a player as a Toclafane.
     *
     * @param armorStand The armour stand to disguise
     * @param disguise   A boolean to determine if this is a player disguise
     */
    public void setToclafaneEquipment(Entity armorStand, boolean disguise);

    /**
     * Sets an entity as a Vashta Nerada.
     *
     * @param le       The LivingEntity to disguise
     * @param disguise A boolean to determine if this is a player disguise
     */
    public void setVashtaNeradaEquipment(LivingEntity le, boolean disguise);

    /**
     * Sets an entity as a Zygon.
     *
     * @param le       The LivingEntity to disguise
     * @param disguise A boolean to determine if this is a player disguise
     */
    public void setZygonEquipment(LivingEntity le, boolean disguise);

    /**
     * Removes a disguise from a Player.
     *
     * @param p The Player to un-disguise
     */
    public void removeEquipment(Player p);

    /**
     * Returns whether an entity is a TARDISWeepingAngels entity.
     *
     * @param entity the entity to check
     * @return true if the entity is a TARDISWeepingAngels entity
     */
    public boolean isWeepingAngelMonster(Entity entity);

    /**
     * Returns the Monster type for a TARDISWeepingAngels entity.
     *
     * @param entity the entity to get the Monster type for
     * @return the Monster type
     */
    public Monster getWeepingAngelMonsterType(Entity entity);

    /**
     * Returns whether the specified entity is a claimed TARDISWeepingAngels monster.
     *
     * @param entity the entity to check
     * @param uuid   the UUID of the claiming player
     * @return a FollowerChecker containing the type of TARDISWeepingAngels monster (JUDOON, K9, OOD) - if the monster
     * is not claimable it will return WEEPING_ANGEL - and an integer from its persistent data container
     */
    public FollowerChecker isClaimedMonster(Entity entity, UUID uuid);

    /**
     * Set the entity entity equipment and ammunition count for a claimed Judoon
     *
     * @param player     the player that will own this Judoon
     * @param armorStand the armour stand to apply the equipment to
     * @param ammunition the persistent data container value with the amount of ammunition
     */
    public void setJudoonEquipment(Player player, Entity armorStand, int ammunition);

    /**
     * Start a following task for a claimed monster
     *
     * @param stand  the armour stand that will follow the player
     * @param player the player that  owns this Judoon
     */
    public void setFollowing(ArmorStand stand, Player player);
}
