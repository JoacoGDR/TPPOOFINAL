package game.backend.cell;

import game.backend.Grid;
import game.backend.element.*;

public class FruitGeneratorCell extends CandyGeneratorCell {
    public FruitGeneratorCell(Grid grid) {
        super(grid);
    }

    @Override
    public Element getContent() {
        //hago un random para que se decida si voy a generar una fruta, o un caramelo
        int j = (int) (Math.random() * (20));
        if (j == 0) {

            int i = (int) (Math.random() * Fruits.values().length);
            return new Fruit(Fruits.values()[i]);
        } else
            return super.getContent();
    }
}
