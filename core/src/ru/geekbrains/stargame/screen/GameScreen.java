package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import java.util.List;
import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.Bullet;
import ru.geekbrains.stargame.sprite.EnemyShip;
import ru.geekbrains.stargame.sprite.MainShip;
import ru.geekbrains.stargame.sprite.Star;
import ru.geekbrains.stargame.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private static  final int STAR_COUNT = 64;

    private Texture bg;
    private Background background;

    private TextureAtlas atlas;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private Star[] stars;
    private MainShip mainShip;

    private Sound bulletSound;
    private Sound laserSound;
    private Music music;

    private EnemyEmitter enemyEmitter;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++){
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        enemyPool = new EnemyPool(worldBounds, bulletPool);
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        mainShip = new MainShip(atlas, bulletPool, laserSound);

        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        enemyEmitter = new EnemyEmitter(worldBounds, bulletSound, enemyPool, atlas);

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds); // позиционируем объект на игровом поле
        for(Star star : stars){
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        music.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    private void update(float delta){
        for (Star star : stars){
            star.update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyEmitter.generate(delta);
    }

    private void checkCollisions(){
        List<EnemyShip> enemyShips = enemyPool.getActiveSprites();
        List<Bullet> bullets = bulletPool.getActiveSprites();
        for(EnemyShip enemyShip : enemyShips){
            if(enemyShip.isDestroyed()){
                continue;
            }
            if(!mainShip.isOutside(enemyShip)){
                enemyShip.destroy();
            }
        }

                for (Bullet bullet : bullets) {
                    if (bullet.isDestroyed()) {
                        continue;
                    }
                    if (mainShip.equals(bullet.getOwner()) ) {
                        for (EnemyShip enemyShip : enemyShips) {
                            if (enemyShip.isDestroyed()) {
                                continue;
                            }
                            if (!bullet.isOutside(enemyShip)) {
                                if(enemyShip.takeDamage(1)){
                                    enemyShip.destroy();
                                }
                                bullet.destroy();
                            }
                        }
                    }
                }

    }

    private void freeAllDestroyed(){
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (Star star : stars){
            star.draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }
}
