package Cast;

import Movie.Movie;
import Movie.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Director extends CastMember {
    public Director() {
    }

    public Director(String firstName, String lastName, int castId) {
        super(firstName, lastName, castId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Director ").append(getFirstName()).append(" ");
        if(getLastName()=="no"){
            sb.append(" ");
        }
        else{
            sb.append(getLastName());
        }
        return sb.toString();
    }
}
