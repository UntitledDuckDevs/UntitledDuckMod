package net.untitledduckmod.mixin.forge;

import net.minecraft.entity.SpawnGroup;
import net.untitledduckmod.common.entity.CustomSpawnGroup;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpawnGroup.class)
public class SpawnGroupMixin {

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
