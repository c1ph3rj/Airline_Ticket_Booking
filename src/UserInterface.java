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
        flightDataBase();
    }

    public void isUserLoggedIn() throws IOException, ParseException {
       try{
           userDataBase();
           boolean userAlreadyLoggedIn = false;
           for (int i = 0; i < userDBOutput.size(); i++) {
               if (userDBOutput.get(i).isLoggedIn()) {
                   clearScreen();
                   System.out.println(TEXT_CYAN + "Welcome Back " + userDBOutput.get(i).getUserName().toUpperCase() + TEXT_RESET);
                   optionsToTheUsersAfterLogin(i);
                   userAlreadyLoggedIn = true;
               }
           }
           if (!userAlreadyLoggedIn) {
               optionsToTheUsersBeforeLogin();
           }
       }catch (Exception e){
           System.out.println(e.toString());
       }
    }

    void userLogin() throws IOException, ParseException {
        clearScreen();
        userDataBase();
        System.out.println("Welcome to C1ph3R Airlines");
        System.out.println(TEXT_YELLOW + "Enter your User Name:" + TEXT_RESET);
        String userName;
        String password;
        boolean userVerified = false;
        do {
            userName = scanner.next();
            for(int i =0;i<userDBOutput.size();i++){
                if(userName.equals(userDBOutput.get(i).getUserName())){
                    clearScreen();
                    System.out.println(TEXT_CYAN + "Welcome "+ userDBOutput.get(i).getUserName()+ " To C1ph3R Airlines."+ TEXT_YELLOW +"\nEnter password:" + TEXT_RESET +
                            "\n(Note: Only 3 attempts)");
                    int noOfAttempts = 3;
                    while (noOfAttempts >= 1) {
                        password = scanner.next();
                        if (password.equals(userDBOutput.get(i).getPassword())) {
                            updateUserDataBase(true,i);
                            userDataBase();
                            optionsToTheUsersAfterLogin(i);
                        } else if(password.isEmpty())
                            System.out.println(TEXT_RED + "Password cannot be empty."+ TEXT_RESET);
                        else {
                            System.out.println(((noOfAttempts == 1) ? TEXT_RED + "Maximum Attempts reached.\nThank you!" + TEXT_RESET:
                                    TEXT_RED + "wrong Password!,Try again." + TEXT_RESET +"\nno.of. attempts left:" +(noOfAttempts -1)));
                            noOfAttempts--;
                        }
                    }
                 userVerified = true;
                }else userVerified = false;
            }
            if(!userVerified){
                response = TEXT_RED + "No users found." +TEXT_RESET;
                System.out.println(response);
            }
        }while (response.contains("No users found.") );
    }

    void optionsToTheUsersAfterLogin(int i) throws IOException, ParseException {
        System.out.println("1 >>>> Search For Flight.\n2 >>>> Logout.\nSelect one of the option above:");
        do{
        response = scanner.next();
        if(response.equals("1")){
            initiateUserInputs(i);
            userDataBase();
            isUserLoggedIn();
        }else if(response.equals("2")){
            System.out.println("Logged Out SuccessFully");
            updateUserDataBase(false, i );
            optionsToTheUsersBeforeLogin();
        }else {
            response = "invalid option.";
            System.out.println(response);
        }
        }while(response.equals("invalid option."));
    }
    void optionsToTheUsersBeforeLogin() throws IOException, ParseException {
        clearScreen();
        System.out.println("Welcome to C1ph3R Airlines:\n1 >>>>> Already an user, please Login.\n2 >>>>> New user, please register to continue.\n3 >>>>> to quit.\nSelect one of the option above:");
        do{
            response = scanner.next();
            if(response.equals("1")){
                userLogin();
            }else if(response.equals("2")){
                registerNewAccount();
                userLogin();
            }else if(response.equals("3"))
                System.exit(1);
            else {
                response = "invalid option.";
                System.out.println(response);
            }
        }while(response.equals("invalid option."));


    }

    void firstPageOfTheAirLines() throws IOException, ParseException {
        new UserInterface();
        isUserLoggedIn();
    }
}
