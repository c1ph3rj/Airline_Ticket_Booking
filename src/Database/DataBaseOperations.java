package Database;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseOperations {
    public final List<FlightDataBase> flightDB = new ArrayList<>();
    JSONArray DB;
    public List<userDataBase> userDBOutput = new ArrayList<>();
    JSONArray userAccountDetails;

    public void flightDataBase() throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader("src/Database/flightDataBase.json"));
        DB = (JSONArray) obj;
        for (Object item : DB) {
            flightDB.add(new FlightDataBase((String) ((JSONObject) item).get("flightName"), ((Long) ((JSONObject) item).get("noOfSeats")).intValue(), (String) ((JSONObject) item).get("departureLocation"), (String) ((JSONObject) item).get("totalDuration"), (JSONObject) ((JSONObject) item).get("time"), (String) ((JSONObject) item).get("arrivalLocation"), ((Long) ((JSONObject) item).get("checkInFlight")).intValue(), (JSONObject) ((JSONObject) item).get("inFirstClass"), (JSONObject) ((JSONObject) item).get("inSecondClass"), (JSONObject) ((JSONObject) item).get("inBusinessClass")));
        }
    }

    public void userDataBase() throws IOException, ParseException {
        Object userObj = new JSONParser().parse(new FileReader("src/Database/userDataBase.json"));
        userAccountDetails = (JSONArray) userObj;
        for (Object item : userAccountDetails) {
            userDBOutput.add(new userDataBase((boolean) ((JSONObject) item).get("isLogin"),(String) ((JSONObject) item).get("userName"), (String) ((JSONObject) item).get("emailId"), (String) ((JSONObject) item).get("mobileNo"), ((Long) ((JSONObject) item).get("age")).intValue(), (String) ((JSONObject) item).get("gender"), (JSONObject) ((JSONObject) item).get("detailsOfTheFLight")));

        }
    }

    void updateUserDataBase(String userName, String mailId, String mobileNumber, int age, String gender, JSONObject flightObj) throws IOException {
        FileWriter fileWriter = new FileWriter("src/Database/userDataBase.json");
        JSONObject obj = new JSONObject();
        obj.put("userName", userName);
        obj.put("emailId", mailId);
        obj.put("mobileNo", mobileNumber);
        obj.put("age", age);
        obj.put("gender", gender);
        obj.put("detailsOfTheFLight", flightObj);
        userAccountDetails.add(userDBOutput.size(), obj);
        fileWriter.write(userAccountDetails.toJSONString());
        fileWriter.close();
    }

}
