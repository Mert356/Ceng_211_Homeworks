package src.main.tools;

import src.main.boxes.Box;
import src.main.boxes.FixedBox;
import src.main.core.BoxGrid;
import src.main.exceptions.UnmovableFixedBoxException;

public class BoxFlipper extends SpecialTool {

    @Override
    public String useTool(BoxGrid grid, int row, int col, char targetLetter) throws UnmovableFixedBoxException {

        Box box = grid.getBox(row, col);
        if (box instanceof FixedBox) {
            throw new UnmovableFixedBoxException();
        }
        box.flipVertically();

        return "The chosen box on location R" + (row + 1) + "-C" + (col + 1) + 
           " has been flipped upside down.";
    }

}
