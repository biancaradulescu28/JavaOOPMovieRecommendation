package User;

import Movie.Item;
import Movie.Review;

import java.util.*;

public class User {
    private String username,firstName, lastName;
    private String email, password;
    private List<Review> userReviews;
    private Preference preference;
    private List<Item>WatchHistory;
    private List<Item>WatchLater;
    private List<Item>Favorites;



    public User(){
        this.username = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
        this.userReviews =new ArrayList<Review>();
        this.preference = null;
        this.WatchHistory = new ArrayList<>();
        this.WatchLater = new ArrayList<>();
        this.Favorites = new ArrayList<>();
    }

    public User(String username, String firstName, String lastName, String email, String password, List<Review> userReviews, Preference preference, List<Item> watchHistory, List<Item> watchLater, List<Item> favorites) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userReviews = userReviews;
        this.preference = preference;
        WatchHistory = watchHistory;
        WatchLater = watchLater;
        Favorites = favorites;
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

    public List<Review> getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(List<Review> userReviews) {
        this.userReviews = userReviews;
    }

    public List<Item> getWatchHistory() {
        return WatchHistory;
    }

    public void setWatchHistory(List<Item> watchHistory) {
        WatchHistory = watchHistory;
    }

    public List<Item> getWatchLater() {
        return WatchLater;
    }

    public void setWatchLater(List<Item> watchLater) {
        WatchLater = watchLater;
    }

    public List<Item> getFavorites() {
        return Favorites;
    }

    public void setFavorites(List<Item> favorites) {
        Favorites = favorites;
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
