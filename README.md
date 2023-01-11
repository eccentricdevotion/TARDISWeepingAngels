# TARDISWeepingAngels

Inspired by: [TARDIS Ticket #530 - Weeping Angels](http://dev.bukkit.org/bukkit-plugins/tardis/tickets/530-weeping-angels/)

This plugin tranforms Minecraft mobs into terrifying Whovian monsters (as seen on  [Doctor Who](https://www.bbc.co.uk/programmes/articles/4tDN85fyxYXTtVPrCql8jB/monsters)).


JAR available here: [http://tardisjenkins.duckdns.org:8080/job/TARDISWeepingAngels/](http://tardisjenkins.duckdns.org:8080/job/TARDISWeepingAngels/)

## Requirements

**From version 4.0.0 this plugin requires Paper server or a compatible fork.**

For the full experience (textures and sounds), you'll need the [TARDIS-Resource-Pack](http://tardisjenkins.duckdns.org:8080/job/TARDIS-Resource-Pack/)

## Monsters
* Weeping Angels
* Cybermen
* Daleks
* Empty Children
* Hath
* Ice Warriors
* Judoon
* K9
* Ood
* Silent
* Silurians
* Sontarans
* Strax
* Toclafane
* Vashta Nerada
* Zygons

## Features

### For each monster:

* Configurable spawn rate
* Configurable drop on death
* Only spawn them in the worlds you want
* Maximum monsters per world

### Angels:

* Configurable killing item
* Can be frozen in place for a configurable time
* Configurable TARDIS Key stealing

### Cybermen:

* Can upgrade villagers and players

## Information

### Weeping Angels

Weeping Angels only spawn at night in loaded chunks. They spawn with grey leather armour and a water lily helmet (their wings).

Weeping Angels can only be killed with the configured weapon - by default a DIAMOND_PICKAXE - hitting them with anything else has no effect. When they die they drop a random (1-3) amount of STONE.

The angels move pretty fast, but you can freeze them in place by looking at them and quickly pressing the sneak key. Better arm yourself or flee quickly though, as they'll be after you again in a snap - and if they touch you, you'll be teleported away to a random location. If the TARDIS plugin is also installed, your TARDIS Key will be stolen.

![Weeping Angel](https://eccentricdevotion.github.io/TARDIS/images/docs/weepingangel1.jpg)

### Cybermen

Cybermen can spawn at anytime. If configured, Cybermen will upgrade villagers and players when they have killed them (a new Cyberman) spawns in their place. If the upgraded entity was a player, the new Cyberman displays the player's name above its head.

![Cyberman](https://eccentricdevotion.github.io/TARDIS/images/docs/cyberman.jpg)

### Ice Warriors

Ice Warriors are really angry. They can spawn at anytime, but only spawn in snowy, icy or cold biomes. They carry an ice dagger. Did I mention they're angry!

![Ice Warrior](https://eccentricdevotion.github.io/TARDIS/images/docs/ice_warrior2.jpg)

### Daleks

Daleks come in different colours, but mostly spawn in their typical bronze colour. Exterminate!

![Dalek](https://eccentricdevotion.github.io/TARDIS/images/docs/dalek.jpg)

### Empty Children

Empty Children spawn anytime, and are of course child size. If you are killed by an Empty Child you get a gas mask applied to your head when you respawn that you can't remove for 30 seconds.

![Empty child](https://eccentricdevotion.github.io/TARDIS/images/docs/empty_child.jpg)

### Hath

Hath don't do much yet, but they look pretty cool.

![Hath](https://eccentricdevotion.github.io/TARDIS/images/docs/hath.jpg)

### Judoon

Judoon are the police force of the Whoniverse. Click an Judoon to claim it as your own. You can equip Judoon with ammunition (craft with arrows and gunpowder and put into a shulker box, then right click the Judoon with the box). Judoon can then be toggled to be in guard mode and will shoot any hostile mobs nearby. Use the /twa follow command to make the Judoon follow you around.

### Ood

Ood spawn randomly around villagers. Click an Ood to claim it as your own. Use the /twa follow command to make the Ood follow you around.

![Ood](https://eccentricdevotion.github.io/TARDIS/images/docs/ood.jpg)

### K-9

You can either craft a K-9 or tame a wolf to get a K-9! Clicking a K-9 will toggle whether he will follow you or stay put. The crafting recipe is 3 iron ingots, 3 redstone, and 3 bones in the crafting grid:

```
III
RRR
BBB
```

![K9](https://eccentricdevotion.github.io/TARDIS/images/docs/k9.jpg)

### Silurians

Only spawn underground in caves. Watch out for their Silurian guns!

![Silurian](https://eccentricdevotion.github.io/TARDIS/images/docs/silurian.png)

### Sontarans

Sontarans will try to kill you (as any good Sontaran should). If you manage to right-click a Sontaran with a Weakness Potion before he kills you, he will transform into Strax.

![Sontaran](https://eccentricdevotion.github.io/TARDIS/images/docs/sontaran.jpg)

### Strax

If you right-click Strax he'll talk to you, and if you right-click him with an empty bucket, you'll be able to milk him. Yum, yum Sontaran lactic fluid :) Be careful not to anger him though as he'll go rabid on you and let his killer Sontaran instincts get the better of him!

![Strax](https://eccentricdevotion.github.io/TARDIS/images/docs/strax.jpg)

### Vashta Nerada

Vashta Nerada have a random (configurable) chance of spawning when a bookshelf is broken, say "Hey who turned out the lights?" and of course try to eat you!

![Daleks](https://eccentricdevotion.github.io/TARDIS/images/docs/vashta_nerada.jpg)

### Zygons

Zygons don't do much yet (except try to kill you), but they look pretty cool.

![Zygon](https://eccentricdevotion.github.io/TARDIS/images/docs/zygon.jpg)

## Commands

| Command | Arguments                             | Description                                                                 |
|---------|---------------------------------------|-----------------------------------------------------------------------------|
| `/twa`  | `spawn [monster type]`                | Spawn a monster on the block you are looking at                             |  
| `       | `disguise [monster type] [on:off]`    | Disguise yourself as a TWA monster                                          |
|         | `equip [monster type]`                | Equip an armor stand with a TWA monster                                     |
| `       | `count [monster type] [world]`        | Reports the current number of monsters in the specified world               |
|         | `kill [monster type] [world]`         | Kills all of the monsters in the specified world                            |
|         | `set [monster type] [world] [amount]` | Sets the maximum number of the monsters that are allowed to spawn the world |
|         | `follow`                              | Makes the Ood/Judoon/K9 you are targeting follow you                        |
|         | `stay`                                | Makes the Ood/Judoon/K9 you are targeting _stop_ following you              |
|         | `remove`                              | Removes the Ood/Judoon/K9 you are targeting                                 |
|         | `give [player] [monster type]`        | Gives a player a monster head to display in an item frame                   |


## Videos

[![Toclafane](https://res.cloudinary.com/marcomontalbano/image/upload/v1673152061/video_to_markdown/images/youtube--skoNjweX-iA-c05b58ac6eb4c4700831b2b3070cd403.jpg)](https://youtu.be/skoNjweX-iA "Toclafane")

[![Judoon](https://res.cloudinary.com/marcomontalbano/image/upload/v1673152114/video_to_markdown/images/youtube--NOYn-u8JWTs-c05b58ac6eb4c4700831b2b3070cd403.jpg)](https://youtu.be/NOYn-u8JWTs "Judoon")

[![Ood](https://res.cloudinary.com/marcomontalbano/image/upload/v1673152441/video_to_markdown/images/youtube--pZcl76iZwNk-c05b58ac6eb4c4700831b2b3070cd403.jpg)](https://youtu.be/pZcl76iZwNk "Ood")
