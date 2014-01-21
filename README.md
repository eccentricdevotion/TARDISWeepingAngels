#TARDISWeepingAngels

Inspired by: [TARDIS Ticket #530 - Weeping Angels](http://dev.bukkit.org/bukkit-plugins/tardis/tickets/530-weeping-angels/)

This plugin tranforms skeletons into terrifying Weeping Angels (as seen on  [Doctor Who](http://www.bbc.co.uk/programmes/p00wqr12/profiles/weeping-angels)).

![Weeping Angel](https://dl.dropboxusercontent.com/u/53758864/weepingangel1.jpg)

JAR available here: [http://dev.bukkit.org/bukkit-plugins/tardisweepingangels/files/](http://dev.bukkit.org/bukkit-plugins/tardisweepingangels/files/)

##Features
* Configurable spawn rate
* Configurable drop on death
* Configurable killing item
* Only spawn them in the worlds you want
* Can be frozen in place for a configurable time
* Configurable TARDIS Key stealing

##Information
Weeping Angels only spawn at night in loaded chunks. They spawn with grey leather armour and a water lily helmet (their wings).

Weeping Angels can only be killed with the configured weapon - by default a DIAMOND_PICKAXE - hitting them with anything else has no effect. When they die they drop a random (1-3) amount of STONE.

The angels move pretty fast, but you can freeze them in place by looking at them and quickly pressing the sneak key. Better arm yourself or flee quickly though, as they'll be after you again in a snap - and if they touch you, you'll be teleported away to a random location. If the TARDIS plugin is also installed (if it isn't WHY NOT?), your TARDIS Key will be stolen.

##Configuration
The default config is shown below:

```
spawn_rate:
    how_many: 5
    how_often: 400
    max_per_world: 50
worlds:
    - world
freeze_time: 100
weapon: DIAMOND_PICKAXE
drops:
    - STONE
    - COBBLESTONE
angels_can_steal: true
```
The `spawn_rate` section sets Angel spawning options.

* `how_many` sets how many Angels to spawn each time.
* `how_often` is the time period (in server ticks - _20 ticks = 1 second_) between spawn attempts.
* `max_per_world` is the maximum number of Weeping Angels can be alive at one time.

The `worlds` section allows you to list the worlds you want the angels to spawn in.

`freeze_time` is the length of time (in server ticks) that the Angels remain motionless for.

`weapon` sets the item that will kill a Weeping Angel.

`drops` sets a list of items that drop when the Angel is killed.

`angels_can_steal` sets whether the Angels can steal your TARDIS Key - requires the TARDIS plugin to be installed.
