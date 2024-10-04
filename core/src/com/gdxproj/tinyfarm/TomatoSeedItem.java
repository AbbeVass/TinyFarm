package com.gdxproj.tinyfarm;

public class TomatoSeedItem extends Item {
    @Override
    String getNickname() {
        return "tomatoSeed";
    }

    @Override
    String getName() {
        return "Tomato Seed";
    }

    @Override
    String getDescription() {
        return "Use to place a tomato plant.";
    }

    @Override
    int getAppearance() {
        return 16;
    }

    @Override
    boolean useOnTile(int tileX, int tileY) {
        FarmWorld world = FarmWorld.getInstance();

        /*
         * Create a new tomato plant entity.
         */
        WorldEntity tomatoPlant = new TomatoPlant(tileX, tileY);

        /*
         * Add the tomato plant entity to the world.
         */
        world.addEntity(tomatoPlant);
        
        /*
         * Returning true means the seed item will be consumed.
         */
        return true;
    }
}
