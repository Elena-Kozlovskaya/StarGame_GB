package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;

public class GameOver extends Sprite {

    private static final float SIZE = 0.06f;
    private static final float BOTTOM_MARGIN = 0.009f;

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }


    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(SIZE);
        setBottom(BOTTOM_MARGIN);

    }
}