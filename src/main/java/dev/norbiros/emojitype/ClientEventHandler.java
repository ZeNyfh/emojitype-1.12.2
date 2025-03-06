package dev.norbiros.emojitype;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;

public class ClientEventHandler {

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.currentScreen instanceof GuiChat) {
            if (!(mc.currentScreen instanceof EmojiChat)) {
                mc.displayGuiScreen(new EmojiChat());
            }
        }
    }

}
