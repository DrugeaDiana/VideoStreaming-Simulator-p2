package tvclasses.databaseclasses;

import movieclasses.Movie;
import userclasses.Credentials;
import userclasses.User;

import java.util.ArrayList;

/**
 * Singleton Pattern
 */
public final class UsersDatabase {
    private static final UsersDatabase INSTANCE = new UsersDatabase();

    private UsersDatabase() { }

    public static UsersDatabase getInstance() {
        return INSTANCE;
    }

    private ArrayList<User> users;

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<User> users) {
        this.users = users;
    }

    /**
     * Adds a new user to the database.
     * @param user new user
     */
    public void addUser(final User user) {
        this.users.add(user);
    }

    /**
     * @param userData input data
     * @return if the user exists or not in the database
     */
    public boolean checkExistingUser(final Credentials userData) {
        for (final User user : users) {
            if (user.getCredentials().getName().equals(userData.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param userData input data
     * @return the user variable that has the name + password combination given at input
     */
    public User getSpecificUser(final Credentials userData) {
        for (final User user : users) {
            if (user.getCredentials().getName().equals(userData.getName())) {
                return user;
            }
        }
        return null;
    }

    /**
     * Deletes the movie given from every user's purchased/watched/liked/rated lists
     * @param movie the movie which triggers the update
     */
    public void updateUsers(final Movie movie) {
        for (final User user : users) {
            if (user.getPurchasedMovies().contains(movie)) {
                if (user.getCredentials().getAccountType().equals("premium")) {
                    user.setNumFreePremiumMovies(user.getNumFreePremiumMovies() + 1);
                } else {
                    user.setTokensCount(user.getTokensCount() + 2);
                }
                user.getPurchasedMovies().remove(movie);
                user.getWatchedMovies().remove(movie);
                user.getLikedMovies().remove(movie);
                user.getRatedMovies().remove(movie);
            }
        }
    }
}
