package tvclasses.actionclasses;

import inputclasses.MovieInput;
import movieclasses.Movie;
import tvclasses.TV;
import tvclasses.databaseclasses.MovieDatabase;
import tvclasses.databaseclasses.UsersDatabase;

public class DatabaseActions {
    public void addMovie(MovieInput movie, TV tv) {
        Movie newMovie = new Movie(movie);
        Movie auxMovie = MovieDatabase.getInstance().getMovieByName(movie.getName());
        if (auxMovie != null) {
            tv.getHomepageAuth().createOutputError(tv);
            return;
        }
        MovieDatabase.getInstance().addMovie(newMovie);
    }

    public void removeMovie(String movieName, TV tv) {
        Movie removedMovie = MovieDatabase.getInstance().getMovieByName(movieName);
        if (removedMovie == null) {
            tv.getHomepageAuth().createOutputError(tv);
            return;
        }
        MovieDatabase.getInstance().deleteMovie(removedMovie);
        UsersDatabase.getInstance().updateUsers(removedMovie);
    }
}
