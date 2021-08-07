package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Vector2 position;
    private Vector2 clickPosition;
    private static final float SPEED = 0.5f;
    private Vector2 velocity;
    private Vector2 clickPosCopy;


    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        position = new Vector2(); // позиция картинки
        clickPosition = new Vector2(); //позиция клика
        velocity = new Vector2(); // скорость (расстояние между позициями двух векторов)
        clickPosCopy = new Vector2();


    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(img, position.x, position.y);
        batch.end();
        clickPosCopy.set(clickPosition);
        if(clickPosCopy.sub(position).len() > SPEED){
            position.add(velocity);
        } else {
            position.set(clickPosition);
        }

    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        clickPosition.set(screenX, Gdx.graphics.getHeight() - screenY); // переворот координат
        velocity.set(clickPosition.cpy().sub(position)).scl(SPEED);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        clickPosition.set(screenX, Gdx.graphics.getHeight() - screenY); // переворот координат
        velocity.set(clickPosition.cpy().sub(position)).scl(SPEED);
        return super.touchDragged(screenX, screenY, pointer);
    }
}
