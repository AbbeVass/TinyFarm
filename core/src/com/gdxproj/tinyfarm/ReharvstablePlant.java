package com.gdxproj.tinyfarm;

public class ReharvstablePlant extends Plant {
    
    private String _nickname;

    public ReharvstablePlant(int tileX, int tileY, int startFrame, String nickname) {
        super(tileX, tileY, startFrame, nickname);

        _nickname = nickname;
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
            Item item = ItemManager.getInstance().getItemFromNickname(_nickname);

            world.getInventory().addItem(item, 1);
        } catch(FarmGameException e) {
            // Not a fatal exception: do nothing
        }
        
        // Go back one frame
        this.aliveTick(-1);
    }
}
