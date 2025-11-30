public abstract class GameObject implements ITerrainObject {
    protected int row, col;

    public GameObject(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setPosition(int r, int c) {
        this.row = r;
        this.col = c;
    }
    
    public int getRow() { return row; }
    public int getCol() { return col; }
}