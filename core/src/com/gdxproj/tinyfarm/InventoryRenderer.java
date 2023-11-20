package com.gdxproj.tinyfarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

public class InventoryRenderer implements InventorySubscriber {
    private Inventory _inventory;
    private Array<Sprite> _itemSprites;
    private Array<Item> _items;
    private Array<Integer> _itemQuantities;
    private SpriteRenderer _spriteRenderer;
    private ShapeRenderer _shapeRenderer;
    private int _width;
    private int _height;
    private float _originX;
    private float _originY;

    public InventoryRenderer(Inventory inventory) {
        _shapeRenderer = new ShapeRenderer();

        _width = Gdx.graphics.getWidth();
        _height = Gdx.graphics.getHeight();

        _originX = _width / 2.0f - 5 * 32.0f;
        _originY = 0.0f;

        _inventory = inventory;

        _itemSprites = new Array<Sprite>(10);
        _itemSprites.setSize(10);

        _itemQuantities = new Array<Integer>(10);
        _itemQuantities.setSize(10);

        _items = new Array<Item>(10);
        _items.setSize(10);

        _spriteRenderer = SpriteRenderer.getRendererForSpritesheet("items.png", 160, 80, 16);

        updateImages();

        //subscribe to the Inventory object so that we can automatically update
        //our view when the inventory is updated
        inventory.addSubscriber(this);
    }

    public void updateImages() {
        _items = _inventory.getFirstNItems(10);

        for(int i = 0; i < 10; ++i) {
            Item itemAt = _items.get(i);
            Sprite itemSprite = null;

            if(itemAt != null) {
                try {
                    itemSprite = _spriteRenderer.getSpriteAtIndex(itemAt.getAppearance());

                    itemSprite.setOrigin(0, 0);
                    itemSprite.setPosition(32.0f * i + _originX, _originY);
                    itemSprite.setScale(2.0f);
                } catch(FarmGameException ex) {
                    //TODO: error handling
                    
                    itemSprite = null;
                }
            }

            _itemSprites.set(i, itemSprite);
            _itemQuantities.set(i, _inventory.getItemCount(itemAt));
        }
    }

    /**
     * Render the inventory hotbar UI. Calls batch.begin() and batch.end(), 
     * client code should NOT call these.
     * @param batch the sprite batch to use for UI elements
     * @param delta frame time delta
     */
    public void render(Batch batch, float delta) {
        UIRenderer uiRenderer = UIRenderer.getInstance();
        BitmapFont font = uiRenderer.getFont();
        int selectedItem = _inventory.getSelection();

        for(int i = 0; i < 10; ++i) {
            _shapeRenderer.begin(ShapeType.Filled);

            if(selectedItem == i) {
                _shapeRenderer.setColor(Color.PINK);
            } else {
                _shapeRenderer.setColor(Color.TAN);
            }
            
            _shapeRenderer.rect(32.0f * i + _originX, _originY, 32.0f, 32.0f);
            _shapeRenderer.end();

            _shapeRenderer.begin(ShapeType.Line);
            _shapeRenderer.setColor(Color.BLACK);
            _shapeRenderer.rect(32.0f * i + _originX, _originY, 32.0f, 32.0f);
            _shapeRenderer.end();
        }

        batch.begin();

        for(int i = 0; i < 10; ++i) {
            Sprite sprite = _itemSprites.get(i);
            
            if(sprite != null) {
                Integer quantity = _itemQuantities.get(i);

                sprite.draw(batch);
                font.setColor(Color.WHITE);
                font.draw(batch, Integer.toString(quantity), 32.0f * i + _originX, _originY + 12.0f);
            }
        }

        batch.end();
    }

    @Override
    public void notifyAddItem(Item item, int count) {
        //TODO: optimize - update only the item that was added
        updateImages();
    }

    @Override
    public void notifyRemovedItem(Item item, int count) {
        //TODO: optimize - update only the item that was removed
        updateImages();
    }

    public void dispose() {
        _shapeRenderer.dispose();
        _spriteRenderer.dispose();
    }

    public Item getItemAtIndex(int index) {
        if(index < 0 || index > _items.size) {
            return null;
        }

        return _items.get(index);
    }
}
