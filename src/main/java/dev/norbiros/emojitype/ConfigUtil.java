package dev.norbiros.emojitype;

import net.minecraftforge.common.config.Configuration;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ConfigUtil {
    public static Configuration config;

    public static void init(File configDir) {
        File configFile = new File(configDir, "emojitype.cfg");
        config = new Configuration(configFile);
        loadConfig();
    }

    public static void loadConfig() {
        // Load emoji codes from config
        String[] emojiArray = config.getStringList("emojiCodes", "general",
                new String[]{":bucket:,\uD83E\uDEA3",
                        ":shears:,✂", ":whiteflag:,⚑"
                },
                "List of emoji replacements");

        List<String> emojiCodeStrings = Arrays.asList(emojiArray);

        // Call update() to refresh emoji mappings
        EmojiType.update(emojiCodeStrings);

        // Save config if changed
        if (config.hasChanged()) {
            config.save();
        }
    }
}
