public class MeritApplication extends Application {

    public MeritApplication(String applicationId) {
        super(applicationId);
        setScholarshipName("Merit");
    }

    @Override
    public void evaluate() {
        if (!checkGeneralEligibility()) {
            return; 
        }

        if (getGpa() >= 3.20) {
            setScholarshipType("Full");
        } else if (getGpa() >= 3.00 && getGpa() < 3.20) {
            setScholarshipType("Half");
        } else {
            setStatus("Rejected");
            setRejectionReason("GPA below 3.0"); 
            return;
        }

        if (isHasRecommendationLetter()) {
            setDuration("2 years");
        } else {
            setDuration("1 year");
        }
        
        setStatus("Accepted");
    }
}