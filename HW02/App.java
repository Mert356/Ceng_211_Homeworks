import java.util.List;

public class App {
    
    private final static String CSV_FILE_PATH = "Files/ScholarshipApplications.csv";

    public static void main(String[] args) {
        
        FileIO fileIO = new FileIO();
        
        List<Application> applications = fileIO.readAndProcessCSV(CSV_FILE_PATH);
        
        if (applications.isEmpty()) {
            System.out.println("No applications found to evaluate.");
            return;
        }

        for (Application app : applications) {
            app.evaluate();
        }

        printSortedResults(applications);
    }
    
    private static void printSortedResults(List<Application> applications) {
        
        int n = applications.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Application app1 = applications.get(j);
                Application app2 = applications.get(j + 1);
                
                if (app1.getApplicationId().compareTo(app2.getApplicationId()) > 0) {
                    applications.set(j, app2);
                    applications.set(j + 1, app1);
                }
            }
        }

        for (Application app : applications) {
            System.out.println(app.toOutputString());
        }
    }
}