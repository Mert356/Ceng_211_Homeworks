public class NeedApplication extends Application {
    
    private double familyIncome = -1.0; 
    private int dependents = 0;

    public NeedApplication(String applicationId) {
        super(applicationId);
        setScholarshipName("Need");
    }

    public void setFamilyIncome(double familyIncome) { this.familyIncome = familyIncome; }
    public void setDependents(int dependents) { this.dependents = dependents; }
    
    protected double getFamilyIncome() { return familyIncome; }
    protected int getDependents() { return dependents; }

    @Override
    public void evaluate() {
        if (!checkGeneralEligibility()) {
            return; 
        }
        
        double finalIncome = (getFamilyIncome() > -1.0) ? getFamilyIncome() : getIncome();
        
        double fullThreshold = 10000.0;
        double halfThreshold = 15000.0;
        double increaseFactor = 1.0;
        
        if (isHasSavingsDocument()) {
            increaseFactor += 0.20;
        }
        if (getDependents() >= 3) {
            increaseFactor += 0.10;
        }
        
        fullThreshold *= increaseFactor;
        halfThreshold *= increaseFactor;

        if (finalIncome <= fullThreshold) {
            setScholarshipType("Full");
        } else if (finalIncome > fullThreshold && finalIncome <= halfThreshold) {
            setScholarshipType("Half");
        } else {
            setStatus("Rejected");
            setRejectionReason("Financial status unstable");
            return;
        }

        setDuration("1 year");
        setStatus("Accepted");
    }
}