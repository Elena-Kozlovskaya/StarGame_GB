package ru.geekbrains.stargame.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.sprite.EnemyShip;

// Утилита генерить вражеские корабли
public class EnemyEmitter {

    private static final float GENERATE_INTERVAL = 4f;

    private  static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private  static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private  static final int ENEMY_SMALL_BULLET_DAMAGE = 1;
    private  static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
    private  static final int ENEMY_SMALL_HP = 1; // количество жизней

    private  static final float ENEMY_MEDIUM_HEIGHT = 0.15f;
    private  static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.02f;
    private  static final int ENEMY_MEDIUM_BULLET_DAMAGE = 5;
    private  static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 4f;
    private  static final int ENEMY_MEDIUM_HP = 5;

    private  static final float ENEMY_BIG_HEIGHT = 0.2f;
    private  static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
    private  static final int ENEMY_BIG_BULLET_DAMAGE = 10;
    private  static final float ENEMY_BIG_RELOAD_INTERVAL = 1f;
    private  static final int ENEMY_BIG_HP = 10;

    private final Rect worldBounds;
    private final Sound bulletSound;
    private final TextureRegion bulletRegion;
    private final EnemyPool enemyPool;

    private final TextureRegion[] enemySmallRegions;
    private final TextureRegion[] enemyMediumRegions;
    private final TextureRegion[] enemyBigRegions;

    private final Vector2 enemySmallV = new Vector2(0f, -0.2f);
    private  static final Vector2 enemySmallBulletV = new Vector2( 0,-0.3f);

    private final Vector2 enemyMediumV = new Vector2(0f, -0.03f);
    private  static final Vector2 enemyMediumBulletV = new Vector2( 0,-0.25f);

    private final Vector2 enemyBigV = new Vector2(0f, -0.005f);
    private  static final Vector2 enemyBigBulletV = new Vector2( 0,-0.3f);

    private float generateTimer;

    public EnemyEmitter(Rect worldBounds, Sound bulletSound, EnemyPool enemyPool, TextureAtlas atlas) {
        this.worldBounds = worldBounds;
        this.bulletSound = bulletSound;
        this.enemyPool = enemyPool;
        bulletRegion = atlas.findRegion("bulletEnemy");
        enemySmallRegions = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);
        enemyMediumRegions = Regions.split(atlas.findRegion("enemy1"), 1, 2, 2);
        enemyBigRegions = Regions.split(atlas.findRegion("enemy2"), 1, 2, 2);
    }

    public  void generate(float delta){
        generateTimer += delta;
        if(generateTimer >= GENERATE_INTERVAL){
            generateTimer = 0;
            // создаем новый корабль
            EnemyShip enemy = enemyPool.obtain();
            float type = (float) Math.random();
            if(type < 0.5f){
                enemy.set(
                        enemySmallRegions,
                        enemySmallV,
                        bulletRegion,
                        enemySmallBulletV,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_DAMAGE,
                        bulletSound,
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        ENEMY_SMALL_HEIGHT,
                        ENEMY_SMALL_HP
                );
            } else if(type < 0.8f) {
                enemy.set(
                        enemyMediumRegions,
                        enemyMediumV,
                        bulletRegion,
                        enemyMediumBulletV,
                        ENEMY_MEDIUM_BULLET_HEIGHT,
                        ENEMY_MEDIUM_BULLET_DAMAGE,
                        bulletSound,
                        ENEMY_MEDIUM_RELOAD_INTERVAL,
                        ENEMY_MEDIUM_HEIGHT,
                        ENEMY_MEDIUM_HP
                );
            } else {
                enemy.set(
                        enemyBigRegions,
                        enemyBigV,
                        bulletRegion,
                        enemyBigBulletV,
                        ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_DAMAGE,
                        bulletSound,
                        ENEMY_BIG_RELOAD_INTERVAL,
                        ENEMY_BIG_HEIGHT,
                        ENEMY_BIG_HP
                );

            }
            enemy.pos.x = Rnd.nextFloat(
                    worldBounds.getLeft() + enemy.getHalfWidth(),
                    worldBounds.getRight() - enemy.getHalfWidth()
            );
            enemy.setBottom(worldBounds.getTop());

        }
    }
}
