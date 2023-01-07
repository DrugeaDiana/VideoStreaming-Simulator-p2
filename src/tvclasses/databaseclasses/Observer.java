package tvclasses.databaseclasses;

import movieclasses.Movie;
import userclasses.User;

public abstract class Observer {
    protected User user;
    private String genre;
    public abstract void update(Movie movie);

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
