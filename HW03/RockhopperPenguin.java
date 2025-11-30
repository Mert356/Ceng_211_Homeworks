
public class RockhopperPenguin extends Penguin {
    public RockhopperPenguin(int id, int row, int col) { super(id, row, col); }
    @Override public String getTypeString() { return "Rockhopper Penguin"; }

    @Override
    public void performSpecialAction(IcyTerrain terrain, Direction dir) {
        // Zıplama mantığı kayma döngüsü içinde (IcyTerrain) kontrol edilir.
        // Bu metod sadece polymorphic yapı için tutulur.
    }
}