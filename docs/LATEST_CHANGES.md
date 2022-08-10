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
