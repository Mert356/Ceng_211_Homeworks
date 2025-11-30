import java.util.*;

public abstract class Penguin extends GameObject {
    protected int id;
    protected List<Food> collectedFood;
    protected boolean isActive;
    protected boolean isStunned;
    protected int maxSlideDistance = 10; 

    public Penguin(int id, int row, int col) {
        super(row, col);
        this.id = id;
        this.collectedFood = new ArrayList<>();
        this.isActive = true;
        this.isStunned = false;
    }

    public void addFood(Food f) { collectedFood.add(f); }
    
    public int getTotalScore() {
        int sum = 0;
        for (Food f : collectedFood) sum += f.getWeight();
        return sum;
    }

    public void removeLightestFood() {
        if (collectedFood.isEmpty()) return;
        collectedFood.sort(Comparator.comparingInt(Food::getWeight));
        collectedFood.remove(0);
    }

    public boolean isAlive() { return isActive; }
    public void kill() { isActive = false; }
    public void stun() { isStunned = true; }
    public boolean isStunned() { return isStunned; }
    public void recover() { isStunned = false; }
    public void setMaxSlideDistance(int d) { this.maxSlideDistance = d; }
    public int getMaxSlideDistance() { return maxSlideDistance; }

    public abstract String getTypeString();
    // IcyTerrain referansı hareket mantığı için gereklidir
    public abstract void performSpecialAction(IcyTerrain terrain, Direction dir);

    @Override
    public String getSymbol() { return "P" + id; }
}