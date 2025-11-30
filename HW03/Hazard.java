
public abstract class Hazard extends GameObject {
    public Hazard(int row, int col) { super(row, col); }
    public abstract boolean canSlide();
}