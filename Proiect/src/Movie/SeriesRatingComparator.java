package Movie;

import java.util.Comparator;

public class SeriesRatingComparator implements Comparator<Series> {
    @Override
    public int compare(Series s1, Series s2) {
        if (s1.CalculateSeriesRating() < s2.CalculateSeriesRating()) {
            return 1;
        } else if (s1.CalculateSeriesRating() > s2.CalculateSeriesRating()) {
            return -1;
        } else {
            return 0;
        }
    }
}
