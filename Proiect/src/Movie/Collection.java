package Movie;

import User.Preference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Collection extends Item{
    private int numberMovies;
    private List<Movie> moviesInCollection;

    public Collection() {
        this.numberMovies = 0;
        this.moviesInCollection = new ArrayList<>();
    }

    public Collection(int id, String title, List<String> genre, String language, int numberMovies, List<Movie> moviesInCollection) {
        super(id, title, genre, language);
        this.numberMovies = numberMovies;
        this.moviesInCollection = moviesInCollection;
    }

    public void setMoviesInCollection(List<Movie> moviesInCollection) {
        this.moviesInCollection = moviesInCollection;
    }
    public int getNumberMovies() {
        return numberMovies;
    }

    public void setNumberMovies(int numberMovies) {
        this.numberMovies = numberMovies;
    }
    public List<Movie> getMoviesInCollection() {
        return moviesInCollection;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\nCollection ").append(this.getTitle()).append("\n")
                .append("Genre: \n");
        for(String s: this.getGenre()){
            sb.append("\t").append(s).append("\n");
        }
                sb.append("Language: ").append(this.getLanguage()).append("\n")
                .append("Movies: ").append("\n");
        for (Movie movie : this.moviesInCollection) {
            sb.append("\t").append(movie.getTitle()).append("\n");
        }
        return sb.toString();
    }

    public boolean matchesCollectionPreference(Preference preference){
        if (!Objects.equals(preference.getGenre(), "") && !this.getGenre().contains(preference.getGenre())) {
            return false;
        }
        if (!Objects.equals(preference.getLanguage(), "") && !this.getLanguage().equals(preference.getLanguage())) {
            return false;
        }
        if(preference.getReleaseYear()!=0){
            int ok=0;
            for(Movie movie: moviesInCollection){
                if(movie.getReleaseYear()>= preference.getReleaseYear() && movie.getReleaseYear()<= preference.getReleaseYear()+2){
                    ok=1;
                    break;
                }
            }
            if(ok==0){
                return false;
            }
        }
        if(preference.getDirector()!=null){
            int ok=0;
            for(Movie movie: moviesInCollection){
                if(preference.getDirector()==movie.getDirector()){
                    ok=1;
                    break;
                }
            }
            if(ok==0){
                return false;
            }

        }
        if(preference.getActor()!=null){
            int ok=0;
            for(Movie movie: moviesInCollection){
                if(movie.getActors().contains(preference.getActor())){
                    ok=1;
                    break;
                }
            }
            if(ok==0){
                return false;
            }
        }
        return true;

    }

}
