import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIO {

    private Application findApplicationById(List<Application> applications, String id) {
        for (Application app : applications) {
            if (app.getApplicationId().equals(id)) {
                return app;
            }
        }
        return null;
    }

    public List<Application> readAndProcessCSV(String filePath) {
        List<Application> applications = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                String[] data = line.split(",");
                if (data.length < 2) continue;

                String prefix = data[0].trim();
                String applicantID = data[1].trim();

                Application app = findApplicationById(applications, applicantID);
                
                if (app == null) {
                    if (applicantID.startsWith("11")) {
                        app = new MeritApplication(applicantID);
                    } else if (applicantID.startsWith("22")) {
                        app = new NeedApplication(applicantID);
                    } else if (applicantID.startsWith("33")) {
                        app = new ResearchApplication(applicantID);
                    } else {
                        continue;
                    }

                    applications.add(app);
                }

                try {
                    switch (prefix) {
                        case "A": 
                            app.setName(data[2].trim());
                            app.setGpa(Double.parseDouble(data[3].trim()));
                            app.setIncome(Double.parseDouble(data[4].trim()));
                            break;
                        case "T": 
                            app.setTranscriptStatus(data[2].trim());
                            break;
                        case "I": 
                            if (app instanceof NeedApplication) {
                                ((NeedApplication) app).setFamilyIncome(Double.parseDouble(data[2].trim()));
                                ((NeedApplication) app).setDependents(Integer.parseInt(data[3].trim()));
                            }
                            break;
                        case "D": 
                            String docType = data[2].trim();
                            if (docType.equals("ENR")) app.setHasEnrollmentCertificate(true);
                            if (docType.equals("REC")) app.setHasRecommendationLetter(true);
                            if (docType.equals("SAV")) app.setHasSavingsDocument(true);
                            if (docType.equals("RSV")) app.setHasSupervisorApproval(true);
                            if (docType.equals("GRP")) app.setHasGrantProposal(true);
                            break;
                        case "P": 
                            if (app instanceof ResearchApplication) {
                                String title = data[2].trim();
                                double impactFactor = Double.parseDouble(data[3].trim());
                                ((ResearchApplication) app).addPublication(new Publication(title, impactFactor));
                            }
                            break;
                    }
                } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                    System.err.println("Hatalı veri satırı (ID: " + applicantID + "): " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("HATA: Dosya okunamadı veya bulunamadı: " + filePath);
            e.printStackTrace();
        }
        
        return applications;
    }
}