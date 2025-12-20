package src.main.boxes;

import src.main.Direction;
import src.main.exceptions.UnmovableFixedBoxException;
import src.main.tools.SpecialTool;

public class FixedBox extends Box {

    public FixedBox() {
        super();
        setOpen(true);
    }

    public FixedBox(Box original) {
        super(); // -->If a FixedBox instance is used as a
        // original variable this part creates random surfaces instead of empty ones
        // -->But we will use this constructor only inside of the BoxFixer class and if a box is already fixed 
        // this will throw an exception so actually we do not need it but just for safety we keep it.


        // This parts copies the surfaces from the original box
        super.setTop(original.getTopSide());
        super.setBottom(original.getBottomSide());
        super.setFront(original.getFrontSide());
        super.setBack(original.getBackSide());
        super.setLeft(original.getLeftSide());
        super.setRight(original.getRightSide());
        this.setOpen(true);
    }

    @Override
    public void roll(Direction dir) throws UnmovableFixedBoxException {
        throw new UnmovableFixedBoxException();
    }

    @Override
    public void flipVertically() throws UnmovableFixedBoxException {
        throw new UnmovableFixedBoxException();
    }

    @Override
    public void setTool(SpecialTool tool) {
        throw new IllegalStateException("FixedBox cannot contain a SpecialTool! Check your grid generation logic.");
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
        return "X";
    }
}
