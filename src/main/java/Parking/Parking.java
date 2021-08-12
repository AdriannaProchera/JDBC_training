package Parking;

public class Parking {
    public Parking(int spacesCounter) {
        SpacesCounter = spacesCounter;
    }

    int SpacesCounter;

    public Parking() {
    }

    public int getSpacesCounter() {
        return SpacesCounter;
    }

    public void setSpacesCounter(int spacesCounter) {
        SpacesCounter = spacesCounter;
    }
}
