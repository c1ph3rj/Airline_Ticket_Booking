//Importing required user defined packages.
package Database;
//Importing Libraries of JSON.

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Creating a Class file to get userInputs.
public class UserInputs extends DataBaseOperations {
    public static final String TEXT_CYAN = "\u001B[36m";

    public static final String TEXT_YELLOW = "\u001B[33m";
    public static final String TEXT_RESET = "\u001B[0m";
    public static final String TEXT_BLUE = "\u001B[34m";
    public static final String TEXT_GREEN = "\u001B[32m";
    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_PURPLE = "\u001B[35m";

    //Creating variables and objects that are used across the class file.
    private final Pattern datePattern = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[- /](0[1-9]|1[012])[- /](19|20)\\d\\d$");
    private final Scanner scanner = new Scanner(System.in);
    private final Date today = new Date();
    private int userNo;
    private final JSONObject flightObj = new JSONObject();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private Long pNRNumber;
    private String userName = "", mailId, mobileNumber, gender, password = "";
    private int age;
    private boolean isLoggedIn = false;
    private String returnTime = "", time, flight, flightClass, departureDate, returnDate, response, arrival, departure, tripType, dateForFlight;
    private int flightNo;

    //Constructor to get the userDatabase and flightDatabase.
    public UserInputs(String userName, String password, String mailId, String mobileNumber, int age, String gender, boolean isLoggedIn) {
        super();
        this.isLoggedIn = isLoggedIn;
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.mailId = mailId;
    }

    public UserInputs() {
        super();

    }

    //Method to clear the screen.
    public void clearScreen() {
        System.out.print("\n");
        System.out.flush();
        System.out.println("\n__________  C1ph3R AirLines  __________\n");
    }

