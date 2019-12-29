/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.equip;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsAPI;
import me.eccentric_nz.tardisweepingangels.monsters.daleks.WeightedChoice;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author eccentric_nz
 */
public class MonsterEquipment implements TARDISWeepingAngelsAPI {

    WeightedChoice<Integer> weightedChoice = new WeightedChoice<Integer>().add(48, 0).add(3, 1).add(3, 2).add(3, 3).add(3, 4).add(3, 5).add(3, 6).add(3, 7).add(3, 8).add(3, 9).add(3, 10).add(3, 11).add(3, 12).add(3, 13).add(3, 14).add(3, 15).add(3, 16);

    @Override
    public void setAngelEquipment(LivingEntity le, boolean disguise) {
        ItemStack head = new ItemStack(Material.BRICK, 1);
        ItemStack arm = new ItemStack(Material.BRICK, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS, 1);
        if (disguise) {
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName("Weeping Angel Head");
        headMeta.setCustomModelData(4);
        head.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Weeping Angel Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Weeping Angel Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Weeping Angel Legs");
        leggings.setItemMeta(legMeta);
        ItemMeta waeponMeta = boots.getItemMeta();
        waeponMeta.setDisplayName("Weeping Angel Feet");
        boots.setItemMeta(waeponMeta);
        EntityEquipment ee = le.getEquipment();
        ee.setHelmet(head);
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(boots);
        if (!disguise) {
            ee.setItemInMainHand(arm);
            ee.setItemInOffHand(arm.clone());
            ee.setItemInMainHandDropChance(0F);
            ee.setItemInOffHandDropChance(0F);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            ee.setBootsDropChance(0F);
            le.setCanPickupItems(false);
            le.getPersistentDataContainer().set(TARDISWeepingAngels.ANGEL, PersistentDataType.INTEGER, Monster.ANGEL.getPersist());
        }
    }

    @Override
    public void setWarriorEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.SNOWBALL, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemStack arm = new ItemStack(Material.SNOWBALL, 1);
        ItemStack weapon = new ItemStack(Material.SNOWBALL, 1);
        if (disguise) {
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Ice Warrior Head");
        headMeta.setCustomModelData(4);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Ice Warrior Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Ice Warrior Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Ice Warrior Legs");
        leggings.setItemMeta(legMeta);

        EntityEquipment ee = le.getEquipment();
        ee.setHelmet(helmet);
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        if (!disguise) {
            ItemMeta sword = weapon.getItemMeta();
            sword.setDisplayName("Ice Warrior Dagger");
            sword.setCustomModelData(3);
            weapon.setItemMeta(sword);
            ee.setItemInMainHand(weapon);
            ee.setItemInOffHand(arm);
            ee.setItemInMainHandDropChance(0F);
            ee.setItemInOffHandDropChance(0F);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
            le.getPersistentDataContainer().set(TARDISWeepingAngels.WARRIOR, PersistentDataType.INTEGER, Monster.WARRIOR.getPersist());
        }
    }

    @Override
    public void setCyberEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.IRON_INGOT, 1);
        ItemStack arm = new ItemStack(Material.IRON_INGOT, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        if (disguise) {
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Cyberman Head");
        headMeta.setCustomModelData(3);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Cyberman Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Cyberman Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Cyberman Legs");
        leggings.setItemMeta(legMeta);
        EntityEquipment ee = le.getEquipment();
        ee.setHelmet(helmet);
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        if (!disguise) {
            ee.setItemInMainHandDropChance(0F);
            ee.setItemInOffHandDropChance(0F);
            ee.setItemInMainHand(arm);
            ee.setItemInOffHand(arm.clone());
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
            le.getPersistentDataContainer().set(TARDISWeepingAngels.CYBERMAN, PersistentDataType.INTEGER, Monster.CYBERMAN.getPersist());
        }
    }

    @Override
    public void setEmptyChildEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.SUGAR, 1);
        ItemStack arm = new ItemStack(Material.SUGAR, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        if (disguise) {
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Empty Child Head");
        headMeta.setCustomModelData(3);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Empty Child Arm");
        armMeta.setCustomModelData(3);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Empty Child Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Empty Child Legs");
        leggings.setItemMeta(legMeta);

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
            ee.setItemInMainHand(arm);
            ee.setItemInOffHand(arm.clone());
            ee.setItemInMainHandDropChance(0F);
            ee.setItemInOffHandDropChance(0F);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.getPersistentDataContainer().set(TARDISWeepingAngels.EMPTY, PersistentDataType.INTEGER, Monster.EMPTY.getPersist());
        }
    }

