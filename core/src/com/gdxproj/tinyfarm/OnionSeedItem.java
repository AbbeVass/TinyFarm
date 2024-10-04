package com.gdxproj.tinyfarm;

public class OnionSeedItem extends Item {
    
    @Override
    String getNickname() {
        return "onionSeed";
    }

    @Override
    String getName() {
        return "Onion Seed";
    }

    @Override
    String getDescription() {
        return "Use to place a onion plant.";
    }

    @Override
    int getAppearance() {
        return 18;
    }

    @Override
    boolean useOnTile(int tileX, int tileY) {
        FarmWorld world = FarmWorld.getInstance();

        /*
         * Create a new onion plant entity.
         */
        WorldEntity onionPlant = new OnionPlant(tileX, tileY);

        /*
         * Add the onion plant entity to the world.
         */
        world.addEntity(onionPlant);
        
        /*
         * Returning true means the seed item will be consumed.
         */
        return true;
    }
}