    //Method to save flight details to the DB.
    void flightDataToTheDB() {
        flightObj.put("PNRNumber",pNRNumber);
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
    void confirmBooking() throws IOException {
        System.out.println(TEXT_GREEN +"\n\nPress >> 1 to confirm. ");
        System.out.println(TEXT_RED + "Press >> 2 to cancel. ");
        System.out.println(TEXT_YELLOW + "Press >> 3 to Re enter data. " + TEXT_RESET  );
        do {
            String input = scanner.next();
            switch (input) {
                case "1" -> {
                    clearScreen();
                    flightDataToTheDB();
                    updateUserDataBase(userNo, flightObj);
                    System.out.println("ThankYou For Booking in C1ph3R Airlines.\nPNR NUMBER:" + pNRNumber + "\nBooked At:" + today + "\n\n");
                    response = "Done";
                }
                case "2" -> {
                    System.out.println("Your Booking Process has been canceled");
                    response = "done";
                }
                case "3" -> userDetailsAsInput();
                default -> {
                    response = "Invalid";
                    System.out.println(response);
                }
            }
        } while (response.equals("Invalid"));
    }

    //Method to print the details of the user What we get.
    public void printUserInputs() throws IOException {
        clearScreen();
        pNRNumber = new Random().nextLong();
        pNRNumber = (pNRNumber < 0) ? (pNRNumber * -1) : pNRNumber;
        System.out.println(TEXT_PURPLE + "____________ Confirm Booking ____________");
        System.out.println(TEXT_GREEN + "PNR Number : " + TEXT_BLUE + pNRNumber + TEXT_GREEN + "\nUserName   : " + TEXT_BLUE + userName + TEXT_GREEN + "\nE-Mail Id  : " + TEXT_BLUE + mailId + TEXT_GREEN + "\nMobile no  : " + TEXT_BLUE + mobileNumber + TEXT_GREEN + "\nAge        : " + TEXT_BLUE + age + TEXT_GREEN + "\nGender     : " + TEXT_BLUE + gender + TEXT_PURPLE + "\n_________ details of the Flight _________" + TEXT_GREEN + "\nFlight Name: " + TEXT_BLUE + flightDB.get(flightNo).flightName + TEXT_GREEN + "\nFlight No  : " + TEXT_BLUE + (flightNo + 1) + TEXT_GREEN + "\nTrip Type  : " + TEXT_BLUE + tripType + TEXT_GREEN + "\nDeparture  : " + TEXT_BLUE + departure + TEXT_GREEN + "\nArrival    : " + TEXT_BLUE + arrival + "\n" + time + TEXT_GREEN + "\nClass      : " + TEXT_BLUE + flightClass + TEXT_GREEN + "\nIn " + flightClass + " Class:-");
        if ((flightClass.equals("Business"))) {
            System.out.println(TEXT_GREEN + "Meals      : " + TEXT_BLUE + flightDB.get(flightNo).inBusinessClass.get("meals"));
            System.out.println(TEXT_GREEN + "CheckIn(Extra) : " + TEXT_BLUE + flightDB.get(flightNo).inBusinessClass.get("checkInExtra") + "Kg");
            System.out.println(TEXT_PURPLE + "_________________ total ________________");
            System.out.println(TEXT_GREEN + "Price for the flight:  RS." + TEXT_BLUE + flightDB.get(flightNo).inBusinessClass.get("price"));
            System.out.println(TEXT_PURPLE + "________________________________________");
        } else if ((flightClass.equals("First"))) {
            System.out.println(TEXT_GREEN + "Meals      : " + TEXT_BLUE + flightDB.get(flightNo).inFirstClass.get("meals"));
            System.out.println(TEXT_GREEN + "CheckIn(Extra) : " + TEXT_BLUE + flightDB.get(flightNo).inFirstClass.get("checkInExtra"));
            System.out.println(TEXT_PURPLE + "________________ total _________________");
            System.out.println(TEXT_GREEN + "Price for the flight:  RS." + TEXT_BLUE + flightDB.get(flightNo).inFirstClass.get("price"));
            System.out.println(TEXT_PURPLE + "________________________________________");
        } else {
            System.out.println(TEXT_GREEN + "Meals      : " + TEXT_BLUE + flightDB.get(flightNo).inSecondClass.get("meals"));
            System.out.println(TEXT_GREEN + "CheckIn(Extra) : " + TEXT_BLUE + flightDB.get(flightNo).inSecondClass.get("checkInExtra"));
            System.out.println(TEXT_PURPLE + "_________________ total ________________");
            System.out.println(TEXT_GREEN + "Price for the flight: RS." + TEXT_BLUE + flightDB.get(flightNo).inSecondClass.get("price") + "\n");
            System.out.println(TEXT_PURPLE + "________________________________________" + TEXT_RESET);
        }
        confirmBooking();
    }

    //Method to get UserDetails and Store in a DB.
    protected void registerNewAccount() throws IOException {
        Pattern userNamePattern = Pattern.compile("^[A-Za-z][A-Za-z0-9_]{7,29}$");
        System.out.println("__________ Enter the below details to book the flight __________");
        //For UserName.
        System.out.println("Enter your UserName:\n(Note: must contain least 8 letters.)");
        do {
            userName = scanner.next();
            Matcher matcher = userNamePattern.matcher(userName);
            response = (userName.isEmpty()) ? "please enter a value to verify!" : (matcher.matches()) ? (userName + " is a valid UserName.\n") : (userName + " is a invalid UserName! try again.\n");
            if (response.contains("invalid"))
                System.out.println(response);
            else {
                for (Database.userDataBase userDataBase : userDBOutput) {
                    if (userName.equals(userDataBase.getUserName())) {
                        response = "UserName Already exist";
                        System.out.println(response);
                    }
                }
            }
        } while (response.contains("invalid") || response.contains("value") || response.contains("Already exist"));
        //For password.
        clearScreen();
        System.out.println("Welcome " + userName);
        Pattern passwordPattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d\\w\\W]{8,}$");
        do {
            System.out.println("Enter a Password :\n(Note : Password must contain a Caps letter , a small letter and a number with minimum length of 8.)");
            password = scanner.next();
            //Matching the pattern of the password with the user input.
            Matcher matcher = passwordPattern.matcher(password);
            //Printing the output.
            response = (password.isEmpty()) ? "please enter a value to verify!" : (matcher.matches()) ? (password + " is a valid Password.\n") : (password + " is a invalid Password! try again.\n");
            System.out.println(response);
        }
        while (response.contains("invalid") || response.contains("value"));
        //For EMail.
        System.out.println("Enter your E-Mail id:");
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
        do {
            String age = scanner.next();
            try {
                result = (Integer.parseInt(age) <= 80 && Integer.parseInt(age) >= 1) ? "Age is valid" : "Age is invalid. Try again.";
            } catch (Exception e) {
                result = "Age is invalid. Try again.";
            }
            if (result.contains("invalid."))
                System.out.println(result);
            else
                this.age = Integer.parseInt(age);
        }//Executes until the user enters a correct age.
        while (result.equals("Age is invalid. Try again."));
        System.out.println("Enter gender:\n1 for male\n2 for female\n3 for others");
        //To verify the gender.
        do {
            gender = scanner.next();
            gender = (gender.equals("1")) ? "Male" : (gender.equals("2")) ? "FemLale" : (gender.equals("3")) ? "Others" : "Invalid Gender.";
            if (gender.contains("Invalid"))
                System.out.println(gender);
        } //Loops executes until the user enters a valid input.
        while (gender.contains("Invalid"));
        System.out.println("Successfully Registered Login to continue.");
        JSONObject flightObj = new JSONObject();
        updateUserDataBase(userName, mailId, mobileNumber, age, gender,password, false, flightObj);
    }

