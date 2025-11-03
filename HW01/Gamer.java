public class Gamer {
    private int id;
    private String nickname;
    private String name;
    private String phoneNumber;
    private int experienceYears;
    private int totalPoints;
    private double averagePerMatch;
    private String medal;

    public Gamer(int id, String nickname, String name, String phoneNumber, int experienceYears) {
        this.id = id;
        this.nickname = nickname;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.experienceYears = experienceYears;
        this.totalPoints = 0;
        this.averagePerMatch = 0.0;
        this.medal = "Not Initilized";
    }

    // Getters
    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public String getNickname(){return this.nickname;}
    public String getPhoneNumber(){return this.phoneNumber;}
    public int getExperienceYears(){return this.experienceYears;}
    public int getTotalPoints(){return this.totalPoints;}
    public double getAveragePerMatch(){return this.averagePerMatch;}
    public String getMedal(){return this.medal;}
    
    // Setters
    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void setAveragePerMatch(double averagePerMatch) {
        this.averagePerMatch = averagePerMatch;
    }

    public void setMedal(String medal) {
        this.medal = medal;
    }   

    public int getNeededExperienceYear() {
        return Math.min(this.experienceYears, 10);
    }
    
    @Override
    public String toString(){
        return "ID => " + id + " Name => " + name + " NickName => " + nickname + " PhoneNumber => " + phoneNumber + " ExpYears => "+ experienceYears;
    }
}
