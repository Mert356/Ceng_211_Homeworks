public class EsportsManagementApp {
    
    private static final String GAMES_FILE = "Files/games.csv";
    private static final String GAMERS_FILE = "Files/gamers.csv";

    private static final int MATCHES_PER_GAMER = 15;

    public static void main(String[] args) {
        Game[] allGames = FileIO.loadGames(GAMES_FILE);
        Gamer[] allGamers = FileIO.loadGamers(GAMERS_FILE);

        if (allGames.length == 0 || allGamers.length == 0) {
            System.err.println("No Gamer and Game Found");
            return;
        }

        MatchManagement matchManager = new MatchManagement(allGamers, allGames, MATCHES_PER_GAMER);
        matchManager.simulateMatches();
        
        PointsBoard pointsBoard = new PointsBoard(allGamers, matchManager);
        pointsBoard.calculateSeasonTotalsAndMedals();

        System.out.println("\n--- Query Results ---");
        Query queryProcessor = new Query(matchManager, pointsBoard);
        
        queryProcessor.runQuery1_HighestScoringMatch(); 
        System.out.println("----------------------------------------");
        queryProcessor.runQuery2_LowestScoringMatch(); 
        System.out.println("----------------------------------------");
        queryProcessor.runQuery3_LowestBonusMatch(); 
        System.out.println("----------------------------------------");
        queryProcessor.runQuery4_HighestScoringGamer(); 
        System.out.println("----------------------------------------");
        queryProcessor.runQuery5_TotalTournamentPoints(); 
        System.out.println("----------------------------------------");
        queryProcessor.runQuery6_MedalDistribution(); 
        System.out.println("----------------------------------------");
        
    }
}