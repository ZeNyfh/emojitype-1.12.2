package dev.norbiros.emojitype;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = EmojitypeForge.MODID, name = EmojitypeForge.NAME, version = EmojitypeForge.VERSION, guiFactory = "com.zenyfh.emojitype.client.gui.EmojiTypeGuiFactory")
public class EmojitypeForge {
    public static final String MODID = "examplemod";
    public static final String NAME = "Example Mod";
    public static final String VERSION = "1.0";
    public static String configDir = "";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configDir = String.valueOf(event.getModConfigurationDirectory());
        logger = event.getModLog(); // This is fine for Forge-based mods
        ConfigUtil.init(event.getModConfigurationDirectory());
        dev.norbiros.emojitype.EmojiType.init(); // Initialize the mod
    }


    @EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("started emojitype, have fun ig");
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }
}
