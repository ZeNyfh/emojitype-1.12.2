package dev.norbiros.emojitype;

import dev.norbiros.emojitype.emoji.EmojiCode;
import net.minecraft.client.gui.GuiChat;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmojiChat extends GuiChat {
    private List<EmojiCode> autocompleteSuggestions = new ArrayList<>();
    private int selectedIndex = 0;
    private boolean shouldRenderAutocomplete = false;

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.inputField == null) return;
        if (!shouldRenderAutocomplete) {
            super.keyTyped(typedChar, keyCode);
        } else {
            inputField.textboxKeyTyped(typedChar, keyCode);
        }

        if (typedChar == ':') {
            replaceEmojis();
        }

        updateAutocomplete();

        if (shouldRenderAutocomplete) {
            if (keyCode == Keyboard.KEY_DOWN) {
                selectedIndex = (selectedIndex + 1) % autocompleteSuggestions.size();
                System.out.println("DOWN: " + selectedIndex);
                return;
            }
            if (keyCode == Keyboard.KEY_UP) {
                selectedIndex = (selectedIndex - 1 + autocompleteSuggestions.size()) % autocompleteSuggestions.size();
                System.out.println("UP: " + selectedIndex);
                return;
            }

            if (keyCode == Keyboard.KEY_TAB) {
                completeEmoji();
            }
        }

    }


    private void replaceEmojis() {
        String text = this.inputField.getText();
        for (EmojiCode emoji : EmojiType.DEFAULT_EMOJI_CODES) {
            text = text.replace(emoji.getCode(), emoji.getEmoji());
        }
        this.inputField.setText(text);
    }

    private void updateAutocomplete() {
        String text = inputField.getText();
        int lastColonIndex = text.lastIndexOf(':');

        if (lastColonIndex != -1 && lastColonIndex < text.length() - 1) {
            String partial = text.substring(lastColonIndex);
            autocompleteSuggestions = EmojiType.DEFAULT_EMOJI_CODES.stream()
                    .filter(e -> e.getCode().startsWith(partial))
                    .collect(Collectors.toList());

            shouldRenderAutocomplete = !autocompleteSuggestions.isEmpty();
        } else {
            autocompleteSuggestions.clear();
            shouldRenderAutocomplete = false;
        }
    }

    private void completeEmoji() {
        EmojiCode selected = autocompleteSuggestions.get(selectedIndex);
        String text = inputField.getText();
        int lastColonIndex = text.lastIndexOf(':');

        inputField.setText(text.substring(0, lastColonIndex) + selected.getEmoji());
        inputField.setCursorPosition(inputField.getText().length());

        autocompleteSuggestions.clear();
        shouldRenderAutocomplete = false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (shouldRenderAutocomplete && !autocompleteSuggestions.isEmpty()) {
            int boxWidth = 100;
            int boxHeight = autocompleteSuggestions.size() * 12 + 4;

            int x = inputField.x;
            int y = inputField.y - boxHeight - 5;

            if (y < 0) {
                y = inputField.y + 14;
            }

            drawRect(x, y, x + boxWidth, y + boxHeight, 0x90000000); // Dark background

            for (int i = 0; i < autocompleteSuggestions.size(); i++) {
                EmojiCode emoji = autocompleteSuggestions.get(i);
                String displayText = emoji.getEmoji() + " " + emoji.getCode();
                int color = (i == selectedIndex) ? 0xFFFFFF00 : 0xFFFFFFFF; // Highlight selected

                fontRenderer.drawString(displayText, x + 2, y + 2 + (i * 12), color);
            }
        }
    }


}
