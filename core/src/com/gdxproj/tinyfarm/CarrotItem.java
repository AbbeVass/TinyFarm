package com.gdxproj.tinyfarm;

public class CarrotItem extends Item{
    @Override
    String getNickname() {
        return "carrot";
    }

    @Override
    String getName() {
        return "Carrot";
    }

    @Override
    String getDescription() {
        return "Harvested carrot.";
    }

    @Override
    int getAppearance() {
        return 2;
    }

    @Override
    boolean useOnTile(int tileX, int tileY) {
        /* Returning false means the carrot seed is not consumed. */
        
        return false;
    }
}
