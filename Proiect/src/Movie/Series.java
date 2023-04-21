package Movie;

import Cast.Actor;
import Cast.Director;
import User.Preference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Series extends Item{
    private List<Actor> actorsS;
    private Director directorS;
    private int releaseYearS;
    private int finishYear;
    private int nrSeasons;
    private List<Review> reviewsS;


    public Series() {
        this.actorsS = new ArrayList<>();
        this.directorS = null;
        this.releaseYearS = 0;
        this.finishYear=0;
        this.nrSeasons = 0;
        this.reviewsS = new ArrayList<>();
    }

    public Series(int id, String title, List<String> genre, String language, List<Actor> actorsS, Director directorS, int releaseYearS, int finishYear, int nrSeasons, List<Review> reviewsS) {
        super(id, title, genre, language);
        this.actorsS = actorsS;
        this.directorS = directorS;
        this.releaseYearS = releaseYearS;
        this.finishYear = finishYear;
        this.nrSeasons = nrSeasons;
        this.reviewsS = reviewsS;
    }

    public int getNrSeasons() {
        return nrSeasons;
    }

    public void setNrSeasons(int nrSeasons) {
        this.nrSeasons = nrSeasons;
    }

    public List<Actor> getActorsS() {
        return actorsS;
    }

    public void setActorsS(List<Actor> actorsS) {
        this.actorsS = actorsS;
    }

    public Director getDirectorS() {
        return directorS;
    }

    public void setDirectorS(Director directorS) {
        this.directorS = directorS;
    }

    public int getReleaseYearS() {
        return releaseYearS;
    }

    public void setReleaseYearS(int releaseYearS) {
        this.releaseYearS = releaseYearS;
    }

    public int getFinishYear() {
        return finishYear;
    }

    public void setFinishYear(int finishYear) {
        this.finishYear = finishYear;
    }

    public List<Review> getReviewsS() {
        return reviewsS;
    }

    public void setReviewsS(List<Review> reviewsS) {
        this.reviewsS = reviewsS;
    }

    public int CalculateSeriesRating(){
        int R=0;
        int rating=0;
        if(this.reviewsS.size()!=0){
            for(Review review:this.reviewsS){
                R+=review.getRating();
            }
            rating = Math.abs(R/(reviewsS.size()));
        }
        return rating;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Series other = (Series) obj;
        if (!Objects.equals(this.getId(), other.getId()))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\nSeries ").append(this.getTitle()).append(" {\n")
                .append("Genre: \n");
        for(String s: this.getGenre()){
            sb.append("\t").append(s).append("\n");
        }
                sb.append("Release year: ").append(this.releaseYearS).append("\n")
                        .append("Finish year: ");
        if(this.finishYear==0){
            sb.append("Present\n");
        }
        else{
            sb.append(this.finishYear).append("\n");
        }
                sb.append("Language: ").append(this.getLanguage()).append("\n")
                .append("Actors: \n");
        for(Actor actor: this.actorsS){
            sb.append("\t").append(actor.getFirstName()).append(" ").append(actor.getLastName()).append("\n");
        }
        sb.append("Director: ").append(this.directorS.getFirstName()).append(" ").append(directorS.getLastName()).append("\n")
                .append("Rating: ").append(CalculateSeriesRating()).append("\n")
                .append("Number Seasons: ").append(this.nrSeasons).append("\n")
                .append("Reviews: ");
        for(Review review: this.reviewsS){
            sb.append("\t").append(review.toString()).append("\n");
        }
        return sb.toString();
    }


    public boolean matchesSeriesPreference(Preference preference){
        if (!Objects.equals(preference.getGenre(), "") && !this.getGenre().contains(preference.getGenre())) {
            return false;
        }
        if (preference.getReleaseYear()!=0 && (releaseYearS-2 > preference.getReleaseYear() || finishYear-2 < preference.getReleaseYear())) {
            return false;
        }
        if (!Objects.equals(preference.getLanguage(), "") && !this.getLanguage().equals(preference.getLanguage())) {
            return false;
        }
        if(preference.getDirector()!=null && preference.getDirector()!=directorS){
            return false;
        }
        if (preference.getActor()!=null && !actorsS.contains(preference.getActor())) {
            return false;
        }
        return true;
    }


}
