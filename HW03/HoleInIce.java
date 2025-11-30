
public class HoleInIce extends Hazard {
    private boolean plugged = false;
    public HoleInIce(int row, int col) { super(row, col); }
    
    public void plug() { plugged = true; }
    public boolean isPlugged() { return plugged; }

    @Override public String getSymbol() { return plugged ? "PH" : "HI"; }
    @Override public boolean canSlide() { return false; }
}