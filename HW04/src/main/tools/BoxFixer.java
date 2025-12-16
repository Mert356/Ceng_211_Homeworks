package src.main.tools;

import src.main.boxes.Box;
import src.main.boxes.FixedBox;
import src.main.core.BoxGrid;
import src.main.exceptions.BoxAlreadyFixedException;

public class BoxFixer extends SpecialTool{

    @Override
    public void useTool(BoxGrid grid, int row, int col,char targetLetter) throws BoxAlreadyFixedException {
        Box box = grid.getBox(row, col);
        if(box instanceof FixedBox){
            throw new BoxAlreadyFixedException();
        }

        FixedBox fBox = new FixedBox();
        fBox.setTopSide(box.getTopSide());
        grid.setBox(row, col, fBox);
    }
    
}
