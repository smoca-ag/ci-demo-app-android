package ch.smoca.demo_android;

public class Body {
    private int weight;
    private int height;

    public Body(int weight, int height) {
        this.weight = weight;
        this.height = height;
    }

    public int getBmi() {
        float heightInM = (float)height / 100;
        return Math.round(weight / (heightInM * heightInM));
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
