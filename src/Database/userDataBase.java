package Database;

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

    public JSONObject getDetailsOfTheFLight() {
        return detailsOfTheFLight;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }


    private boolean isLoggedIn;
    private String userName;
    private String eMail;
    private String mobileNumber;
    private int age;
    private String gender;
    private String password;
    private JSONObject detailsOfTheFLight;
    public userDataBase(boolean isLoggedIn, String userName,String password, String eMail, String mobileNumber, int age, String gender, JSONObject detailsOfTheFLight) {
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
