![Teaser Image](https://i.imgur.com/VeKFezN.jpg "Untitled Duck Mod")

This mod adds ducks and geese to minecraft.
It works on both fabric and forge since it is powered by [geckolib] and [architectury].

If you encounter any bugs, please report them on the github issue tracker linked in this project.

## Requirements

You will need to install [geckolib] for this mod to work.
Additionally, on fabric, make sure you have [fabric-api][fabric-api-mr] installed.

## Features

### Duck

- Can be bred with seeds.
- They lay eggs and drop feathers similar to chickens.
- Unlike chickens, they also like to swim in the water, groom themselves and dip their head into the water.
- Also, they dance when there is a music disc playing nearby!

#### Duck Sack

- You can craft a duck sack and use it to transport ducks similar to how you can bucket fish.

### Goose

- They can be bred with all kinds of seeds similar to ducks.
- But they love kelp and seagrass even more, which you can use to tame them.
- Similar to dogs they will try to attack with you and defend you from attacking monsters.
- Once tamed you can sit them down and give them weapons.
- Geese know how to defend themselves. Once attacked they will remember and fight you in groups.
  - They will forgive you though when you give them food by right-clicking or throwing it to them.
- They will scare away illagers similar to how cats scare away creepers.
- Occasionally, they will try to steal the item in your hand.
  - They will happily exchange that for food though, so you don't have to kill them.

####  Goose Foot

- Item dropped from geese.
- Can be used to brew a potion of intimidation that scares illagers away(**this only works in survival mode!**).

Known Issues
------------

At the moment there are some issues with duck and goose animations not playing when using shaders with the iris mod.
It seems to be an issue with geckolib which might get fixed in the future.

FAQ
---

### Where do the ducks and geese spawn?

They spawn in every biome but require a grass block to be present.
This is similar to 

### How can I configure the spawn rates?

**Forge:** For forge the spawn rates are world/server specific and the configuration file is stored in `.minecraft/saves/YOURWORLD/serverconfig/untitledduckmod-server.toml`.
In there you can adjust the spawn weight for each mob which basically makes them spawn more often the higher the value is.

**Fabric:** The configuration file in fabric is stored in `.minecraft/config/untitledduckmod.json`.
There are no comments in this file, but you can adjust the number behind `duck_spawn_weight` and `goose_spawn_weight` to adjust how often they should spawn.
Alternatively, if you have the [ModMenu][modmenu-mr] installed you can use the configuration ui to change these values.

### Will you backport to 1.12, 1.16?

No, only the most recent minecraft versions will receive updates.

### Will you port to 1.19?

Yes! But I want to wait until Mojang considers it done and released 1.19.1 or 1.19.2.

Credits
-------

- Duck Model, Texture and Animations by ArtistMonster24
- Duck Sounds by [Sidearm Studios] from the [Ultimate Animal Sounds] pack
- Duckling Sounds by [basedMedia]: [Chirp1], [Chirp2]
- 1.17 Port by [quiqueck], many thanks!

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
[modmenu-mr]: https://modrinth.com/mod/modmenu
[modmenu-cf]: https://www.curseforge.com/minecraft/mc-mods/modmenu
