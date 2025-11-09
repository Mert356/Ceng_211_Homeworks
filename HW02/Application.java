public class Application {
    
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

    // Document flags 
    private boolean hasEnrollmentCertificate = false;
    private boolean hasRecommendationLetter = false; 
    private boolean hasSavingsDocument = false;      
    private boolean hasSupervisorApproval = false;   
    private boolean hasGrantProposal = false;        

    public Application(String applicationId) {
        this.applicationId = applicationId;
    }

    // Public Getters
    public String getApplicationId() { return applicationId; }
    public String getName() { return name; }
    public double getGpa() { return gpa; }
    public String getStatus() { return status; }
    public String getScholarshipName() { return scholarshipName; } 
    public String getScholarshipType() { return scholarshipType; }
    public String getDuration() { return duration; }
    public String getRejectionReason() { return rejectionReason; }
    public boolean isHasEnrollmentCertificate() { return hasEnrollmentCertificate; }
    public boolean isHasRecommendationLetter() { return hasRecommendationLetter; }
    public boolean isHasSavingsDocument() { return hasSavingsDocument; }
    public boolean isHasSupervisorApproval() { return hasSupervisorApproval; }
    public boolean isHasGrantProposal() { return hasGrantProposal; }
    public double getIncome() { return income; }
    public String getTranscriptStatus() { return transcriptStatus; }
    
    // Public Setters (for FileIO.java)
    public void setName(String name) { this.name = name; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    public void setIncome(double income) { this.income = income; }
    public void setTranscriptStatus(String transcriptStatus) { this.transcriptStatus = transcriptStatus; }
    public void setStatus(String status) { this.status = status; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
    public void setScholarshipType(String scholarshipType) { this.scholarshipType = scholarshipType; }
    public void setDuration(String duration) { this.duration = duration; }
    public void setScholarshipName(String scholarshipName) { this.scholarshipName = scholarshipName; }
    public void setHasEnrollmentCertificate(boolean hasEnrollmentCertificate) { this.hasEnrollmentCertificate = hasEnrollmentCertificate; }
    public void setHasRecommendationLetter(boolean hasRecommendationLetter) { this.hasRecommendationLetter = hasRecommendationLetter; }
    public void setHasSavingsDocument(boolean hasSavingsDocument) { this.hasSavingsDocument = hasSavingsDocument; }
    public void setHasSupervisorApproval(boolean hasSupervisorApproval) { this.hasSupervisorApproval = hasSupervisorApproval; }
    public void setHasGrantProposal(boolean hasGrantProposal) { this.hasGrantProposal = hasGrantProposal; }
    
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
        
         if (getName().isEmpty() || getGpa() == -1.0 || getIncome() == -1.0) {
             setStatus("Rejected");
             setRejectionReason("Missing mandatory information");
             return false;
        }

        return true;
    }

    public void evaluate() {} 

    public String toOutputString() {
        String base = "Applicant ID: " + getApplicationId() + ", Name: " + getName();

        if (getStatus().equals("Accepted")) {
            return base + ", Scholarship: " + getScholarshipName() + ", Status: Accepted, Type: " + getScholarshipType() + ", Duration: " + getDuration();
        } else {
            return base + ", Scholarship: " + getScholarshipName() + ", Status: Rejected, Reason: " + getRejectionReason();
        }
    }
}