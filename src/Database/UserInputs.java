//Importing required user defined packages.
package Database;
//Importing Libraries of JSON.
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
//Importring predefined Java Libraries.
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//Creating a Class file to get userInputs.
public class UserInputs extends DataBaseOperations {
    //Creating variables and objects that are used across the class file.
    private final Pattern datePattern = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[- /](0[1-9]|1[012])[- /](19|20)\\d\\d$");
    private final Scanner scanner = new Scanner(System.in);
    private Long bookingId;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private final Date today = new Date();
    private String returnTime = "", time, flight, flightClass, departureDate, returnDate, response, arrival, departure, tripType, dateForFlight;
    private final JSONObject flightObj = new JSONObject();
    private int flightNo;
    //Constructor to get the userDatabase and flightDatabase.
    public UserInputs() throws IOException, ParseException {
        userDataBase();
        flightDataBase();
    }
    //Method to clear the screen.
    public void clearScreen() {
        System.out.print("\n");
        System.out.flush();
        System.out.println("\n__________  C1ph3R AirLines  __________\n");
    }
    //Method to save flight details to the DB.
    void flightDataToTheDB() {
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
    }
    //Method to confirm Booking.
    void confirmBooking(String userName,String mailId,String mobileNumber,int age,String gender) throws IOException {
        System.out.println("Press >> 1 to confirm. ");
        System.out.println("Press >> 2 to cancel. ");
        System.out.println("Press >> 3 to Re enter data. ");
       do{
           String input = scanner.next();
           if(input.equals("1")){
               clearScreen();
               flightDataToTheDB();
               updateUserDataBase(userName, mailId, mobileNumber,age, gender, flightObj);
               System.out.println("ThankYou For Booking in C1ph3R Airlines.\nBooking Id:" + bookingId+"\nBooked At:" + today +"\n\n");
               System.exit(1);
           }else if(input.equals("2")){
               System.out.println("Your Booking Process has been canceled");
               response = "done";
               System.exit(1);
           }else if(input.equals("3"))
               userDetailsAsInput();
           else
               response = "Invalid";
           System.out.println(response);
       }while(response.equals("Invalid"));
    }
    //Method to print the details of the user What we get.
    void printUserInputs(String userName,String mailId,String mobileNumber,int age,String gender) throws IOException {
        clearScreen();
        bookingId = new Random().nextLong();
        bookingId = (bookingId<0)?(bookingId*-1):bookingId;
        System.out.println("____________ Confrim Booking ____________");
        System.out.println("Booking Id : " + bookingId);
        System.out.println("UserName   : " + userName);
        System.out.println("E-Mail Id  : " + mailId);
        System.out.println("Mobile no  : " + mobileNumber);
        System.out.println("Age        : " + age );
        System.out.println("Gender     : " + gender);
        System.out.println("_________ details of the Flight _________");
        System.out.println("Flight Name: " + flightDB.get(flightNo).flightName);
        System.out.println("Flight No  : " + (flightNo + 1));
        System.out.println("Trip Type  : " + tripType);
        System.out.println("Departure  : " + departure);
        System.out.println("Arrival    : " + arrival);
        System.out.println(dateForFlight);
        System.out.println(time);
        System.out.println("Class      : " + flightClass);
        System.out.println("In "+flightClass+" Class:-");
        if ((flightClass.equals("Business"))){
            System.out.println("Meals      : " + flightDB.get(flightNo).inBusinessClass.get("meals"));
            System.out.println("CheckIn(Extra) : "+ flightDB.get(flightNo).inBusinessClass.get("checkInExtra") + "Kg");
            System.out.println("_______________ total _______________");
            System.out.println("Price for the flight: " + flightDB.get(flightNo).inBusinessClass.get("price"));
        }
        else if ((flightClass.equals("First"))) {
            System.out.println("Meals      : " + flightDB.get(flightNo).inFirstClass.get("meals"));
            System.out.println("CheckIn(Extra) : "+ flightDB.get(flightNo).inFirstClass.get("checkInExtra"));
            System.out.println("_______________ total _______________");
            System.out.println("Price for the flight: " + flightDB.get(flightNo).inFirstClass.get("price") );
        }
        else{
            System.out.println("Meals      : " + flightDB.get(flightNo).inSecondClass.get("meals"));
            System.out.println("CheckIn(Extra) : "+ flightDB.get(flightNo).inSecondClass.get("checkInExtra"));
            System.out.println("_______________ total _______________");
            System.out.println("Price for the flight: RS." + flightDB.get(flightNo).inSecondClass.get("price") +"\n" );
            System.out.println("_____________________________________");
        }
        confirmBooking(userName, mailId, mobileNumber, age, gender);
    }
    //Method to get UserDetails and Store in a DB.
    void userDetailsAsInput() throws IOException {
        System.out.println("__________ Enter the below details to book the flight __________");
        System.out.println("Enter your UserName:");
        //For UserName.
        String userName = scanner.next();
        System.out.println("Enter your E-Mail id:");
        String mailId;
        String result;
        //To verify email Pattern.
        Pattern emailPattern = Pattern.compile("^[a-zA-Z]+[\\.0-9a-zA-Z]+[@][a-zA-Z0-9]+\\.[a-z]{2,}$");
        do {
            mailId = scanner.next();
            Matcher matcher = emailPattern.matcher(mailId);
            result = (mailId.isEmpty()) ? "please enter a value to verify!" : (matcher.matches()) ? (mailId + " is a valid mail id.\n") : (mailId + " is a invalid mail id! try again.\n");
            if (result.contains("invalid"))
                System.out.println(result);
        }
        //executes until user inputs a correct email id.
        while (result.contains("invalid") || result.contains("value"));
        System.out.println("Enter your Mobile Number: ");
        //To verify Phone no.
        String mobileNumber;
        Pattern mobileNoPattern = Pattern.compile("^[^0-5][0-9]{9}$");
        do {
            mobileNumber = scanner.next();
            Matcher matcher = mobileNoPattern.matcher(mobileNumber);
            result = (mobileNumber.isEmpty()) ? "please enter a value to verify!" : (matcher.matches()) ? (mobileNumber + " is a valid mobile number.\n") : (mobileNumber + " is a invalid mobile number! try again.\n");
            if (result.contains("invalid mobile"))
                System.out.println(result);
        }//Executes until user enters a correct phone number.
        while (result.contains("invalid") || result.contains("value"));
        System.out.println("Enter your age:\n" +
                "(age must be 1 to 80)");
        //To get Age.
        String age;
        do {
            age = scanner.next();
            try {
                result = (Integer.parseInt(age) <= 80 && Integer.parseInt(age) >= 1) ? "Age is valid" : "Age is invalid. Try again.";
            } catch (Exception e) {
                result = "Age is invalid. Try again.";
            }
            if (result.contains("invalid."))
                System.out.println(result);
        }//Executes until the user enters a correct age.
        while (result.equals("Age is invalid. Try again."));
        System.out.println("Enter gender:\n1 for male\n2 for female\n3 for others");
        //To verify the gender.
        String gender;
        do {
            gender = scanner.next();
            gender = (gender.equals("1")) ? "Male" : (gender.equals("2")) ? "FemLale" : (gender.equals("3")) ? "Others" : "Invalid Gender.";
            if (gender.contains("Invalid"))
                System.out.println(gender);
        } //Loops executes until the user enters a valid input.
        while (gender.contains("Invalid"));
        printUserInputs(userName, mailId, mobileNumber, Integer.parseInt(age), gender);
    }
    //Method to get the return time if the user choose round trip.
    void setReturnTime() {
        //printing previous Input details as short as possible.
        clearScreen();
        System.out.println("Departure From: " + departure + "\t\tArrival to:" + arrival);
        System.out.println(dateForFlight);
        System.out.println("selected flight number: " + flight);
        System.out.println("Departure Flight Time");
        JSONArray departureTime = (JSONArray) flightDB.get(Integer.parseInt(flight) - 1).time.get("departure");
        JSONArray arrivalTime = (JSONArray) flightDB.get(Integer.parseInt(flight) - 1).time.get("arrival");
        //printing the time based on the flight selection.
        for (int i = 0; i < departureTime.size(); i++)
            System.out.println((i + 1) + ". Departure time : " + departureTime.get(i) + " Arrival time: " + arrivalTime.get(i));
        do {//checking the input.
            returnTime = scanner.next();
            try {
                if ((Integer.parseInt(returnTime)) <= departureTime.size() && Integer.parseInt(returnTime) != 0) {
                    returnTime = (returnTime) + ". Departure time : " + departureTime.get(Integer.parseInt(returnTime) - 1) + "\tArrival time: " + arrivalTime.get(Integer.parseInt(returnTime) - 1);
                    time = time + "\n" + returnTime;
                    System.out.println(returnTime);
                    response = "Time Occurred";
                    userDetailsAsInput();
                    break;
                } else response = "invalid Input! try again.";
            } catch (Exception e) {
                response = "invalid Input! try again.";
            }
            System.out.println(response);
        }//Executes until the user enters correct input.
        while (response.equals("invalid Input! try again."));

    }
//To get the time for the flight.
    void setTime() {
        //Printing the pervious inputs as much as possible.
        clearScreen();
        System.out.println("Departure From: " + departure + "\t\tArrival to:" + arrival);
        System.out.println(dateForFlight);
        System.out.println("selected flight number: " + flight);
        JSONArray departureTime = (JSONArray) flightDB.get(Integer.parseInt(flight) - 1).time.get("departure");
        JSONArray arrivalTime = (JSONArray) flightDB.get(Integer.parseInt(flight) - 1).time.get("arrival");
        //printing the time based on the flight selection.
        for (int i = 0; i < departureTime.size(); i++)
            System.out.println((i + 1) + ". Departure time : " + departureTime.get(i) + " Arrival time: " + arrivalTime.get(i));
        do {//verifying the input.
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
                response = "invalid Input! try again.";
            }
            System.out.println(response);
        }//Executes loops until user enters the correct value.
        while (response.equals("invalid Input! try again."));
    }
