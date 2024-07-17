package net.untitledduckmod.common.config.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.untitledduckmod.DuckMod;

public class DuckModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> TinyConfig.getScreen(parent, DuckMod.MOD_ID);
    }
}

