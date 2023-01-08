package tvclasses.databaseclasses;

import inputclasses.ContainsInput;
import inputclasses.SortingInput;
import movieclasses.Movie;
import userclasses.User;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *  Singleton pattern
 */
public final class MovieDatabase {

    private static final  MovieDatabase INSTANCE = new MovieDatabase();

    private ArrayList<AddObserver> addObservers = new ArrayList<>();
    private ArrayList<RemoveObserver> removeObservers = new ArrayList<>();

    private  MovieDatabase() { }

    public static MovieDatabase getInstance() {
        return INSTANCE;
    }

    private ArrayList<Movie> movies;

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(final ArrayList<Movie> movies) {
        this.movies = movies;
    }

    /**
     * Adds a new movie to the database and updates every AddObserver
     * @param movie new movie
     */
    public void addMovie(final Movie movie) {
        movies.add(movie);
        for (AddObserver addObserver : addObservers) {
            if (movie.getGenres().contains(addObserver.getGenre())) {
                addObserver.update(movie);
            }
        }
    }

    /**
     * Deletes the specified movie and updates the RemoveObservers
     * @param movie movie to be deleted
     */
    public void deleteMovie(final Movie movie) {
        movies.remove(movie);
        for (RemoveObserver removeObserver : removeObservers) {
            if (movie.getName().equals(removeObserver.getName())) {
                removeObserver.update(movie);
            }
        }
    }

    /**
     * Looks through the database for the specific film and returns it
     * @param name name of the film
     * @return the film variable
     */
    public Movie getMovieByName(final String name) {
        for (final Movie movie : movies) {
            if (movie.getName().equals(name)) {
                return movie;
            }
        }
        return null;
    }

    /**
     * @param name the string the name starts with
     * @param movieList list of the movies to look through
     * @return the list of movies that start with that string
     */
    public ArrayList<Movie> searchMoviesName(final String name, final ArrayList<Movie> movieList) {
        final ArrayList<Movie> foundMovies = new ArrayList<>();
        for (final Movie movie : movieList) {
            if (movie.getName().startsWith(name)) {
                foundMovies.add(movie);
            }
        }
        return foundMovies;
    }

    /**
     * @param user active user
     * @return list of movies that are not banned in the user's country of origin
     */
    public ArrayList<Movie> filterBanned(final User user) {
        final ArrayList<Movie> filteredMovies = new ArrayList<>();
        for (final Movie movie : movies) {
            if (!movie.getCountriesBanned().contains(user.getCredentials().getCountry())) {
                filteredMovies.add(movie);
            }
        }
        return filteredMovies;
    }

    /**
     * @param user the user we want to get the information about
     * @return a list of movies they haven't seen yet.
     */
    public ArrayList<Movie> filterUnseen(final User user) {
        final ArrayList<Movie> filteredMovies = filterBanned(user);
        filteredMovies.removeIf(movie -> user.getWatchedMovies().contains(movie));
        return filteredMovies;
    }

    /**
     * @param sortCrt the criteria to sort the list
     * @param filtered the list that's sorted
     */
    public void sortedMovieList(final SortingInput sortCrt, final ArrayList<Movie> filtered) {

        final String param1 = sortCrt.getRating();
        final String param2 = sortCrt.getDuration();

        if (param1 != null && param2 != null) {
            if (param1.equals("decreasing") && param2.equals("decreasing")) {
                filtered.sort(((o1, o2) -> o1.getRating() == o2.getRating() ? o2.getDuration()
                        - o1.getDuration() : (int) (o2.getRating() - o1.getRating())));
            } else if (param1.equals("increasing") && param2.equals("increasing")) {
                filtered.sort(((o1, o2) -> o1.getRating() == o2.getRating() ? o1.getDuration()
                        - o2.getDuration() : (int) (o1.getRating() - o2.getRating())));
            } else if (param1.equals("increasing") && param2.equals("decreasing")) {
                filtered.sort(new MovieCompIncDecr());
            } else if (param1.equals("decreasing") && param2.equals("increasing")) {
                filtered.sort(new MovieCompDecrIncr());
            }
        } else if (param1 != null) {
            if (param1.equals("decreasing")) {
                filtered.sort(new MovieCompRatingDecr());
            } else {
                filtered.sort((Comparator.comparingDouble(Movie::getRating)));
            }
        } else if (param2 != null) {
            if (param2.equals("decreasing")) {
                filtered.sort(((o1, o2) -> o2.getDuration() - o1.getDuration()));
            } else {
                filtered.sort((Comparator.comparingInt(Movie::getDuration)));
            }
        }
    }

    /**
     * Filters the list we're given based on a specific list of actors/genres that the movies have
     * @param containsInput input variable that has the specific criteria we're using
     * @param sortedMovie the list we're filtering
     */
    public void filterByContains(final ContainsInput containsInput,
                                  final ArrayList<Movie> sortedMovie) {
        final ArrayList<String> actors = containsInput.getActors();
        final ArrayList<String> genres = containsInput.getGenre();
        final ArrayList<Movie> toBeRemovedAc = new ArrayList<>();
        final ArrayList<Movie> toBeRemovedGen = new ArrayList<>();
        if (actors != null) {
            for (final String actor : actors) {
                for (final Movie movie : sortedMovie) {
                    if (!movie.getActors().contains(actor)) {
                        toBeRemovedAc.add(movie);
                    }
                }
            }
            sortedMovie.removeAll(toBeRemovedAc);
        }
        if (genres != null) {
            for (final String genre : genres) {
                for (final Movie movie : sortedMovie) {
                    if (!movie.getGenres().contains(genre)) {
                        toBeRemovedGen.add(movie);
                    }
                }
            }
            sortedMovie.removeAll(toBeRemovedGen);
        }
    }

    public ArrayList<AddObserver> getAddObservers() {
        return addObservers;
    }

    public void setAddObservers(final ArrayList<AddObserver> addObservers) {
        this.addObservers = addObservers;
    }

    public ArrayList<RemoveObserver> getRemoveObservers() {
        return removeObservers;
    }

    public void setRemoveObservers(final ArrayList<RemoveObserver> removeObservers) {
        this.removeObservers = removeObservers;
    }
}
