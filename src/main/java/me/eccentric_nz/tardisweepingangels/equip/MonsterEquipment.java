/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.equip;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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
public class MonsterEquipment {

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
            ee.setItemInHand(wing);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            ee.setBootsDropChance(0F);
            le.setCanPickupItems(false);
        }
    }

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
            ee.setItemInHand(weapon);
            ee.setItemInHandDropChance(0F);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
        }
    }

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
            ee.setItemInHandDropChance(0F);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
        }
    }

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
            PotionEffect p = new PotionEffect(PotionEffectType.SLOW, 360000, 1);
            le.removePotionEffect(PotionEffectType.SPEED);
            le.addPotionEffect(p);
            le.setCanPickupItems(false);
            ee.setItemInHand(null);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
        }
    }

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
            ee.setItemInHand(null);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
        }
    }

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
            ee.setItemInHand(bow);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
        }
    }

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
            ee.setItemInHand(sword);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
        }
    }

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
            ee.setItemInHand(null);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCustomName("Strax");
            le.setCanPickupItems(false);
        }
    }

    public void setSilentEquipment(LivingEntity le) {
        final LivingEntity g = (LivingEntity) le.getLocation().getWorld().spawnEntity(le.getLocation(), EntityType.GUARDIAN);
        PotionEffect p = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1);
        g.addPotionEffect(p);
        le.setPassenger(g);
//        /summon Guardian ~ ~ ~ {Invulnerable:1b,ActiveEffects:[{Id:14b,Duration:20000000,ShowParticles:0b}],Riding:{id:"Enderman"}}
    }

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
        DisguiseAPI.disguiseToAll(le, mobDisguise);
        PotionEffect p = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 360000, 1);
        le.addPotionEffect(p);
        le.setMaxHealth(30.0d);
        le.setHealth(30.0d);
        le.setCanPickupItems(false);
    }

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
            ee.setItemInHand(null);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
        }
    }

    public void removeEquipment(Player p) {
        PlayerInventory inv = p.getInventory();
        inv.setHelmet(null);
        inv.setChestplate(null);
        inv.setLeggings(null);
        inv.setBoots(null);
    }
}
