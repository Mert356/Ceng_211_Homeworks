import java.util.*;

public class App {
    public static void main(String[] args) {
        System.out.println("Welcome to Sliding Penguins Puzzle Game App.");
        IcyTerrain terrain = new IcyTerrain();
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();

        List<Penguin> penguins = terrain.getPenguins();
        int playerIdx = rand.nextInt(3);
        Penguin playerPenguin = penguins.get(playerIdx);
        
        System.out.println("Penguins Generated:");
        for(Penguin p : penguins) {
            String suffix = (p == playerPenguin) ? " ---> YOUR PENGUIN" : "";
            System.out.println(p.getSymbol() + ": " + p.getTypeString() + suffix);
        }

        // Oyun Döngüsü (4 Tur)
        for (int turn = 1; turn <= 4; turn++) {
            System.out.println("\n*** TURN " + turn + " ***");
            
            for (Penguin p : penguins) {
                if (!p.isAlive()) continue;

                terrain.printGrid();
                System.out.println("Current turn: " + p.getSymbol() + " (" + p.getTypeString() + ")");
                
                Direction dir = null;
                boolean useSpecial = false;

                if (p == playerPenguin) {
                    System.out.print("Use special action? (Y/N): ");
                    useSpecial = scanner.next().equalsIgnoreCase("Y");

                    System.out.print("Move (U/D/L/R): ");
                    String dInput = scanner.next().toUpperCase();
                    switch(dInput) {
                        case "U": dir = Direction.UP; break;
                        case "D": dir = Direction.DOWN; break;
                        case "L": dir = Direction.LEFT; break;
                        case "R": dir = Direction.RIGHT; break;
                        default: dir = Direction.UP;
                    }
                } else {
                    dir = Direction.values()[rand.nextInt(4)];
                    useSpecial = (rand.nextInt(100) < 30);
                    if(p instanceof RockhopperPenguin && rand.nextBoolean()) useSpecial = true;
                }
                
                terrain.simulateTurn(p, dir, useSpecial);
            }
        }

        System.out.println("\n*** GAME OVER - LEADERBOARD ***");
        penguins.sort((p1, p2) -> p2.getTotalScore() - p1.getTotalScore());
        
        int rank = 1;
        for (Penguin p : penguins) {
            String suffix = (p == playerPenguin) ? " (You)" : "";
            System.out.println(rank + ". " + p.getSymbol() + suffix + " | Total Weight: " + p.getTotalScore());
            rank++;
        }
        
        scanner.close();
    }
}