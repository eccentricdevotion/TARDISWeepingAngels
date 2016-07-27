/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.equip;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsAPI;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.SnowmanWatcher;
import org.bukkit.Material;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author eccentric_nz
 */
public class MonsterEquipment implements TARDISWeepingAngelsAPI {

    @Override
    public void setAngelEquipment(LivingEntity le, boolean disguise) {
        ItemStack wing = new ItemStack(Material.BARRIER, 1);
        ItemStack head = new ItemStack(Material.STONE_BUTTON, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS, 1);
        if (disguise) {
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta hmeta = head.getItemMeta();
        hmeta.setDisplayName("Weeping Angel Head");
        head.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Weeping Angel Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Weeping Angel Legs");
        leggings.setItemMeta(lmeta);
        ItemMeta bmeta = boots.getItemMeta();
        bmeta.setDisplayName("Weeping Angel Feet");
        boots.setItemMeta(bmeta);
        EntityEquipment ee = le.getEquipment();
        ee.setHelmet(head);
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(boots);
        if (!disguise) {
            ee.setItemInMainHand(wing);
            ee.setItemInOffHand(wing);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            ee.setBootsDropChance(0F);
            le.setCanPickupItems(false);
        }
    }

    @Override
    public void setWarriorEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.IRON_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemStack weapon = new ItemStack(Material.IRON_SWORD, 1);
        if (disguise) {
            helmet.setDurability((short) 160);
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Ice Warrior Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Ice Warrior Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Ice Warrior Legs");
        leggings.setItemMeta(lmeta);

        EntityEquipment ee = le.getEquipment();
        ee.setHelmet(helmet);
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        if (!disguise) {
            ItemMeta sword = weapon.getItemMeta();
            sword.setDisplayName("Ice Warrior Dagger");
            weapon.setItemMeta(sword);
            ee.setItemInMainHand(weapon);
            ee.setItemInOffHand(null);
            ee.setItemInMainHandDropChance(0F);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
        }
    }

    @Override
    public void setCyberEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.IRON_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        if (disguise) {
            helmet.setDurability((short) 160);
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Cyberman Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Cyberman Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Cyberman Legs");
        leggings.setItemMeta(lmeta);
        EntityEquipment ee = le.getEquipment();
        ee.setHelmet(helmet);
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        if (!disguise) {
            ee.setItemInMainHandDropChance(0F);
            ee.setItemInMainHand(null);
            ee.setItemInOffHand(null);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
        }
    }

    @Override
    public void setEmptyChildEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.IRON_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        if (disguise) {
            helmet.setDurability((short) 160);
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Empty Child Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Empty Child Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Empty Child Legs");
        leggings.setItemMeta(lmeta);

        EntityEquipment ee = le.getEquipment();
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        ee.setHelmet(helmet);
        if (!disguise) {
            PotionEffect p = new PotionEffect(PotionEffectType.SLOW, 360000, 1, true, false);
            le.removePotionEffect(PotionEffectType.SPEED);
            le.addPotionEffect(p);
            le.setCanPickupItems(false);
            ee.setItemInMainHand(null);
            ee.setItemInOffHand(null);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
        }
    }

    @Override
    public void setZygonEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.GOLD_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.GOLD_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLD_LEGGINGS, 1);
        if (disguise) {
            helmet.setDurability((short) 160);
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Zygon Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Zygon Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Zygon Legs");
        leggings.setItemMeta(lmeta);

        EntityEquipment ee = le.getEquipment();
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        ee.setHelmet(helmet);
        if (!disguise) {
            ee.setItemInMainHand(null);
            ee.setItemInOffHand(null);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
        }
    }

    @Override
    public void setSilurianEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.GOLD_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.GOLD_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLD_LEGGINGS, 1);
        if (disguise) {
            helmet.setDurability((short) 160);
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Silurian Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Silurian Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Silurian Legs");
        leggings.setItemMeta(lmeta);

        EntityEquipment ee = le.getEquipment();
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        ee.setHelmet(helmet);
        if (!disguise) {
            ItemStack bow = new ItemStack(Material.BOW, 1);
            ItemMeta bmeta = bow.getItemMeta();
            bmeta.setDisplayName("Silurian Weapon");
            bow.setItemMeta(bmeta);
            ee.setItemInMainHand(bow);
            ee.setItemInOffHand(null);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
        }
    }

    @Override
    public void setSontaranEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.GOLD_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.GOLD_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLD_LEGGINGS, 1);
        if (disguise) {
            helmet.setDurability((short) 160);
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Sontaran Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Sontaran Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Sontaran Legs");
        leggings.setItemMeta(lmeta);

        EntityEquipment ee = le.getEquipment();
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        ee.setHelmet(helmet);
        if (!disguise) {
            ItemStack sword = new ItemStack(Material.STONE_SWORD, 1);
            ItemMeta bmeta = sword.getItemMeta();
            bmeta.setDisplayName("Sontaran Weapon");
            sword.setItemMeta(bmeta);
            ee.setItemInMainHand(sword);
            ee.setItemInOffHand(null);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
        }
    }

    @Override
    public void setButlerEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
        if (disguise) {
            helmet.setDurability((short) 160);
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Strax Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Strax Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Strax Legs");
        leggings.setItemMeta(lmeta);

        EntityEquipment ee = le.getEquipment();
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        ee.setHelmet(helmet);
        if (!disguise) {
            ee.setItemInMainHand(null);
            ee.setItemInOffHand(null);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCustomName("Strax");
            le.setCanPickupItems(false);
        }
    }

    @Override
    public void setSilentEquipment(LivingEntity le) {
        final LivingEntity g = (LivingEntity) le.getLocation().getWorld().spawnEntity(le.getLocation(), EntityType.GUARDIAN);
        g.setSilent(true);
        PotionEffect p = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false);
        g.addPotionEffect(p);
        le.setPassenger(g);
