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
import ru.geekbrains.stargame.pool.ExplosionPool;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.Bullet;
import ru.geekbrains.stargame.sprite.EnemyShip;
import ru.geekbrains.stargame.sprite.GameOver;
import ru.geekbrains.stargame.sprite.MainShip;
import ru.geekbrains.stargame.sprite.NewGameButton;
import ru.geekbrains.stargame.sprite.Star;
import ru.geekbrains.stargame.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private static  final int STAR_COUNT = 64;

    private Texture bg;
    private Background background;

    private TextureAtlas atlas;

    private Star[] stars;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private MainShip mainShip;
    private GameOver gameOver;
    private NewGameButton newGameButton;

    private Sound bulletSound;
    private Sound laserSound;
    private Sound explosionSound;
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
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyPool = new EnemyPool(worldBounds, bulletPool, explosionPool);
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        mainShip = new MainShip(atlas, bulletPool, explosionPool, laserSound);
        gameOver = new GameOver(atlas);
        newGameButton = new NewGameButton(atlas, this);

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
        gameOver.resize(worldBounds);
        newGameButton.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        explosionSound.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        music.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        newGameButton.touchDown(touch, pointer,button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        newGameButton.touchUp(touch, pointer,button);
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
        explosionPool.updateActiveSprites(delta);
        if(!mainShip.isDestroyed()) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta);
        }
    }

    private void checkCollisions(){
        if(mainShip.isDestroyed()){
            return;
        }
        List<EnemyShip> enemyShips = enemyPool.getActiveSprites();
        List<Bullet> bullets = bulletPool.getActiveSprites();
        for(EnemyShip enemyShip : enemyShips){
            if(enemyShip.isDestroyed()){
                continue;
            }
            float minDist = enemyShip.getHalfWidth() + mainShip.getHalfWidth();
            if(mainShip.pos.dst(enemyShip.pos) < minDist){
                mainShip.takeDamage(enemyShip.getBulletDamage() * 2);
                enemyShip.destroy();
            }
        }

                for (Bullet bullet : bullets) {
                    if (bullet.isDestroyed()) {
                        continue;
                    }
                        for (EnemyShip enemyShip : enemyShips) {
                            if (enemyShip.isDestroyed() || !mainShip.equals(bullet.getOwner())) {
                                continue;
                            }
                            if (enemyShip.isBulletCollision(bullet)) {
                                enemyShip.takeDamage(bullet.getDamage());
                                bullet.destroy();
                            }
                        }
                        if(bullet.getOwner() != mainShip && mainShip.isBulletCollision(bullet)){
                            mainShip.takeDamage(bullet.getDamage());
                            bullet.destroy();
                        }
                    }
    }

    private void freeAllDestroyed(){
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (Star star : stars){
            star.draw(batch);
        }
        if(!mainShip.isDestroyed()) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        }
        explosionPool.drawActiveSprites(batch);

        if (mainShip.isDestroyed()){
            gameOver.draw(batch);
            newGameButton.draw(batch);
        }
        batch.end();
    }

    public void gameOver(){
        if(mainShip.isDestroyed()){
        List<EnemyShip> enemyShips = enemyPool.getActiveSprites();
        List<Bullet> bullets = bulletPool.getActiveSprites();
        for(EnemyShip enemyShip : enemyShips) {
            if (!enemyShip.isDestroyed()) {
                enemyShip.destroy();
            }
        }

        for (Bullet bullet : bullets) {
            if (!bullet.isDestroyed()) {
                    bullet.destroy();
                }
            }
        System.out.println("flashAllDestroy");
            mainShip.flushDestroy();
            freeAllDestroyed();
            mainShip.revival();
        }
    }
}
