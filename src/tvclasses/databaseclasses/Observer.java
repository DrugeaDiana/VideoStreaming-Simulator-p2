package tvclasses.databaseclasses;

import movieclasses.Movie;
import userclasses.User;


/**
 * Observer Design Pattern
 */
public abstract class Observer {
    protected User user;

    public abstract void update(Movie movie);

}
