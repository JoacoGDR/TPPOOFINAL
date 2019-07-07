package game.backend.element;

public class TimeBomb extends Candy {
    private int counter;
    public TimeBomb(){
    }
    public TimeBomb(CandyColor color,int counter ) {
        super(color);
        this.counter = counter;
    }
    public int getCounter() {
        return counter;
    }

    public void decrementCounter(){
        counter--;
    }

}
