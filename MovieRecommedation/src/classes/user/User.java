package classes.user;

import java.util.*;
import classes.title.Title;
import classes.title.Review;

public class User {
    private String username,firstName, lastName;
    private String email, password;
    private Preference preference;



    public User(){
        this.username = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
        this.preference = null;
    }

    public User(String username, String firstName, String lastName, String email, String password, Preference preference) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.preference = preference;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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



    @Override
    public String toString() {
        return "\n" +
                "Username: " + this.username + "\n" +
                "FirstName: " + this.firstName + "\n" +
                "LastName: " + this.lastName + "\n" +
                "Email: " + this.email + "\n" +
                "Password: " + this.password + "\n";
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (!Objects.equals(this.username, other.username))
            return false;
        if (!Objects.equals(this.password, other.password))
            return false;
        return true;
    }
}