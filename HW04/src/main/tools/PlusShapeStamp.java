package src.main.tools;

import src.main.core.BoxGrid;
import src.main.boxes.Box;

public class PlusShapeStamp extends SpecialTool {

    @Override
    public String useTool(BoxGrid grid, int row, int col, char targetLetter) {

        int[][] offsets = {
                { 0, 0 },
                { -1, 0 },
                { 1, 0 },
                { 0, -1 },
                { 0, 1 }
        };

        int numRows = grid.getRowCount();
        int numCols = grid.getColCount();

        for (int[] offset : offsets) {
            int targetRow = row + offset[0];
            int targetCol = col + offset[1];

            if (targetRow >= 0 && targetRow < numRows &&
                    targetCol >= 0 && targetCol < numCols) {

                Box box = grid.getBox(targetRow, targetCol);

                if (box != null) {
                    box.setTop(targetLetter);
                }
            }
        }

        return "Top sides of the chosen box (R" + (row + 1) + "-C" + (col + 1) + 
           ") and its surrounding boxes have been stamped to letter \"" + targetLetter + "\".";
    }
}
