package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class GiveCommand {

    private final TARDISWeepingAngels plugin;

    public GiveCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    public boolean give(CommandSender sender, String[] args) {
        if (args.length < 3) {
            return false;
        }
        // get the player
        Player player = plugin.getServer().getPlayer(args[1]);
        if (player == null) {
            sender.sendMessage(plugin.pluginName + "Player not found!");
            return true;
        }
        // check monster type
        String upper = args[2].toUpperCase();
        Monster monster;
        try {
            monster = Monster.valueOf(upper);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(plugin.pluginName + "Invalid monster type!");
            return true;
        }
        Material material = null;
        int cmd = 3;
        switch (monster) {
            case DALEK:
                material = Material.SLIME_BALL;
                cmd = 10000004;
                break;
            case CYBERMAN:
                material = Material.IRON_INGOT;
                break;
            case EMPTY_CHILD:
                material = Material.SUGAR;
                break;
            case HATH:
                material = Material.PUFFERFISH;
                cmd = 4;
                break;
            case SILENT:
                material = Material.END_STONE;
                cmd = 5;
                break;
            case SILURIAN:
                material = Material.FEATHER;
                break;
            case VASHTA_NERADA:
                material = Material.BOOK;
                cmd = 4;
                break;
            case WEEPING_ANGEL:
                material = Material.BRICK;
                break;
            case ZYGON:
                material = Material.PAINTING;
                break;
            case ICE_WARRIOR:
                material = Material.SNOWBALL;
                cmd = 4;
                break;
            case SONTARAN:
                material = Material.POTATO;
                cmd = 4;
                break;
            case OOD:
                material = Material.ROTTEN_FLESH;
                cmd = 30;
                break;
            case JUDOON:
                material = Material.YELLOW_DYE;
                cmd = 11;
                break;
            default:
                break;
        }
        if (material == null) {
            sender.sendMessage(plugin.pluginName + "That monster type can't be equipped as a helmet!");
            return true;
        }
        ItemStack is = new ItemStack(material, 1);
        ItemMeta im = is.getItemMeta();
        im.getPersistentDataContainer().set(TARDISWeepingAngels.MONSTER_HEAD, PersistentDataType.INTEGER, 99);
        im.setDisplayName(monster.getName() + " Head");
        im.setCustomModelData(cmd);
        is.setItemMeta(im);
        player.getInventory().addItem(is);
        player.updateInventory();
        sender.sendMessage(plugin.pluginName + "Gave " + player.getName() + " 1 " + monster.getName() + " head");
        String who = "The server";
        if (sender instanceof Player) {
            who = sender.getName();
        }
        if (!who.equals(player.getName())) {
            sender.sendMessage(plugin.pluginName + who + " gave you 1 " + monster.getName() + " head");
        }
        return true;
    }
}
