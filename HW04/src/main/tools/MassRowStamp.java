package src.main.tools;

import src.main.core.BoxGrid;

public class MassRowStamp extends SpecialTool {

    @Override
    public String useTool(BoxGrid grid, int row, int col, char targetLetter) throws Exception {

        for (int i = 0; i < grid.getColCount(); i++) {
            grid.getBox(row, i).setTop(targetLetter);
        }

        return "All boxes in row R" + (row + 1) + " have been stamped to letter \"" + targetLetter + "\".";

    }

}
