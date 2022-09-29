package Database;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class userDataBase {
    public String getUserName() {
        return userName;
    }

    public String getEMail() {
        return eMail;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public JSONArray getDetailsOfTheFLight() {
        return detailsOfTheFLight;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }


    private final boolean isLoggedIn;
    private final String userName;
    private final String eMail;
    private final String mobileNumber;
    private final int age;
    private final String gender;
    private final String password;
    private final JSONArray detailsOfTheFLight;

    public userDataBase(boolean isLoggedIn, String userName, String password, String eMail, String mobileNumber, int age, String gender, JSONArray detailsOfTheFLight) {
        this.isLoggedIn = isLoggedIn;
        this.userName = userName;
        this.password = password;
        this.eMail = eMail;
        this.mobileNumber = mobileNumber;
        this.age = age;
        this.gender = gender;
        this.detailsOfTheFLight = detailsOfTheFLight;
    }

    public String getPassword() {
        return password;
    }
}
