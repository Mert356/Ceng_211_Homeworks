package src.main.boxes;

public class UnchangingBox extends Box {

    public UnchangingBox() {
        super();
    }

    @Override
    public void setTopSide(char top) {
        // "Cannot be re-stamped" kuralı gereği işlem yapmıyoruz.
        // Gelen 'top' değeri yoksayılır.
    }

    @Override
    public String getTypeString() {
        return "U";
    }
}