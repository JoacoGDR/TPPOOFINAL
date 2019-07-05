package game.backend.level;

import game.backend.GameState;
import game.backend.element.Fruit;
import game.backend.element.Fruits;
import game.backend.element.Nothing;

import java.util.ArrayList;
import java.util.List;



public class Level3 extends Level1{

    private List<Fruit> fruits = new ArrayList<>();
    private Level3State gstate;
    private int MAX_MOVES = 20;

    private static int getRand(int min, int max) {
        return (int) (min + (Math.random() * (max - min)));
    }

    @Override
    public void initialize(){ //inicia el grid con las frutas.
        super.initialize();
        int fruitQty = getRand(1,15);
        for (int j = 0; j <= fruitQty; j++) {
            fruits.add(new Fruit(Fruits.values()[getRand(0, Fruits.values().length) ]));
        }
        for (Fruit f : fruits) {
            setContent(getRand(0, SIZE - 1), getRand(0, SIZE/2), f);
        }
    }

    @Override
    protected GameState newState() {
        gstate = new Level3State(fruits, MAX_MOVES);
        return gstate;
    }

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2) {
        boolean ret;
        int r,c;
        if (ret = super.tryMove(i1, j1, i2, j2)) {
            gstate.addMove();
            //cada vez que hago un movimiento, voy a chequear mi grid y ver si hay frutas abajo

            for(r = SIZE-1, c = 0; c < SIZE; c++){
                /*
                System.out.println(r);
                System.out.println(c);
                */
                if(get(r,c) instanceof Fruit){
                   clearContent(r,c);
                   getCell(r,c).fallUpperContent();
                   gstate.addDestroyedFruit();

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

        Level3State(List<Fruit> fruits, int maxMoves){
            reqFruits = fruits.size();
            this.maxMoves = maxMoves;
        }

        public void addDestroyedFruit(){
            fruitsDestroyed++;

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
