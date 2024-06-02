package com.lumijiez.cacheduper;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Tags.MODID, version = Tags.VERSION, name = Tags.MODNAME, acceptedMinecraftVersions = "[1.7.10]")
public class CacheDuper {

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CacheDupeCommand());
    }

}