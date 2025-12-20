package src.main.core;

import java.util.Random;
import java.util.Scanner;

import src.main.Direction;
import src.main.boxes.Box;
import src.main.exceptions.BoxAlreadyFixedException;
import src.main.exceptions.EmptyBoxException;
import src.main.exceptions.UnmovableFixedBoxException;
import src.main.tools.SpecialTool;

public class BoxPuzzle {

    private BoxGrid boxGrid;
    private Scanner scanner;
    private char targetLetter;
    private final int MAX_TURNS = 5;
    private PuzzleMenu menu; // Inner Class Instance

    public BoxPuzzle() {
        this.scanner = new Scanner(System.in);
        this.boxGrid = new BoxGrid();
        this.menu = new PuzzleMenu();

        this.targetLetter = (char) ('A' + new Random().nextInt(8));
    }

    public void play() {
        System.out.println("Welcome to Box Top Side Matching Puzzle App. An 8x8 box grid is being generated.");
        System.out.println("Your goal is to maximize the letter \"" + targetLetter + "\" on the top sides of the boxes.");
        System.out.println("The initial state of the box grid:\n");

        for (int turn = 1; turn <= MAX_TURNS; turn++) {
            System.out.println("\n" + boxGrid.toString());
            System.out.println("=====> TURN " + turn + ":");

            menu.handleViewBoxSurfaces();

            System.out.println("---> TURN " + turn + " - FIRST STAGE:");
            boolean moveSuccessful = handleRollingStage();

            if (!moveSuccessful) {
                continue;
            }

            System.out.println("The new state of the box grid:\n" + boxGrid.toString());

            System.out.println("---> TURN " + turn + " - SECOND STAGE:");
            handleOpeningStage();
        }

        endGame();
    }

    private boolean handleRollingStage() {
        String currentMessage = "Please enter the location of the edge box you want to roll:";

        while (true) {
            int[] pos = menu.getCoordinateInput(currentMessage);
            int row = pos[0];
            int col = pos[1];

            if (!isEdgeBox(row, col)) {
                currentMessage = "INCORRECT INPUT: The chosen box is not on any of the edges. Please reenter the location:";
                continue;
            }

            Direction dir;
            String directionText = "";

            if (isCorner(row, col)) {
                dir = menu.askDirectionForCorner(row, col);

                switch (dir) {
                    case UP:    directionText = "upwards"; break;
                    case DOWN:  directionText = "downwards"; break;
                    case LEFT:  directionText = "to left"; break;
                    case RIGHT: directionText = "to the right"; break;
                }

                System.out.println("The chosen box and any box on its path have been rolled " + directionText + ".");
                
            } else {
                dir = determineRollDirection(row, col);

                switch (dir) {
                    case UP:    directionText = "upwards"; break;
                    case DOWN:  directionText = "downwards"; break;
                    case LEFT:  directionText = "to left"; break;
                    case RIGHT: directionText = "to the right"; break;
                }

                System.out.println("The chosen box is automatically rolled " + directionText + ".");
            }

            try {
                boxGrid.rollGrid(row, col, dir);
                return true;
            } catch (UnmovableFixedBoxException e) {
                System.out.println("HOWEVER, IT IS FIXED BOX AND CANNOT BE MOVED. Continuing to the next turn... [cite: 53]");
                return false;
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                return false;
            }
        }
    }

    // Check if the box is at a corner
    private boolean isCorner(int r, int c) {
        return (r == 0 || r == 7) && (c == 0 || c == 7);
    }

    private void handleOpeningStage() {
        String currentMessage = "Please enter the location of the box you want to open:";

        while (true) {
            int[] pos = menu.getCoordinateInput(currentMessage);
            int row = pos[0];
            int col = pos[1];

            // Check if the box was moved in the rolling stage
            if (!boxGrid.hasBoxMoved(row, col)) {
                currentMessage = "INCORRECT INPUT: The chosen box was not rolled during the first stage. Please reenter the location:";
                continue;
            }

            Box selectedBox = boxGrid.getBox(row, col);
            try {
                SpecialTool tool = selectedBox.open();
                useAcquiredTool(tool);
                break; 
            } catch (EmptyBoxException e) {
                System.out.println("BOX IS EMPTY! Continuing to the next turn...");
                break; 
            }
        }
    }

    /**
     * The acquired tool is processed here regardless of its type.
     * Using generics to handle different SpecialTool types.
     */

