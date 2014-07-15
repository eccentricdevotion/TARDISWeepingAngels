/*
 * Copyright (C) 2014 eccentric_nz
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
package me.eccentric_nz.tardisweepingangels.commands;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

/**
 * TabCompleter
 */
public class TabComplete implements TabCompleter {

    private final TARDISWeepingAngels plugin;
    ImmutableList<String> LETTER_SUBS = ImmutableList.of("a", "c", "d", "e", "i", "o", "s", "v", "z");
    private final ImmutableList<String> ONOFF_SUBS = ImmutableList.of("on", "off");
    private final ImmutableList<String> CONFIG_SUBS = ImmutableList.of("add", "remove");
    private final ImmutableList<String> WORLD_SUBS;
    private final ImmutableList<String> MONSTER_SUBS;

    public TabComplete(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        List<String> tmp = new ArrayList<String>();
        for (Monster m : Monster.values()) {
            tmp.add(m.toString());
        }
        MONSTER_SUBS = ImmutableList.copyOf(tmp);
        List<String> worlds = new ArrayList<String>();
        for (World w : this.plugin.getServer().getWorlds()) {
            worlds.add(w.getName());
        }
        WORLD_SUBS = ImmutableList.copyOf(worlds);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            if (command.getName().equals("twac") || command.getName().equals("twak") || command.getName().equals("twa")) {
                return partial(args[0], LETTER_SUBS);
            }
            if (command.getName().equals("twas") || command.getName().equals("twad")) {
                return partial(args[0], MONSTER_SUBS);
            }
        } else if (args.length == 2) {
            if (command.getName().equals("twad")) {
                return partial(args[1], ONOFF_SUBS);
            }
            if (command.getName().equals("twa")) {
                return partial(args[1], CONFIG_SUBS);
            }
            if (command.getName().equals("twac") || command.getName().equals("twak")) {
                return partial(args[1], WORLD_SUBS);
            }
        } else if (args.length == 3) {
            if (command.getName().equals("twa")) {
                return partial(args[2], WORLD_SUBS);
            }
        }
        return ImmutableList.of();
    }

    private List<String> partial(String token, Collection<String> from) {
        return StringUtil.copyPartialMatches(token, from, new ArrayList<String>(from.size()));
    }
}
