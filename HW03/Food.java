
public class Food extends GameObject {
    private String name;
    private int weight;

    public Food(int row, int col, String name, int weight) {
        super(row, col);
        this.name = name;
        this.weight = weight;
    }

    public int getWeight() { return weight; }
    public String getName() { return name; }
    
    @Override
    public String getSymbol() {
        return name.substring(0, 2); 
    }
}