package src.main.tools;

import src.main.core.BoxGrid;

public class MassRowStamp extends SpecialTool {

    @Override
    public void useTool(BoxGrid grid, int row, int col, char targetLetter) throws Exception {

        for (int i = 0; i < grid.getColCount(); i++) {
            grid.getBox(row, i).setTopSide(targetLetter);
        }

    }

}
