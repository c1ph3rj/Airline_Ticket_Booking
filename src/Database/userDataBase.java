package Database;

import org.json.simple.JSONObject;

public class userDataBase {
    private final String userName;
    private final String eMail;
    private final String mobileNumber;
    private final int age;
    private final String gender;
    private final JSONObject detailsOfTheFLight;
    public userDataBase(String userName, String eMail, String mobileNumber, int age, String gender, JSONObject detailsOfTheFLight) {
        this.userName = userName;
        this.eMail = eMail;
        this.mobileNumber = mobileNumber;
        this.age = age;
        this.gender = gender;
        this.detailsOfTheFLight = detailsOfTheFLight;
    }

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
}
