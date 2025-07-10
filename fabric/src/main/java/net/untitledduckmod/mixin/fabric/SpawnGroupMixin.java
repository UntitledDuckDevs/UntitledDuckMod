package net.untitledduckmod.mixin.fabric;

import net.minecraft.entity.SpawnGroup;
import net.untitledduckmod.common.entity.CustomSpawnGroup;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

// credit to hybrid aquatic for the code
@SuppressWarnings("unused")
@Mixin(SpawnGroup.class)
public class SpawnGroupMixin {

    SpawnGroupMixin(String enumname, int ordinal, String name, int spawnCap, boolean peaceful, boolean rare, int immediateDespawnRange) {
        throw new AssertionError();
    }

    @Shadow
    @Mutable
    @Final
    private static SpawnGroup[] field_6301;

    @Unique
    private static SpawnGroup untitledDuck$createSpawnGroup(String enumname, int ordinal, CustomSpawnGroup spawnGroup) {
        return ((SpawnGroup)(Object) new SpawnGroupMixin(enumname, ordinal, spawnGroup.name, spawnGroup.spawnCap, spawnGroup.peaceful, spawnGroup.rare, spawnGroup.immediateDespawnRange));
    }

    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/SpawnGroup;field_6301:[Lnet/minecraft/entity/SpawnGroup;", shift = At.Shift.AFTER))
    private static void addGroups(CallbackInfo ci) {
        int vanillaSpawnGroupsLength = field_6301.length;
        CustomSpawnGroup[] groups = CustomSpawnGroup.values();
        field_6301 = Arrays.copyOf(field_6301, vanillaSpawnGroupsLength + groups.length);

        for(int i = 0; i < groups.length; i++) {
            int pos = vanillaSpawnGroupsLength + i;
            CustomSpawnGroup spawnGroup = groups[i];
            spawnGroup.spawnGroup = field_6301[pos] = untitledDuck$createSpawnGroup(spawnGroup.name(), pos, spawnGroup);
        }
    }

}
