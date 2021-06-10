package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.HeadBuilder;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        if (monster == Monster.K9 || monster == Monster.TOCLAFANE) {
            sender.sendMessage(plugin.pluginName + "That monster type can't be equipped as a helmet!");
            return true;
        }
        ItemStack is = HeadBuilder.getItemStack(monster);
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
