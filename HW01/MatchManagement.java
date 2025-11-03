public class MatchManagement {

    private Match[][] allMatches; 
    private Gamer[] allGamers;
    private Game[] allGames;
    private final int MATCHES_PER_GAMER;
    
    public MatchManagement(Gamer[] allGamers, Game[] allGames, int matchesPerGamer) {
        this.allGamers = allGamers;
        this.allGames = allGames;
        this.MATCHES_PER_GAMER = matchesPerGamer;
        this.allMatches = new Match[allGamers.length][matchesPerGamer];
    }
    
    public void simulateMatches() {
        // Itarate each gamer and simulate matches
        for (int i = 0; i < allGamers.length; i++) {
            Gamer currentGamer = allGamers[i];
            // Simulate 15 matches for each gamer
            for (int j = 0; j < MATCHES_PER_GAMER; j++) {
                Match newMatch = new Match(currentGamer, allGames);
                this.allMatches[i][j] = newMatch;
            }
        }
    }
    
    public Match[][] getAllMatches() {
        return allMatches;
    }
    
    public Gamer[] getAllGamers() {
        return allGamers;
    }

    public Match[] getMatchesForGamer(int gamerIndex) {
        if (gamerIndex >= 0 && gamerIndex < allMatches.length) {
            return allMatches[gamerIndex];
        }
        return null;
    }
}