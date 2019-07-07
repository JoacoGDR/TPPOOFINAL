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

            int i = getRand(0, SIZE/2);
            int j = getRand(0, SIZE - 1);
            while((get(i,j) instanceof Fruit)) {
                i = getRand(0, SIZE/2);
                j = getRand(0, SIZE - 1);
            }
            setContent(i, j, f);
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

    //Chequeo si en la linea de abajo hay frutas
    //las Elimino y retorno true si elimine
    //false si no elimine nada

    private boolean clearedFruits() {
        int r, c;
        boolean ret = false;
        for (r = SIZE - 1, c = 0; c < SIZE; c++) {
            if (get(r, c) instanceof Fruit) {
                clearFruit(g()[r][c]);
                fallElements();
                gstate.addDestroyedFruit();
                System.out.println("Rompiste" + gstate.fruitsDestroyed + "frutas");
                System.out.println("Son: " + gstate.reqFruits);
                ret = true;
            }

        }
        return ret;
    }

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2) {
        boolean ret;
        int r, c;

        if (ret = super.tryMove(i1, j1, i2, j2)) {

            //cada vez que hago un movimiento, voy a chequear mi grid y ver si hay frutas abajo

            //salgo del while cuando clearedFruits() me da false, osea no se limpio ninguna fruta
            //osea no habia frutas por limpiar
            while(clearedFruits());

        }


        System.out.println("MOVIMIENTOS: " + gstate.getMoves());
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

        @Override
        public long getScore() {
            return fruitsDestroyed;
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

        @Override
        public String toString() {
            return String.format("Moves: %d/%d, Fruits: %d/%d",getMoves(),MAX_MOVES,reqFruits-fruitsDestroyed,reqFruits);
        }
    }

    //-----------------------------------------------------
}