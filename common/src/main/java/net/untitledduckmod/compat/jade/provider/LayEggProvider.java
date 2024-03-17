package net.untitledduckmod.compat.jade.provider;

import net.untitledduckmod.DuckMod;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.untitledduckmod.common.entity.WaterfowlEntity;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;

public enum LayEggProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        if (!accessor.getServerData().contains("NextEggIn")) {
            return;
        }
        tooltip.add(Text.translatable("jade.nextEgg", IThemeHelper.get().seconds(accessor.getServerData().getInt("NextEggIn"))));
    }

    @Override
    public void appendServerData(NbtCompound tag, EntityAccessor accessor) {
       if (accessor.getEntity() instanceof WaterfowlEntity entity) {
           tag.putInt("NextEggIn", entity.getEggLayTime());
       }
    }

    @Override
    public Identifier getUid() {
        return DuckMod.id("lay_egg");
    }
}
