package src.main.boxes;

import src.main.Direction;
import src.main.exceptions.UnmovableFixedBoxException;
import src.main.tools.SpecialTool;

public class FixedBox extends Box {

    public FixedBox() {
        super();
    }

    @Override
    public void roll(Direction dir) throws UnmovableFixedBoxException {
        throw new UnmovableFixedBoxException();
    }

    @Override
    public void setTopSide(char top) {
        // This type cannot change its topSide
    }

    @Override
    public void setTool(SpecialTool tool) {

        throw new IllegalStateException("FixedBox cannot contain a SpecialTool! Check your grid generation logic.");
    }

    @Override
    public String getTypeString() {
        return "X";
    }
}
