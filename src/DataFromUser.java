import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFromUser {
    private final Pattern datePattern = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[- /](0[1-9]|1[012])[- /](19|20)\\d\\d$");
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private Date today = new Date();
    private String flightClass;
    private String departureDate;
    private String returnDate;
    private String response;
    private String arrival;
    private String departure;
    private String tripType;
    private JSONArray DB;
    private final List<FlightDataBase> flightDB = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("\n__________  C1ph3R AirLines  __________\n");
    }

    void flightDataBase() throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader("src/flightDataBase.json"));
        DB = (JSONArray) obj;
        for (Object item : DB) {
            String flightName = (String) ((JSONObject) item).get("flightName");
            int noOfSeats = ((Long) ((JSONObject) item).get("noOfSeats")).intValue();
            String departureLocation= (String) ((JSONObject) item).get("departureLocation");
            String totalDuration= (String) ((JSONObject) item).get("totalDuration");
            JSONObject time= (JSONObject) ((JSONObject) item).get("time");
            String arrival= (String) ((JSONObject) item).get("arrivalLocation");
            int checkInCabin= ((Long) ((JSONObject) item).get("checkInFlight")).intValue();
            JSONObject inFirstClass= (JSONObject)((JSONObject) item).get("inFirstClass");
            JSONObject inSecondClass=(JSONObject) ((JSONObject) item).get("inSecondClass");
            JSONObject inBusinessClass= (JSONObject) ((JSONObject) item).get("inBusinessClass");
            flightDB.add(new FlightDataBase(flightName,noOfSeats,departureLocation,totalDuration,time,arrival,checkInCabin,inFirstClass,inSecondClass,inBusinessClass));
        }
    }
    void setFlightCLass(){
        clearScreen();
        System.out.println("Departure From: "+ departure + "\t\tArrival to:" + arrival);
        String dateForFlight = (tripType.equals("RoundTrip"))?"Departure Date: " + departureDate +" "+ "Return Date: " + returnDate:"Departure Date: " + departureDate ;
        System.out.println(dateForFlight);
        System.out.println("Select Class:\n1. Business\n2. First\n3. Second");
        do{
            flightClass = scanner.next();
            response = (flightClass.equals("1")||flightClass.equals("2")||flightClass.equals("3"))?"FlightClass Selected":"Invalid Input try again.";
            if(response.equals("FlightClass Selected")) {
                flightClass = (flightClass.equals("1"))?"Business":(flightClass.equals("2"))?"First":"Second";
                System.out.println(flightClass);
            }
            else
                System.out.println(response);
        }while(response.equals("Invalid Input try again."));


    }
    void setDate() throws java.text.ParseException {
        clearScreen();
        System.out.println("Departure From : "+ departure + "\t\tArrival to :" + arrival);
        System.out.println("Enter Departure Date :\n(dd-mm-yyyy)\nExample: 10-03-2022");
        do {
            departureDate = scanner.next();
            Matcher dateMatch = datePattern.matcher(departureDate);
            if (dateMatch.matches()) {
                Date date1 = sdf.parse(departureDate);
                if (date1.compareTo(today) > 0) {
                    System.out.println("departure date set to : " + departureDate);
                    response = "Departure Date has occurred";
                    if(tripType.equals("RoundTrip"))
                        setReturnDate();
                    else setFlightCLass();
                } else response = "Enter a valid date.";
            } else response = "Enter a valid date in the format of (dd-mm-yyyy)\nExample: 10-03-2022" ;
            System.out.println(response);
        }while (response.contains("Enter a valid date"));
    }

    void setReturnDate() throws java.text.ParseException {
        clearScreen();
        System.out.println("Departure From : "+ departure + "\t\tArrival to :" + arrival);
        System.out.println("Departure date : " +departureDate+"\nEnter Return Date :\n(dd-mm-yyyy)\nExample: 10-03-2022");
        do {
            returnDate = scanner.next();
            Matcher dateMatch = datePattern.matcher(returnDate);
            if (dateMatch.matches()) {
                Date date1 = sdf.parse(returnDate);
                if (date1.compareTo(sdf.parse(departureDate)) > 0) {
                    System.out.println("return date set to : " + returnDate);
                    response = "Return Date has occurred";
                    setFlightCLass();
                } else response = "Enter a valid date.";
            } else response = "Enter a valid date in the format of (dd-mm-yyyy)\nExample: 10-03-2022" ;
            System.out.println(response);
        }while (response.contains("Enter a valid date"));
    }


    void setSelectArrival(){
        clearScreen();
        System.out.println("Select Destination \nTo:");
        for(int i =0;i<flightDB.size();i++){
            System.out.println((i+1)+". "+(flightDB.get(i).arrivalLocation));
        }
        do{
            arrival = scanner.next();
            try{
                response = (Integer.parseInt(arrival) <=flightDB.size()&&Integer.parseInt(arrival) >0)?"Arrival selected." :"Invalid Input try again.";
                if(response.equals("Arrival selected.")){
                    arrival = (flightDB.get(Integer.parseInt(arrival)-1).arrivalLocation);
                    setDate();
                }
                else
                    System.out.println(response);
            }catch(Exception e){
                response = "Invalid Input try again.";
                System.out.println(response);
            }
        }while(response.equals("Invalid Input try again."));
    }
    void setSelectDeparture(){
        clearScreen();
        System.out.println("Select Destination \nFrom:");
        for(int i =0;i<flightDB.size();i++){
            System.out.println((i+1)+". "+(flightDB.get(i).departureLocation));
        }
        do{
            departure = scanner.next();
            try{
                response = (Integer.parseInt(departure) <=flightDB.size()&&Integer.parseInt(departure) >0)?"Departure selected.":"Invalid Input try again.";
                if(response.equals("Departure selected.")){
                    departure = (flightDB.get(Integer.parseInt(departure)-1).departureLocation);
                    setSelectArrival();
                }
                else
                    System.out.println(response);
            }catch(Exception e){
                response = "Invalid Input try again.";
                System.out.println(response);
            }
        }while(response.equals("Invalid Input try again."));
    }
    void setSelectTripType(){
        System.out.println("Select the trip type:\n");
        System.out.println("1. Oneway\n2. RoundTrip\n");
        do{
            tripType = scanner.next();
            response = (tripType.equals("1")||tripType.equals("2"))?"TripType Selected":"Invalid Input try again.";
            if(response.equals("TripType Selected")) {
                tripType = (tripType.equals("1"))?"OneWay":"RoundTrip";
                System.out.println(tripType);
                setSelectDeparture();
            }
            else
                System.out.println(response);
        }while(response.equals("Invalid Input try again."));
    }

}
