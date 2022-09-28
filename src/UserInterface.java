import Database.UserInputs;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserInterface extends UserInputs {
    private String response = "";
    private Scanner scanner = new Scanner(System.in);
    UserInterface() throws IOException, ParseException {
        userDataBase();
        flightDataBase();
    }

    public void isUserLoggedIn() throws IOException, ParseException {
        userDataBase();
        boolean userAlreadyLoggedIn = false;
        for (int i = 0; i < userDBOutput.size(); i++) {
            if (userDBOutput.get(i).isLoggedIn()) {
                clearScreen();
                System.out.println("Welcome Back " + userDBOutput.get(i).getUserName());
                optionsToTheUsers(i);
                userAlreadyLoggedIn = true;
            }
        }
        if (!userAlreadyLoggedIn) {
            userLogin();
        }
    }

    void userLogin() throws IOException, ParseException {
        clearScreen();
        System.out.println("Welcome to C1ph3R Airlines");
        System.out.println("Enter your User Name:");
        String userName;
        String password;
        boolean userVerified = false;
        int noOfAttempts = 3;
        do {
            userName = scanner.next();
            for(int i =0;i<userDBOutput.size();i++){
                if(userName.equals(userDBOutput.get(i).getUserName())){
                    clearScreen();
                    System.out.println("Welcome "+ userDBOutput.get(i).getUserName());
                    while (noOfAttempts >= 1) {
                        password = scanner.next();
                        if (password.equals(userDBOutput.get(i).getPassword())) {
                            clearScreen();
                            updateUserDataBase(true,i);
                            isUserLoggedIn();
                        } else if(password.isEmpty())
                            System.out.println("Password cannot be empty.");
                        else {
                            System.out.println(((noOfAttempts == 3) ? "Maximum Attempts reached.\nThank you!" :
                                    "wrong Password!,Try again." + "\nno.of. attempts left:" +noOfAttempts));
                            noOfAttempts--;
                        }
                    }
                 userVerified = true;
                }
            }
            if(!userVerified){
                response = "No users found.";
                System.out.println(response);
            }
        }while (response.contains("No users found.") );
    }

    void optionsToTheUsers(int userId) throws IOException, ParseException {
        System.out.println("1 >>>> Search For Flight.\n2 >>>> Logout.");
        response = scanner.next();
        if(response.equals("1")){
            setSelectTripType();
            isUserLoggedIn();
        }else if(response.equals("2")){
            System.out.println("Logged Out SuccessFully");
            updateUserDataBase(false,userId );
            isUserLoggedIn();
        }else
            response = "invalid option.";
    }


    void firstPageOfTheAirLines() throws IOException, ParseException {

        new UserInterface();
        isUserLoggedIn();
    }
}
