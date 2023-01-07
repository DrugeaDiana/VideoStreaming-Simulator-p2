package tvclasses.databaseclasses;

import movieclasses.Movie;

import java.util.Comparator;

public final class MovieCompRatingDecr implements Comparator<Movie> {
    /**
     * Comparator used to sort the movie array for when rate is decreasing
     * and duration is increasing.
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return if the objects are respecting the order or not
     */
    @Override
    public int compare(final Movie o1, final Movie o2) {
        final double rate1 = o1.getRating();
        final double rate2 = o2.getRating();
        return Double.compare(rate2, rate1);
    }
}