public class Query {

    private MatchManagement matchManager;
    private PointsBoard pointsBoard;

    public Query(MatchManagement matchManager, PointsBoard pointsBoard) {
        this.matchManager = matchManager;
        this.pointsBoard = pointsBoard;
    }

    // Print match details about given match
    private void printMatchDetails(Match match) {
        Game[] games = match.getGames();
        int[] rounds = match.getRounds();

        String gameNames = "[";
        String roundsCount = "[";
        for (int k = 0; k < 3; k++) {
            gameNames += games[k].getGameName();
            roundsCount += rounds[k];
            if (k < 2) {// boundary value is 2 because there are 3 games
                gameNames+=", ";
                roundsCount+=", ";
            }
        }

        gameNames += "]";
        roundsCount += "]";

        System.out.println("Match ID: " + match.getId());
        System.out.println("Games: " + gameNames.toString());
        System.out.println("Rounds: " + roundsCount.toString());
        System.out.println("Raw Points: " + match.getRawPoints());
        System.out.println("Skill Points: " + match.getSkillPoints());
        System.out.println("Bonus Points: " + match.getBonusPoints());
        System.out.println("Match Points: " + match.getMatchPoints());
    }
    // Find match by highest or lowest points
    private Match findMatchByPoint(boolean findMax) {
    Match[][] allMatches = matchManager.getAllMatches();
    Match targetMatch = null;
    Integer targetPoints = null;

    for (Match[] gamerMatches : allMatches) {
        for (Match match : gamerMatches) {
            int points = match.getMatchPoints();

            if (targetPoints == null) { // Take the first match as initial target
                targetPoints = points;
                targetMatch = match;
            } else if ((findMax && points > targetPoints) || (!findMax && points < targetPoints)) {
                // Update target if current match has higher/lower points
                targetPoints = points;
                targetMatch = match;
            }
        }
    }

    return targetMatch;
}


    public void runQuery1_HighestScoringMatch() {
        System.out.println("1. Highest-Scoring Match");
        Match highestMatch = findMatchByPoint(true);

        System.out.println("Highest-Scoring Match:");
        if (highestMatch != null) {
            printMatchDetails(highestMatch);
        }
    }

    public void runQuery2_LowestScoringMatch() {
        System.out.println("2. Lowest-Scoring Match & Most Contributing Game");
        Match lowestMatch = findMatchByPoint(false);

        System.out.println("Lowest-Scoring Match:");
        if (lowestMatch == null) return;

        printMatchDetails(lowestMatch);

        Game[] games = lowestMatch.getGames();
        int[] rounds = lowestMatch.getRounds();
        rounds = lowestMatch.getRounds();
        
        Game mostContributingGame = null;
        int maxContribution = -1;
        int contributingGameIndex = -1;

        for (int i = 0; i < 3; i++) {
            int contribution = rounds[i] * games[i].getBasePoint();
            if (contribution > maxContribution) {
                maxContribution = contribution;
                mostContributingGame = games[i];
                contributingGameIndex = i;
            }
        }

        System.out.println("Most Contributing Game in this Match:");
        if (mostContributingGame != null) {
            System.out.println("Game: " + mostContributingGame.getGameName());
            System.out.println("Contribution: " + rounds[contributingGameIndex] + " rounds × " + mostContributingGame.getBasePoint() + " points = " + maxContribution);
        }
    }
    
    public void runQuery3_LowestBonusMatch() {
        System.out.println("3. Match with the Lowest Bonus Points");
        Match lowestBonusMatch = findMatchByBonus(Integer.MAX_VALUE);

        System.out.println("Match with Lowest Bonus Points:");
        if (lowestBonusMatch != null) {
            System.out.println("Match ID: " + lowestBonusMatch.getId());
            Game[] games = lowestBonusMatch.getGames();
            StringBuilder gameNames = new StringBuilder("[");
            for (int k = 0; k < 3; k++) {
                gameNames.append(games[k].getGameName());
                if (k < 2) gameNames.append(", ");
            }
            gameNames.append("]");
            System.out.println("Games: " + gameNames.toString());

            System.out.println("Skill Points: " + lowestBonusMatch.getSkillPoints());
            System.out.println("Bonus Points: " + lowestBonusMatch.getBonusPoints());
            System.out.println("Match Points: " + lowestBonusMatch.getMatchPoints());
        }
    }

