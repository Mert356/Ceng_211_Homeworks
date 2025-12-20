package src.main.tools;

import src.main.core.BoxGrid;

public abstract class SpecialTool {
    public abstract String useTool(BoxGrid grid,int row,int col,char targetLetter) throws Exception;

    @Override
    public String toString(){
        return this.getClass().getSimpleName();
    }
}