//        /summon Guardian ~ ~ ~ {Invulnerable:1b,ActiveEffects:[{Id:14b,Duration:20000000,ShowParticles:0b}],Riding:{id:"Enderman"}}
    }

    @Override
    public void setDalekEquipment(LivingEntity le) {
        ItemStack helmet = new ItemStack(Material.VINE, 1);
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Dalek Head");
        helmet.setItemMeta(hmeta);
        EntityEquipment ee = le.getEquipment();
        ee.setHelmet(helmet);
        ee.setHelmetDropChance(0F);
        ee.setChestplate(null);
        ee.setLeggings(null);
        ee.setBoots(null);
        MobDisguise mobDisguise = new MobDisguise(DisguiseType.SNOWMAN);
        LivingWatcher livingWatcher = mobDisguise.getWatcher();
        SnowmanWatcher snw = (SnowmanWatcher) livingWatcher;
        snw.setHat(false);
        DisguiseAPI.disguiseToAll(le, mobDisguise);
        PotionEffect p = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 360000, 1, true, false);
        le.addPotionEffect(p);
        le.setMaxHealth(30.0d);
        le.setHealth(30.0d);
        le.setCanPickupItems(false);
    }

    @Override
    public void setVashtaNeradaEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.GOLD_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.GOLD_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLD_LEGGINGS, 1);
        if (disguise) {
            helmet.setDurability((short) 160);
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.setDisplayName("Vashta Nerada Head");
        helmet.setItemMeta(hmeta);
        ItemMeta cmeta = chestplate.getItemMeta();
        cmeta.setDisplayName("Vashta Nerada Chest");
        chestplate.setItemMeta(cmeta);
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.setDisplayName("Vashta Nerada Legs");
        leggings.setItemMeta(lmeta);

        EntityEquipment ee = le.getEquipment();
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        ee.setHelmet(helmet);
        if (!disguise) {
            ee.setItemInMainHand(null);
            ee.setItemInOffHand(null);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
        }
    }

    @Override
    public void removeEquipment(Player p) {
        PlayerInventory inv = p.getInventory();
        inv.setHelmet(null);
        inv.setChestplate(null);
        inv.setLeggings(null);
        inv.setBoots(null);
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
            Entity passenger = ((Enderman) entity).getPassenger();
            if (passenger != null && passenger.getType().equals(EntityType.GUARDIAN)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Monster getWeepingAngelMonsterType(Entity entity) {
        if (entity instanceof Zombie || entity instanceof PigZombie || entity instanceof Skeleton) {
            EntityEquipment ee = ((LivingEntity) entity).getEquipment();
            ItemStack is = ee.getHelmet();
            if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
                String dn = is.getItemMeta().getDisplayName();
                if (dn.startsWith("Cyberman")) {
                    return Monster.CYBERMAN;
                }
                if (dn.startsWith("Dalek")) {
                    return Monster.DALEK;
                }
                if (dn.startsWith("Empty Child")) {
                    return Monster.EMPTY_CHILD;
                }
                if (dn.startsWith("Ice Warrior")) {
                    return Monster.ICE_WARRIOR;
                }
                if (dn.startsWith("Silurian")) {
                    return Monster.SILURIAN;
                }
                if (dn.startsWith("Sontaran")) {
                    return Monster.SONTARAN;
                }
                if (dn.startsWith("Strax")) {
                    return Monster.STRAX;
                }
                if (dn.startsWith("Vashta")) {
                    return Monster.VASHTA_NERADA;
                }
                if (dn.startsWith("Weeping Angel")) {
                    return Monster.WEEPING_ANGEL;
                }
                if (dn.startsWith("Zygon")) {
                    return Monster.ZYGON;
                }
            }
        }
        if (entity instanceof Enderman) {
            Entity passenger = ((Enderman) entity).getPassenger();
            if (passenger != null && passenger.getType().equals(EntityType.GUARDIAN)) {
                return Monster.SILENT;
            }
        }
        return null;
    }
}
