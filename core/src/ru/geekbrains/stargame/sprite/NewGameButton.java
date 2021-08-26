package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.stargame.base.BaseButton;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.screen.GameScreen;

public class NewGameButton extends BaseButton {

    private static final float TOP_MARGIN = - 0.12f; // коэф для отступа
    private static final float SIZE = 0.05f;

    private final GameScreen gameScreen;


    public NewGameButton(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(SIZE);
        // позиционируем справа внизу
        setTop(TOP_MARGIN);
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}
