package com.gdxproj.tinyfarm;

import com.badlogic.gdx.utils.StreamUtils;
import java.util.Random;

public abstract class Plant extends WorldEntity {
    protected float _aliveTime;
    private int _startFrame;
    private String _nickname;
    private Boolean _survived = false;

    Random rand = new Random();

    public Plant(int tileX, int tileY, int startFrame, String nickname) {
        super(tileX, tileY);

        _startFrame = startFrame;
        _nickname = nickname;
        _aliveTime = 0.0f;
    }

    @Override
    public void aliveTick(float delta) {
        if (delta == -1) {
            _aliveTime = 4;
            _survived = false;
        } else {
            _aliveTime += delta;
        }
    }

    @Override
    public int getAppearance() {
        int frame = (int)(_aliveTime / 2.0f);

        if(frame < 0) {
            frame = 0;
        } else if(frame >= 4) {

            if (!_survived) {
                // The plant has a 20% chance of dying
                int randNum = rand.nextInt(5);
                if (randNum == 0) {
                    this.destroy();
                    return _startFrame + 3;
                } else {
                    // The plant has survived and will not risk being destroyed until it's harvested
                    _survived = true;
                }
            }
            frame = 4;
        }

        return _startFrame + frame;
    }

    public int getCurrentFrame() {
        int frame = (int)(_aliveTime / 2.0f);

        return frame;
    }

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
        
        // Schedule the entity for deletion
        this.destroy();
        
    }
}
