
public class KingPenguin extends Penguin {
    public KingPenguin(int id, int row, int col) { super(id, row, col); }
    @Override public String getTypeString() { return "King Penguin"; }
    
    @Override
    public void performSpecialAction(IcyTerrain terrain, Direction dir) {
        // King 5. karede durur
        this.setMaxSlideDistance(5);
    }
}