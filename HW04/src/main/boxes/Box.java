package src.main.boxes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import src.main.Direction;
import src.main.exceptions.EmptyBoxException;
import src.main.exceptions.UnmovableFixedBoxException;
import src.main.tools.SpecialTool;

public abstract class Box {

    private char top, bottom, front, back, left, right;
    private SpecialTool tool;
    private boolean isOpen;

    public Box() {
        isOpen = false;
        generateRandomSurfaces();
    }

    // Rolling the box in the given direction
    public void roll(Direction dir) throws UnmovableFixedBoxException {
        char temptop = top;
        switch (dir) {
            case UP:
                top = front;
                front = bottom;
                bottom = back;
                back = temptop;
                break;
            case DOWN:
                top = back;
                back = bottom;
                bottom = front;
                front = temptop;
                break;
            case LEFT:
                top = right;
                right = bottom;
                bottom = left;
                left = temptop;
                break;
            case RIGHT:

                top = left;
                left = bottom;
                bottom = right;
                right = temptop;
                break;
            default:
                break;
        }
    }

    // Helper for BoxFlipper tool
    public void flipVertically() throws UnmovableFixedBoxException {
        // Change bottom and top
        char tempTop = top;
        top = bottom;
        bottom = tempTop;

        // Change front and back
        char tempFront = front;
        front = back;
        back = tempFront;
    }

    public SpecialTool open() throws EmptyBoxException {
        if (this.tool == null) {
            this.isOpen = true;
            throw new EmptyBoxException();
        }

        SpecialTool foundTool = this.tool;
        this.tool = null;
        this.isOpen = true;

        return foundTool;
    }

    private void generateRandomSurfaces() {
        ArrayList<Character> surfaces = new ArrayList<>();
        Random rand = new Random();

        while (surfaces.size() < 6) {

            char letter = (char) ('A' + rand.nextInt(8));

            int count = Collections.frequency(surfaces, letter);

            if (count < 2) {
                surfaces.add(letter);
            }
        }

        this.top = surfaces.get(0);
        this.back = surfaces.get(1);
        this.right = surfaces.get(2);
        this.bottom = surfaces.get(3);
        this.front = surfaces.get(4);
        this.left = surfaces.get(5);
    }

    // GETTERS
    public char getFrontSide() {
        return front;
    }

    public char getBackSide() {
        return back;
    }

    public char getLeftSide() {
        return left;
    }

    public char getRightSide() {
        return right;
    }

    public char getTopSide() {
        return top;
    }

    public char getBottomSide() {
        return this.bottom;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public boolean hasTool() {
        return this.tool != null;
    }

    // SETTERS

    public void setTop(char top) {
        this.top = top;
    }

    public void setBottom(char bottom) {
        this.bottom = bottom;
    }

    public void setFront(char front) {
        this.front = front;
    }

    public void setBack(char back) {
        this.back = back;
    }

    public void setLeft(char left) {
        this.left = left;
    }

    public void setRight(char right) {
        this.right = right;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void setTool(SpecialTool tool) {
        this.tool = tool;
    }

    // for getting help while creating the grid

    public abstract String getTypeString();

    public String getStatusString() {
        return isOpen() ? "O" : "M";
    }

    @Override
    public String toString() {
        return " " + getTypeString() + "-" + getTopSide() + "-" + getStatusString() + " ";
    }
}
