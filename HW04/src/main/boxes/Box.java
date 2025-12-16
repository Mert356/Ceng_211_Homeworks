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

    public Box() {
        isOpen = false;
        generateRandomSurfaces();
    }

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

    public char getTopSide() {
        return top;
    }

    public void setTopSide(char top) {
        this.top = top;
    }

    public char getBottomSide() {
        return this.bottom;
    }

    public void setBottomSide(char bottom) {
        this.bottom = bottom;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public void setTool(SpecialTool tool) {
        this.tool = tool;
    }

    public boolean hasTool() {
        return this.tool != null;
    }

    public abstract String getTypeString();

    public String getStatusString() {
        return isOpen() ? "O" : "M";
    }

    @Override
    public String toString() {

        return " " + getTypeString() + "-" + getTopSide() + "-" + getStatusString() + " ";
    }
}
