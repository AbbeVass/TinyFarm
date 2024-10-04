package com.gdxproj.tinyfarm;

public class CarrotSeedItem extends Item{
    @Override
    String getNickname() {
        return "carrotSeed";
    }

    @Override
    String getName() {
        return "Carrot Seed";
    }

    @Override
    String getDescription() {
        return "Use to place a carrot plant.";
    }

    @Override
    int getAppearance() {
        return 12;
    }

    @Override
    boolean useOnTile(int tileX, int tileY) {
        FarmWorld world = FarmWorld.getInstance();

        /*
         * Create a new carrot plant entity.
         */
        WorldEntity carrotPlant = new CarrotPlant(tileX, tileY);

        /*
         * Add the carrot plant entity to the world.
         */
        world.addEntity(carrotPlant);
        
        /*
         * Returning true means the seed item will be consumed.
         */
        return true;
    }
}
