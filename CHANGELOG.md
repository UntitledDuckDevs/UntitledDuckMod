## [0.2.0] - 2021-05-09

This release includes the geese! We would have liked to add more features, but we don't want to make you wait
any longer. Unfortunately I currently don't have the time to continue implementing features, but I will give my best
fixing bugs you report on github.

Another thing I am not sure about is the spawning rates. I don't fully understand
how the spawning rates work yet, but they seem kinda appropriate now. I am willing to change these if you got
concrete requests.
Adding a config file and exposing the spawning rates/weights as values is probably a good thing to add.
So please keep in mind this still a **beta** mod and not 100% stable or perfect yet.

### Added 

- Goose
    - They can be bred with all kinds of seeds similar to ducks.
    - You can tame them using those! But they love kelp and seagrass even more.
      - Similar to dogs they will try to attack with you and defend you from attacking monsters.
      - Once tamed you can sit them down, give them weapons.
    - Geese know how to defend themself and once attacked they will remember and fight you in groups.
      - They will forgive you though when you give them food by right clicking or throwing it to them.
    - They will scare away illagers similar to how cats scare away creepers.
    - Rarely they will try to steal your item in your hand.
      - They will happily exchange that for food though, so you don't have to kill them.
- Goose Foot
  - Item dropped from geese.
  - Can be used to brew potion of intimidation that scares illagers away.
- Duck Sack
  - You can craft a duck sack and use it to transport ducks similar to how you can bucket fish.

### Changes

- Upgrade forge, fabric and geckolib requirements. While the old version still ran in 1.16.4 this one won't.
  Please don't ask for 1.16.4 compatibility. 1.16.4 mods will work in an 1.16.5 instance aswell so there is no reason
  to run such an old version of minecraft anymore.