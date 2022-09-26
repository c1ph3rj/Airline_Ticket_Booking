import org.json.simple.JSONObject;

public class UserDetails {
    private String firstName;
    private String lastName;
    private String password;
    private int age;
    private String gender;
    private boolean physicallyAble;
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public boolean isPhysicallyAble() {
        return physicallyAble;
    }

    public void setDetailsOfTheBooking(JSONObject detailsOfTheBooking) {
        this.detailsOfTheBooking = detailsOfTheBooking;
    }

    private JSONObject detailsOfTheBooking;
    public UserDetails(String firstName, String lastName, String password, int age, String gender, boolean physicallychallenged) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.physicallyAble = physicallychallenged;
    }
}
