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
            avgImpactFactor = totalImpactFactor / publications.size();
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
        
        int baseDurationInMonths = getScholarshipType().equals("Full") ? 12 : 6;
        
        if (isHasSupervisorApproval()) {
            baseDurationInMonths += 12;
        }
        
        if (baseDurationInMonths % 12 == 0) {
            int years = baseDurationInMonths / 12;
            setDuration(years + (years > 1 ? " years" : " year"));
        } else {
            setDuration(baseDurationInMonths + " months");
        }

        setStatus("Accepted");
    }
}