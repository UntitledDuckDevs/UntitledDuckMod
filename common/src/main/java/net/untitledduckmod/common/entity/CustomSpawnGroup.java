package net.untitledduckmod.common.entity;

import net.minecraft.entity.SpawnGroup;

public enum CustomSpawnGroup {
    WATERFOWL("waterfowl", 10, true, false, 128);

    public SpawnGroup spawnGroup;
    public final String name;
    public final int spawnCap;
    public final boolean peaceful;
    public final boolean rare;
    public final int immediateDespawnRange;

    public static final String WATERFOWL_NAME = "waterfowl";
    public static final int WATERFOWL_SPAWN_CAP = 10;
    public static final boolean WATERFOWL_PEACEFUL = true;
    public static final boolean WATERFOWL_RARE = false;
    public static final int WATERFOWL_IMMEDIATE_DESPAWN_RANGE = 128;

    CustomSpawnGroup(String name, int spawnCap, boolean peaceful, boolean rare, int immediateDespawnRange) {
        this.name = name;
        this.spawnCap = spawnCap;
        this.peaceful = peaceful;
        this.rare = rare;
        this.immediateDespawnRange = immediateDespawnRange;
    }
}
