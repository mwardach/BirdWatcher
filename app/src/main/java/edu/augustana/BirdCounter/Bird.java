package edu.augustana.BirdCounter;

public class Bird {

    String birdName;
    int birdCount;

    public Bird() {

    }

    public Bird(String birdName, int birdCount) {
        this.birdName = birdName;
        this.birdCount = birdCount;
    }

    public String getBirdName() {
        return birdName;
    }

    public int getBirdCount() {
        return birdCount;
    }

    public void setBirdCount(int count) {
        birdCount = count;
    }
}
