package game.backend.move;

import game.backend.Grid;
import game.backend.element.Nothing;

import static game.backend.Grid.SIZE;

public class FruitMove extends CandyMove{

    public FruitMove(Grid grid) {
        super(grid);
    }

    @Override
    public void removeElements() {
        System.out.println(i2);
        if(i2 == SIZE - 1){
            clearContent(i2,j2);
            setContent(i2,j2,new Nothing());

        }
            super.removeElements();
    }
}

