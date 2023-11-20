package com.gdxproj.tinyfarm;

/**
 * Abstract class representing a world entity with some position.
 */
public abstract class WorldEntity {
    private int _tileX;
    private int _tileY;
    private boolean _willBeDeleted;

    /**
     * Create a new WorldEntity object at the specified coordinates.
     * @param x the tile x-coordinate of the object
     * @param y the tile y-coordinate of the object
     */
    public WorldEntity(int tileX, int tileY) {
        _tileX = tileX;
        _tileY = tileY;
        _willBeDeleted = false;
    }

    /**
     * Get the entity's appearance code. Index in the entities.png spritesheet.
     * @return the entity's appearance code
     */
    abstract int getAppearance();

    /**
     * Alive tick - do living entity logic
     * @param delta The time since the last frame, in seconds.
     */
    public void aliveTick(float delta) {

    }

    /**
     * Called when the entity is first created.
     */
    public void create() {

    }
    
    /**
     * Called when the entity is destroyed.
     */
    public void destroy() {
        _willBeDeleted = true;
    }

    /**
     * Get the entity's X-coordinate in the world.
     * @return the entity's X-coordinate
     */
    public int getX() {
        return _tileX;
    }

    /**
     * Get the entity's Y-coordinate in the world.
     * @return the entity's Y-coordinate
     */
    public int getY() {
        return _tileY;
    }

    /**
     * Returns whether the entity is scheduled for deletion.
     * @return true if the entity will be removed from the world on the next tick, false otherwise
     */
    public boolean shouldBeDeleted() {
        return _willBeDeleted;
    }

    /**
     * Interact with the entity.
     */
    public abstract void interact();
}
