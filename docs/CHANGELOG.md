## Version ["v0.7.0"] - 2024-04-04

### Features

- Ducks can now dance constantly by the jukebox([#79](https://github.com/Okabintaro/UntitledDuckMod/issues/79))
- Ducks can be tamed now([#84](https://github.com/Okabintaro/UntitledDuckMod/issues/84))
- Ducks can fish now([#53](https://github.com/Okabintaro/UntitledDuckMod/issues/53))
- Tamed ducks can catch treasure when fishing
- Ducks can now spawn in swamps too
- Introduce #untitledduckmod:ducks_spawnable_on 和 #untitledduckmod:geese_spawnable_on tags
- Added blacklist list for intimidation potion effects, mobs in this list will be unaffected by intimidation effects
- Added Jade plugin for duck and goose laying time

#### Forge

- Forge can now change the spawn rate with config.

### Bug fixes

- Fix Duck sack error storing duck data
- Fix occupied duck sacks disappearing when in use([#85](https://github.com/Okabintaro/UntitledDuckMod/issues/85))
- Fix bug to spawn one variant from eggs by [utsudashinou](https://github.com/utsudashinou)([#76](https://github.com/Okabintaro/UntitledDuckMod/issues/76))

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
