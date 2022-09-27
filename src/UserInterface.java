import Database.UserInputs;
import org.json.simple.parser.ParseException;

import java.io.IOException;


public class UserInterface extends UserInputs {

   UserInterface() throws IOException, ParseException {
       userDataBase();
       flightDataBase();
   }

    void isUserLoggedIn() throws IOException, ParseException {
        boolean userAlreadyLoggedIn = false;
        for(int i = 0; i<userDBOutput.size();i++){
            if(userDBOutput.get(i).isLoggedIn()){
                clearScreen();
                System.out.println("Welcome Back " + userDBOutput.get(i).getUserName());
                userAlreadyLoggedIn = true;
                initiateUserInputs(i);
                break;
            }
        }
        if(!userAlreadyLoggedIn) {
            clearScreen();
            System.out.println("Welcome to C1ph3R Airlines");
            setSelectTripType();
        }
    }

    void firstPageOfTheAirLines() throws IOException, ParseException {
        isUserLoggedIn();
    }
}
