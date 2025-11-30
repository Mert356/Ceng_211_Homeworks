
public class RoyalPenguin extends Penguin {
    public RoyalPenguin(int id, int row, int col) { super(id, row, col); }
    @Override public String getTypeString() { return "Royal Penguin"; }

    @Override
    public void performSpecialAction(IcyTerrain terrain, Direction dir) {
        // Kaymadan önce 1 kare güvenli hareket
        int dr = 0, dc = 0;
        if(dir == Direction.UP) dr = -1;
        else if(dir == Direction.DOWN) dr = 1;
        else if(dir == Direction.LEFT) dc = -1;
        else if(dir == Direction.RIGHT) dc = 1;

        int newR = this.row + dr;
        int newC = this.col + dc;

        if (terrain.isValidMove(newR, newC)) {
            terrain.moveObjectDirectly(this, newR, newC);
            System.out.println("Royal Penguin moved 1 step safely.");
        } else if (!terrain.isInBounds(newR, newC)) {
             this.kill();
             System.out.println("Royal Penguin fell into water while stepping!");
        }
    }
}