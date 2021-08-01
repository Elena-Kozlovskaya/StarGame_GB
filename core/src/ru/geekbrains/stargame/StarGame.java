package ru.geekbrains.stargame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class StarGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture img;
	private int x = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.jpg");
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		x++;
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(background, 0,0);
		batch.draw(img, x, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
