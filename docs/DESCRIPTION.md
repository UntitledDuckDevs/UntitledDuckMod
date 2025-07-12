![Teaser Image](https://i.imgur.com/VeKFezN.jpg "Untitled Duck Mod")

![Available](https://cf.way2muchnoise.eu/versions/451789.svg)

This mod adds ducks and geese to minecraft.
It works on both fabric and forge since it is powered by [geckolib] and [architectury].

If you encounter any bugs, please report them on the github issue tracker linked in this project.

## Requirements

You will need to install [geckolib] for this mod to work.
Additionally, on fabric, make sure you have [fabric-api][fabric-api-mr] installed.

## Features
Starting from v2.1.0, their model sizes will be random, meaning they will come in different sizes.
It can be turned off via the config "*_baby_random_size".

### Duck
- Spawn in rivers and swamps (tag:#untitledduckmod:duck_spawn).
- Have three variants.
- Can be bred with seeds (tag:#untitledduckmod:duck_breeding_food).
- They love fishes even more, which you can use to tame them (tag:#untitledduckmod:duck_taming_food).
- They lay eggs and drop feathers similar to chickens.
- Unlike chickens, they also like to swim in the water, groom themselves and dip their head into the water.
- After being tamed, they can sometimes catch fish or other things such as treasures.
- Also, they dance when there is a music disc playing nearby!
- When they are named a specific name, they will have a special texture.

#### Duck Sack

- You can craft a duck sack and use it to transport ducks similar to how you can bucket fish.

### Goose

- Spawn in rivers (tag:#untitledduckmod:goose_spawn).
- Have two variants.
- They can be bred with all kinds of seeds similar to ducks (tag:#untitledduckmod:goose_breeding_food).
- But they love kelp and seagrass even more, which you can use to tame them (tag:#untitledduckmod:goose_taming_food).
- Similar to dogs they will try to attack with you and defend you from attacking monsters.
- Once tamed you can sit them down and give them weapons.
- Geese know how to defend themselves. Once attacked they will remember and fight you in groups.
- They will forgive you though when you give them food by right-clicking or throwing it to them.
- They will scare away illagers similar to how cats scare away creepers.
- Occasionally, they will try to steal the item in your hand.
- They will happily exchange that for food though, so you don't have to kill them.
- When they are named a specific name, they will have a special texture.

####  Goose Foot

- Item dropped from geese.
- Can be used to brew a potion of intimidation that scares illagers away(**this only works in survival mode!**).

## Config
- duck_spawn_weight - Spawn weight of ducks
- duck_min_group_size - Minimum group size for duck spawns
- duck_max_group_size - Maximum group size for duck spawns
- duck_fishing_change - Probability of ducks successfully catching fish
- duck_tamed_not_follow - No more following behavior when tamed (wander)
- duck_baby_random_size - Duckling model size will be randomised and will affect adult size
- goose_spawn_weight - Spawn weight of geese
- goose_min_group_size - Minimum group size for goose spawns
- goose_max_group_size - Maximum group size for goose spawns
- goose_tamed_not_follow - No more following behavior when tamed (wander)
- goose_baby_random_size - Gosling model size will be randomised and will affect adult size
- intimidation_blacklist - Intimidation potion blacklist
- food_healing_value - Food can heal the health value of duck & goose

Known Issues
------------

At the moment there are some issues with duck and goose animations not playing when using shaders with the iris mod.
It seems to be an issue with geckolib which might get fixed in the future.

FAQ
---

### Where do the ducks and geese spawn?

They spawn in every biome but require a grass block to be present.
This is so they also spawn in modded biomes and similar to how vanilla animals spawn.

### How can I configure the spawn rates?

**Forge:** For forge the spawn rates are world/server specific and the configuration file is stored in `.minecraft/config/untitledduckmod.json`.
In there you can adjust the spawn weight for each mob which basically makes them spawn more often the higher the value is.

**Fabric:** The configuration file in fabric is stored in `.minecraft/config/untitledduckmod.json`.
There are no comments in this file, but you can adjust the number behind `duck_spawn_weight` and `goose_spawn_weight` to adjust how often they should spawn.
Alternatively, if you have the [ModMenu][modmenu-mr] installed you can use the configuration ui to change these values.

### Will you backport to 1.12, 1.16?

No, only the most recent minecraft versions will receive updates.

Credits
-------

- Duck Model, Texture and Animations by ArtistMonster24
- Duck Sounds by [Sidearm Studios] from the [Ultimate Animal Sounds] pack
- Duckling Sounds by [basedMedia]: [Chirp1], [Chirp2]
- 1.17 Port by [quiqueck], many thanks!
- 1.21.6 Port by [Xebeth], many thanks!

[basedMedia]: https://freesound.org/people/basedMedia/
[geckolib]: https://geckolib.com
[architectury]: https://github.com/architectury/architectury-plugin
[Sidearm Studios]: https://sidearmstudios.com
[Ultimate Animal Sounds]: https://assetstore.unity.com/packages/audio/sound-fx/animals/ultimate-animal-sounds-173490
[Chirp1]: https://freesound.org/people/basedMedia/sounds/548099/
[Chirp2]: https://freesound.org/people/basedMedia/sounds/548096/
[fabric-api-cf]: https://www.curseforge.com/minecraft/mc-mods/fabric-api
[fabric-api-mr]: https://modrinth.com/mod/fabric-api
[quiqueck]: https://github.com/quiqueck
[Xebeth]: https://github.com/Xebeth
[modmenu-mr]: https://modrinth.com/mod/modmenu
[modmenu-cf]: https://www.curseforge.com/minecraft/mc-mods/modmenu