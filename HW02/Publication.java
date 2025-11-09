public class Publication {
    private String title;
    public String getTitle() {
        return title;
    }

    private double impactFactor;

    public Publication(String title, double impactFactor) {
        this.title = title;
        this.impactFactor = impactFactor;
    }

    public double getImpactFactor() {
        return impactFactor;
    }
}