package game.backend.level;
import game.backend.GameState;
import game.backend.element.CandyColor;
import game.backend.element.TimeBomb;
import java.util.ArrayList;
import java.util.List;



public class Level2 extends Level1{
    private List<TimeBomb> bombs = new ArrayList<>();

    @Override
    public void initialize() {
        super.initialize(); //Hace lo mismo que antes solo que ahora reemplaza algunos elemenos por bombas de Tiempo
        for (int j = 0; j <= getRand(1, 4); j++) {
            bombs.add(new TimeBomb(CandyColor.values()[getRand(0, CandyColor.values().length)], getRand(5, 10)));
            System.out.println(bombs.size());
        }
        for (TimeBomb b : bombs) {
            int i = getRand(0,SIZE-1);
            int j = getRand(0,SIZE-1);
            setContent(i, j,b);
            if(tryRemove(g()[i][j]) != null)
                bombs.remove(b);
        }
        fallElements();
        System.out.println(bombs.size());
    }
}


