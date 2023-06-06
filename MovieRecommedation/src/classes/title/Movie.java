package classes.title;

import classes.cast.Actor;
import classes.cast.Director;
import classes.user.Preference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Movie extends Title {
    private List<Actor> actors;

    private Director director;
    private int releaseYear;

    private List<Review> reviews;


    public Movie() {
        this.actors = new ArrayList<>();
        this.director = null;
        this.releaseYear = 0;
        this.reviews = new ArrayList<>();
    }

    public Movie(int id, String title, List<String> genre, String language, List<Actor> actors, Director director, int releaseYear, List<Review> reviews) {
        super(id, title, genre, language);
        this.actors = actors;
        this.director = director;
        this.releaseYear = releaseYear;
        this.reviews = reviews;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public int CalculateMovieRating(){
        int R=0;
        int rating=0;
        if(this.reviews.size()!=0){
            for(Review review:this.reviews){
                R+=review.getRating();
            }
            rating = Math.abs(R/(reviews.size()));
        }
        return rating;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nMovie ").append(this.getTitle()).append(" {\n")
                .append("Genre: \n");
        for(String s: this.getGenre()){
            sb.append("\t").append(s).append("\n");
        }
        sb.append("Release year: ").append(this.releaseYear).append("\n")
                .append("Language: ").append(this.getLanguage()).append("\n")
                .append("Actors: \n");
        for(Actor actor: this.actors){
            sb.append("\t").append(actor.getFirstName()).append(" ").append(actor.getLastName()).append("\n");
        }
        sb.append("Director: ").append(this.director.getFirstName()).append(" ").append(director.getLastName()).append("\n")
                .append("Rating: ").append(CalculateMovieRating()).append("\n")
                .append("Reviews: \n");
        for(Review review: this.reviews){
            sb.append("\t").append(review.toString()).append("\n");
        }

        return sb.toString();
    }

    public boolean matchesMoviePreference(Preference preference){
        if (!Objects.equals(preference.getGenre(), "") && !this.getGenre().contains(preference.getGenre())) {
            return false;
        }
        if (preference.getReleaseYear()!=0 && (releaseYear < preference.getReleaseYear() || releaseYear> preference.getReleaseYear()+2)) {
            return false;
        }
        if (!Objects.equals(preference.getLanguage(), "") && !this.getLanguage().equals(preference.getLanguage())) {
            return false;
        }
        if(preference.getDirector()!=null && preference.getDirector()!=director){
            return false;
        }
        if (preference.getActor()!=null && !actors.contains(preference.getActor())) {
            return false;
        }
        return true;
    }

}
