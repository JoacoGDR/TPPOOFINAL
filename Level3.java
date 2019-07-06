package game.backend.level;

import game.backend.GameState;
import game.backend.cell.Cell;
import game.backend.element.Fruit;
import game.backend.element.FruitType;
import game.backend.element.Nothing;

import java.util.ArrayList;
import java.util.List;



public class Level3 extends Level1{

    private List<Fruit> fruits = new ArrayList<>();
    private Level3State gstate;
    private int MAX_MOVES = 20;


    @Override
    public void initialize(){ //inicia el grid con las frutas.
        super.initialize();
        int fruitQty = getRand(1,4);
        for (int j = 0; j <= fruitQty; j++) {
            fruits.add(new Fruit(FruitType.values()[getRand(0, FruitType.values().length) ]));
        }
        for (Fruit f : fruits) {
            setContent(getRand(0, SIZE/2), getRand(0, SIZE-1), f);
        }
        gstate.setReqFruits(fruits.size());
    }

    @Override
    protected GameState newState() {
        gstate = new Level3State(MAX_MOVES);
        return gstate;
    }

    public void clearFruit(Cell cell){
        cell.setContent(new Nothing());
    }

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2) {
        boolean ret;
        int r, c;
        if (ret = super.tryMove(i1, j1, i2, j2)) {
            gstate.addMove();
            //cada vez que hago un movimiento, voy a chequear mi grid y ver si hay frutas abajo

            for (r = SIZE - 1, c = 0; c < SIZE; c++) {
                if (get(r, c) instanceof Fruit) {
                    clearFruit(g()[r][c]);
                    fallElements();
                    gstate.addDestroyedFruit();
                    System.out.println("Rompiste" + gstate.fruitsDestroyed + "frutas");
                    System.out.println("Son: " + gstate.reqFruits);
                }

            }

        }
        return ret;
    }


    //---------------------------------------------------
    private class Level3State extends  GameState{

        private int reqFruits;
        private int fruitsDestroyed = 0;
        private int maxMoves;

        Level3State(int maxMoves){
            this.maxMoves = maxMoves;
        }

        public void addDestroyedFruit(){
            fruitsDestroyed++;

        }

        public void setReqFruits(int reqFruits) {
            this.reqFruits = reqFruits;
        }

        @Override
        public boolean gameOver() {
            return playerWon() || getMoves() == maxMoves;
        }

        @Override
        public boolean playerWon() {//se llama siempre
            return fruitsDestroyed == reqFruits;
        }
    }

    //-----------------------------------------------------
}
