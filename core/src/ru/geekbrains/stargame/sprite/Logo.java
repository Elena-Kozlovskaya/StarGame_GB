package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;

public class Logo extends Sprite {
    private Vector2 touch;
    private static final float V_LEN = 0.05f;
    private Vector2 velocity;

    public Logo(Texture texture) {
        super(new TextureRegion(texture));
        this.touch = new Vector2(); // позиция картинки
        this.velocity = new Vector2(); // скорость (расстояние между позициями двух векторов)

    }

    @Override
    public void resize(Rect worldBounds) {
        System.out.println("Logo resize");
        setHeightProportion(0.2f);
    }

    @Override
    public void update(float delta) {
        System.out.println("Logo update");
        if(touch.dst(pos) > V_LEN){
            pos.add(velocity);
        } else {
            pos.set(touch);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        System.out.println("Logo touchDown");
        this.touch.set(touch);
        velocity.set(touch.cpy().sub(pos)).setLength(V_LEN);
        return false;

    }




}
