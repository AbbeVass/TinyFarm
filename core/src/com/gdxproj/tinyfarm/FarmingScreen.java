package com.gdxproj.tinyfarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class FarmingScreen implements Screen {
    private final TinyFarmGame _game;
    private final FarmWorld _world;

    private Stage stage;

    private SpriteBatch worldBatch;
    private SpriteBatch uiBatch;
	private TiledMap tiledMap;
	private OrthographicCamera orthoCamera;
	private TiledMapRenderer tiledMapRenderer;
    private InventoryRenderer inventoryRenderer;
    private SpriteRenderer itemSpriteRenderer;
    private SpriteRenderer entitySpriteRenderer;

    private float absoluteDelta;

    public FarmingScreen(final TinyFarmGame game) {
        _game = game;
        _world = FarmWorld.getInstance();

        absoluteDelta = 0.0f;

        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

        float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();

		inventoryRenderer = new InventoryRenderer(FarmWorld.getInstance().getInventory());

        itemSpriteRenderer = SpriteRenderer.getRendererForSpritesheet("items.png", 160, 80, 16);
        entitySpriteRenderer = SpriteRenderer.getRendererForSpritesheet("entities.png", 160, 144, 16);

		orthoCamera = new OrthographicCamera();
		orthoCamera.setToOrtho(false, width, height);
		orthoCamera.zoom = 0.5f;
		orthoCamera.position.x = 0.0f;
		orthoCamera.position.y = 0.0f;
		orthoCamera.position.z = 0.0f;

		tiledMap = new TmxMapLoader().load("basicFarm.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		uiBatch = new SpriteBatch();
        worldBatch = new SpriteBatch();

        try {
            FarmWorld.getInstance().getInventory().addItem(ItemManager.getInstance().getItemFromNickname("wheatSeed"), 3);
        } catch(FarmGameException ex) {
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        /*
         * world logic - tick entities
         */
        _world.tickEntities(delta);

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			orthoCamera.translate(-64.0f * delta, 0);
		} if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			orthoCamera.translate(64.0f * delta, 0);
		} if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			orthoCamera.translate(0, 64.0f * delta);
		} if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			orthoCamera.translate(0, -64.0f * delta);
		}

        Inventory inventory = FarmWorld.getInstance().getInventory();
        int selectionIndex = inventory.getSelection();
        Item selectedItem = inventoryRenderer.getItemAtIndex(selectionIndex);

        if(Gdx.input.isKeyPressed(Input.Keys.NUM_0)) {
            inventory.setSelection(9);
        } else if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            inventory.setSelection(0);
        } else if(Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            inventory.setSelection(1);
        } else if(Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            inventory.setSelection(2);
        } else if(Gdx.input.isKeyPressed(Input.Keys.NUM_4)) {
            inventory.setSelection(3);
        } else if(Gdx.input.isKeyPressed(Input.Keys.NUM_5)) {
            inventory.setSelection(4);
        } else if(Gdx.input.isKeyPressed(Input.Keys.NUM_6)) {
            inventory.setSelection(5);
        } else if(Gdx.input.isKeyPressed(Input.Keys.NUM_7)) {
            inventory.setSelection(6);
        } else if(Gdx.input.isKeyPressed(Input.Keys.NUM_8)) {
            inventory.setSelection(7);
        } else if(Gdx.input.isKeyPressed(Input.Keys.NUM_9)) {
            inventory.setSelection(8);
        }
        
        /*
         * Home key: Reset the camera position
         */
        if(Gdx.input.isKeyJustPressed(Input.Keys.HOME)) {
			orthoCamera.position.x = 0.0f;
			orthoCamera.position.y = 0.0f;
			orthoCamera.position.z = 0.0f;
		}

        /*
         * Prepare for drawing, clear the screen to black
         */
        Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		orthoCamera.update();

        /*
         * World sprite batch uses the camera's projection matrix
         */
        worldBatch.setProjectionMatrix(orthoCamera.combined);

        /*
         * Render the farm tilemap
         */
		tiledMapRenderer.setView(orthoCamera);
		tiledMapRenderer.render();

        /*
         * Render world entities
         */
        worldBatch.begin();

        _world.iterateEntities(new WorldEntityCallback() {
            @Override
            void callback(WorldEntity entity) {
                Sprite entitySprite = null;

                try {
                    entitySprite = entitySpriteRenderer.getSpriteAtIndex(entity.getAppearance());
                } catch(FarmGameException ex) {
                    //TODO: error handling

                    return;
                }

                entitySprite.setPosition(entity.getX() * 16.0f, entity.getY() * 16.0f);
                entitySprite.draw(worldBatch);
            }
        });

        worldBatch.end();

        /*
         * Render the tile selection  overlay
         */
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.input.getY();
		
		Vector3 mouseWorldCoords = orthoCamera.unproject(new Vector3(mouseX, mouseY, 0));

		int tileIndexX = (int)(mouseWorldCoords.x / 16.0f);
		int tileIndexY = (int)(mouseWorldCoords.y / 16.0f);

        int tilePositionX = tileIndexX * 16;
		int tilePositionY = tileIndexY * 16;

        boolean isValidTileSelection =
            tileIndexX >= 0 && tileIndexX < 30 &&
            tileIndexY >= 0 && tileIndexY < 20;


		if(isValidTileSelection) {
			_game.shapeRenderer.setProjectionMatrix(orthoCamera.combined);

			_game.shapeRenderer.begin(ShapeType.Line);
			_game.shapeRenderer.setColor(Color.RED);
			_game.shapeRenderer.rect(tilePositionX, tilePositionY, 16.0f, 16.0f);
			_game.shapeRenderer.end();
		}

        /*
         * Scene2D: render UI
         */
        stage.act(delta);
        stage.draw();

        /*
         * Test code. Print "Farm Game" in the corner
         */
        uiBatch.begin();
        BitmapFont font = UIRenderer.getInstance().getFont();
        font.setColor(Color.WHITE);
        font.draw(uiBatch, "Farm Game", 0.0f, 12.0f);
        uiBatch.end();

        /*
         * Render inventory item overlays
         */
        inventoryRenderer.render(uiBatch, delta);

        /*
         * If there is a valid actively selected item, render
         * the hover sprite.
         */
        if(selectedItem != null && isValidTileSelection) {
            int selectedItemAppearance = selectedItem.getAppearance();
            Sprite itemSprite = null;

            try {
                itemSprite = itemSpriteRenderer.getSpriteAtIndex(selectedItemAppearance);
            } catch(FarmGameException ex) {
                //TODO: error handling

                itemSprite = null;
            }

            if(itemSprite != null) {
                itemSprite.setPosition(tilePositionX, tilePositionY);

                float alpha = MathUtils.clamp((float)(0.125 * Math.cos(absoluteDelta * 10.0)) + 0.5f, 0.0f, 1.0f);

                itemSprite.setAlpha(alpha);

                worldBatch.begin();
                itemSprite.draw(worldBatch);
                worldBatch.end();
            }
        }

        /*
         * LMB pressed - use item
         */
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if(selectedItem != null && isValidTileSelection) {
                if(selectedItem.useOnTile(tileIndexX, tileIndexY)) {
                    inventory.removeItem(selectedItem, 1);
                }
            }
        }
        /*
         * RMB pressed - interact with entity.
         */
        else if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            if(isValidTileSelection) {
                _world.interactWithEntity(tileIndexX, tileIndexY);
            }
        }

        //WARNING: at this point, selectedItem MIGHT NOT be the currently
        //selected item any more.

        //update absolute delta
        absoluteDelta += delta;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        tiledMap.dispose();
		uiBatch.dispose();
        worldBatch.dispose();
		inventoryRenderer.dispose();
    }
    
}
