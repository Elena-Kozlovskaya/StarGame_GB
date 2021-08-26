package ru.geekbrains.stargame.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.ExplosionPool;
import ru.geekbrains.stargame.sprite.Bullet;
import ru.geekbrains.stargame.sprite.Explosion;

public abstract class Ship extends Sprite {

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;

    protected final Vector2 v0;
    protected Vector2 v;

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;

    protected float bulletHeight;
    protected int bulletDamage;
    protected Sound bulletSound;
    protected int hp;


    protected float reloadInterval;
    protected float reloadTimer;

    private float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

    public Ship() {
        v0 = new Vector2();
        v = new Vector2();
        bulletPos = new Vector2();
        bulletV = new Vector2();
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        v0 = new Vector2();
        v = new Vector2();
        bulletPos = new Vector2();
        bulletV = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if(reloadTimer >= reloadInterval){
            reloadTimer = 0f;
            shoot();
        }
        // смена кадра мигание (реакция на попадание пули)
        damageAnimateTimer += delta;
        if(damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL){
            frame = 0;
        }
    }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
        frame = 1;
        damageAnimateTimer = 0f;
    }

// т.к. все корабли обладают одинаковым поведением, но нет возможности реализовать
// это поведение в базовом классе из-за различий в реализации поведения кораблей
//поэтому определяем это поведение в абстрактном методе, с реализацией в подклассах
// если будут созданы еще объекты типа Ship, можно будет обратиться к данной реализации метода
    public abstract boolean isBulletCollision(Bullet bullet);

    public int getBulletDamage() {
        return bulletDamage;
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    public int getHp() {
        return hp;
    }

    private void shoot(){
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, bulletDamage);
        bulletSound.play(0.03f);
    }

    private void boom(){
        Explosion explosion = explosionPool.obtain();
        explosion.set(pos, getHeight()); // передаем позицию корабля и делаем размер взрыва размером с корабль
    }
}
