public class PointsBoard {
    
    private Gamer[] finalStandings; 
    private MatchManagement matchManager; 
    private static final int TOTAL_MATCHES = 15; 

    public PointsBoard(Gamer[] allGamers, MatchManagement matchManager) {
        this.finalStandings = allGamers; 
        this.matchManager = matchManager;
    }

    public void calculateSeasonTotalsAndAverages() {
        Match[][] allMatches = matchManager.getAllMatches();
        
        // Calculate total points and average per match for each gamer
        for (int i = 0; i < finalStandings.length; i++) {
            Gamer currentGamer = finalStandings[i];
            int seasonTotalPoints = 0;
            // Sum up points from all matches
            for (int j = 0; j < allMatches[i].length; j++) {
                seasonTotalPoints += allMatches[i][j].getMatchPoints();
            }

            currentGamer.setTotalPoints(seasonTotalPoints); 
            
            double average = (double) seasonTotalPoints / TOTAL_MATCHES;
            currentGamer.setAveragePerMatch(average); 
        }
    }

    // For assigning medals based on total points
    public void assignMedals() {
        for (Gamer gamer : finalStandings) {
            int totalScore = gamer.getTotalPoints();
            String medal = "NONE";

            if (totalScore >= 4400) { 
                medal = "GOLD";
            } else if (totalScore >= 3800) { 
                medal = "SILVER";
            } else if (totalScore >= 3500) { 
                medal = "BRONZE";
            } else { 
                medal = "NONE";
            }
            gamer.setMedal(medal);
        }
    }
    
    public void calculateSeasonTotalsAndMedals() {
        calculateSeasonTotalsAndAverages();
        assignMedals();
    }

    public Gamer[] getFinalStandings() {
        return finalStandings;
    }
}