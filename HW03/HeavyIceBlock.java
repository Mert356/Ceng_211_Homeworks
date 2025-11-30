
public class HeavyIceBlock extends Hazard {
    public HeavyIceBlock(int row, int col) { super(row, col); }
    @Override public String getSymbol() { return "HB"; }
    @Override public boolean canSlide() { return false; }
}