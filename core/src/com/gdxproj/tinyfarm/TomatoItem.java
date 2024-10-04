package com.gdxproj.tinyfarm;

public class TomatoItem extends Item {
    @Override
    String getNickname() {
        return "tomato";
    }

    @Override
    String getName() {
        return "Tomato";
    }

    @Override
    String getDescription() {
        return "Harvested tomato.";
    }

    @Override
    int getAppearance() {
        return 6;
    }

    @Override
    boolean useOnTile(int tileX, int tileY) {
        /* Returning false means the tomato seed is not consumed. */
        
        return false;
    }
}
