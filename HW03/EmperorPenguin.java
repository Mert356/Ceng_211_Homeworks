
public class EmperorPenguin extends Penguin {
    public EmperorPenguin(int id, int row, int col) { super(id, row, col); }
    @Override public String getTypeString() { return "Emperor Penguin"; }

    @Override
    public void performSpecialAction(IcyTerrain terrain, Direction dir) {
        // Emperor 3. karede durur
        this.setMaxSlideDistance(3);
    }
}