package game.backend.level;

import game.backend.Figure;
import game.backend.GameState;
import game.backend.Grid;
import game.backend.cell.Cell;
import game.backend.cell.FruitGeneratorCell;
import game.backend.element.*;


public class Level3 extends Grid {
    private static int MAX_MOVES = 20;
    private static int REQUIRED_FRUITS = 5;

    private Cell wallCell, fruitGenCell;

    @Override
    protected GameState newState() {
        return new Level3State(MAX_MOVES, REQUIRED_FRUITS);
    }

    @Override
    protected void fillCells() {
        wallCell = new Cell(this);
        wallCell.setContent(new Wall());
        fruitGenCell = new FruitGeneratorCell(this); //genera frutas y caramelos random.

        //corners
        g()[0][0].setAround(fruitGenCell, g()[1][0], wallCell, g()[0][1]);
        g()[0][SIZE-1].setAround(fruitGenCell, g()[1][SIZE-1], g()[0][SIZE-2], wallCell);
        g()[SIZE-1][0].setAround(g()[SIZE-2][0], wallCell, wallCell, g()[SIZE-1][1]);
        g()[SIZE-1][SIZE-1].setAround(g()[SIZE-2][SIZE-1], wallCell, g()[SIZE-1][SIZE-2], wallCell);

        //upper line cells
        for (int j = 1; j < SIZE-1; j++) {
            g()[0][j].setAround(fruitGenCell,g()[1][j],g()[0][j-1],g()[0][j+1]);
        }
        //bottom line cells
        for (int j = 1; j < SIZE-1; j++) {
            g()[SIZE-1][j].setAround(g()[SIZE-2][j], wallCell, g()[SIZE-1][j-1],g()[SIZE-1][j+1]);
        }
        //left line cells
        for (int i = 1; i < SIZE-1; i++) {
            g()[i][0].setAround(g()[i-1][0],g()[i+1][0], wallCell ,g()[i][1]);
        }
        //right line cells
        for (int i = 1; i < SIZE-1; i++) {
            g()[i][SIZE-1].setAround(g()[i-1][SIZE-1],g()[i+1][SIZE-1], g()[i][SIZE-2], wallCell);
        }
        //central cells
        for (int i = 1; i < SIZE-1; i++) {
            for (int j = 1; j < SIZE-1; j++) {
                g()[i][j].setAround(g()[i-1][j],g()[i+1][j],g()[i][j-1],g()[i][j+1]);
            }
        }
    }

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2) {
        boolean ret;
        if (ret = super.tryMove(i1, j1, i2, j2)) { //si lo movio, agrega un movimiento.
            state().addMove();
            /*for(int i = SIZE-1  , j = SIZE-1; i >= 0; i--){
                if(get(i,j) instanceof Fruit ){
                    getCell(i,j).clearContent();
                    System.out.println(getCell(i,j));
                    getCell(i,j).fallUpperContent();
                }*/ //INTENTOS FALLIDOS
            }

        return ret;
    }

    @Override
    public void fallElements(){
        int i = SIZE - 1;
        while (i >= 0) {
            int j = 0;
            while (j < SIZE) {
                if (getCell(i,j).isEmpty()) {
                    if (getCell(i,j).fallUpperContent()) {
                        i = SIZE;
                        j = -1;
                        break;
                    }
                }else if(i == SIZE - 1 && get(i,j) instanceof Fruit){
                    System.out.println("ROMPIIIIIIIIIIII");
                    getCell(i,j).clearContent();
                    getCell(i,j).fallUpperContent();
                    state().addDestroyedFruit();
                    System.out.println(state().getDestroyedFruits());
                }
                j++;
            }
            i--;
        }

    }




    private class Level3State extends GameState{

        private int maxMoves;
        private int reqFruits;
        private int destroyedFruits = 0;

        Level3State(int maxMoves, int reqFruits){
            this.maxMoves = maxMoves;
            this.reqFruits = reqFruits;
        }



        @Override
        public boolean gameOver() {

            return playerWon() || getMoves() > maxMoves;
        }

        @Override
        public boolean playerWon() {
            System.out.println("ACAAAA");
            return destroyedFruits >= reqFruits;
        }
    }




}
