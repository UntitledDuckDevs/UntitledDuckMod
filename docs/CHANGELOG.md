## Version ["v1.7.0"] - 2026-08-30

### Features
- Geese and ducks cannot pick up their own meat
#### Forge
- Incubation compat

## Version ["v1.5.2"] - 2026-2-3

### Bug fixes
- Incorrect translation of tipped_arrow

## Version ["v1.5.1"] - 2026-2-3

### Bug fixes
- [#139](https://github.com/UntitledDuckDevs/UntitledDuckMod/issues/#139)


## Version ["v1.5.0"] - 2026-2-3

### Credits
Special thanks to:
[Practise40hrs](https://github.com/Practise40hrs) - For providing the beautiful new textures for the **Pekin Duck** and **Greylag Goose** variants.

### Features
- Tamed geese now only pick up items from their owners
- Added a new Pekin Duck variant.
  ![Pekin Duck](images/pekin_duck.png)
- Added a new Greylag Goose variant.
  ![Greylag Goose](images/greylag_goose.png)

### Bug fixes
- Missing the name of intimidation arrow [#138](https://github.com/UntitledDuckDevs/UntitledDuckMod/issues/138)

## Version ["v1.3.5"] - 2026-1-22

### Bug fixes
- Supplement config translation
#### Forge
- Fix the initial value of force_eat_random_min_tick

## Version ["v1.3.0"] - 2026-1-22

### Features
Ducks and geese will auto-eat their held food within a configured tick.

### Bug fixes
- Fix [#131](https://github.com/UntitledDuckDevs/UntitledDuckMod/issues/131)


## Version ["v1.2.0"] - 2025-8-19

### Features
Update the goose's pickup logic: once tamed, it only picks up items given by its owner.

### Bug fixes
- Fix [#123](https://github.com/UntitledDuckDevs/UntitledDuckMod/issues/123)
#### Forge
- Fix [#124](https://github.com/UntitledDuckDevs/UntitledDuckMod/issues/124)

## Version ["v1.1.0"] - 2025-7-9

### Features
- Make the sizes of ducks and geese random (can be disabled in the config).
- Add CustomSpawnGroup

### Bug fixes
- Fix issues [#113](https://github.com/UntitledDuckDevs/UntitledDuckMod/issues/113), [#114](https://github.com/UntitledDuckDevs/UntitledDuckMod/issues/114) and [#115](https://github.com/UntitledDuckDevs/UntitledDuckMod/issues/115)

## Version ["v0.9.5"] - 2025-7-1

### Bug fixes
- Fix entity cannot be tamed

## Version ["v0.8.5"] - 2025-3-1

### Features
- Add config DuckTamedNotFollow
- Add config GooseTamedNotFollow

## Version ["v0.8.1"] - 2025-2-16

### Features
- Add Spanish translation by [@TheLegendofSaram](https://github.com/TheLegendofSaram)

### Bug fixes
- Fix [#100](https://github.com/Okabintaro/UntitledDuckMod/issues/100) and [#101](https://github.com/Okabintaro/UntitledDuckMod/issues/101)

## Version ["v0.8.0"] - 2024-12-21

### Features
- Add config foodHealingValue

### Bug fixes
- Fix c2me conflict([#93](https://github.com/Okabintaro/UntitledDuckMod/issues/93))

## Version ["v0.7.0"] - 2024-07-19

### Features

- Ducks can now dance constantly by the jukebox([#79](https://github.com/Okabintaro/UntitledDuckMod/issues/79))
- Ducks can be tamed now([#84](https://github.com/Okabintaro/UntitledDuckMod/issues/84))
- Ducks can fish now([#53](https://github.com/Okabintaro/UntitledDuckMod/issues/53))
- Tamed ducks can catch treasure when fishing
- Ducks can now spawn in swamps too
- Introduce the following tags: #untitledduckmod:ducks_spawnable_on, #untitledduckmod:geese_spawnable_on, #untitledduckmod:duck_breeding_food, #untitledduckmod:duck_taming_food, #untitledduckmod:goose_breeding_food, #untitledduckmod:goose_food, and #untitledduckmod:goose_taming_food
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
