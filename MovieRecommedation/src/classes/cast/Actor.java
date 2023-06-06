package classes.cast;

import java.util.*;

public class Actor extends CastMember {
    public Actor() {}

    public Actor(int castId, String firstName, String lastName) {
        super(castId, firstName, lastName);
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Actor ").append(getFirstName()).append(" ");
        if(Objects.equals(getLastName(), "no")){
            sb.append(" ");
        }
        else{
            sb.append(getLastName());
        }
        return sb.toString();
    }
}
