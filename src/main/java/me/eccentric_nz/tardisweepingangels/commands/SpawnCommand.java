package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

public class SpawnCommand implements CommandExecutor {

    private final TARDISWeepingAngels plugin;

    public SpawnCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("twas")) {
            // check monster type
            Monster monster;
            try {
                String upper = args[0].toUpperCase();
                monster = Monster.valueOf(upper);
            } catch (IllegalArgumentException e) {
                sender.sendMessage(plugin.pluginName + "Invalid monster type!");
                return true;
            }
            Player player = null;
            if (sender instanceof Player) {
                player = (Player) sender;
            }
            if (player == null) {
                sender.sendMessage(plugin.pluginName + "Command can only be used by a player!");
                return true;
            }
            @SuppressWarnings("deprecation")
            final Location eyeLocation = player.getTargetBlock(null, 50).getLocation();
            eyeLocation.setX(eyeLocation.getX() + 0.5F);
            eyeLocation.setY(eyeLocation.getY() + 1);
            eyeLocation.setZ(eyeLocation.getZ() + 0.5F);
            World world = eyeLocation.getWorld();
            final MonsterEquipment equip = new MonsterEquipment();
            switch (monster) {
                case ANGEL:
                case WEEPING_ANGEL:
                    final LivingEntity a = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.SKELETON);
                    a.setNoDamageTicks(75);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            equip.setAngelEquipment(a, false);
                        }
                    }, 5L);
                    break;
                case CYBERMAN:
                    final LivingEntity c = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                    c.setNoDamageTicks(75);
                    Zombie cyber = (Zombie) c;
                    cyber.setVillager(false);
                    cyber.setBaby(false);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            equip.setCyberEquipment(c, false);
                        }
                    }, 5L);
                    break;
                case DALEK:
                    final LivingEntity d = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.SKELETON);
                    d.setNoDamageTicks(75);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            equip.setDalekEquipment(d);
                        }
                    }, 2L);
                    break;
                case ICE:
                case ICE_WARRIOR:
                case WARRIOR:
                    final LivingEntity i = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.PIG_ZOMBIE);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            equip.setWarriorEquipment(i, false);
                        }
                    }, 5L);
                    PigZombie pigman = (PigZombie) i;
                    pigman.setBaby(false);
                    pigman.setAngry(true);
                    pigman.setAnger(Integer.MAX_VALUE);
                    break;
                case CHILD:
                case EMPTY:
                case EMPTY_CHILD:
                    final LivingEntity e = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                    e.setNoDamageTicks(75);
                    Zombie child = (Zombie) e;
                    child.setVillager(false);
                    child.setBaby(true);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            equip.setEmptyChildEquipment(e, false);
                        }
                    }, 5L);
                    break;
                case SILURIAN:
                    final LivingEntity s = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.SKELETON);
                    s.setNoDamageTicks(75);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            equip.setSilurianEquipment(s, false);
                        }
                    }, 5L);
                    break;
                case SONTARAN:
                    final LivingEntity o = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                    o.setNoDamageTicks(75);
                    Zombie sontaran = (Zombie) o;
                    sontaran.setBaby(false);
                    sontaran.setVillager(false);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            equip.setSontaranEquipment(o, false);
                        }
                    }, 5L);
                    break;
                case STRAX:
                    final LivingEntity x = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.PIG_ZOMBIE);
                    x.setNoDamageTicks(75);
                    PigZombie strax = (PigZombie) x;
                    strax.setBaby(false);
                    strax.setAngry(false);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            equip.setButlerEquipment(x, false);
                        }
                    }, 5L);
                    break;
                case VASHTA:
                case VASHTA_NERADA:
                    final LivingEntity v = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                    v.setNoDamageTicks(75);
                    Zombie vashta = (Zombie) v;
                    vashta.setVillager(false);
                    vashta.setBaby(false);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            equip.setVashtaNeradaEquipment(v, false);
                        }
                    }, 5L);
                    break;
                case ZYGON:
                    final LivingEntity z = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                    z.setNoDamageTicks(75);
                    Zombie zygon = (Zombie) z;
                    zygon.setVillager(false);
                    zygon.setBaby(false);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            equip.setZygonEquipment(z, false);
                        }
                    }, 5L);
                    break;
            }
            return true;
        }
        return false;
    }
}
