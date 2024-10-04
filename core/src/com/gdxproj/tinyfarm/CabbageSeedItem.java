package com.gdxproj.tinyfarm;

public class CabbageSeedItem extends Item {
    @Override
    String getNickname() {
        return "cabbageSeed";
    }

    @Override
    String getName() {
        return "Cabbage Seed";
    }

    @Override
    String getDescription() {
        return "Use to place a cabbage plant.";
    }

    @Override
    int getAppearance() {
        return 11;
    }

    @Override
    boolean useOnTile(int tileX, int tileY) {
        FarmWorld world = FarmWorld.getInstance();

        /*
         * Create a new cabbage plant entity.
         */
        WorldEntity cabbagePlant = new CabbagePlant(tileX, tileY);

        /*
         * Add the cabbage plant entity to the world.
         */
        world.addEntity(cabbagePlant);
        
        /*
         * Returning true means the seed item will be consumed.
         */
        return true;
    }
}
