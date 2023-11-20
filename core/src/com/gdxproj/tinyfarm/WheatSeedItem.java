package com.gdxproj.tinyfarm;

public class WheatSeedItem extends Item {
    @Override
    String getNickname() {
        return "wheatSeed";
    }

    @Override
    String getName() {
        return "Wheat Seed";
    }

    @Override
    String getDescription() {
        return "Use to place a wheat plant.";
    }

    @Override
    int getAppearance() {
        return 13;
    }

    @Override
    boolean useOnTile(int tileX, int tileY) {
        FarmWorld world = FarmWorld.getInstance();

        /*
         * Create a new wheat plant entity.
         */
        WorldEntity wheatPlant = new WheatPlant(tileX, tileY);

        /*
         * Add the wheat plant entity to the world.
         */
        world.addEntity(wheatPlant);
        
        /*
         * Returning true means the seed item will be consumed.
         */
        return true;
    }
    
}