    // Find match with lowest bonus points
    private Match findMatchByBonus(int targetBonus) {
        Match[][] allMatches = matchManager.getAllMatches();
        Match targetMatch = null;
        int minBonus = Integer.MAX_VALUE;

        // Iterate through all matches to find the one with the lowest bonus points
        for (Match[] gamerMatches : allMatches) {
            for (Match match : gamerMatches) {
                if (match.getBonusPoints() < minBonus) {
                    minBonus = match.getBonusPoints();
                    targetMatch = match;
                }
            }
        }
        return targetMatch;
    }

    // Find highest scoring gamer
    public void runQuery4_HighestScoringGamer() {
        System.out.println("4. Highest-Scoring Gamer");
        Gamer highestGamer = findHighestGamer();

        System.out.println("Highest-Scoring Gamer:");
        if (highestGamer != null) {
            System.out.println("Nickname: " + highestGamer.getNickname());
            System.out.println("Name: " + highestGamer.getName());
            System.out.println("Total Points: " + highestGamer.getTotalPoints());

            System.out.println("Average Per Match: " + String.format("%.2f", highestGamer.getAveragePerMatch()));
            System.out.println("Medal: " + highestGamer.getMedal());
        }
    }

    private Gamer findHighestGamer() {
        Gamer[] gamers = pointsBoard.getFinalStandings();
        Gamer highestGamer = null;
        int maxTotalPoints = -1;
        // Iterate through gamers to find the one with highest total points
        for (Gamer gamer : gamers) {
            if (gamer.getTotalPoints() > maxTotalPoints) {
                maxTotalPoints = gamer.getTotalPoints();
                highestGamer = gamer;
            }
        }
        return highestGamer;
    }
    // Calculate total tournament points
    public void runQuery5_TotalTournamentPoints() {
        System.out.println("5. Total Tournament Points");
        Match[][] allMatches = matchManager.getAllMatches();
        long totalTournamentPoints = 0;
        int totalMatches = 0;

        for (Match[] gamerMatches : allMatches) {
            totalMatches += gamerMatches.length;
            for (Match match : gamerMatches) {
                totalTournamentPoints += match.getMatchPoints();
            }
        }

        System.out.println("Total Tournament Points across " + totalMatches + " matches: " + totalTournamentPoints);
    }


    //Print medal distribution
    public void runQuery6_MedalDistribution() {
        System.out.println("6. Medal Distribution");
        Gamer[] gamers = pointsBoard.getFinalStandings();
        int totalGamers = gamers.length;

        int goldCount = 0;
        int silverCount = 0;
        int bronzeCount = 0;
        int noneCount = 0;

        for (Gamer gamer : gamers) {
            String medal = gamer.getMedal();
            if (medal.equals("GOLD")) {
                goldCount++;
            } else if (medal.equals("SILVER")) {
                silverCount++;
            } else if (medal.equals("BRONZE")) {
                bronzeCount++;
            } else {
                noneCount++;
            }
        }
        
        double goldPercent = (double) goldCount / totalGamers * 100;
        double silverPercent = (double) silverCount / totalGamers * 100;
        double bronzePercent = (double) bronzeCount / totalGamers * 100;
        double nonePercent = (double) noneCount / totalGamers * 100;

        System.out.println("Medal Distribution:");
        System.out.println("GOLD: " + goldCount + " gamers (" + String.format("%.1f", goldPercent) + "%)");
        System.out.println("SILVER: " + silverCount + " gamers (" + String.format("%.1f", silverPercent) + "%)");
        System.out.println("BRONZE: " + bronzeCount + " gamers (" + String.format("%.1f", bronzePercent) + "%)");
        System.out.println("NONE: " + noneCount + " gamers (" + String.format("%.1f", nonePercent) + "%)");
    }
}
