package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;

public class MainShip extends Sprite {

    private static final float PADDING = 0.03f;
    private static final float V_LEN = 0.05f;

    private Vector2 v0;
    private Vector2 v;
    private Rect worldBounds;




    public MainShip(TextureRegion region) {
        super(region);
        this.v0 = new Vector2(0.5f, 0);
        this.v = new Vector2();
    }


    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.23f);
        // позиционируем по центру внизу
        setBottom(worldBounds.getBottom() + PADDING);


    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkAndHandleBounds();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }

    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                moveRight();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                stop();
                break;
        }
        return false;
    }

    public void moveRight(){
        v.set(v0);
    }

    public void moveLeft(){
        v.set(v0).rotateDeg(180);
    }

    public void stop(){
        v.setZero();
    }
    private void checkAndHandleBounds() {
        if(getRight() < worldBounds.getLeft()){
            setLeft(worldBounds.getLeft());
        }
        if(getLeft() > worldBounds.getRight()){
            setRight(worldBounds.getRight());
        }
    }


}




