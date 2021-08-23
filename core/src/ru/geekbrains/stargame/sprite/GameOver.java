package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;

public class GameOver extends Sprite {

    private static final float SIZE = 0.08f;

    private Rect worldBounds;

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }


    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(SIZE);
        setBottom(worldBounds.getBottom() + worldBounds.getHalfHeight());

    }
}