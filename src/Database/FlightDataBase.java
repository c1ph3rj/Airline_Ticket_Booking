package Database;

import org.json.simple.JSONObject;

public class FlightDataBase {
    String flightName;
    int noOfSeats;
    String departureLocation;
    String totalDuration;
    JSONObject time;
    String arrivalLocation;
    int checkInCabin;
    JSONObject inFirstClass;
    JSONObject inSecondClass;
    JSONObject inBusinessClass;

    public FlightDataBase(String flightName, int noOfSeats, String departureLocation, String totalDuration, JSONObject time, String arrivalLocation, int checkInCabin, JSONObject inFirstClass, JSONObject inSecondClass, JSONObject inBusinessClass) {
        this.flightName = flightName;
        this.noOfSeats = noOfSeats;
        this.departureLocation = departureLocation;
        this.totalDuration = totalDuration;
        this.time = time;
        this.arrivalLocation = arrivalLocation;
        this.checkInCabin = checkInCabin;
        this.inFirstClass = inFirstClass;
        this.inSecondClass = inSecondClass;
        this.inBusinessClass = inBusinessClass;
    }

    public JSONObject getInFirstClass() {
        return inFirstClass;
    }

    public JSONObject getInSecondClass() {
        return inSecondClass;
    }

    public JSONObject getInBusinessClass() {
        return inBusinessClass;
    }

}
