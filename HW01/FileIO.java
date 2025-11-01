import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileIO {

    public static int getLineCount(String filePath) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
        return count;
    }

    public static Game[] loadGames(String filePath) {
        int gameCount = getLineCount(filePath);
        Game[] games = new Game[gameCount];
        int index = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                int basePoint = Integer.parseInt(parts[2].trim());
                games[index++] = new Game(id, name, basePoint);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println(e.getMessage());
        }
        return games;
    }

    public static Gamer[] loadGamers(String filePath) {
        int gamerCount = getLineCount(filePath);
        Gamer[] gamers = new Gamer[gamerCount];
        int index = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim());
                String nickname = parts[1].trim();
                String name = parts[2].trim();
                String phone = parts[3].trim();
                int experienceYears = Integer.parseInt(parts[4].trim());
                gamers[index++] = new Gamer(id, nickname, name, phone, experienceYears);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println(e.getMessage());
        }
        return gamers;
    }
}