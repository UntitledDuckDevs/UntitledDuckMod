package net.untitledduckmod.mixin.forge.compat.incubation;

import com.teamabnormals.blueprint.core.api.EggLayer;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;
import net.untitledduckmod.common.entity.WaterfowlEntity;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WaterfowlEntity.class)
@Implements(@Interface(iface = EggLayer.class, prefix = "egg$"))
public abstract class WaterfowlEggLayerMixin {

    @Shadow(remap = false)
    protected int eggLayTime;

    @Shadow(remap = false)
    protected abstract SoundEvent getLayEggSound();

    public Item egg$getEggItem() {
        return ((WaterfowlEntity) (Object) this).getEggItem();
    }

    public boolean egg$isBirdJockey() {
        return false;
    }

    public int egg$getEggTimer() {
        return this.eggLayTime;
    }

    public void egg$setEggTimer(int timer) {
        this.eggLayTime = timer;
    }

    public SoundEvent egg$getEggLayingSound() {
        return this.getLayEggSound();
    }

    public int egg$getNextEggTime(Random random) {
        return ((WaterfowlEntity) (Object) this).getRandomLayTime();
    }
}
