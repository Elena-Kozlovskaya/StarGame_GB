package ru.geekbrains.stargame.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class BaseScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;


    @Override
    public void show() {
        System.out.println("show");
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);

    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize width = " + width + " height = " + height);
    }

    @Override
    public void pause() {
        System.out.println("pause"); //- свернули игру
    }

    @Override
    public void resume() {
        System.out.println("resume"); //- развернули игру
    }

    @Override
    public void hide() {
        System.out.println("hide "); //- закрыли экран
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("dispose"); //- очистка от ненужных объектов
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode = " + keycode); // нажали клавишу
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp keycode = " + keycode); //отпустили клавишу
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped character = " + character); //факт нажатия, отображает символ клавиши
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown screenX = " + screenX + " screenY " + screenY); //нажатие по экрану или мышке
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp screenX = " + screenX + " screenY " + screenY); //отпустили по экрану или мышке
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged screenX = " + screenX + " screenY " + screenY); //нажали  и протащили по экрану или мышке
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        System.out.println("scrolled amountX = " + amountX + " amountY = " + amountY);
        return false;
    }
}
