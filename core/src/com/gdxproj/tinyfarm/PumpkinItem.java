package com.gdxproj.tinyfarm;

public class PumpkinItem extends Item {
    @Override
    String getNickname() {
        return "pumpkin";
    }

    @Override
    String getName() {
        return "Pumpkin";
    }

    @Override
    String getDescription() {
        return "Harvested pumpkin.";
    }

    @Override
    int getAppearance() {
        return 0;
    }

    @Override
    boolean useOnTile(int tileX, int tileY) {
        /* Returning false means the pumpkin seed is not consumed. */
        
        return false;
    }
}
