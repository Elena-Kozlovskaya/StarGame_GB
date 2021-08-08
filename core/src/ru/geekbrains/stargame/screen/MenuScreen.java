package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.Logo;

public class MenuScreen extends BaseScreen {

    private Texture bg;
    private Background background;
    private Texture lg;
    private Logo logo;


    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        lg = new Texture("badlogic.jpg");
        background = new Background(bg);
        logo = new Logo(lg);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        logo.resize(worldBounds);

    }
    @Override
    public void render(float delta) {
        super.render(delta);
        draw();
        update(delta);

    }
    public void draw(){
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        batch.end();
    }
    public void update(float delta){
        logo.update(delta);
    }


    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        lg.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        System.out.println("MenuScreen touchDown");
        logo.touchDown(touch, pointer, button);
        return false;
    }
}