    void userDetailsAsInput() throws IOException {
        if (!isLoggedIn) {
            registerNewAccount();
        } else {
            clearScreen();
            System.out.println(TEXT_CYAN + "Hi " + userName + TEXT_RESET);
            System.out.println("Your account has already logged in,"+ TEXT_YELLOW +"\nDo you need to continue with your details\nor Login with other Id?" + TEXT_RESET);
            System.out.println(TEXT_GREEN + "1 >>>>> Continue" + TEXT_RED + "\n2 >>>>> Register new Account." + TEXT_RESET+ "\nSelect One Of The Options Above:");
            do {
                String userSelection = scanner.next();
                try {
                    if (userSelection.equals("1")) {
                        response = "continue";
                        printUserInputs();
                    } else if (userSelection.equals("2")) {
                        response = "continue";
                        updateUserDataBase(false, userNo);
                        registerNewAccount();
                        break;
                    } else
                        response = "Invalid";
                } catch (Exception e) {
                    response = e.toString();
                }
                if (response.contains("Invalid"))
                    System.out.println(response);
            }//Executes until the user enters a correct age.
            while (response.equals("Invalid"));
        }
    }

    //Method to get the return time if the user choose round trip.
    void setReturnTime() {
        //printing previous Input details as short as possible.
        clearScreen();
        System.out.println(TEXT_BLUE + "Departure From: " + arrival + "\t\tArrival to:" + departure);
        System.out.println(dateForFlight);
        System.out.println("selected flight number: " + flight);
        System.out.println(time+ TEXT_RESET);
        System.out.println(TEXT_YELLOW+ "\nSelect Departure time of your flight ( "+ returnDate+" ):" + TEXT_RESET);
        JSONArray departureTime = (JSONArray) flightDB.get(Integer.parseInt(flight) - 1).time.get("departure");
        JSONArray arrivalTime = (JSONArray) flightDB.get(Integer.parseInt(flight) - 1).time.get("arrival");
        //printing the time based on the flight selection.
        for (int i = 0; i < departureTime.size(); i++)
            System.out.println((i + 1) + " >>>> The flight left the airport at:" + departureTime.get(i) + ",And reach your destination at: " + arrivalTime.get(i));
        System.out.println(" * Select One Of The Options Above:");
        do {//checking the input.
            returnTime = scanner.next();
            try {
                if ((Integer.parseInt(returnTime)) <= departureTime.size() && Integer.parseInt(returnTime) != 0) {
                    returnTime = "Date: "+(returnDate) + ", Departure time at your destination: " + departureTime.get(Integer.parseInt(returnTime) - 1) + "\tArrival time at your location " + arrivalTime.get(Integer.parseInt(returnTime) - 1);
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
        //Printing the previous inputs as much as possible.
        clearScreen();
        System.out.println(TEXT_BLUE + "Departure From: " + departure + "\t\tArrival to:" + arrival);
        System.out.println(dateForFlight);
        System.out.println("selected flight number: " + flight + TEXT_RESET + "\n");
        System.out.println(TEXT_YELLOW + "Select Departure timing of your flight ( "+ departureDate + " ):"+ TEXT_RESET);
        JSONArray departureTime = (JSONArray) flightDB.get(Integer.parseInt(flight) - 1).time.get("departure");
        JSONArray arrivalTime = (JSONArray) flightDB.get(Integer.parseInt(flight) - 1).time.get("arrival");
        //printing the time based on the flight selection.
        for (int i = 0; i < departureTime.size(); i++)
            System.out.println((i + 1) + " >>>> The Flight left the airport at: " + departureTime.get(i) + ",And reach your Destinaition at: " + arrivalTime.get(i)+".");
        System.out.println(" * Select One Of The Option Above:");
        do {//verifying the input.
            time = scanner.next();
            try {
                if ((Integer.parseInt(time)) <= departureTime.size() && Integer.parseInt(time) != 0) {
                    time = "Date: "+(departureDate) + ", Departure time at your location: " + departureTime.get(Integer.parseInt(time) - 1) + ",Arrival time to your destination: " + arrivalTime.get(Integer.parseInt(time) - 1);
                    System.out.println(time);
                    response = "Time Occurred";
                    if (tripType.equals("RoundTrip"))
                        setReturnTime();
                    else {
                        userDetailsAsInput();
                        break;
                    }
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
        System.out.println(TEXT_BLUE +"Departure From: " + departure + "\t\tArrival to:" + arrival);
        System.out.println(dateForFlight);
        System.out.println("Flight Class: " + flightClass);
        System.out.println(TEXT_YELLOW + "\nHere is the list of flight available:\n" + TEXT_RESET);
        boolean flightFound = false;
        for (int i = 0; i < flightDB.size(); i++) {
            if (departure.equals(flightDB.get(i).departureLocation) && arrival.equals(flightDB.get(i).arrivalLocation)) {
                System.out.println(TEXT_GREEN + "\nSelect " + (i + 1) + " for the flight below:" + TEXT_RESET);
                System.out.println(TEXT_GREEN + "--------------------------------------------" + TEXT_RESET);
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
                System.out.println(TEXT_RED+"____________________________________________\n"+TEXT_RESET);
            }
        }
        System.out.println("\nSelect One Of The Options Above:");
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
        System.out.println(TEXT_BLUE + "Departure From: " + departure + "\t\tArrival to:" + arrival);
        dateForFlight = (tripType.equals("RoundTrip")) ? "Departure Date: " + departureDate + " " + "Return Date: " + returnDate : "Departure Date: " + departureDate;
        System.out.println(dateForFlight + TEXT_RESET);
        System.out.println(TEXT_YELLOW + "\nSelect Class: " + TEXT_RESET +"\n1. Business\n2. First\n3. Second\nSelect One Of The Options Above:");
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
        System.out.println("--------------- In First Class ---------------");
        System.out.println("Meals in First Class: " + flightDB.get(i).inFirstClass.get("meals"));
        System.out.println("Check In(Extra): " + flightDB.get(i).inFirstClass.get("checkInExtra") + "Kg MAX.");
        System.out.println("Price for Single person: " + flightDB.get(i).inFirstClass.get("price"));
        System.out.println("No of seats available: " + flightDB.get(i).inFirstClass.get("noOfSeatsAvailableInFirstClass"));
    }

    //Method to print the business class values.
    private void businessClass(int i) {
        System.out.println("-------------- In Business Class -------------");
        System.out.println("Meals in Business Class: " + flightDB.get(i).inBusinessClass.get("meals"));
        System.out.println("Check In(Extra): " + flightDB.get(i).inBusinessClass.get("checkInExtra") + "Kg MAX.");
        System.out.println("Price for Single person: " + flightDB.get(i).inBusinessClass.get("price"));
        System.out.println("No of seats available: " + flightDB.get(i).inBusinessClass.get("noOfSeatsAvailableInBusinessClass"));
    }

    //Method to print the second class values.
    private void secondClass(int i) {
        System.out.println("--------------- In Second Class --------------");
        System.out.println("Meals in Second Class: " + flightDB.get(i).inSecondClass.get("meals"));
        System.out.println("Check In(Extra): " + flightDB.get(i).inSecondClass.get("checkInExtra") + "Kg MAX.");
        System.out.println("Price for Single person: " + flightDB.get(i).inSecondClass.get("price"));
        System.out.println("No of seats available: " + flightDB.get(i).inSecondClass.get("noOfSeatsAvailableInSecondClass"));
    }

    //Method to set the data for the flight.
    void setDate() {
        clearScreen();
        System.out.println(TEXT_BLUE + "Departure From : " + departure + "\t\tArrival to :" + arrival + TEXT_RESET);
        System.out.println(TEXT_YELLOW + "\nEnter Departure Date :" + TEXT_RESET + "\n(dd-mm-yyyy)\nExample: 10-03-2022");
        do {
            try {
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
            }catch(Exception e){
                response = "Enter a valid date in the format of (dd-mm-yyyy)\nExample: 10-03-2022";
            }
            System.out.println(response);
        } while (response.contains("Enter a valid date"));
    }

    // Method to get the return date from the user based on the trip type.
    void setReturnDate() throws java.text.ParseException {
        clearScreen();
        System.out.println(TEXT_BLUE +"Departure From : " + departure + "\t\tArrival to :" + arrival + TEXT_RESET);
        System.out.println(TEXT_BLUE + "Departure date : " + departureDate + TEXT_YELLOW+ "\n\nEnter Return Date :"+ TEXT_RESET+"\n(dd-mm-yyyy)\nExample: 10-03-2022" );
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
        System.out.println(TEXT_YELLOW + "Select Destination Location \nTo:" + TEXT_RESET);
        for (FlightDataBase flightDataBase : flightDB) {
            if (!(listOfCities.contains((flightDataBase.arrivalLocation))) && !departure.equals((flightDataBase.arrivalLocation)))
                listOfCities.add((flightDataBase.arrivalLocation));
        }
        for (int i = 0; i < listOfCities.size(); i++)
            System.out.println((i + 1) + " >>>>> " + listOfCities.get(i));
        System.out.println("Select One Of The Options Above:");
        do {
            arrival = scanner.next();
            try {
                response = (Integer.parseInt(arrival) <= listOfCities.size() && Integer.parseInt(arrival) > 0) ? "Arrival selected." : "Invalid Input try again.";
                if (response.equals("Arrival selected.")) {
                    arrival = listOfCities.get(Integer.parseInt(arrival) - 1);
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
        System.out.println(TEXT_YELLOW + "Select Your Location\nFrom:" + TEXT_RESET);
        for (FlightDataBase flightDataBase : flightDB) {
            if (!(listOfCities.contains((flightDataBase.departureLocation))))
                listOfCities.add((flightDataBase.departureLocation));
        }
        for (int i = 0; i < listOfCities.size(); i++)
            System.out.println((i + 1) + " >>>>> " + listOfCities.get(i));
        System.out.println("Select One Of The Options Above:");
        do {
            departure = scanner.next();
            try {
                response = (Integer.parseInt(departure) <= flightDB.size() && Integer.parseInt(departure) > 0) ? "Departure selected." : "Invalid Input try again.";
                if (response.equals("Departure selected.")) {
                    departure = listOfCities.get(Integer.parseInt(departure) - 1);
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
        System.out.println(TEXT_YELLOW + "\nSelect the trip type:" + TEXT_RESET);
        System.out.println("1 >>>>> Oneway\n2 >>>>> RoundTrip\nSelect One of the option above:");
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

    public void initiateUserInputs(int i) {
        this.isLoggedIn = userDBOutput.get(i).isLoggedIn();
        this.userName = userDBOutput.get(i).getUserName();
        this.age = userDBOutput.get(i).getAge();
        this.gender = userDBOutput.get(i).getGender();
        this.mobileNumber = userDBOutput.get(i).getMobileNumber();
        this.mailId = userDBOutput.get(i).getEMail();
        userNo = i;
        setSelectTripType();
    }

}