// Method to select the flight.
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
                response = "Invalid Input try again.";
                System.out.println(response);
            }
        } while (response.equals("Invalid Input try again."));
    }
//Method to select the flight class.
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
//method to print the first class values.
    private void firstClass(int i) {
        System.out.println("\n--------------- In First Class ---------------");
        System.out.println("Meals in First Class: " + flightDB.get(i).inFirstClass.get("meals"));
        System.out.println("Check In(Extra): " + flightDB.get(i).inFirstClass.get("checkInExtra") + "Kg MAX.");
        System.out.println("Price for Single person: " + flightDB.get(i).inFirstClass.get("price"));
        System.out.println("No of seats available: " + flightDB.get(i).inFirstClass.get("noOfSeatsAvailableInFirstClass"));
    }
//Method to print the business class values.
    private void businessClass(int i) {
        System.out.println("\n-------------- In Business Class -------------");
        System.out.println("Meals in Business Class: " + flightDB.get(i).inBusinessClass.get("meals"));
        System.out.println("Check In(Extra): " + flightDB.get(i).inBusinessClass.get("checkInExtra") + "Kg MAX.");
        System.out.println("Price for Single person: " + flightDB.get(i).inBusinessClass.get("price"));
        System.out.println("No of seats available: " + flightDB.get(i).inBusinessClass.get("noOfSeatsAvailableInBusinessClass"));
    }
