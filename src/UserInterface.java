import Database.UserInputs;
import org.json.simple.parser.ParseException;

import java.io.IOException;


public class UserInterface extends UserInputs {

    UserInterface() throws IOException, ParseException {
        super();
        firstPageOfTheAirLines();
    }

    void firstPageOfTheAirLines() throws IOException {
        clearScreen();
        System.out.println("Welcome to C1ph3R Airlines");
        setSelectTripType();

    }
}
