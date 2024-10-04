package com.gdxproj.tinyfarm;

public class CabbageItem extends Item {
    @Override
    String getNickname() {
        return "cabbage";
    }

    @Override
    String getName() {
        return "Cabbage";
    }

    @Override
    String getDescription() {
        return "Harvested cabbage.";
    }

    @Override
    int getAppearance() {
        return 1;
    }

    @Override
    boolean useOnTile(int tileX, int tileY) {
        /* Returning false means the cabbage seed is not consumed. */
        
        return false;
    }
}
