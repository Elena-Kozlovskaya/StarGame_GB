package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;

public class MainShip extends Sprite {

    private static final float BOTTOM_MARGIN = 0.05f;
    private static final float HEIGHT = 0.15f;
    private static final int INVALID_POINTER = -1; // несуществующий палец

    private final Vector2 v0 = new Vector2(0.5f, 0);
    private Vector2 v = new Vector2();

    private boolean pressedLeft;
    private boolean pressedRight;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    private Rect worldBounds;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletV;
    private Vector2 bulletPos;

    private float bulletHeight;
    private int bulletDamage;
    private Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
    private float bulletTime = 0;


    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletV = new Vector2(0, 0.5f);
        bulletPos = new Vector2();
        bulletHeight = 0.01f;
        bulletDamage = 1;


    }


    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        this.worldBounds = worldBounds;
        // позиционируем по центру внизу
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);


    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);

        if(bulletTime <= 0){
                    shoot();
            bulletTime = 5;
        } else {
            bulletTime -= 0.5f;
        }


        /*if(getRight() > worldBounds.getRight()){
            setRight(worldBounds.getRight());
            stop();
        }
        if(getLeft() < worldBounds.getLeft()){
            setLeft(worldBounds.getLeft());
            stop();
        }*/

        if(getLeft() > worldBounds.getRight()){
            setRight(worldBounds.getLeft());
        }
        if(getRight() < worldBounds.getLeft()){
            setLeft(worldBounds.getRight());
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(touch.x < worldBounds.pos.x) {
            if(leftPointer != INVALID_POINTER){
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if(rightPointer != INVALID_POINTER){
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer){
            leftPointer = INVALID_POINTER;
            if(rightPointer != INVALID_POINTER){
                moveRight();
            } else {
                stop();
            }
        } else if(pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if(leftPointer != INVALID_POINTER){
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if(pressedRight){
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if(pressedLeft){
                    moveLeft();
                } else {
                    stop();
                }
                break;
            case Input.Keys.UP:
                    shoot();
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

    private void shoot(){
        Bullet bullet = bulletPool.obtain();
        sound.play(0.03f);
        System.out.println("shoot.sound.play");
        bulletPos.set(pos.x, pos.y + getHalfHeight());
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, bulletDamage);
    }



}



