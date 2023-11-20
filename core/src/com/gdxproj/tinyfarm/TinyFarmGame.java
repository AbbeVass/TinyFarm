package com.gdxproj.tinyfarm;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TinyFarmGame extends Game {
	public Skin skin;
	public SpriteBatch batch;
	public BitmapFont font;
	public ShapeRenderer shapeRenderer;
	
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();

		skin = new Skin(Gdx.files.internal("kenney-pixel/skin/skin.json"));
		
		setScreen(new FarmingScreen(this));
	}
	
	public void dispose() {
		batch.dispose();
		font.dispose();
		shapeRenderer.dispose();
		skin.dispose();
	}
}
