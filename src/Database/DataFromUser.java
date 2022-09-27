package Database;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFromUser {
    private final Pattern datePattern = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[- /](0[1-9]|1[012])[- /](19|20)\\d\\d$");
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    private Date today = new Date();
    private String returnTime = "", time, flight, flightClass, departureDate, returnDate, response, arrival, departure, tripType, dateForFlight;
    private List <userDataBase> userDBOutput = new ArrayList < > ();
    private JSONArray outputData;
    private int flightNo;
    private JSONArray DB;
    private final List <FlightDataBase> flightDB = new ArrayList < > ();
    private final Scanner scanner = new Scanner(System.in);
    public void clearScreen() {
        System.out.print("\n");
        System.out.flush();
        System.out.println("\n__________  C1ph3R AirLines  __________\n");
    }

    public void flightDataBase() throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader("src/Database/flightDataBase.json"));
        DB = (JSONArray) obj;
        for (Object item: DB) {
            flightDB.add(new FlightDataBase((String)((JSONObject) item).get("flightName"), ((Long)((JSONObject) item).get("noOfSeats")).intValue(), (String)((JSONObject) item).get("departureLocation"), (String)((JSONObject) item).get("totalDuration"), (JSONObject)((JSONObject) item).get("time"), (String)((JSONObject) item).get("arrivalLocation"), ((Long)((JSONObject) item).get("checkInFlight")).intValue(), (JSONObject)((JSONObject) item).get("inFirstClass"), (JSONObject)((JSONObject) item).get("inSecondClass"), (JSONObject)((JSONObject) item).get("inBusinessClass")));
        }
    }
    public void userDataBase() throws IOException, ParseException {
        Object userObj = new JSONParser().parse(new FileReader("src/Database/userDataBase.json"));
        outputData = (JSONArray) userObj;
        for (Object item: outputData) {
            userDBOutput.add(new userDataBase((String)((JSONObject) item).get("userName"), (String)((JSONObject) item).get("emailId"), (String)((JSONObject) item).get("mobileNo"), ((Long)((JSONObject) item).get("age")).intValue(), (String)((JSONObject) item).get("gender"), (JSONObject)((JSONObject) item).get("detailsOfTheFLight")));

        }
    }
    void updateUserDataBase(String userName, String mailId, String mobileNumber, int age, String gender) throws IOException {
        FileWriter fileWriter = new FileWriter("src/Database/userDataBase.json");
        JSONObject flightObj = new JSONObject();
        flightObj.put("tripType", tripType);
        flightObj.put("from", departure);
        flightObj.put("to", arrival);
        flightObj.put("Date", dateForFlight);
        flightObj.put("time", time);
        flightObj.put("returnTimeIfAvailable", returnTime);
        flightObj.put("flightClass", flightClass);
        if (flightClass.equals("First"))
            flightObj.put("detailsOfFlightClass", flightDB.get(flightNo).inFirstClass);
        else if (flightClass.equals("Second"))
            flightObj.put("detailsOfFlightClass", flightDB.get(flightNo).inSecondClass);
        else if (flightClass.equals("Business"))
            flightObj.put("detailsOfFlightClass", flightDB.get(flightNo).inBusinessClass);
        JSONObject obj = new JSONObject();
        obj.put("userName", userName);
        obj.put("emailId", mailId);
        obj.put("mobileNo", mobileNumber);
        obj.put("age", age);
        obj.put("gender", gender);
        obj.put("detailsOfTheFLight", flightObj);
        outputData.add(userDBOutput.size(), obj);
        fileWriter.write(outputData.toJSONString());
        System.out.println(outputData.toJSONString());
        fileWriter.close();
    }

    void userDetailsAsInput() throws IOException {
        System.out.println("__________ Enter the below details to book the flight __________");
        System.out.println("Enter your UserName:");
        String userName = scanner.next();
        System.out.println("Enter your E-Mail id:");
        String mailId;
        String result;
        Pattern emailPattern = Pattern.compile("^[a-zA-Z]+[\\.0-9a-zA-Z]+[@][a-zA-Z0-9]+\\.[a-z]{2,}$");
        do {
            mailId = scanner.next();
            Matcher matcher = emailPattern.matcher(mailId);
            result = (mailId.isEmpty()) ? "please enter a value to verify!" : (matcher.matches()) ? (mailId + " is a valid mail id.\n") : (mailId + " is a invalid mail id! try again.\n");
            if (result.contains("invalid"))
                System.out.println(result);
        }
        while (result.contains("invalid") || result.contains("value"));
        System.out.println("Enter your Mobile Number: ");
        String mobileNumber;
        Pattern mobileNoPattern = Pattern.compile("^[^0-5][0-9]{9}$");
        do {
            mobileNumber = scanner.next();
            Matcher matcher = mobileNoPattern.matcher(mobileNumber);
            result = (mobileNumber.isEmpty()) ? "please enter a value to verify!" : (matcher.matches()) ? (mobileNumber + " is a valid mobile number.\n") : (mobileNumber + " is a invalid mobile number! try again.\n");
            if (result.contains("invalid mobile"))
                System.out.println(result);
        } while (result.contains("invalid") || result.contains("value"));
        System.out.println("Enter your age:");
        String age;
        do {
            age = scanner.next();
            try {
                result = (Integer.parseInt(age) <= 70 && Integer.parseInt(age) >= 1) ? "Age is valid" : "Age is invalid. Try again.";
            } catch (Exception e) {
                result = "Age is invalid. Try again.";
            }
            if (result.contains("invalid."))
                System.out.println(result);
        } while (result.equals("Age is invalid. Try again."));
        System.out.println("Enter gender:\n1 for male\n2 for female\n3 for others");
        String gender;
        do {
            gender = scanner.next();
            gender = (gender.equals("1")) ? "Male" : (gender.equals("2")) ? "FemLale" : (gender.equals("3")) ? "Others" : "Invalid Gender.";
            if (gender.contains("Invalid"))
                System.out.println(gender);
        } while (gender.contains("Invalid"));
        updateUserDataBase(userName, mailId, mobileNumber, Integer.parseInt(age), gender);
        System.out.println("Success");
        System.exit(0);

    }
    void setReturnTime() {
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
                    returnTime = (returnTime) + ". Departure time : " + departureTime.get(Integer.parseInt(returnTime) - 1) + "\tArrival time: " + arrivalTime.get(Integer.parseInt(returnTime) - 1);
                    System.out.println(returnTime);
                    response = "Time Occurred";
                    userDetailsAsInput();
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
                    time = (time) + ". Departure time : " + departureTime.get(Integer.parseInt(time) - 1) + "\tArrival time: " + arrivalTime.get(Integer.parseInt(time) - 1);
                    System.out.println(time);
                    response = "Time Occurred";
                    if (tripType.equals("RoundTrip"))
                        setReturnTime();
                    else userDetailsAsInput();
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
                System.out.println("Select " + (flightNo + 1) + " for the flight below:");
                System.out.println("Name of the airlines:" + flightDB.get(i).flightName);
                System.out.println("Total duration: " + flightDB.get(i).totalDuration);
                System.out.println("Check in(Cabin):" + flightDB.get(i).checkInCabin + " Kg MAX.");
                flightNo = i;
                flight = String.valueOf(flightDB.indexOf(flightDB.get(i)) + 1);
                if ((flightClass.equals("Business")))
                    businessClass(i);
                else if ((flightClass.equals("First")))
                    firstClass(i);
                else
                    secondClass(i);
                flightFound = true;
            }
        }
        if (!flightFound) {
            System.out.println("no Flights found sorry!");
            setSelectDeparture();
        }
        do {
            String flight1 = scanner.next();
            try {
                response = (Integer.parseInt(flight1) <= flightDB.size() && Integer.parseInt(flight1) > 0 && (flight1.equals(flight))) ? "flight selected." : "Invalid Input try again.";
                if (response.equals("flight selected.")) {
                    System.out.println("Selected flight no: " + flight1);
                    setTime();
                    break;
                } else
                    System.out.println(response);
            } catch (Exception e) {
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
    public void setSelectTripType() {
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