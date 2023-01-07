package tvclasses.databaseclasses;

import movieclasses.Movie;

import java.util.Comparator;

public final class MovieCompDecrIncr implements Comparator<Movie> {
    /**
     * Comparator used to sort the movie array for when rate is increasing
     * and duration is decreasing.
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return if the objects are respecting the order or not
     */
    @Override
    public int compare(final Movie o1, final Movie o2) {
        final double rate1 = o1.getRating();
        final double rate2 = o2.getRating();
        final int dur1 = o1.getDuration();
        final int dur2 = o2.getDuration();
        if (dur1 == dur2) {
            return Double.compare(rate2, rate1);
        } else if (dur2 < dur1) {
            return 1;
        } else {
            return -1;
        }

    }
}
