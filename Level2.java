package game.backend.level;
import game.backend.GameState;
import game.backend.element.CandyColor;
import game.backend.element.TimeBomb;
import java.util.ArrayList;
import java.util.List;


public class Level2 extends Level1 {
    private BombList bombs = new BombList();
    private int bombsRemaining(){
        return bombs.size();
    }
    @Override
    public void initialize() {
        super.initialize(); //Hace lo mismo que antes solo que ahora reemplaza algunos elemenos por bombas de Tiempo
        for (int j = 0; j <= getRand(1, 4); j++) {
            bombs.add(new TimeBomb(CandyColor.values()[getRand(0, CandyColor.values().length)], getRand(5, 10)));
            System.out.println(bombsRemaining());
        }
        for (TimeBomb b : bombs.list) {
            int i = getRand(0,SIZE-1);
            int j = getRand(0,SIZE-1);
            setContent(i, j,b);
            tryRemove(g()[i][j]);

        }
        fallElements();
    }

    @Override
    protected GameState newState() {
        return new Level2State(bombs);
    }

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2) {
        boolean ret;
        if (ret = super.tryMove(i1, j1, i2, j2)) {
            if(!bombs.isEmpty())
                bombs.decrementCounter();
        }
        return ret;
    }

    @Override
    public void clearContent(int i, int j) {
       if(get(i,j) instanceof TimeBomb) {
           TimeBomb bomb = (TimeBomb) get(i, j);
           bombs.remove(bomb);
       }
       g()[i][j].clearContent();
    }

    private class Level2State extends GameState {
        private BombList bombs;

        Level2State(BombList bombs) {
            this.bombs = bombs;
        }

        @Override
        public boolean gameOver() {
            return playerWon() || bombs.minCounter <= 0;
        }

        @Override
        public boolean playerWon() {
            return bombs.isEmpty();
        }
    }

    private class BombList{
        private List<TimeBomb> list = new ArrayList<>();
        private int minCounter;
        public void add(TimeBomb bomb){
            if(list.isEmpty()) {
                minCounter = bomb.getCounter();
            }
            list.add(bomb);
            if(minCounter >= bomb.getCounter())
                minCounter = bomb.getCounter();
        }
        public void remove(TimeBomb bomb){
            list.remove(bomb);
            updateMinCounter();
            System.out.println("Removed" + " there are " + size() +" bombs left");
            System.out.println("The minCounter is " + minCounter);
        }
        public void updateMinCounter(){
            if(bombs.isEmpty())
                minCounter = 0;
            else {
                minCounter = list.stream()
                        .map(TimeBomb::getCounter)
                        .min(Integer::compare).get();
            }
        }
        public void decrementCounter(){
            for(TimeBomb bomb : list) {
                bomb.decrementCounter();
            }
            minCounter--;
        }
        public boolean isEmpty(){
            return list.size() == 0;
        }
        public int size(){return list.size();}
    }


}