//Method to print the second class values.
    private void secondClass(int i) {
        System.out.println("\n--------------- In Second Class --------------");
        System.out.println("Meals in Second Class: " + flightDB.get(i).inSecondClass.get("meals"));
        System.out.println("Check In(Extra): " + flightDB.get(i).inSecondClass.get("checkInExtra") + "Kg MAX.");
        System.out.println("Price for Single person: " + flightDB.get(i).inSecondClass.get("price"));
        System.out.println("No of seats available: " + flightDB.get(i).inSecondClass.get("noOfSeatsAvailableInSecondClass"));
    }
//Method to set the data for the flight.
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
// Method to get the return date from the user based on the trip type.
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
//method to select destination location.
    void setSelectArrival() {
        clearScreen();
        ArrayList<String> listOfCities = new ArrayList<>();
        System.out.println("Select Destination \nTo:");
        for (int i = 0; i < flightDB.size(); i++) {
            if(!(listOfCities.contains((flightDB.get(i).arrivalLocation)))&&!departure.equals((flightDB.get(i).arrivalLocation)))
                listOfCities.add((flightDB.get(i).arrivalLocation));
        }
        for(int i =0;i<listOfCities.size();i++)
            System.out.println((i + 1) + ". " + listOfCities.get(i));
        do {
            arrival = scanner.next();
            try {
                response = (Integer.parseInt(arrival) <= listOfCities.size() && Integer.parseInt(arrival) > 0) ? "Arrival selected." : "Invalid Input try again.";
                if (response.equals("Arrival selected.")) {
                    arrival = listOfCities.get(Integer.parseInt(arrival)-1);
                    setDate();
                } else
                    System.out.println(response);
            } catch (Exception e) {
                response = "Invalid Input try again.";
                System.out.println(response);
            }
        } while (response.equals("Invalid Input try again."));
    }
//Method to select the departure location.
    void setSelectDeparture() {
        clearScreen();
        ArrayList<String> listOfCities = new ArrayList<>();
        System.out.println("Select Destination \nFrom:");
        for (int i = 0; i < flightDB.size(); i++) {
            if(!(listOfCities.contains((flightDB.get(i).departureLocation))))
                listOfCities.add((flightDB.get(i).departureLocation));
        }
        for(int i =0;i<listOfCities.size();i++)
            System.out.println((i + 1) + ". " + listOfCities.get(i));
        do {
            departure = scanner.next();
            try {
                response = (Integer.parseInt(departure) <= flightDB.size() && Integer.parseInt(departure) > 0) ? "Departure selected." : "Invalid Input try again.";
                if (response.equals("Departure selected.")) {
                    departure = listOfCities.get(Integer.parseInt(departure)-1);
                    setSelectArrival();
                } else
                    System.out.println(response);
            } catch (Exception e) {
                response = "Invalid Input try again.";
                System.out.println(response);
            }
        } while (response.equals("Invalid Input try again."));
    }
//Method to set the trip type.
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