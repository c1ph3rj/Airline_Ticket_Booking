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

    private String userName;
    private String eMail;
    private String mobileNumber;
    private int age;
    private String gender;
    private JSONObject detailsOfTheFLight;
    public userDataBase(String userName, String eMail, String mobileNumber, int age, String gender, JSONObject detailsOfTheFLight) {
        this.userName = userName;
        this.eMail = eMail;
        this.mobileNumber = mobileNumber;
        this.age = age;
        this.gender = gender;
        this.detailsOfTheFLight = detailsOfTheFLight;
    }
}