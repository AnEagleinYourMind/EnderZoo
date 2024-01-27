package crazypants.enderzoo;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import crazypants.enderzoo.config.Config;
import crazypants.enderzoo.spawn.MobSpawnEventHandler;
import crazypants.enderzoo.spawn.MobSpawns;

import static crazypants.enderzoo.EnderZoo.*;

@Mod(
        modid = MODID,
        name = MOD_NAME,
        version = VERSION,
        dependencies = "required-after:Forge@10.13.0.1150,)",
        guiFactory = "crazypants.enderzoo.config.ConfigFactoryEnderZoo")
public class EnderZoo {

    public static final String MODID = "EnderZoo";
    public static final String MOD_NAME = "Ender Zoo";
    public static final String VERSION = "GRADLETOKEN_VERSION";

    @Instance(MODID)
    public static EnderZoo instance;

    public static MobSpawnEventHandler spawnEventHandler;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.load(event);
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        instance = this;
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        MobSpawns.instance.loadSpawnConfig();

        spawnEventHandler = new MobSpawnEventHandler();
        spawnEventHandler.init();
    }

}
