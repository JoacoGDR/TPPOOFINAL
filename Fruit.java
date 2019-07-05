package game.backend.element;

import java.util.Objects;

public class Fruit extends Element {

    private Fruits fruitType;

    public Fruit(){
    }

    public Fruit(Fruits fruitType){
        this.fruitType = fruitType;
    }

    public void setFruitType(Fruits fruitType) {
        this.fruitType = fruitType;
    }


    public Fruits getFruitType(){
        return fruitType;
    }

    @Override
    public boolean isMovable() {
        return true;
    }

    @Override
    public String getKey() {
        return "FRUIT";
    }

    @Override
    public long getScore(){
        return 100;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFruitType());
    }
}
