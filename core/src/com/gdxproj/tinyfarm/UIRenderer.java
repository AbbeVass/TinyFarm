package com.gdxproj.tinyfarm;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class UIRenderer {
    private BitmapFont _font;
    private static UIRenderer __instance;

    private UIRenderer() {
        _font = new BitmapFont(Gdx.files.internal("mono.fnt"));
    }

    /**
     * Get the item manager's singleton instance.
     * @return the singleton instance
     */
    public static UIRenderer getInstance() {
        if(__instance == null) {
            __instance = new UIRenderer();
        }

        return __instance;
    }

    /**
     * Get the default font.
     * @return the default font
     */
    public BitmapFont getFont() {
        return _font;
    }
}
