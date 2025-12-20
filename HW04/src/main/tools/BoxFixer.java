package src.main.tools;

import src.main.boxes.Box;
import src.main.boxes.FixedBox;
import src.main.core.BoxGrid;
import src.main.exceptions.BoxAlreadyFixedException;

public class BoxFixer extends SpecialTool {

    @Override
    public String useTool(BoxGrid grid, int row, int col, char targetLetter) throws BoxAlreadyFixedException {
        Box box = grid.getBox(row, col);
        if (box instanceof FixedBox) {
            throw new BoxAlreadyFixedException();
        }

        FixedBox fBox = new FixedBox(box);
        grid.setBox(row, col, fBox);

        return "The box on location R" + (row + 1) + "-C" + (col + 1) + " has been replaced with a FixedBox.";
    }
}
