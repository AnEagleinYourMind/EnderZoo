package crazypants.enderzoo.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import crazypants.enderzoo.EnderZoo;
import crazypants.enderzoo.Log;

public final class Config {

    public static class Section {

        public final String name;
        public final String lang;

        public Section(String name, String lang) {
            this.name = name;
            this.lang = lang;
            register();
        }

        private void register() {
            sections.add(this);
        }

        public String lc() {
            return name.toLowerCase();
        }
    }

    public static final List<Section> sections;

    static {
        sections = new ArrayList<>();
    }

    public static Configuration config;

    public static File configDirectory;
    public static final String CONFIG_RESOURCE_PATH = "/assets/enderzoo/config/";

    public static final Section sectionDebug = new Section("Debug", "debug");
    public static boolean spawnConfigPrintDetailedOutput = false;

    public static void load(FMLPreInitializationEvent event) {

        FMLCommonHandler.instance().bus().register(new Config());
        configDirectory = new File(event.getModConfigurationDirectory(), EnderZoo.MODID.toLowerCase());
        if (!configDirectory.exists()) {
            configDirectory.mkdir();
        }

        File configFile = new File(configDirectory, "EnderZoo.cfg");
        config = new Configuration(configFile);
        syncConfig();
    }

    public static void syncConfig() {
        try {
            Config.processConfig(config);
        } catch (Exception e) {
            Log.error("EnderZoo has a problem loading it's configuration");
            e.printStackTrace();
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }

    @SubscribeEvent
    public void onConfigChanged(OnConfigChangedEvent event) {
        if (event.modID.equals(EnderZoo.MODID)) {
            Log.info("Updating config...");
            syncConfig();
        }
    }

    public static void processConfig(Configuration config) {
        spawnConfigPrintDetailedOutput = config.getBoolean(
                "spawnConfigPrintDetailedOutput",
                sectionDebug.name,
                spawnConfigPrintDetailedOutput,
                "When enabled detailed information about spawn config will be printed to the log.");
    }

    private Config() {}

}
