package com.gdxproj.tinyfarm;

public class OnionItem extends Item {
    @Override
    String getNickname() {
        return "onion";
    }

    @Override
    String getName() {
        return "Onion";
    }

    @Override
    String getDescription() {
        return "Harvested onion.";
    }

    @Override
    int getAppearance() {
        return 8;
    }

    @Override
    boolean useOnTile(int tileX, int tileY) {
        /* Returning false means the onion seed is not consumed. */
        
        return false;
    }
}
