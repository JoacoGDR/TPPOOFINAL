package game.backend.element;

public class Fruit extends Element {
    FruitType type;
    public Fruit(FruitType type) {
        this.type = type;
    }
    public Fruit(){
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
    public String getFullKey() {
        return getKey() + type;
    }
}
