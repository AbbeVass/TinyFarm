package com.gdxproj.tinyfarm;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Class which can be used to render sprites from a spritesheet based on given indices.
 * Instead of constructing a SpriteRenderer directly, use the static method
 * getRendererForSpritesheet() to take advantage of caching.
 */
public class SpriteRenderer {
    private String _spritesheet;
    private Texture _texture;
    private int _width;
    private int _height;
    private int _dimension;
    private int _columnsPerRow;
    private int _rows;

    private static HashMap<String, SpriteRenderer> __rendererCache;

    /**
     * Create a new SpriteRenderer with the given spritesheet and parameters.
     * @param spritesheet the name of the spritesheet file
     * @param width the width of the spritesheet image
     * @param height the height of the spritesheet image
     * @param dimension the dimension of each sprite in the spritesheet (16 if 16x16, 32 if 32x32)
     */
    private SpriteRenderer(String spritesheet, int width, int height, int dimension) {
        _spritesheet = spritesheet;
        _texture = new Texture(Gdx.files.internal(spritesheet));
        _width = width;
        _height = height;
        _dimension = dimension;
        _columnsPerRow = width / dimension;
        _rows = height / dimension;
    }

    /**
     * Get the sprite with given index in the spritesheet.
     * @param index the index to get
     * @return the new Sprite
     * @throws FarmGameException If the index is invalid (out of bounds), an exception may be thrown
     */
    public Sprite getSpriteAtIndex(int index) throws FarmGameException {
        if(index >= _rows * _columnsPerRow) {
            throw new FarmGameException("invalid sprite index " + index + " in sheet \'" + _spritesheet + "\'");
        }

        int originX = (index % _columnsPerRow) * _dimension;
        int originY = (index / _columnsPerRow) * _dimension;

        return new Sprite(new TextureRegion(_texture, originX, originY, _dimension, _dimension));
    }

    public void dispose() {
        _texture.dispose();
    }

    /**
     * Static method to create a SpriteRenderer object for a given spritesheet.
     * Supports caching SpriteRenderer objects.
     * @param spritesheet the name of the spritesheet file
     * @param width the width of the spritesheet image
     * @param height the height of the spritesheet image
     * @param dimension the dimension of each sprite in the spritesheet (16 if 16x16, 32 if 32x32)
     * @return a SpriteRenderer object for the spritesheet
     */
    public static SpriteRenderer getRendererForSpritesheet(String spritesheet, int width, int height, int dimension) {
        if(__rendererCache == null) {
            __rendererCache = new HashMap<String, SpriteRenderer>();
        }

        SpriteRenderer renderer = __rendererCache.get(spritesheet);

        if(renderer == null) {
            renderer = new SpriteRenderer(spritesheet, width, height, dimension);
            __rendererCache.put(spritesheet, renderer);
        }

        return renderer;
    }
}
