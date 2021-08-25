package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.stargame.base.BaseButton;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.screen.GameScreen;

public class NewGameButton extends BaseButton {

    private static final float PADDING = 0.1f; // коэф для отступа

    private GameScreen gameScreen;


    public NewGameButton(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.08f);
        // позиционируем справа внизу
        setBottom(worldBounds.getBottom() + PADDING);
    }

    @Override
    public void action() {
        System.out.println("NewGameButton action");
        gameScreen.gameOver();
        this.destroy();
    }
}
