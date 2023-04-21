package User;
import Cast.Actor;
import Cast.Director;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Preference {
    private String type;
    private String genre;
    private Actor actor;
    private Director director;
    private int releaseYear;
    private String language;

    public Preference(){
        this.type = "";
        this.genre ="";
        this.actor = null;
        this.director = null;
        this.releaseYear = 0;
        this.language = "";
    }

    public Preference(String type, String genre, Actor actor, Director director, int releaseYear, String language) {
        this.type = type;
        this.genre = genre;
        this.actor = actor;
        this.director = director;
        this.releaseYear = releaseYear;
        this.language = language;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Preference: \n");
        if(!Objects.equals(this.type, "")){
            sb.append("Type: ").append(type).append("\n");
        }
        else{
            sb.append("Genre: empty\n");
        }
        if(!Objects.equals(this.genre, "")){
            sb.append("Genre: ").append(genre).append("\n");
        }
        else{
            sb.append("Genre: empty\n");
        }
        if(!Objects.equals(this.actor, null)){
            sb.append("Actor: ").append(actor.getFirstName()).append(" ").append(actor.getLastName()).append("\n");
        }
        else{
            sb.append("Actor: empty\n");
        }
        if(!Objects.equals(this.director, null)){
            sb.append("Director: ").append(director.getFirstName()).append(" ").append(director.getLastName()).append("\n");
        }
        else{
            sb.append("Director: empty\n");
        }
        if(!Objects.equals(this.releaseYear, 0)){
            sb.append("Release year: ").append(releaseYear).append("\n");
        }
        else{
            sb.append("Release year: empty\n");
        }
        if(!Objects.equals(this.language, "")){
            sb.append("Language: ").append(language).append("\n");
        }
        else{
            sb.append("Language: empty\n");
        }
        return sb.toString();
    }
}
