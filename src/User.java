public class User {
    private int userID;
    private String fullName;
    private String username;
    private String email;
    private String password;

    // Constructor
    public User(int userID, String fullName, String username, String email, String password) {
        this.userID = userID;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    public User(String fullName, String username, String email, String password) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getter and Setter methods for each attribute

    public int getUserID() {
        return userID;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Override toString method for easy printing
    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

