package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Ship;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.ExplosionPool;

public class EnemyShip extends Ship {

    public EnemyShip(Rect worldBounds, BulletPool bulletPool, ExplosionPool explosionPool) {
        super();
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        //позиция выстрела пули
        bulletPos.set(pos.x, pos.y - getHalfHeight());
        if(getTop() < worldBounds.getTop()){
            v.set(v0);
        } else {
            reloadTimer = reloadInterval * 0.8f;
        }
        // если корабль пересек нижнюю границу экрана, он уничтожается
        if (getBottom() < worldBounds.getBottom()){
            destroy();
        }
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
    }

    public void setPos( float x, float y){
        pos.set(x, y);
        bulletPos.set(pos.x, pos.y - getHalfHeight());

    }
 // чтобы пуля долетала до серидины корабля
    @Override
    public boolean isBulletCollision(Bullet bullet){
        return !(
                bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y
                );
    }

    @Override
    public void destroy() {
        super.destroy();
        reloadTimer = 0f;
    }
}
