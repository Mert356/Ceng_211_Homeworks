package src.main;

import src.main.core.BoxPuzzle;

public class BoxPuzzleApp {

    public static void main(String[] args) {        
        try {
            BoxPuzzle puzzle = new BoxPuzzle();
            puzzle.play();
            
        } catch (Exception e) {
            System.out.println("A critical error occurred causing the application to stop.");
            e.printStackTrace();
        }
    }
}
