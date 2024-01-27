package crazypants.enderzoo.config;

import crazypants.enderzoo.IoUtil;
import crazypants.enderzoo.Log;
import crazypants.enderzoo.spawn.impl.SpawnEntry;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SpawnConfig {

    public static final String CONFIG_NAME_USER = "SpawnConfig_User.xml";

    public static List<SpawnEntry> loadSpawnConfig() {
        File userFile = new File(Config.configDirectory, CONFIG_NAME_USER);
        List<SpawnEntry> userEntries = new ArrayList<>();

        try {
            String userText = IoUtil.readConfigFile(userFile, Config.CONFIG_RESOURCE_PATH + CONFIG_NAME_USER, false);
            if (userText == null || userText.trim().isEmpty()) {
                Log.error("Empty user config file: " + userFile.getAbsolutePath());
            } else {
                userEntries.addAll(SpawnConfigParser.parseSpawnConfig(userText));
                Log.info("Loaded " + userEntries.size() + " entries from user spawn config.");
            }
        } catch (Exception e) {
            Log.error("Could not load user defined spawn entries from file: " + CONFIG_NAME_USER);
            e.printStackTrace();
        }

        return userEntries;
    }

}
