package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Ship;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;

public class EnemyShip extends Ship {

    public EnemyShip(Rect worldBounds, BulletPool bulletPool) {
        super();
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(getTop() < worldBounds.getTop()){
            v.set(v0);
        } else {
            reloadTimer = reloadInterval * 0.8f;
        }
        // если корабль пересек нижнюю границу экрана, он уничтожается
        if (getBottom() < worldBounds.getBottom()){
            destroy();
        }
        //позиция выстрела пули
        bulletPos.set(pos.x, pos.y - getHalfHeight());

    }

    public void set(
           TextureRegion[] regions,
           Vector2 v0,
           TextureRegion bulletRegion,
           Vector2 bulletV,
           float bulletHeight,
           int bulletDamage,
           Sound bulletSound,
           float reloadInterval,
           float height,
           int hp

    ){
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletV.set(bulletV);
        this.bulletHeight = bulletHeight;
        this.bulletDamage = bulletDamage;
        this.bulletSound = bulletSound;
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        this.hp = hp;
        v.set(0, -0.4f);
        bulletPos.set(pos.x, pos.y + getHalfHeight());
    }
}
