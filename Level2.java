
package game.backend.level;
import game.backend.Figure;
import game.backend.GameState;
import game.backend.cell.Cell;
import game.backend.element.CandyColor;
import game.backend.element.TimeBomb;
import java.util.ArrayList;
import java.util.List;


public class Level2 extends Level1 {
    private List<TimeBomb> bombs = new ArrayList<>();
    private int MIN_COUNT = 5, MAX_COUNT = 10;
    private Level2State gstate;

    //Se inicializa de la misma forma que Level1 y luego se agregan las bombas
    @Override
    public void initialize() {
        super.initialize(); 
        int amount = getRand(1, 4);
        for (int j = 0; j <= amount; j++) {
            bombs.add(new TimeBomb(CandyColor.values()[getRand(0, CandyColor.values().length)], getRand(MIN_COUNT, MAX_COUNT)));
        }
        //Se distribuyen las bombas
        //De esta forma, 2 o mas bombas no pueden ser situadas en un mismo lugar
        for (TimeBomb b : bombs) {
            int i = getRand(0, SIZE - 1);
            int j = getRand(0, SIZE - 1);
            while((get(i,j) instanceof TimeBomb)) {
                i = getRand(0, SIZE - 1);
                j = getRand(0, SIZE - 1);
            }
            setContent(i, j, b);

        }
        //Luego de agregar las bombas, vemos si se formo alguna figura
        //para asi eliminarla y que el juego empiece correctamente
        for(int i = 0; i< SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tryRemove(g()[i][j]);
            }
        }
        fallElements();
        gstate.updateMinCounter();
    }


    public int bombsRemaining() {
        return bombs.size();
    }

    @Override
    protected GameState newState() {
        gstate = new Level2State(MAX_COUNT);
        return gstate;
    }

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2) {
        boolean ret;
        if (ret = super.tryMove(i1, j1, i2, j2)) {
            if (!bombs.isEmpty())
                gstate.decrementCounter();
        }
        return ret;
    }

    public List<TimeBomb> getBombs() {
        return bombs;
    }

    
    private class Level2State extends GameState {
        private int minCounter;

        Level2State(int minCounter) {

            this.minCounter = minCounter;
        }


        public void decrementCounter() {
            for (TimeBomb b : bombs) {
                b.decrementCounter();
            }
            updateMinCounter();
        }

        public void updateMinCounter() {
            for (TimeBomb b : bombs) {
                if (b.getCounter() < minCounter) {
                    minCounter = b.getCounter();
                }

            }
        }

        @Override
        public long getScore() {
            return minCounter;
        }

        @Override
        public boolean gameOver() {
            return playerWon() || minCounter == 0;
        }

        @Override
        public boolean playerWon() {
            return bombs.isEmpty();
        }
        
        
        @Override
        public String toString() {
            return String.format("Bombs remaining: %d, Counter: %d", bombsRemaining(), minCounter);
        }
    }
}
