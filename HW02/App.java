import java.util.Collections;
import java.util.List;

public class App {
    
    private final static String CSV_FILE_PATH = "Files/ScholarshipApplications.csv";

    public static void main(String[] args) {
        
        FileIO fileIO = new FileIO();
                List<Application> applications = fileIO.readAndProcessCSV(CSV_FILE_PATH);
        
        if (applications.isEmpty()) {
            System.out.println("Değerlendirilecek başvuru bulunamadı.");
            return;
        }

        for (Application app : applications) {
            app.evaluate();
        }

        Collections.sort(applications);

        printResults(applications);
    }

    private static void printResults(List<Application> applications) {
        for (Application app : applications) {
            System.out.println(app.toOutputString());
        }
    }
}