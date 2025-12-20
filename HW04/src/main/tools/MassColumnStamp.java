package src.main.tools;

import src.main.core.BoxGrid;

public class MassColumnStamp extends SpecialTool {

    @Override
    public String useTool(BoxGrid grid, int row, int col, char targetLetter) throws Exception {

        for (int i = 0; i < grid.getRowCount(); i++) {
            grid.getBox(i, col).setTop(targetLetter);
        }

        return "All boxes in column C" + (col + 1) + " have been stamped to letter \"" + targetLetter + "\".";

    }

}