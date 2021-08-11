package ru.geekbrains.stargame.sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;

public class Star extends Sprite {

    private Vector2 v;
    private Rect worldBounds; // узнать границы игрового мира, для возврата объекта(с противоположной стороны), который вышел за границу экрана

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star")); // поиск названия региона в конфиг файле атласа
        v = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        // метод срабатывает, когда открывается экран, сода приходят актуальные границы игрового мира (прямоугольника Rect worldBounds). И получаем первую точку на игровом
        // в которую можно спозиционировать объект
        // срабатывает при каждом изменении размеров игрового экрана
        this.worldBounds = worldBounds; //  сохраняем границы игрового мира
        float height = Rnd.nextFloat(0.005f, 0.013f);  // выставить размер звезд (рандомно в диапазоне)
        setHeightProportion(height);
        float x = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight()); // позиционирование (рандомно от левого до правого края) звезд на игровом поле
        float y = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop()); // позиционирование (рандомно от нижнего до верхнего края) звезд на игровом поле
        pos.set(x,y); // задали координаты вектора позиции
        v.set(Rnd.nextFloat(-0.005f, 0.005f), getHeight() * -5); // задали коэффициент скорости движения (зависимость от размера звезды)
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta); // сложили векторы и умножили на скаляр (дельта, для синхронизации частоты срабатывания метода апдейт)
        checkAndHandleBounds();
    }

    /**
     * Заколцовывает движение звезд на игровом поле
     */
    private void checkAndHandleBounds(){
        if(getRight() < worldBounds.getLeft()){
            setLeft(worldBounds.getRight());
        }
        if(getLeft() > worldBounds.getRight()){
            setRight(worldBounds.getLeft());
        }
        if(getTop() < worldBounds.getBottom()){
            setBottom(worldBounds.getTop());
        }
        if(getBottom() > worldBounds.getTop()){
            setTop(worldBounds.getBottom());
        }
    }


}
