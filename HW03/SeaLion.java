
public class SeaLion extends Hazard {
    public SeaLion(int row, int col) { super(row, col); }
    @Override public String getSymbol() { return "SL"; }
    @Override public boolean canSlide() { return true; }
}