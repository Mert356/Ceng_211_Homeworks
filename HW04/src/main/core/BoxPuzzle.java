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
        System.out.println("Welcome to Box Top Side Matching Puzzle App.");
        System.out.println("An 8x8 box grid is being generated.");
        System.out.println("Your goal is to maximize the letter \"" + targetLetter + "\" on the top sides.");

        for (int turn = 1; turn <= MAX_TURNS; turn++) {
            System.out.println("\n" + boxGrid.toString()); // Gridi yazdır
            System.out.println("=====> TURN " + turn + ":");

            menu.handleViewBoxSurfaces();

            System.out.println("---> TURN " + turn + " FIRST STAGE:");
            boolean moveSuccessful = handleRollingStage();

            if (!moveSuccessful) {
                System.out.println("Turn wasted due to fixed box blocking or error!");
                continue; // Tur yandı, sonraki tura geç
            }

            System.out.println("The new state of the box grid:\n" + boxGrid.toString());

            System.out.println("---> TURN " + turn + " SECOND STAGE:");
            handleOpeningStage();
            // Bu aşamada hata olsa bile (EmptyBox) tur bitmiş sayılır ve döngü devam eder.
        }

        endGame();
    }

    private boolean handleRollingStage() {
        while (true) {
            int[] pos = menu.getCoordinateInput("Please enter the location of the edge box you want to roll:");
            int row = pos[0];
            int col = pos[1];

            // Kenar kontrolü
            if (!isEdgeBox(row, col)) {
                System.out.println("INCORRECT INPUT: The chosen box is not on any of the edges.");
                continue;
            }

            Direction dir = determineRollDirection(row, col);

            try {
                // BoxGrid'e henüz eklemediysen rollGrid metodunu yazman gerekecek!
                boxGrid.rollGrid(row, col, dir);
                return true;
            } catch (UnmovableFixedBoxException e) {
                System.out.println("EXCEPTION: " + e.getMessage());
                return false;
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                return false;
            }
        }
    }

    private void handleOpeningStage() {
        while (true) {
            int[] pos = menu.getCoordinateInput("Please enter the location of the box you want to open:");
            int row = pos[0];
            int col = pos[1];

            // Not: Ödevde "seçilen kutu hareket etmiş olmalı" kuralı var.
            // Bu kontrol BoxGrid tarafında yapılabilir veya burada basitçe geçilebilir.
            // Şimdilik doğrudan açmayı deniyoruz.

            Box selectedBox = boxGrid.getBox(row, col);
            try {
                SpecialTool tool = selectedBox.open();

                useAcquiredTool(tool);
                break; // Başarılı işlem sonrası döngüden çık

            } catch (EmptyBoxException e) {
                System.out.println("BOX IS EMPTY! Continuing to the next turn... [cite: 74]");
                break; // Tur yanar, döngüden çık
            }
        }
    }

    /**
     * [cite_start]ÖDEVDE İSTENEN GENERIC METHOD [cite: 77]
     * Bulunan tool ne olursa olsun burada işlenir.
     */
    private <T extends SpecialTool> void useAcquiredTool(T tool) {
        System.out.println("It contains a SpecialTool --> " + tool.toString());

        while (true) {
            int[] targetPos = menu.getCoordinateInput("Please enter the location of the box to use this SpecialTool:");
            try {
                // Generic tool kullanımı
                tool.useTool(boxGrid, targetPos[0], targetPos[1], targetLetter);
                System.out.println("Tool used successfully!");
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
            // Basitlik adına köşelerde sabit yön veya kullanıcı seçimi sunulabilir.
            // Ödevde kullanıcı seçimi isteniyor:
            return menu.askDirectionForCorner(r, c);
        }

        // Kenarlara göre otomatik yön
        if (r == 0)
            return Direction.DOWN;
        if (r == 7)
            return Direction.UP;
        if (c == 0)
            return Direction.RIGHT;
        if (c == 7)
            return Direction.LEFT;

        return Direction.RIGHT; // Varsayılan (Asla buraya düşmemeli)
    }

    private void endGame() {
        System.out.println("\nGAME OVER");
        System.out.println(boxGrid.toString());
        int score = boxGrid.calculateScore(targetLetter); // BoxGrid'e bu metodu eklemelisin
        System.out.println("THE TOTAL NUMBER OF TARGET LETTER \"" + targetLetter + "\" --> " + score);
        System.out.println("The game has been SUCCESSFULLY completed!");
    }

    private class PuzzleMenu {

        public int[] getCoordinateInput(String message) {
            while (true) {
                System.out.print(message + " ");
                String input = scanner.next(); // Örn: R1-C1 veya 1-1

                try {
                    input = input.toUpperCase().replace("R", "").replace("C", "");
                    String[] parts = input.split("-");

                    if (parts.length != 2)
                        throw new Exception();

                    int row = Integer.parseInt(parts[0]) - 1; // 1-based to 0-based
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
            System.out.println("The chosen box is in a corner.");
            // Köşeye göre mantıklı yönleri sunabiliriz ama basitçe soruyoruz:
            System.out.println("Select direction: [1] Vertical (Up/Down) [2] Horizontal (Left/Right)");
            String choice = scanner.next();

            if (choice.equals("1")) {
                return (r == 0) ? Direction.DOWN : Direction.UP;
            } else {
                return (c == 0) ? Direction.RIGHT : Direction.LEFT;
            }
        }

        // BoxPuzzle.java -> PuzzleMenu class'ı içinde:

        // BoxPuzzle.java -> PuzzleMenu class içinde

private void printBoxSurfaces(Box box) {
            if (box == null) return;

            // Hizalama için Sabitler
            String indent = "      "; // 7 boşluk (Sol tarafı ortalamak için)
            String singleLine = "-------"; // Tekli kutu genişliği
            String tripleLine = "-------------------"; // 3 kutu genişliği (Orta kısım için)

            // 1. Back (Arka) - En Üst
            System.out.println(indent + singleLine);
            System.out.println(indent + "|  " + box.getBackSide() + "  |");

            // 2. Orta Kısım (Left - Top - Right)
            // Çift çizgiyi önlemek için burayı tek parça halinde yazıyoruz
            System.out.println(tripleLine);
            System.out.println("|  " + box.getLeftSide() + "  |  " + box.getTopSide() + "  |  " + box.getRightSide() + "  |");
            System.out.println(tripleLine);

            // 3. Front (Ön)
            System.out.println(indent + "|  " + box.getFrontSide() + "  |");
            
            // 4. Bottom (Alt)
            // Front ile Bottom arasındaki çizgi
            System.out.println(indent + singleLine); 
            System.out.println(indent + "|  " + box.getBottomSide() + "  |");
            
            // Kapanış Çizgisi
            System.out.println(indent + singleLine);
        }

    }
}