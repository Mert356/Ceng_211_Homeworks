import java.util.Random;

public class Match {
    private static int nextMatchId = 1;

    private int id;
    private Gamer gamer;
    private Game[] games;
    private int[] rounds;

    private int rawPoints;
    private int skillPoints;
    private int bonusPoints;
    private int matchPoints;

    public Match(Gamer gamer, Game[] allGames) {
        this.id = nextMatchId++;
        this.gamer = gamer;
        this.games = new Game[3];
        this.rounds = new int[3];

        simulateMatchSetup(allGames);
        calculatePoints();
    }

    private void simulateMatchSetup(Game[] allGames) {
        Random rand = new Random();
        int totalGames = allGames.length;

        for (int i = 0; i < 3; i++) {
            this.games[i] = allGames[rand.nextInt(totalGames)];
            this.rounds[i] = rand.nextInt(10) + 1;
        }
    }

    private void calculatePoints() {
        calculateRawPoints();
        calculateSkillPoints();
        calculateBonusPoints();
        this.matchPoints = this.skillPoints + this.bonusPoints;
    }

    private void calculateRawPoints() {
        this.rawPoints = 0;
        for (int i = 0; i < 3; i++) {
            this.rawPoints += this.rounds[i] * this.games[i].getBasePoint();
        }
    }

    private void calculateSkillPoints() {
        // Skill multiplier based on experience years which comes from gamer class by getNeededExperienceYear method
        int effectiveExp = gamer.getNeededExperienceYear();
        double skillMultiplier = 1.0 + effectiveExp * 0.02;
        this.skillPoints = (int) Math.floor(this.rawPoints * skillMultiplier);
    }

    // Bonus point calculation
    private void calculateBonusPoints() {
        if (rawPoints >= 600) {
            this.bonusPoints = 100;
        } else if (rawPoints >= 400) {
            this.bonusPoints = 50;
        } else if (rawPoints >= 200) {
            this.bonusPoints = 25;
        } else {
            this.bonusPoints = 10;
        }
    }

    // Getters
    public int getId() {
        return id;
    }

    public Game[] getGames() {
        return games;
    }

    public int[] getRounds() {
        return rounds;
    }

    public int getRawPoints() {
        return rawPoints;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public int getMatchPoints() {
        return matchPoints;
    }
}