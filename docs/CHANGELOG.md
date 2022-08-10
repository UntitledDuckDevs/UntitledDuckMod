## Version ["v0.6.0"] - 2022-08-10 for 1.19

This version adds support for Minecraft 1.19, 1.19.1 and 1.19.2. In addition to that the spawning rules also changed a bit.
Ducks and geese will now only spawn near rivers. You can modify that behavior yourself though by adding biomes to the `#untitledduckmod:duck_spawn` and `#untitledduckmod:goose_spawn` using a data pack.

**Note for Forge Users:**
You can also no longer change the spawn rates in the forge configs since those are configured using [Biome Modifiers](https://forge.gemwire.uk/wiki/Biome_Modifiers#Add_Spawns) now. You can adjust the rates by overriding the corresponding modifier with a datapack.

### Features

- Make ducks and geese spawn near rivers
- Introduce #untitledduckmod:duck_spawn and #untitledduckmod:goose_spawn biome tags

### Bug fixes

- Fix duck animation standing in water after dive/clean
## Version ["v0.5.0"] - 2022-07-13

### Features

- Buff goose health to 20 HP when tamed([#61](https://github.com/Okabintaro/UntitledDuckMod/issues/61))

### Bug fixes

- Fix potion brewing and pillager intimidation by goose on forge([#63](https://github.com/Okabintaro/UntitledDuckMod/issues/63))
## Version ["v0.4.1"] - 2022-07-09

### Bug fixes

- Fix inspecio compatibility([#40](https://github.com/Okabintaro/UntitledDuckMod/issues/40))
## Version ["v0.4.0"] - 2022-07-08

This release adds support for minecraft 1.18.2 on both fabric and forge again.
It introduces configuration support for the spawn rate/weight which you can change now.
Additionally, most of the annoying bugs were fixed now which is why I am going to release this as v1.0 soon.

Thanks for playing and providing your feedback!

### Features

- Add cake recipe that uses a goose egg([#47](https://github.com/Okabintaro/UntitledDuckMod/issues/47))
- Implement forge config loading([#50](https://github.com/Okabintaro/UntitledDuckMod/issues/50),[#38](https://github.com/Okabintaro/UntitledDuckMod/issues/38))
- Implement fabric config loading([#50](https://github.com/Okabintaro/UntitledDuckMod/issues/50),[#38](https://github.com/Okabintaro/UntitledDuckMod/issues/38))
- Add fabric mod menu integration, update metadata([#50](https://github.com/Okabintaro/UntitledDuckMod/issues/50),[#38](https://github.com/Okabintaro/UntitledDuckMod/issues/38))

### Bug fixes

- Make all sounds mono, which should spatialize them properly([#36](https://github.com/Okabintaro/UntitledDuckMod/issues/36))
- Make dispensers shoot eggs([#29](https://github.com/Okabintaro/UntitledDuckMod/issues/29))
## [0.3.0] - 2021-07-05

### Added

- Ported to 1.17 by quiqueck(https://github.com/quiqueck), many thanks!
