import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFromUser {
    private final Pattern datePattern = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[- /](0[1-9]|1[012])[- /](19|20)\\d\\d$");
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    private Date today = new Date();
    private String returnTime, time, flight, flightClass, departureDate, returnDate, response, arrival, departure, tripType, dateForFlight;
    private String userName;
    private final List < UserDetails > userDB = new ArrayList < > ();
    private JSONArray userData;
    private JSONArray DB;
    private final List < FlightDataBase > flightDB = new ArrayList < > ();
    private final Scanner scanner = new Scanner(System.in);

    void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("\n__________  C1ph3R AirLines  __________\n");
    }

    void flightDataBase() throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader("src/flightDataBase.json"));
        DB = (JSONArray) obj;
        for (Object item: DB) {
            flightDB.add(new FlightDataBase((String)((JSONObject) item).get("flightName"), ((Long)((JSONObject) item).get("noOfSeats")).intValue(), (String)((JSONObject) item).get("departureLocation"), (String)((JSONObject) item).get("totalDuration"), (JSONObject)((JSONObject) item).get("time"), (String)((JSONObject) item).get("arrivalLocation"), ((Long)((JSONObject) item).get("checkInFlight")).intValue(), (JSONObject)((JSONObject) item).get("inFirstClass"), (JSONObject)((JSONObject) item).get("inSecondClass"), (JSONObject)((JSONObject) item).get("inBusinessClass")));
        }
    }
    void userDataBase() throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader("src/userDataBase.json"));
        userData = (JSONArray) obj;
        for (Object item: userData) {
            userDB.add(new UserDetails((String)((JSONObject) item).get("firstName"), (String)((JSONObject) item).get("lastName"), (String)((JSONObject) item).get("Password"), ((Long)((JSONObject) item).get("age")).intValue(), (String)((JSONObject) item).get("gender"), (Boolean)((JSONObject) item).get("physicallyChallenged")));
        }
    }

    void userVerification() throws IOException {
        clearScreen();
        System.out.println("Login to Continue");
        System.out.println("__________________________________");
        System.out.println("Enter UserName: ");
        boolean userVerified = false;
        int noOfAttempts = 1;
        while (noOfAttempts <= 3) {
            userName = scanner.next();
            for (int i = 0; i < userDB.size(); i++) {
                if (userName.equals(userDB.get(i).getFirstName())) {
                    System.out.println("Welcome " + userName + "\nEnter password:\n");
                    userVerified = true;
                    String checkPin = scanner.next();
                    if (checkPin.equals(userDB.get(i).getPassword())) {
                        clearScreen();
                        System.out.println("success");
                    } else {
                        System.out.println(((noOfAttempts == 3) ? "Maximum Attempts reached.\nThank you!" :
                                "wrong password!,Try again."));
                        noOfAttempts++;
                    }
                }
            }
            if (!userVerified) {
                System.out.println("No users Found");
                noOfAttempts++;
            }
        }
    }

    void setReturnTime(){
        clearScreen();
        System.out.println("Departure From: " + departure + "\t\tArrival to:" + arrival);
        System.out.println(dateForFlight);
        System.out.println("selected flight number: " + flight);
        JSONArray departureTime = (JSONArray) flightDB.get(Integer.parseInt(flight) - 1).time.get("departure");
        JSONArray arrivalTime = (JSONArray) flightDB.get(Integer.parseInt(flight) - 1).time.get("arrival");
        for (int i = 0; i < departureTime.size(); i++)
            System.out.println((i + 1) + ". Departure time : " + departureTime.get(i) + " Arrival time: " + arrivalTime.get(i));
        do {
            returnTime = scanner.next();
            try {
                if ((Integer.parseInt(returnTime)) <= departureTime.size() && Integer.parseInt(returnTime) != 0) {
                    System.out.println((returnTime) + ". Departure time : " + departureTime.get(Integer.parseInt(returnTime) - 1) + "\tArrival time: " + arrivalTime.get(Integer.parseInt(returnTime) - 1));
                    response = "Time Occurred";
                    userVerification();
                    break;
                } else response = "invalid Input! try again.";
            } catch (Exception e) {
                response = e.toString();
            }
            System.out.println(response);
        } while (response.equals("invalid Input! try again."));

    }
        void setTime() {
            clearScreen();
            System.out.println("Departure From: " + departure + "\t\tArrival to:" + arrival);
            System.out.println(dateForFlight);
            System.out.println("selected flight number: " + flight);
            JSONArray departureTime = (JSONArray) flightDB.get(Integer.parseInt(flight) - 1).time.get("departure");
            JSONArray arrivalTime = (JSONArray) flightDB.get(Integer.parseInt(flight) - 1).time.get("arrival");
            for (int i = 0; i < departureTime.size(); i++)
                System.out.println((i + 1) + ". Departure time : " + departureTime.get(i) + " Arrival time: " + arrivalTime.get(i));
            do {
                time = scanner.next();
                try {
                    if ((Integer.parseInt(time)) <= departureTime.size() && Integer.parseInt(time) != 0) {
                        System.out.println((time) + ". Departure time : " + departureTime.get(Integer.parseInt(time) - 1) + "\tArrival time: " + arrivalTime.get(Integer.parseInt(time) - 1));
                        response = "Time Occurred";
                        if(tripType.equals("RoundTrip"))
                            setReturnTime();
                        else userVerification();
                    } else response = "invalid Input! try again.";
                } catch (Exception e) {
                    response = e.toString();
                }
                System.out.println(response);
            } while (response.equals("invalid Input! try again."));
        }

    private void selectFlight() {
        clearScreen();
        System.out.println("Departure From: " + departure + "\t\tArrival to:" + arrival);
        System.out.println(dateForFlight);
        System.out.println("Flight Class: " + flightClass);
        System.out.println("Here is the list of flight available:\n");
        boolean flightFound = false;
        for (int i = 0; i < flightDB.size(); i++) {
            if (departure.equals(flightDB.get(i).departureLocation) && arrival.equals(flightDB.get(i).arrivalLocation)) {
                System.out.println("____________________________________________");
                System.out.println("Select " + (i + 1) + " for the flight below:");
                System.out.println("Name of the airlines:" + flightDB.get(i).flightName);
                System.out.println("Total duration: " + flightDB.get(i).totalDuration);
                System.out.println("Check in(Cabin):" + flightDB.get(i).checkInCabin + " Kg MAX.");
                flight = String.valueOf(flightDB.indexOf(flightDB.get(i))-1);
                if ((flightClass.equals("Business")))
                    businessClass(i);
                else if ((flightClass.equals("First")))
                    firstClass(i);
                else
                    secondClass(i);
                flightFound = true;
            }
        }
        if(!flightFound) {
            System.out.println("no Flights found sorry!");
            setSelectDeparture();
        }
        do {
            String flight1 = scanner.next();
            try {
                response = (Integer.parseInt(flight1) <= flightDB.size() && Integer.parseInt(flight1) > 0&&(flight1.equals(flight))) ? "flight selected." : "Invalid Input try again.";
                if (response.equals("flight selected.")) {
                    System.out.println("Selected flight no: " + flight1);
                    setTime();
                    break;
                } else
                    System.out.println(response);
            } catch (Exception e) {
                response = "Invalid Input try again.";
                System.out.println(response);
            }
        } while (response.equals("Invalid Input try again."));
    }
        void setFlightCLass() {
            clearScreen();
            System.out.println("Departure From: " + departure + "\t\tArrival to:" + arrival);
            dateForFlight = (tripType.equals("RoundTrip")) ? "Departure Date: " + departureDate + " " + "Return Date: " + returnDate : "Departure Date: " + departureDate;
            System.out.println(dateForFlight);
            System.out.println("Select Class:\n1. Business\n2. First\n3. Second");
            do {
                flightClass = scanner.next();
                response = (flightClass.equals("1") || flightClass.equals("2") || flightClass.equals("3")) ? "FlightClass Selected" : "Invalid Input try again.";
                if (response.equals("FlightClass Selected")) {
                    flightClass = (flightClass.equals("1")) ? "Business" : (flightClass.equals("2")) ? "First" : "Second";
                    selectFlight();
                } else
                    System.out.println(response);
            } while (response.equals("Invalid Input try again."));
        }

        private void firstClass(int i) {
            System.out.println("\n--------------- In First Class ---------------");
            System.out.println("Meals in First Class: " + flightDB.get(i).inFirstClass.get("meals"));
            System.out.println("Check In(Extra): " + flightDB.get(i).inFirstClass.get("checkInExtra") + "Kg MAX.");
            System.out.println("Price for Single person: " + flightDB.get(i).inFirstClass.get("price"));
            System.out.println("No of seats available: " + flightDB.get(i).inFirstClass.get("noOfSeatsAvailableInFirstClass"));
        }
        private void businessClass(int i) {
            System.out.println("\n-------------- In Business Class -------------");
            System.out.println("Meals in Business Class: " + flightDB.get(i).inBusinessClass.get("meals"));
            System.out.println("Check In(Extra): " + flightDB.get(i).inBusinessClass.get("checkInExtra") + "Kg MAX.");
            System.out.println("Price for Single person: " + flightDB.get(i).inBusinessClass.get("price"));
            System.out.println("No of seats available: " + flightDB.get(i).inBusinessClass.get("noOfSeatsAvailableInBusinessClass"));
        }
        private void secondClass(int i) {
            System.out.println("\n--------------- In Second Class --------------");
            System.out.println("Meals in Second Class: " + flightDB.get(i).inSecondClass.get("meals"));
            System.out.println("Check In(Extra): " + flightDB.get(i).inSecondClass.get("checkInExtra") + "Kg MAX.");
            System.out.println("Price for Single person: " + flightDB.get(i).inSecondClass.get("price"));
            System.out.println("No of seats available: " + flightDB.get(i).inSecondClass.get("noOfSeatsAvailableInSecondClass"));
        }

        void setDate() throws java.text.ParseException {
            clearScreen();
            System.out.println("Departure From : " + departure + "\t\tArrival to :" + arrival);
            System.out.println("Enter Departure Date :\n(dd-mm-yyyy)\nExample: 10-03-2022");
            do {
                departureDate = scanner.next();
                Matcher dateMatch = datePattern.matcher(departureDate);
                if (dateMatch.matches()) {
                    Date date1 = sdf.parse(departureDate);
                    if (date1.compareTo(today) > 0) {
                        System.out.println("departure date set to : " + departureDate);
                        response = "Departure Date has occurred";
                        if (tripType.equals("RoundTrip"))
                            setReturnDate();
                        else setFlightCLass();
                    } else response = "Enter a valid date.";
                } else response = "Enter a valid date in the format of (dd-mm-yyyy)\nExample: 10-03-2022";
                System.out.println(response);
            } while (response.contains("Enter a valid date"));
        }

        void setReturnDate() throws java.text.ParseException {
            clearScreen();
            System.out.println("Departure From : " + departure + "\t\tArrival to :" + arrival);
            System.out.println("Departure date : " + departureDate + "\nEnter Return Date :\n(dd-mm-yyyy)\nExample: 10-03-2022");
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
                } else response = "Enter a valid date in the format of (dd-mm-yyyy)\nExample: 10-03-2022";
                System.out.println(response);
            } while (response.contains("Enter a valid date"));
        }

        void setSelectArrival() {
            clearScreen();
            System.out.println("Select Destination \nTo:");
            for (int i = 0; i < flightDB.size(); i++) {
                System.out.println((i + 1) + ". " + (flightDB.get(i).arrivalLocation));
            }
            do {
                arrival = scanner.next();
                try {
                    response = (Integer.parseInt(arrival) <= flightDB.size() && Integer.parseInt(arrival) > 0) ? "Arrival selected." : "Invalid Input try again.";
                    if (response.equals("Arrival selected.")) {
                        arrival = (flightDB.get(Integer.parseInt(arrival) - 1).arrivalLocation);
                        setDate();
                    } else
                        System.out.println(response);
                } catch (Exception e) {
                    response = "Invalid Input try again.";
                    System.out.println(response);
                }
            } while (response.equals("Invalid Input try again."));
        }
        void setSelectDeparture() {
            clearScreen();
            System.out.println("Select Destination \nFrom:");
            for (int i = 0; i < flightDB.size(); i++) {
                System.out.println((i + 1) + ". " + (flightDB.get(i).departureLocation));
            }
            do {
                departure = scanner.next();
                try {
                    response = (Integer.parseInt(departure) <= flightDB.size() && Integer.parseInt(departure) > 0) ? "Departure selected." : "Invalid Input try again.";
                    if (response.equals("Departure selected.")) {
                        departure = (flightDB.get(Integer.parseInt(departure) - 1).departureLocation);
                        setSelectArrival();
                    } else
                        System.out.println(response);
                } catch (Exception e) {
                    response = "Invalid Input try again.";
                    System.out.println(response);
                }
            } while (response.equals("Invalid Input try again."));
        }
        void setSelectTripType() {
            System.out.println("Select the trip type:\n");
            System.out.println("1. Oneway\n2. RoundTrip\n");
            do {
                tripType = scanner.next();
                response = (tripType.equals("1") || tripType.equals("2")) ? "TripType Selected" : "Invalid Input try again.";
                if (response.equals("TripType Selected")) {
                    tripType = (tripType.equals("1")) ? "OneWay" : "RoundTrip";
                    System.out.println(tripType);
                    setSelectDeparture();
                } else
                    System.out.println(response);
            } while (response.equals("Invalid Input try again."));
        }

    }