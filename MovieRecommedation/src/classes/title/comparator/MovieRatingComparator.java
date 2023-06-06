package classes.title.comparator;

import classes.title.Movie;

import java.util.Comparator;

public class MovieRatingComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie m1, Movie m2) {
        if (m1.CalculateMovieRating() < m2.CalculateMovieRating()) {
            return 1;
        } else if (m1.CalculateMovieRating() > m2.CalculateMovieRating()) {
            return -1;
        } else {
            return 0;
        }
    }

}