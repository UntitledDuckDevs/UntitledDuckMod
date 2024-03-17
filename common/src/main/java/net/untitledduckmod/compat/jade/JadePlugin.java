package net.untitledduckmod.compat.jade;

import net.untitledduckmod.DuckMod;
import net.untitledduckmod.common.entity.DuckEntity;
import net.untitledduckmod.common.entity.GooseEntity;
import net.untitledduckmod.compat.jade.provider.LayEggProvider;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin(DuckMod.MOD_ID)
public class JadePlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerEntityDataProvider(LayEggProvider.INSTANCE, DuckEntity.class);
        registration.registerEntityDataProvider(LayEggProvider.INSTANCE, GooseEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerEntityComponent(LayEggProvider.INSTANCE, DuckEntity.class);
        registration.registerEntityComponent(LayEggProvider.INSTANCE, GooseEntity.class);
    }

}
