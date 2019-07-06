package game.backend.level;
import game.backend.GameState;
import game.backend.element.CandyColor;
import game.backend.element.TimeBomb;
import java.util.ArrayList;
import java.util.List;


public class Level2 extends Level1 {
    private List<TimeBomb> bombs = new ArrayList<>();
    private int MIN_COUNT = 5, MAX_COUNT = 10;
    private Level2State gstate;
    //private BombList bombs = new BombList();



    @Override
    public void initialize() {
        super.initialize(); //Hace lo mismo que antes solo que ahora reemplaza algunos elemenos por bombas de Tiempo
        int amount = getRand(1,4);
        for (int j = 0; j <= amount; j++) {
            bombs.add(new TimeBomb(CandyColor.values()[getRand(0, CandyColor.values().length)], getRand(MIN_COUNT, MAX_COUNT)));

        }

        for (TimeBomb b : bombs) {
            int i = getRand(0,SIZE-1);
            int j = getRand(0,SIZE-1);
            setContent(i, j,b);
            tryRemove(g()[i][j]);

        }
        fallElements();
        gstate.setBombs(bombs);
        gstate.updateMinCounter();
        System.out.println(gstate.bombsRemaining());
    }

    @Override
    protected GameState newState() {
        gstate = new Level2State(MAX_COUNT); //le mando el MAXCOUNT, porque despues lo voy a bajar. SI le mando el MINCOUNT, entonces puede que nunca lo baje y va a estar mal
        return gstate;
    }

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2) {
        boolean ret;
        if (ret = super.tryMove(i1, j1, i2, j2)) {
            if(!gstate.getBombs().isEmpty())
                gstate.decrementCounter();
        }
        return ret;
    }

    @Override
    public void clearContent(int i, int j) {
        if(get(i,j) instanceof TimeBomb) {
            TimeBomb bomb = (TimeBomb) get(i, j);
            gstate.removeBomb(bomb);
            bombs.remove(bomb);
        }
        g()[i][j].clearContent();
    }

    private class Level2State extends GameState {
        private List<TimeBomb> bombs;
        private int minCounter;

        Level2State(int minCounter) {

            this.minCounter = minCounter;
        }

        public void setBombs(List<TimeBomb> bombs) {
            this.bombs = bombs;
        }

        public List<TimeBomb> getBombs() {
            return bombs;
        }

        public void removeBomb(TimeBomb bomb){
            bombs.remove(bomb);
            if(!bombs.isEmpty()){
                updateMinCounter();
            }else
                minCounter = 0;
        }

        public void decrementCounter(){
            for(TimeBomb b : bombs){
                b.decrementCounter();
            }
            minCounter--;
        }

        public void updateMinCounter(){
            for(TimeBomb b : bombs){
                if(b.getCounter() < minCounter){
                    minCounter = b.getCounter();
                }

            }
        }

        public int bombsRemaining(){
            return bombs.size();
        }

        @Override
        public boolean gameOver() {
            return playerWon() || minCounter == 0;
        }

        @Override
        public boolean playerWon() {
            return bombs.isEmpty();
        }
    }

    /*private class BombList{
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
    }*/


}
