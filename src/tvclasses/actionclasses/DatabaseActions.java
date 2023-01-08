package tvclasses.actionclasses;

import inputclasses.MovieInput;
import movieclasses.Movie;
import tvclasses.TV;
import tvclasses.databaseclasses.MovieDatabase;
import tvclasses.databaseclasses.UsersDatabase;

public final class DatabaseActions {
    /**
     * Adds a new movie to the database.
     * @param movie the new movie we want to add
     * @param tv program main variable
     */
    public void addMovie(final MovieInput movie, final TV tv) {
        Movie newMovie = new Movie(movie);
        Movie auxMovie = MovieDatabase.getInstance().getMovieByName(movie.getName());
        if (auxMovie != null) {
            tv.getHomepageAuth().createOutputError(tv);
            return;
        }
        MovieDatabase.getInstance().addMovie(newMovie);
    }

    /**
     * Deletes a movie from the database and updates users.
     * @param movieName the movie we want to remove
     * @param tv program main variable
     */
    public void removeMovie(final String movieName, final TV tv) {
        Movie removedMovie = MovieDatabase.getInstance().getMovieByName(movieName);
        if (removedMovie == null) {
            tv.getHomepageAuth().createOutputError(tv);
            return;
        }
        MovieDatabase.getInstance().deleteMovie(removedMovie);
        UsersDatabase.getInstance().updateUsers(removedMovie);
    }
}
