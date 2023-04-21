package Cast;

import Movie.Movie;
import Movie.Item;

import java.util.*;

public class Actor extends CastMember {
    public Actor() {}

    public Actor(String firstName, String lastName, int castId) {
        super(firstName, lastName, castId);
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Actor ").append(getFirstName()).append(" ");
        if(getLastName()=="no"){
            sb.append(" ");
        }
        else{
            sb.append(getLastName());
        }
        return sb.toString();
    }
}
