import org.json.simple.JSONObject;

public class FlightDataBase {
    String flightName;
    int noOfSeats;
    String tripDate;
    String departureLocation;
    String totalDuration;
    String timeOfDeparture;
    String arrivalLocation;
    String timeOfArrival;
    int checkInCabin;
    JSONObject inFirstClass;
    JSONObject inSecondClass;
    JSONObject inBusinessClass;

    public FlightDataBase(String flightName, int noOfSeats, String tripDate, String departureLocation, String totalDuration, String timeOfDeparture, String arrivalLocation, String timeOfArrival, int checkInCabin, JSONObject inFirstClass, JSONObject inSecondClass, JSONObject inBusinessClass) {
        this.flightName = flightName;
        this.noOfSeats = noOfSeats;
        this.tripDate = tripDate;
        this.departureLocation = departureLocation;
        this.totalDuration = totalDuration;
        this.timeOfDeparture = timeOfDeparture;
        this.arrivalLocation = arrivalLocation;
        this.timeOfArrival = timeOfArrival;
        this.checkInCabin = checkInCabin;
        this.inFirstClass = inFirstClass;
        this.inSecondClass = inSecondClass;
        this.inBusinessClass = inBusinessClass;
    }
}