    private <T extends SpecialTool> void useAcquiredTool(T tool) {
        System.out.println("It contains a SpecialTool --> " + tool.toString());

        while (true) {
            int[] targetPos = menu.getCoordinateInput("Please enter the location of the box to use this SpecialTool:");
            try {
                String successMessage = tool.useTool(boxGrid, targetPos[0], targetPos[1], targetLetter);
                
                System.out.println(successMessage);
                
                break;
            } catch (BoxAlreadyFixedException | UnmovableFixedBoxException e) {
                System.out.println("This move is invalid: " + e.getMessage());
                System.out.println("Turn wasted!");
                break;
            } catch (Exception e) {
                System.out.println("Error using tool: " + e.getMessage());
                break;
            }
        }
    }

    private boolean isEdgeBox(int r, int c) {
        return r == 0 || r == 7 || c == 0 || c == 7;
    }

    private Direction determineRollDirection(int r, int c) {
        boolean isCorner = (r == 0 || r == 7) && (c == 0 || c == 7);

        if (isCorner) {
            
            return menu.askDirectionForCorner(r, c);
        }

        // apply direction based on edge
        if (r == 0)
            return Direction.DOWN;
        if (r == 7)
            return Direction.UP;
        if (c == 0)
            return Direction.RIGHT;
        if (c == 7)
            return Direction.LEFT;

        return Direction.RIGHT; // Default, should not reach here
    }

    private void endGame() {
        System.out.println("\nGAME OVER");
        System.out.println(boxGrid.toString());
        int score = boxGrid.calculateScore(targetLetter);
        System.out.println("THE TOTAL NUMBER OF TARGET LETTER \"" + targetLetter + "\" --> " + score);
        System.out.println("The game has been SUCCESSFULLY completed!");
    }

    private class PuzzleMenu {

        public int[] getCoordinateInput(String message) {

            while (true) {
                System.out.print(message + " ");
                String input = scanner.next();

                try {
                    input = input.toUpperCase().replace("R", "").replace("C", "");
                    String[] parts = input.split("-");

                    if (parts.length != 2)
                        throw new Exception();

                    int row = Integer.parseInt(parts[0]) - 1;
                    int col = Integer.parseInt(parts[1]) - 1;

                    if (row >= 0 && row < 8 && col >= 0 && col < 8) {
                        return new int[] { row, col };
                    } else {
                        System.out.println("Invalid range! Please enter between 1-8.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid format! Use format like R1-C1 or 1-1.");
                }
            }
        }

        public void handleViewBoxSurfaces() {
            System.out.print("---> Do you want to view all surfaces of a box? [1] Yes or [2] No? ");
            String choice = scanner.next();

            if (choice.equals("1")) {
                int[] pos = getCoordinateInput("Please enter the location of the box you want to view:");
                Box box = boxGrid.getBox(pos[0], pos[1]);
                printBoxSurfaces(box);
            }
        }

        public Direction askDirectionForCorner(int r, int c) {
            Direction dir1 = null;
            Direction dir2 = null;
            String name1 = "";
            String name2 = "";

            // Set options according to corner position
            if (r == 0 && c == 0) {
                dir1 = Direction.RIGHT;
                name1 = "right";
                dir2 = Direction.DOWN;
                name2 = "downwards";
            }
            else if (r == 0 && c == 7) {
                dir1 = Direction.LEFT;
                name1 = "left";
                dir2 = Direction.DOWN;
                name2 = "downwards";
            }
            else if (r == 7 && c == 0) {
                dir1 = Direction.RIGHT;
                name1 = "right";
                dir2 = Direction.UP;
                name2 = "upwards";
            }
            else if (r == 7 && c == 7) {
                dir1 = Direction.LEFT;
                name1 = "left";
                dir2 = Direction.UP;
                name2 = "upwards";
            }

            System.out.print("The chosen box can be rolled to either [1] " + name1 + " or [2] " + name2 + ": ");

            String choice = scanner.next();
            while (!choice.equals("1") && !choice.equals("2")) {
                System.out.print("Invalid input! Please enter 1 or 2: ");
                choice = scanner.next();
            }
            return choice.equals("1") ? dir1 : dir2;
        }

        private void printBoxSurfaces(Box box) {
            if (box == null)
                System.out.println("Box is null!");

            String indent = "      ";
            String singleLine = "-------";
            String tripleLine = "-------------------";

            // back
            System.out.println(indent + singleLine);
            System.out.println(indent + "|  " + box.getBackSide() + "  |");

            // Left, Top, Right - Middle part
            System.out.println(tripleLine);
            System.out.println(
                    "|  " + box.getLeftSide() + "  |  " + box.getTopSide() + "  |  " + box.getRightSide() + "  |");
            System.out.println(tripleLine);

            System.out.println(indent + "|  " + box.getFrontSide() + "  |");

            System.out.println(indent + singleLine);
            System.out.println(indent + "|  " + box.getBottomSide() + "  |");

            System.out.println(indent + singleLine);
        }

    }
}