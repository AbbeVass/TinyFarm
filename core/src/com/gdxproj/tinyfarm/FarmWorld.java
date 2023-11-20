package com.gdxproj.tinyfarm;

import java.util.Vector;

public class FarmWorld {
    private static FarmWorld __instance;
    
    private Vector<Vector<WorldEntity>> _entities;
    private Inventory _inventory;

    private FarmWorld() {
        _inventory = new Inventory();
        _entities = new Vector<Vector<WorldEntity>>();
        
        clearEntities();
    }

    public static FarmWorld getInstance() {
        if(__instance == null) {
            __instance = new FarmWorld();
        }

        return __instance;
    }

    public Inventory getInventory() {
        return _inventory;
    }

    public void tickEntities(float delta) {
        for(int i = 0; i < 256; ++i) {
            for(int j = 0; j < 256; ++j) {
                WorldEntity entity = _entities.get(i).get(j);

                if(entity != null) {
                    entity.aliveTick(delta);

                    if(entity.shouldBeDeleted()) {
                        _entities.get(i).set(j, null);
                    }
                }
            }
        }
    }

    public void iterateEntities(WorldEntityCallback callback) {
        for(int i = 0; i < 256; ++i) {
            for(int j = 0; j < 256; ++j) {
                WorldEntity entity = _entities.get(i).get(j);

                if(entity != null) {
                    callback.callback(entity);
                }
            }
        }
    }

    public void clearEntities() {
        _entities.setSize(256);

        for(int i = 0; i < 256; ++i) {
            Vector<WorldEntity> entityRow = new Vector<WorldEntity>();
            entityRow.setSize(256);

            _entities.set(i, entityRow);
        }
    }

    public void addEntity(WorldEntity entity) {
        int tileX = entity.getX();
        int tileY = entity.getY();

        _entities.get(tileX).set(tileY, entity);
    }

    public void interactWithEntity(int X, int Y) {
        WorldEntity entity = _entities.get(X).get(Y);

        /* Avoid a null pointer exception if the entity doesn't exist. */
        if(entity == null) {
            return;
        }

        entity.interact();
    }
}
