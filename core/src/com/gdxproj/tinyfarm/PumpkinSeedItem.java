package com.gdxproj.tinyfarm;

public class PumpkinSeedItem extends Item {
    @Override
    String getNickname() {
        return "pumpkinSeed";
    }

    @Override
    String getName() {
        return "Pumpkin Seed";
    }

    @Override
    String getDescription() {
        return "Use to place a pumpkin plant.";
    }

    @Override
    int getAppearance() {
        return 10;
    }

    @Override
    boolean useOnTile(int tileX, int tileY) {
        FarmWorld world = FarmWorld.getInstance();

        /*
         * Create a new pumpkin plant entity.
         */
        WorldEntity pumpkinPlant = new PumpkinPlant(tileX, tileY);

        /*
         * Add the pumpkin plant entity to the world.
         */
        world.addEntity(pumpkinPlant);
        
        /*
         * Returning true means the seed item will be consumed.
         */
        return true;
    }
}
