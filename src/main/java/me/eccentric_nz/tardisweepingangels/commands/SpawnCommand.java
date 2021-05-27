package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardishelper.TARDISHelperPlugin;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.monsters.cybermen.CybermanEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.daleks.DalekEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.empty_child.EmptyChildEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.hath.HathEquipment;
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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class SpawnCommand {

	private final TARDISWeepingAngels plugin;
	private final Set<Material> trans = null;

	public SpawnCommand(TARDISWeepingAngels plugin) {
		this.plugin = plugin;
	}

	public boolean spawn(CommandSender sender, String[] args) {
		if (args.length < 2) {
			return false;
		}
		String upper = args[1].toUpperCase();
		// check monster type
		Monster monster;
		try {
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
		// check player has permission for this monster
		if (!player.hasPermission("tardisweepingangels.spawn." + monster.getPermission())) {
			sender.sendMessage(plugin.pluginName + "You don't have permission to spawn a " + monster + "!");
			return true;
		}
		Location eyeLocation = player.getTargetBlock(trans, 50).getLocation();
		eyeLocation.add(0.5, 1.0, 0.5);
		eyeLocation.setYaw(player.getLocation().getYaw() - 180.0f);
		World world = eyeLocation.getWorld();
		switch (monster) {
			case WEEPING_ANGEL -> {
				assert world != null;
				LivingEntity a = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.SKELETON);
				a.setSilent(true);
				a.setNoDamageTicks(75);
				AngelEquipment.set(a, false);
				player.playSound(a.getLocation(), "blink", 1.0f, 1.0f);
				plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(a, EntityType.SKELETON, Monster.WEEPING_ANGEL, eyeLocation));
			}
			case CYBERMAN -> {
				assert world != null;
				LivingEntity c = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
				c.setSilent(true);
				c.setNoDamageTicks(75);
				Ageable cyber = (Ageable) c;
				cyber.setAdult();
				player.playSound(c.getLocation(), "cyberman", 1.0f, 1.0f);
				CybermanEquipment.set(c, false);
				plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(c, EntityType.ZOMBIE, Monster.CYBERMAN, eyeLocation));
			}
			case DALEK -> {
				assert world != null;
				LivingEntity d = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.SKELETON);
				d.setSilent(true);
				d.setNoDamageTicks(75);
				DalekEquipment.set(d, false);
				player.playSound(d.getLocation(), "dalek", 1.0f, 1.0f);
				plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(d, EntityType.SKELETON, Monster.DALEK, eyeLocation));
				if (args.length > 1 && args[1].equalsIgnoreCase("flying") &&
					plugin.getServer().getPluginManager().isPluginEnabled("TARDISChunkGenerator")) {
					TARDISHelperPlugin tardisHelper = (TARDISHelperPlugin) plugin.getServer().getPluginManager().getPlugin("TARDISChunkGenerator");
					// make the Dalek fly
					EntityEquipment ee = d.getEquipment();
					assert ee != null;
					ee.setChestplate(new ItemStack(Material.ELYTRA, 1));
					// teleport them straight up
					d.teleport(d.getLocation().add(0.0d, 20.0d, 0.0d));
					d.setGliding(true);
					assert tardisHelper != null;
					tardisHelper.setFallFlyingTag(d);
					ee.setChestplate(new ItemStack(Material.AIR));
				}
			}
			case EMPTY_CHILD -> {
				assert world != null;
				LivingEntity e = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
				e.setSilent(true);
				e.setNoDamageTicks(75);
				Ageable child = (Ageable) e;
				child.setBaby();
				EmptyChildEquipment.set(e, false);
				player.playSound(e.getLocation(), "empty_child", 1.0f, 1.0f);
				plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.ZOMBIE, Monster.EMPTY_CHILD, eyeLocation));
			}
			case HATH -> {
				assert world != null;
				LivingEntity h = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIFIED_PIGLIN);
				h.setSilent(true);
				h.setNoDamageTicks(75);
				HathEquipment.set(h, false);
				player.playSound(h.getLocation(), "hath", 1.0f, 1.0f);
				plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(h, EntityType.ZOMBIFIED_PIGLIN, Monster.HATH, eyeLocation));
			}
			case ICE_WARRIOR -> {
				assert world != null;
				LivingEntity i = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIFIED_PIGLIN);
				i.setSilent(true);
				IceWarriorEquipment.set(i, false);
				player.playSound(i.getLocation(), "warrior", 1.0f, 1.0f);
				plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(i, EntityType.ZOMBIFIED_PIGLIN, Monster.ICE_WARRIOR, eyeLocation));
				PigZombie pigman = (PigZombie) i;
				pigman.setAngry(true);
				pigman.setAnger(Integer.MAX_VALUE);
				Ageable ageable = (Ageable) i;
				ageable.setAdult();
			}
			case JUDOON -> {
				assert world != null;
				Entity judoon = world.spawnEntity(eyeLocation, EntityType.ARMOR_STAND);
				JudoonEquipment.set(null, judoon, false);
				player.playSound(judoon.getLocation(), "judoon", 1.0f, 1.0f);
				plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(judoon, EntityType.ARMOR_STAND, Monster.JUDOON, eyeLocation));
			}
			case K9 -> {
				assert world != null;
				Entity k9 = world.spawnEntity(eyeLocation, EntityType.ARMOR_STAND);
				K9Equipment.set(player, k9, false);
				player.playSound(k9.getLocation(), "k9", 1.0f, 1.0f);
				plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(k9, EntityType.ARMOR_STAND, Monster.K9, eyeLocation));
			}
			case OOD -> {
				assert world != null;
				Entity ood = world.spawnEntity(eyeLocation, EntityType.ARMOR_STAND);
				OodEquipment.set(null, ood, false);
				player.playSound(ood.getLocation(), "ood", 1.0f, 1.0f);
				plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(ood, EntityType.ARMOR_STAND, Monster.OOD, eyeLocation));
			}
			case SILENT -> {
				assert world != null;
				LivingEntity l = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ENDERMAN);
				l.setSilent(true);
				SilentEquipment.set(l, false);
				player.playSound(l.getLocation(), "silence", 1.0f, 1.0f);
				plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(l, EntityType.ENDERMAN, Monster.SILENT, eyeLocation));
			}
			case SILURIAN -> {
				assert world != null;
				LivingEntity s = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.SKELETON);
				s.setSilent(true);
				s.setNoDamageTicks(75);
				SilurianEquipment.set(s, false);
				player.playSound(s.getLocation(), "silurian", 1.0f, 1.0f);
				plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(s, EntityType.SKELETON, Monster.SILURIAN, eyeLocation));
			}
			case SONTARAN -> {
				assert world != null;
				LivingEntity o = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
				o.setSilent(true);
				o.setNoDamageTicks(75);
				Ageable sontaran = (Ageable) o;
				sontaran.setAdult();
				SontaranEquipment.set(o, false);
				player.playSound(o.getLocation(), "sontaran", 1.0f, 1.0f);
				plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(o, EntityType.ZOMBIE, Monster.SONTARAN, eyeLocation));
			}
			case STRAX -> {
				assert world != null;
				LivingEntity x = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIFIED_PIGLIN);
				x.setSilent(true);
				x.setNoDamageTicks(75);
				PigZombie strax = (PigZombie) x;
				strax.setAngry(false);
				StraxEquipment.set(x, false);
				Ageable sageable = (Ageable) x;
				sageable.setAdult();
				player.playSound(x.getLocation(), "strax", 1.0f, 1.0f);
				plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(x, EntityType.ZOMBIFIED_PIGLIN, Monster.STRAX, eyeLocation));
			}
			case TOCLAFANE -> {
				assert world != null;
				Entity toclafane = world.spawnEntity(eyeLocation, EntityType.ARMOR_STAND);
				ToclafaneEquipment.set(toclafane, false);
				player.playSound(toclafane.getLocation(), "toclafane", 1.0f, 1.0f);
				plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(toclafane, EntityType.ARMOR_STAND, Monster.TOCLAFANE, eyeLocation));
			}
			case VASHTA_NERADA -> {
				assert world != null;
				LivingEntity v = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
				v.setSilent(true);
				v.setNoDamageTicks(75);
				Ageable vashta = (Ageable) v;
				vashta.setAdult();
				VashtaNeradaEquipment.set(v, false);
				player.playSound(v.getLocation(), "vashta", 1.0f, 1.0f);
				plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(v, EntityType.ZOMBIE, Monster.VASHTA_NERADA, eyeLocation));
			}
			case ZYGON -> {
				assert world != null;
				LivingEntity z = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
				z.setSilent(true);
				z.setNoDamageTicks(75);
				Ageable zygon = (Ageable) z;
				zygon.setAdult();
				player.playSound(z.getLocation(), "zygon", 1.0f, 1.0f);
				ZygonEquipment.set(z, false);
				plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(z, EntityType.ZOMBIE, Monster.ZYGON, eyeLocation));
			}
		}
		return true;
	}
}
