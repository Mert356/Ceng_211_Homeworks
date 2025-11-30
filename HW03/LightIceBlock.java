
public class LightIceBlock extends Hazard {
    public LightIceBlock(int row, int col) { super(row, col); }
    @Override public String getSymbol() { return "LB"; }
    @Override public boolean canSlide() { return true; }
}