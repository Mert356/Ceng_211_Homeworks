import java.util.ArrayList;

public class ResearchApplication extends Application {
    
    private ArrayList<Publication> publications = new ArrayList<>();

    public ResearchApplication(String applicationId) {
        super(applicationId);
        setScholarshipName("Research");
    }

    public void addPublication(Publication publication) {
        this.publications.add(publication);
    }
    protected ArrayList<Publication> getPublications() { return publications; }
    
    @Override
    public void evaluate() {
        if (!checkGeneralEligibility()) {
            return; 
        }
        
        if (getPublications().isEmpty() && !isHasGrantProposal()) {
            setStatus("Rejected");
            setRejectionReason("Missing publication or proposal");
            return;
        }
        
        double avgImpactFactor = 0.0;
        if (!getPublications().isEmpty()) {
            double totalImpactFactor = 0.0;
            for (Publication p : getPublications()) {
                totalImpactFactor += p.getImpactFactor();
            }
            avgImpactFactor = totalImpactFactor / getPublications().size();
        } 
        
        if (avgImpactFactor >= 1.50) {
            setScholarshipType("Full");
        } else if (avgImpactFactor >= 1.00 && avgImpactFactor < 1.50) {
            setScholarshipType("Half");
        } else {
            setStatus("Rejected");
            setRejectionReason("Publication impact too low");
            return;
        }
        
        setStatus("Accepted");

        int baseDurationInMonths;
        if (getScholarshipType().equals("Full")) {
            baseDurationInMonths = 12; 
        } else { 
            baseDurationInMonths = 6;  
        }
        
        if (isHasSupervisorApproval()) {
            baseDurationInMonths += 12;
        }
        
        if (baseDurationInMonths == 12) {
            setDuration("1 year");
        } else if (baseDurationInMonths == 24) {
            setDuration("2 years");
        } else if (baseDurationInMonths % 12 == 0) {
            setDuration((baseDurationInMonths / 12) + " years");
        } else {
            setDuration(baseDurationInMonths + " months");
        }
    }
}