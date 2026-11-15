package net.untitledduckmod.compat.jade.provider;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.common.config.UntitledConfig;
import net.untitledduckmod.common.entity.WaterfowlEntity;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;

public class NextEatProvider  implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {

    public static final NextEatProvider INSTANCE = new NextEatProvider();

    static final Identifier UID = DuckMod.id("next_eat");

    @Override
    public void appendServerData(NbtCompound tag, EntityAccessor accessor) {
        if (accessor.getEntity() instanceof WaterfowlEntity entity) {
            if (entity.isEdibleFood(entity.getMainHandStack()) && UntitledConfig.enableForceEat()) {
                var next = entity.getRandomForceEatTick() - entity.getHeldFoodTick() + 20;
                if (next >= 0) {
                    tag.putInt("NextEat", next);
                }
            }
        }
    }

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        if (!accessor.getServerData().contains("NextEat")) {
            return;
        }
        var nextEat = accessor.getServerData().getInt("NextEat");

        tooltip.add(Text.translatable("untitledduckmod.jade.nextEat", IThemeHelper.get().seconds(nextEat)));
    }

    @Override
    public Identifier getUid() {
        return UID;
    }
}
