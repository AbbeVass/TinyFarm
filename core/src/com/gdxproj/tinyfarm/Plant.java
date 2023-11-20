package com.gdxproj.tinyfarm;

public abstract class Plant extends WorldEntity {
    protected float _aliveTime;

    public Plant(int tileX, int tileY) {
        super(tileX, tileY);

        _aliveTime = 0.0f;
    }

    @Override
    public void aliveTick(float delta) {
        _aliveTime += delta;
    }

    @Override
    public int getAppearance() {
        int frame = (int)(_aliveTime / 2.0f);

        if(frame < 0) {
            frame = 0;
        } else if(frame > 4) {
            frame = 4;
        }

        return 50 + frame;
    }
}
