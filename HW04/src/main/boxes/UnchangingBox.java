package src.main.boxes;

public class UnchangingBox extends Box {

    public UnchangingBox() {
        super();
    }

    @Override
    public void setTop(char top) { /* Blocked */ }

    @Override
    public void setBottom(char bottom) { /* Blocked */ }

    @Override
    public void setFront(char front) { /* Blocked */ }

    @Override
    public void setBack(char back) { /* Blocked */ }

    @Override
    public void setLeft(char left) { /* Blocked */ }

    @Override
    public void setRight(char right) { /* Blocked */ }

    // for getting help while creating the grid
    @Override
    public String getTypeString() {
        return "U";
    }
}