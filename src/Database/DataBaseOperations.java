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

    public DataBaseOperations() {
    }

    protected void flightDataBase() throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader("src/Database/flightDataBase.json"));
        DB = (JSONArray) obj;
        for (Object item : DB) {
            flightDB.add(new FlightDataBase((String) ((JSONObject) item).get("flightName"), ((Long) ((JSONObject) item).get("noOfSeats")).intValue(), (String) ((JSONObject) item).get("departureLocation"), (String) ((JSONObject) item).get("totalDuration"), (JSONObject) ((JSONObject) item).get("time"), (String) ((JSONObject) item).get("arrivalLocation"), ((Long) ((JSONObject) item).get("checkInFlight")).intValue(), (JSONObject) ((JSONObject) item).get("inFirstClass"), (JSONObject) ((JSONObject) item).get("inSecondClass"), (JSONObject) ((JSONObject) item).get("inBusinessClass")));
        }
    }

    protected void userDataBase() {
        try {
            Object userObj = new JSONParser().parse(new FileReader("src/Database/userDataBase.json"));
            userAccountDetails = (JSONArray) userObj;
            userDBOutput = new ArrayList<>();
            for (Object item : userAccountDetails) {
                userDBOutput.add(new userDataBase((boolean) ((JSONObject) item).get("isLogin"),(String) ((JSONObject) item).get("userName"), (String) ((JSONObject) item).get("password"),(String) ((JSONObject) item).get("emailId"), (String) ((JSONObject) item).get("mobileNo"), ((Long) ((JSONObject) item).get("age")).intValue(), (String) ((JSONObject) item).get("gender"), (JSONArray) ((JSONObject) item).get("detailsOfTheFLight")));

            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    void updateUserDataBase(String userName, String mailId, String mobileNumber, int age, String gender,String password, boolean isLogin, JSONObject flightObj) throws IOException {
        JSONObject obj = new JSONObject();
        JSONArray detailsOfTheFlight = new JSONArray();
        detailsOfTheFlight.add(flightObj);
        FileWriter fileWriter = new FileWriter("src/Database/userDataBase.json");
        obj.put("isLogin", isLogin);
        obj.put("password", password);
        obj.put("userName", userName);
        obj.put("emailId", mailId);
        obj.put("mobileNo", mobileNumber);
        obj.put("age", age);
        obj.put("gender", gender);
        obj.put("detailsOfTheFLight", detailsOfTheFlight);
        userAccountDetails.add(userDBOutput.size(), obj);
        fileWriter.write(userAccountDetails.toJSONString());
        fileWriter.close();
    }

    void updateUserDataBase( int userId, JSONObject flightObj) throws IOException {
        FileWriter fileWriter = new FileWriter("src/Database/userDataBase.json");
        userDBOutput.get(userId).getDetailsOfTheFLight().add((userDBOutput.get(userId).getDetailsOfTheFLight().size()),flightObj);
        fileWriter.write(userAccountDetails.toJSONString());
        fileWriter.close();
    }
    protected void updateUserDataBase(boolean isLogin, int i) throws IOException {
        FileWriter fileWriter = new FileWriter("src/Database/userDataBase.json");
        System.out.println(i);
        ((JSONObject)userAccountDetails.get(i)).put("isLogin",isLogin);
        fileWriter.write(userAccountDetails.toJSONString());
        fileWriter.close();
    }
}
