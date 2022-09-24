import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class UserInterface {

    String response;
    String arraival;
    String departure;
    private Scanner scanner = new Scanner(System.in);
    private String tripType;
    private final List<FlightDataBase> flightDB = new ArrayList<>();
    private JSONArray DB;
    void flightDataBase() throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader("src/flightDataBase.json"));
        DB = (JSONArray) obj;
        for (Object item : DB) {
            String flightName = (String) ((JSONObject) item).get("flightName");
            int noOfSeats = ((Long) ((JSONObject) item).get("noOfSeats")).intValue();
            String tripDate= (String) ((JSONObject) item).get("tripDate");
            String departureLocation= (String) ((JSONObject) item).get("departureLocation");
            String totalDuration= (String) ((JSONObject) item).get("totalDuration");
            String timeOfDeparture= (String) ((JSONObject) item).get("timeOfDeparture");
            String arrival= (String) ((JSONObject) item).get("arrivalLocation");
            String timeOfArrival= (String) ((JSONObject) item).get("timeOfArrival");
            int checkInCabin= ((Long) ((JSONObject) item).get("checkInFlight")).intValue();
            JSONObject inFirstClass= (JSONObject)((JSONObject) item).get("inFirstClass");
            JSONObject inSecondClass=(JSONObject) ((JSONObject) item).get("inSecondClass");
            JSONObject inBusinessClass= (JSONObject) ((JSONObject) item).get("inBusinessClass");
            flightDB.add(new FlightDataBase(flightName,noOfSeats,tripDate,departureLocation,totalDuration,timeOfDeparture,arrival,timeOfArrival,checkInCabin,inFirstClass,inSecondClass,inBusinessClass));
        }


    }
    void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("\n__________  C1ph3R AirLines  __________\n");
    }
    void selectArraival(){
        clearScreen();
        System.out.println("Select Destination \nTo:");
        for(int i =0;i<flightDB.size();i++){
            System.out.println((i+1)+". "+(flightDB.get(i).arrivalLocation));
        }
        do{
            String Input = scanner.next();
            try{
                response = (Integer.parseInt(Input) <=flightDB.size()&&Integer.parseInt(Input) >0)? arraival = flightDB.get(Integer.parseInt(Input)).arrivalLocation:"Invalid Input try again.";
                if(response.equals("Selected trip type Oneway"))
                    System.out.println("Departure From : "+ departure + "\tArraival to :" + Input);
                else
                    System.out.println(response);
            }catch(Exception e){
                response = "Invalid Input try again.";
                System.out.println(response);
            }
        }while(response.equals("Invalid Input try again."));
    }
    void selectDeparture(){
        clearScreen();
        System.out.println("Select Destination \nFrom:");
        for(int i =0;i<flightDB.size();i++){
            System.out.println((i+1)+". "+(flightDB.get(i).departureLocation));
        }
        do{
            departure = scanner.next();
            try{
                response = (Integer.parseInt(departure) <=flightDB.size()&&Integer.parseInt(departure) >0)?"Departure selected.":"Invalid Input try again.";
                if(response.equals("Departure selected."))
                    selectArraival();
                else
                    System.out.println(response);
            }catch(Exception e){
                response = "Invalid Input try again.";
                System.out.println(response);
            }
        }while(response.equals("Invalid Input try again."));
    }

    void selectTripType(){
        System.out.println("Select the trip type:\n");
        System.out.println("1. Oneway\n2. RoundTrip\n");
        do{
            tripType = scanner.next();
            response = (tripType.equals("1")||tripType.equals("2"))?"TripType Oneway":"Invalid Input try again.";
            if(response.equals("TripType Oneway")) {
                selectDeparture();
                break;
            }
            else
                System.out.println(response);
        }while(response.equals("Invalid Input try again."));
    }
    void firstPageOfTheAirLines(){
        clearScreen();
        System.out.println("Welcome to C1ph3R Airlines");
        selectTripType();

    }
    UserInterface() throws IOException, ParseException {
        flightDataBase();
        firstPageOfTheAirLines();
    }
}
