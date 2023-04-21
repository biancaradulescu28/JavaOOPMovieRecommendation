package Movie;

import java.util.Comparator;

public class MovieIdComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie m1, Movie m2) {
        return Integer.compare(m1.getId(), m2.getId());
    }

}
