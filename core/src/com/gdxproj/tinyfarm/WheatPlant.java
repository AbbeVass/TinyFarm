package com.gdxproj.tinyfarm;

public class WheatPlant extends Plant {
    
    public WheatPlant(int tileX, int tileY) {
        super(tileX, tileY);
    }

    private int getCurrentFrame() {
        int frame = (int)(_aliveTime / 2.0f);

        return frame;
    }

    @Override
    public int getAppearance() {
        int frame = getCurrentFrame();

        if(frame < 0) {
            frame = 0;
        } else if(frame > 4) {
            frame = 4;
        }

        return 50 + frame;
    }

    @Override
    public void interact() {

        /* The player interacted with the plant.
         * Check first if the plant is ready for harvesting.
         */
        if(getCurrentFrame() < 4) {
            return;
        }

        /**
         * The plant is ready for harvesting,
         * so add a wheat item to the inventory, and remove
         * the plant entity from the world.
         */
        FarmWorld world = FarmWorld.getInstance();

        try {
            Item wheatItem = ItemManager.getInstance().getItemFromNickname("wheat");

            world.getInventory().addItem(wheatItem, 1);
        } catch(FarmGameException e) {
            // Not a fatal exception: do nothing
        }
        
        // Schedule the entity for deletion
        this.destroy();
    }
}
