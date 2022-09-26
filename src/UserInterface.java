import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class UserInterface extends DataFromUser {
 private Scanner scanner = new Scanner(System.in);

    void firstPageOfTheAirLines(){
        clearScreen();
        System.out.println("Welcome to C1ph3R Airlines");
        setSelectTripType();

    }
    UserInterface() throws IOException, ParseException {
        flightDataBase();
        firstPageOfTheAirLines();
    }
}
