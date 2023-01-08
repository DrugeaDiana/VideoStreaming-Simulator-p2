package userclasses;

import inputclasses.UserInput;
import movieclasses.Genre;
import movieclasses.Movie;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"subscribed"})
public final class User {
    private static final int FREE_PREMIUM_MOVIES = 15;
    private Credentials credentials;
    private int tokensCount;
    private int numFreePremiumMovies;
    private ArrayList<Movie> purchasedMovies = new ArrayList<>();
    private ArrayList<Movie> watchedMovies = new ArrayList<>();
    private ArrayList<Movie> likedMovies = new ArrayList<>();
    private ArrayList<Movie> ratedMovies = new ArrayList<>();

    private ArrayList<Notification> notifications = new ArrayList<>();

    private final ArrayList<String> subscribed = new ArrayList<>();

    /**
     * Creates a new user based on UserInput data received from input.
     * @param userInput input data
     */
    public User(final UserInput userInput) {
        credentials = new Credentials(userInput.getCredentials());
        tokensCount = 0;
        numFreePremiumMovies = FREE_PREMIUM_MOVIES;
    }

    public User() {
        tokensCount = 0;
        numFreePremiumMovies = FREE_PREMIUM_MOVIES;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public int getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(final ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(final ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(final ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(final ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(final ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    /**
     * @return the top of the genres they like
     */
    public ArrayList<Genre> createTop() {
        ArrayList<Genre> top = new ArrayList<>();
        ArrayList<String> genreNames = new ArrayList<>();
        for (Movie movie : likedMovies) {
            for (String name : movie.getGenres()) {
                if (!genreNames.contains(name)) {
                    genreNames.add(name);
                }
            }
        }
        for (String name : genreNames) {
            Genre auxGenre = new Genre(name);
            top.add(auxGenre);
        }
        for (Movie movie : likedMovies) {
            for (Genre genre : top) {
                if (movie.getGenres().contains(genre.getName())) {
                    genre.setNumber(genre.getNumber() + 1);
                }
            }
        }

        top.sort((o1, o2) -> o2.getNumber() - o1.getNumber());
        return top;
    }

    public ArrayList<String> getSubscribed() {
        return subscribed;
    }

}
