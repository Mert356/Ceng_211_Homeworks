
public abstract class Application implements Comparable<Application> {
    
    private String applicationId;
    private String name = "";
    private double gpa = -1.0; 
    private double income = -1.0;
    private String transcriptStatus = " "; 
    
    private String status = "Pending";
    private String rejectionReason = "";
    private String scholarshipType = ""; 
    private String duration = ""; 
    private String scholarshipName = "Unknown";

    private boolean hasEnrollmentCertificate = false;
    private boolean hasRecommendationLetter = false; 
    private boolean hasSavingsDocument = false;      
    private boolean hasSupervisorApproval = false;   
    private boolean hasGrantProposal = false;        

    public Application(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationId() { return applicationId; }
    public String getName() { return name; }

    protected double getGpa() { return gpa; }
    protected double getIncome() { return income; }
    protected String getTranscriptStatus() { return transcriptStatus; }
    protected boolean isHasEnrollmentCertificate() { return hasEnrollmentCertificate; }
    protected boolean isHasRecommendationLetter() { return hasRecommendationLetter; }
    protected boolean isHasSavingsDocument() { return hasSavingsDocument; }
    protected boolean isHasSupervisorApproval() { return hasSupervisorApproval; }
    protected boolean isHasGrantProposal() { return hasGrantProposal; }
    protected String getScholarshipType() { return scholarshipType; }

    public void setName(String name) { this.name = name; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    public void setIncome(double income) { this.income = income; }
    public void setTranscriptStatus(String transcriptStatus) { this.transcriptStatus = transcriptStatus; }
    public void setHasEnrollmentCertificate(boolean hasEnrollmentCertificate) { this.hasEnrollmentCertificate = hasEnrollmentCertificate; }
    public void setHasRecommendationLetter(boolean hasRecommendationLetter) { this.hasRecommendationLetter = hasRecommendationLetter; }
    public void setHasSavingsDocument(boolean hasSavingsDocument) { this.hasSavingsDocument = hasSavingsDocument; }
    public void setHasSupervisorApproval(boolean hasSupervisorApproval) { this.hasSupervisorApproval = hasSupervisorApproval; }
    public void setHasGrantProposal(boolean hasGrantProposal) { this.hasGrantProposal = hasGrantProposal; }

    protected void setStatus(String status) { this.status = status; }
    protected void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
    protected void setScholarshipType(String scholarshipType) { this.scholarshipType = scholarshipType; }
    protected void setDuration(String duration) { this.duration = duration; }
    protected void setScholarshipName(String scholarshipName) { this.scholarshipName = scholarshipName; }

    protected boolean checkGeneralEligibility() {
        if (!isHasEnrollmentCertificate()) {
            setStatus("Rejected");
            setRejectionReason("Missing Enrollment Certificate");
            return false;
        }

        if (getTranscriptStatus().equals("N") || getTranscriptStatus().trim().isEmpty() || getTranscriptStatus().equals(" ")) {
            setStatus("Rejected");
            setRejectionReason("Missing Transcript");
            return false;
        }

        if (getGpa() < 2.50) {
            setStatus("Rejected");
            setRejectionReason("GPA below 2.5");
            return false;
        }
        
         if (getName().isEmpty() || getGpa() == -1.0) {
             setStatus("Rejected");
             setRejectionReason("Missing mandatory information");
             return false;
        }

        return true;
    }

    public abstract void evaluate(); 

    public String toOutputString() {
        String base = "Applicant ID: " + applicationId + ", Name: " + name;

        if (status.equals("Accepted")) {
            return base + ", Scholarship: " + scholarshipName + ", Status: Accepted, Type: " + scholarshipType + ", Duration: " + duration;
        } else {
            return base + ", Scholarship: " + scholarshipName + ", Status: Rejected, Reason: " + rejectionReason;
        }
    }

    @Override
    public int compareTo(Application other) {
        return this.applicationId.compareTo(other.applicationId);
    }
}