    @Override
    public void setZygonEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.PAINTING, 1);
        ItemStack arm = new ItemStack(Material.PAINTING, 1);
        ItemStack chestplate = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
        if (disguise) {
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Zygon Head");
        headMeta.setCustomModelData(3);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Zygon Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Zygon Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Zygon Legs");
        leggings.setItemMeta(legMeta);

        EntityEquipment ee = le.getEquipment();
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        ee.setHelmet(helmet);
        if (!disguise) {
            ee.setItemInMainHand(arm);
            ee.setItemInOffHand(arm.clone());
            ee.setItemInMainHandDropChance(0F);
            ee.setItemInOffHandDropChance(0F);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
            le.getPersistentDataContainer().set(TARDISWeepingAngels.ZYGON, PersistentDataType.INTEGER, Monster.ZYGON.getPersist());
        }
    }

    @Override
    public void setSilurianEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.FEATHER, 1);
        ItemStack arm = new ItemStack(Material.BOW, 1);
        ItemStack chestplate = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
        if (disguise) {
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Silurian Head");
        headMeta.setCustomModelData(3);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Silurian Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Silurian Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Silurian Legs");
        leggings.setItemMeta(legMeta);

        EntityEquipment ee = le.getEquipment();
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        ee.setHelmet(helmet);
        if (!disguise) {
            ItemStack bow = new ItemStack(Material.BOW, 1);
            ItemMeta waeponMeta = bow.getItemMeta();
            waeponMeta.setDisplayName("Silurian Weapon");
            waeponMeta.setCustomModelData(3);
            bow.setItemMeta(waeponMeta);
            ee.setItemInMainHand(bow);
            ee.setItemInOffHand(arm);
            ee.setItemInMainHandDropChance(0F);
            ee.setItemInOffHandDropChance(0F);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
            le.getPersistentDataContainer().set(TARDISWeepingAngels.SILURIAN, PersistentDataType.INTEGER, Monster.SILURIAN.getPersist());
        }
    }

    @Override
    public void setSontaranEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.POTATO, 1);
        ItemStack arm = new ItemStack(Material.POTATO, 1);
        ItemStack chestplate = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
        if (disguise) {
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Sontaran Head");
        headMeta.setCustomModelData(4);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Sontaran Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Sontaran Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Sontaran Legs");
        leggings.setItemMeta(legMeta);

        EntityEquipment ee = le.getEquipment();
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        ee.setHelmet(helmet);
        if (!disguise) {
            ItemStack sword = new ItemStack(Material.POTATO, 1);
            ItemMeta waeponMeta = sword.getItemMeta();
            waeponMeta.setDisplayName("Sontaran Weapon");
            waeponMeta.setCustomModelData(3);
            sword.setItemMeta(waeponMeta);
            ee.setItemInMainHand(sword);
            ee.setItemInOffHand(arm);
            ee.setItemInMainHandDropChance(0F);
            ee.setItemInOffHandDropChance(0F);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
            le.getPersistentDataContainer().set(TARDISWeepingAngels.SONTARAN, PersistentDataType.INTEGER, Monster.SONTARAN.getPersist());
        }
    }

    @Override
    public void setButlerEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.BAKED_POTATO, 1);
        ItemStack arm = new ItemStack(Material.BAKED_POTATO, 1);
        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
        if (disguise) {
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Strax Head");
        headMeta.setCustomModelData(3);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Strax Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Strax Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Strax Legs");
        leggings.setItemMeta(legMeta);

        EntityEquipment ee = le.getEquipment();
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        ee.setHelmet(helmet);
        if (!disguise) {
            ee.setItemInMainHand(arm);
            ee.setItemInOffHand(arm.clone());
            ee.setItemInMainHandDropChance(0F);
            ee.setItemInOffHandDropChance(0F);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCustomName("Strax");
            le.setCanPickupItems(false);
        }
    }

    @Override
    public void setSilentEquipment(LivingEntity le) {
        LivingEntity g = (LivingEntity) le.getLocation().getWorld().spawnEntity(le.getLocation(), EntityType.GUARDIAN);
        g.setSilent(true);
        PotionEffect p = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false);
        g.addPotionEffect(p);
        le.addPassenger(g);
        le.getPersistentDataContainer().set(TARDISWeepingAngels.SILENT, PersistentDataType.INTEGER, Monster.SILENT.getPersist());
    }

    @Override
    public void setDalekEquipment(LivingEntity le, boolean disguise) {
        le.getPersistentDataContainer().set(TARDISWeepingAngels.DALEK, PersistentDataType.INTEGER, Monster.DALEK.getPersist());
        ItemStack helmet = new ItemStack(Material.MUSHROOM_STEM, 1);
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Dalek Head");
        headMeta.setCustomModelData(10000005 + weightedChoice.next());
        helmet.setItemMeta(headMeta);
        EntityEquipment ee = le.getEquipment();
        ee.setHelmet(helmet);
        ee.setChestplate(null);
        ee.setLeggings(null);
        ee.setBoots(null);
        PotionEffect invisibility = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false);
        le.addPotionEffect(invisibility);
        if (!disguise) {
            ee.setHelmetDropChance(0F);
            ItemStack bow = new ItemStack(Material.BOW, 1);
            ItemMeta bim = bow.getItemMeta();
            bim.setCustomModelData(1);
            bow.setItemMeta(bim);
            ee.setItemInMainHand(bow);
            ee.setItemInMainHandDropChance(0F);
            PotionEffect resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 360000, 1, true, false);
            le.addPotionEffect(resistance);
            AttributeInstance attribute = le.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            attribute.setBaseValue(30.0d);
            le.setHealth(30.0d);
            le.setCanPickupItems(false);
            le.setRemoveWhenFarAway(false);
        }
    }

    @Override
    public void setVashtaNeradaEquipment(LivingEntity le, boolean disguise) {
        ItemStack helmet = new ItemStack(Material.BOOK, 1);
        ItemStack arm = new ItemStack(Material.BOOK, 1);
        ItemStack chestplate = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
        if (disguise) {
            chestplate.setDurability((short) 235);
            leggings.setDurability((short) 220);
        }
        ItemMeta headMeta = helmet.getItemMeta();
        headMeta.setDisplayName("Vashta Nerada Head");
        headMeta.setCustomModelData(4);
        helmet.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Vashta Nerada Arm");
        armMeta.setCustomModelData(3);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chestplate.getItemMeta();
        chestMeta.setDisplayName("Vashta Nerada Chest");
        chestplate.setItemMeta(chestMeta);
        ItemMeta legMeta = leggings.getItemMeta();
        legMeta.setDisplayName("Vashta Nerada Legs");
        leggings.setItemMeta(legMeta);

        EntityEquipment ee = le.getEquipment();
        ee.setChestplate(chestplate);
        ee.setLeggings(leggings);
        ee.setBoots(null);
        ee.setHelmet(helmet);
        if (!disguise) {
            ee.setItemInMainHand(arm);
            ee.setItemInOffHand(arm.clone());
            ee.setItemInMainHandDropChance(0F);
            ee.setItemInOffHandDropChance(0F);
            ee.setHelmetDropChance(0F);
            ee.setChestplateDropChance(0F);
            ee.setLeggingsDropChance(0F);
            le.setCanPickupItems(false);
            le.getPersistentDataContainer().set(TARDISWeepingAngels.VASHTA, PersistentDataType.INTEGER, Monster.VASHTA.getPersist());
        }
    }

    @Override
    public void removeEquipment(Player p) {
        PlayerInventory inv = p.getInventory();
        ItemStack is = inv.getHelmet();
        if (is != null && is.hasItemMeta()) {
            ItemMeta im = is.getItemMeta();
            if (im.hasDisplayName() && im.getDisplayName().equals("Dalek Head")) {
                p.removePotionEffect(PotionEffectType.INVISIBILITY);
            }
        }
        inv.setHelmet(null);
        inv.setChestplate(null);
        inv.setLeggings(null);
        inv.setBoots(null);
        p.updateInventory();
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
                Entity passenger = ((Enderman) entity).getPassengers().get(0);
                if (passenger != null && passenger.getType().equals(EntityType.GUARDIAN)) {
                    return true;
                }
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
}
