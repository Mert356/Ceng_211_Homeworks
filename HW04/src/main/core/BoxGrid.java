package src.main.core;

import java.util.ArrayList;
import java.util.Random;

import src.main.Direction;
import src.main.boxes.Box;
import src.main.boxes.FixedBox;
import src.main.boxes.RegularBox;
import src.main.boxes.UnchangingBox;
import src.main.exceptions.UnmovableFixedBoxException;
import src.main.tools.BoxFixer;
import src.main.tools.BoxFlipper;
import src.main.tools.MassColumnStamp;
import src.main.tools.MassRowStamp;
import src.main.tools.PlusShapeStamp;
import src.main.tools.SpecialTool;

/*
    ----------- ANSWER TO COLLECTIONS QUESTION -----------
    I chose to use a nested ArrayList structure (ArrayList<ArrayList<Box>>) to represent the 8x8 grid.
    This allows for dynamic sizing (although documentation says that fixed sized 8x8 grid) and easy access to rows and columns. Each inner ArrayList represents a row of boxes,
    and the outer ArrayList contains all the rows
*/

public class BoxGrid {
    private ArrayList<ArrayList<Box>> grid;
    private ArrayList<Box> movedBoxesThisTurn; // For tracking moved boxes in the current turn
    private Random random;
    private final int ROW_COUNT = 8;
    private final int COL_COUNT = 8;

    // GETTERS

    public ArrayList<Box> getMovedBoxesThisTurn() {
        return movedBoxesThisTurn;
    }

    public ArrayList<ArrayList<Box>> getGrid() {
        return grid;
    }

    public int getRowCount() {
        return ROW_COUNT;
    }

    public int getColCount() {
        return COL_COUNT;
    }

    public BoxGrid() {
        this.grid = new ArrayList<>();
        this.random = new Random();
        this.movedBoxesThisTurn  = new ArrayList<>();
        initializeGrid();
    }


    // Helper Methods
    public Box getBox(int row, int col) {
        if (row < 0 ||row > ROW_COUNT - 1 || col > COL_COUNT - 1 || col < 0) {
            throw new IllegalArgumentException("Invalid row or column index.");
        } else {
            return grid.get(row).get(col);
        }
    }

    public void setBox(int row, int col, Box newBox) {
        if (row < 0 || row > ROW_COUNT - 1 || col > COL_COUNT - 1 || col < 0) {
            throw new IllegalArgumentException("Invalid row or column index.");
        } else {
            grid.get(row).set(col, newBox);
        }
    }

    // Check if a box has moved in the current turn
    public boolean hasBoxMoved(int row, int col) {
        Box box = getBox(row, col);
        return movedBoxesThisTurn.contains(box);
    }


    // Initialize the grid with random boxes and tools
    public void initializeGrid() {
        for (int i = 0; i < ROW_COUNT; i++) {

            // Create a new ArrayList for a row
            ArrayList<Box> rowList = new ArrayList<>();

            for (int j = 0; j < COL_COUNT; j++) {

                // Generate a random box and assign a tool based on box type
                Box box = generateRandomBox();
                assignRandomTool(box);
                rowList.add(j, box);
            }
            grid.add(rowList);
        }
    }


    // Generate a random box based on defined probabilities
    private Box generateRandomBox() {
        int chance = random.nextInt(100);
        if (chance < 85) {
            return new RegularBox();
        } else if (chance < 95) {
            return new UnchangingBox();
        } else {
            return new FixedBox();
        }
    }

    // Assign tools based on box type and probabilities
    private void assignRandomTool(Box box) {
        if (box instanceof RegularBox) {
            int chance = random.nextInt(100);
            if (chance < 75) {
                box.setTool(createRandomTool());
            }
        } else if (box instanceof UnchangingBox) {
            box.setTool(createRandomTool());
        }
    }

    // Create a random special tool
    // The probabilities are same for all tools
    private SpecialTool createRandomTool() {
        int chance = random.nextInt(5);
        switch (chance) {
            case 0:
                return new BoxFixer();
            case 1:
                return new BoxFlipper();
            case 2:
                return new MassColumnStamp();
            case 3:
                return new MassRowStamp();
            case 4:
                return new PlusShapeStamp();
            default:
                throw new IllegalStateException("Unexpected value: " + chance); 
                // This should never happen but added for safety
        }

    }


    // Roll the grid starting from (startRow, startCol) in the specified direction

    public void rollGrid(int startRow, int startCol, Direction dir) throws UnmovableFixedBoxException {
        movedBoxesThisTurn.clear();
        // 1. Step: Check if the starting box is FixedBox
        Box startBox = grid.get(startRow).get(startCol);
        if (startBox instanceof FixedBox) {
            throw new UnmovableFixedBoxException();
        }

        // 2. Step: Apply rolling in the specified direction with domino effect
        switch (dir) {
            case RIGHT:
                for (int j = startCol; j < COL_COUNT; j++) {
                    if (!applyRollOrStop(startRow, j, dir)) break; 
                }
                break;

            case LEFT:
                for (int j = startCol; j >= 0; j--) {
                    if (!applyRollOrStop(startRow, j, dir)) break;
                }
                break;

            case DOWN:
                for (int i = startRow; i < ROW_COUNT; i++) {
                    if (!applyRollOrStop(i, startCol, dir)) break;
                }
                break;

            case UP:
                for (int i = startRow; i >= 0; i--) {
                    if (!applyRollOrStop(i, startCol, dir)) break;
                }
                break;
        }
    }

    // Helper method to apply roll or stop the domino effect
    // Returns true if rolling was applied, false if stopped by FixedBox
    private boolean applyRollOrStop(int r, int c, Direction dir) {
        Box currentBox = grid.get(r).get(c);

        if (currentBox instanceof FixedBox) {
            return false;
        }

        try {
            currentBox.roll(dir);
            movedBoxesThisTurn.add(currentBox); // Track moved boxes
        } catch (UnmovableFixedBoxException e) {
            return false; // For safety, though this should not occur due to prior checks
        }
        return true;
    }

    public int calculateScore(char targetLetter) {
        int score = 0;

        // Look through all boxes in the grid
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COL_COUNT; j++) {
                Box box = grid.get(i).get(j);
                
                if (box.getTopSide() == targetLetter) {
                    score++;
                }
            }
        }
        
        return score;
    }


    // Format the grid as a string for display
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("      ");
        for (int i = 1; i <= COL_COUNT; i++) {
            sb.append(String.format("   C%d   ", i));
        }
        sb.append("\n");

        sb.append("      ");
        for (int i = 0; i < COL_COUNT; i++) {
            sb.append("--------");
        }
        sb.append("\n");

        for (int i = 0; i < ROW_COUNT; i++) {
            sb.append(String.format(" R%d   |", (i + 1)));

            for (int j = 0; j < COL_COUNT; j++) {
                Box box = grid.get(i).get(j);
                sb.append(box.toString());
                sb.append("|");
            }
            sb.append("\n");

            sb.append("      ");
            for (int k = 0; k < COL_COUNT; k++) {
                sb.append("--------");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
