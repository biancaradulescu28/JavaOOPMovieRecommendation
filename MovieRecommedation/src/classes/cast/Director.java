package classes.cast;

import java.util.Objects;

public class Director extends CastMember {
    public Director() {
    }

    public Director(int castId, String firstName, String lastName) {
        super(castId, firstName, lastName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Director ").append(getFirstName()).append(" ");
        if(Objects.equals(getLastName(), "no")){
            sb.append(" ");
        }
        else{
            sb.append(getLastName());
        }
        return sb.toString();
    }
}