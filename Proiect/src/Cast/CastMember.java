package Cast;

import java.util.Objects;

public class CastMember {

    private String firstName, lastName;
    private int castId;

    public CastMember(){
        this.firstName = "";
        this.lastName = "";
        this.castId = 0;
    }
    public CastMember(String firstName, String lastName, int castId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.castId = castId;
    }

    public int getCastId() {
        return castId;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CastMember other = (CastMember) obj;
        if (!Objects.equals(this.firstName, other.firstName))
            return false;
        if (!Objects.equals(this.lastName, other.lastName))
            return false;
        return true;
    }
    @Override
    public int hashCode() {
        return Objects.hash(firstName,lastName);
    }


}
