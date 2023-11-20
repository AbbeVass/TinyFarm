package com.gdxproj.tinyfarm;

public class WheatItem extends Item {
    @Override
    String getNickname() {
        return "wheat";
    }

    @Override
    String getName() {
        return "Wheat";
    }

    @Override
    String getDescription() {
        return "Harvested wheat.";
    }

    @Override
    int getAppearance() {
        return 3;
    }

    @Override
    boolean useOnTile(int tileX, int tileY) {
        /* Returning false means the wheat seed is not consumed. */
        
        return false;
    }
    
}
