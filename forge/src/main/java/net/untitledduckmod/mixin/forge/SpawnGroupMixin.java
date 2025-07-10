package net.untitledduckmod.mixin.forge;

import net.minecraft.entity.SpawnGroup;
import net.untitledduckmod.common.entity.CustomSpawnGroup;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// credit to hybrid aquatic for the code
@SuppressWarnings("unused")
@Mixin(SpawnGroup.class)
public class SpawnGroupMixin {

    SpawnGroupMixin(String enumname, int ordinal, String name, int spawnCap, boolean peaceful, boolean rare, int immediateDespawnRange) {
        throw new AssertionError();
    }

    @Unique
    private static SpawnGroup untitledDuck$createSpawnGroup(String enumname, int ordinal, CustomSpawnGroup spawnGroup) {
        return ((SpawnGroup)(Object) new SpawnGroupMixin(enumname, ordinal, spawnGroup.name, spawnGroup.spawnCap, spawnGroup.peaceful, spawnGroup.rare, spawnGroup.immediateDespawnRange));
    }

    @Inject(method = "<clinit>", at = @At(value = "TAIL"))
    private static void addGroups(CallbackInfo ci) {
        var spawnGroup = SpawnGroup.byName(CustomSpawnGroup.WATERFOWL.name);
        if (spawnGroup == null) {
            spawnGroup = SpawnGroup.create(
                    CustomSpawnGroup.WATERFOWL_NAME,
                    CustomSpawnGroup.WATERFOWL_NAME,
                    CustomSpawnGroup.WATERFOWL_SPAWN_CAP,
                    CustomSpawnGroup.WATERFOWL_PEACEFUL,
                    CustomSpawnGroup.WATERFOWL_RARE,
                    CustomSpawnGroup.WATERFOWL_IMMEDIATE_DESPAWN_RANGE
            );
        }
        CustomSpawnGroup.WATERFOWL.spawnGroup = spawnGroup;
    }

}
