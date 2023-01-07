package tvclasses.actionclasses;

import inputclasses.MovieInput;
import movieclasses.Movie;
import tvclasses.databaseclasses.MovieDatabase;
import tvclasses.databaseclasses.UsersDatabase;

public class DatabaseActions {
    public void addMovie(MovieInput movie) {
        Movie newMovie = new Movie(movie);
        MovieDatabase.getInstance().addMovie(newMovie);
    }

    public void removeMovie(String movieName) {
        Movie removedMovie = MovieDatabase.getInstance().getMovieByName(movieName);
        MovieDatabase.getInstance().deleteMovie(removedMovie);
        UsersDatabase.getInstance().updateUsers(removedMovie);
    }
}
