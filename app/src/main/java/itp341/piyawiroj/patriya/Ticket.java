package itp341.piyawiroj.patriya;

import java.io.Serializable;

public class Ticket implements Serializable {

    private int startingLocation;
    private int endingLocation;
    private String tripType;
    private String priorities;

    @Override
    public String toString() {
        return "Ticket{" +
                "startingLocation='" + startingLocation + '\'' +
                ", endingLocation='" + endingLocation + '\'' +
                ", tripType='" + tripType + '\'' +
                ", priorities='" + priorities + '\'' +
                '}';
    }

    public Ticket(int startingLocation, int endingLocation, String tripType, String priorities) {
        this.startingLocation = startingLocation;
        this.endingLocation = endingLocation;
        this.tripType = tripType;
        this.priorities = priorities;
    }

    public int getStartingLocation() {
        return startingLocation;
    }

    public void setStartingLocation(int startingLocation) {
        this.startingLocation = startingLocation;
    }

    public int getEndingLocation() {
        return endingLocation;
    }

    public void setEndingLocation(int endingLocation) {
        this.endingLocation = endingLocation;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getPriorities() {
        return priorities;
    }

    public void setPriorities(String priorities) {
        this.priorities = priorities;
    }
}
